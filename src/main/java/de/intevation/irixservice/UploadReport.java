/* Copyright (C) 2015 by Bundesamt fuer Strahlenschutz
 * Software engineering by Intevation GmbH
 *
 * This file is Free Software under the GNU GPL (v>=3)
 * and comes with ABSOLUTELY NO WARRANTY!
 * See LICENSE.txt for details.
 */

package de.intevation.irixservice;

import jakarta.jws.WebService;
import jakarta.annotation.Resource;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;

//still exists in Java 21
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.XMLConstants;

import org.iaea._2012.irix.format.ReportType;
import org.iaea._2012.irix.format.ObjectFactory;
import org.iaea._2012.irix.format.annexes.AnnexesType;
import org.iaea._2012.irix.format.annexes.AnnotationType;

import de.bfs.irix.extensions.dokpool.DokpoolMeta;
import de.bfs.irixbroker.IrixBroker;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Properties;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import jakarta.servlet.ServletContext;

import static java.lang.System.Logger.Level.DEBUG;
import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.WARNING;


import org.w3c.dom.Element;

/**
 * {@link jakarta.jws.WebService} interface implementation for uploading
 * IRIX-XML reports.
 */
@WebService(
        endpointInterface = "de.intevation.irixservice.UploadReportInterface")
public class UploadReport implements UploadReportInterface {

    private static System.Logger log = System.getLogger(UploadReport.class.getName());

    /**
     * Path to the irixSchema xsd file.
     */
    private static final String IRIX_SCHEMA_LOC =
            "/WEB-INF/irix-schema/IRIX.xsd";

    /**
     * Path to the Dokpool extension xsd file.
     */
    private static final String DOKPOOL_SCHEMA_LOC =
            "/WEB-INF/irix-schema/Dokpool-3.xsd";

    /**
     * The element name to identify a DokpoolMeta element.
     */
    private static final String DOKPOOL_ELEMENT_NAME = "DokpoolMeta";

    @Resource
    private WebServiceContext context;

    /**
     * Directory where reports should be stored.
     */
    public String outputDir;

    /**
     * SubDirectory where reports
     * with deliveryError should be stored.
     */
    private static String outputErrorDir = "error";

    /**
     * SubDirectory where reports
     * that have been successfully delivered should be stored.
     */
    private static String outputSaveDir = "save";

    /**
     * Properties for BfS IrixBroker.
     */
    public Properties bfsIrixBrokerProperties;

    /**
     * The IRIX XSD-schema file.
     */
    public File irixSchemaFile;
    /**
     * The Dokpool XSD-schema file.
     */
    public File dokpoolSchemaFile;

    /**
     * Has {@link this.init()} successfully been called?
     */
    public boolean initialized;

    public UploadReport() {
        initialized = false;
    }

    /**
     * Get servlet context and initialize parameters from
     * configuration in web.xml.
     *
     * @throws UploadReportException if the servlet context cannot be obtained.
     */
    protected void init() throws UploadReportException {
        ServletContext sc;
        try {
            sc = (ServletContext) context.getMessageContext().get(
                    MessageContext.SERVLET_CONTEXT);
        } catch (IllegalStateException e) {
            log.log(ERROR, "Failed to get servlet context.");
            throw new UploadReportException(
                    "Failed to get servlet context.", e);
        }

        String file;

        // TODO make configurable - no filesaving if storage-dir is not set
        outputDir = sc.getInitParameter("storage-dir");
        log.log(DEBUG, "Using: " + outputDir + " as Storage location.");

        irixSchemaFile = new File(sc.getRealPath(IRIX_SCHEMA_LOC));
        dokpoolSchemaFile = new File(sc.getRealPath(DOKPOOL_SCHEMA_LOC));

        try {
            file = sc.getInitParameter("irixbroker-properties");
            String bfsIrixBrokerPropertiesFile = sc.getRealPath(file);
            bfsIrixBrokerProperties = new Properties();

            try {
                FileInputStream stream =
                        new FileInputStream(bfsIrixBrokerPropertiesFile);
                bfsIrixBrokerProperties.load(stream);
                stream.close();
            } catch (IOException ioe) {
                log.log(ERROR, "Failed to load bfsIrixBrokerProperties.");
                throw new UploadReportException(
                        "Failed to get servlet context.", ioe);
            }
        } catch (Exception e) {
            log.log(ERROR, "Failed to init() UploadReport: ", e);
            throw new UploadReportException(
                    "Failed to init() UploadReport.", e);
        }

        initialized = true;
    }

    /**
     * Create directory to store IRIX-XML reports, if not yet exists.
     *
     * @throws UploadReportException if the directory cannot be created.
     */
    protected void createOutputDir() throws UploadReportException {
        File theDir = new File(outputDir);
        if (!theDir.exists()) {
            log.log(DEBUG, "creating directory: " + theDir.getAbsolutePath());
            try {
                theDir.mkdir();
                log.log(DEBUG, "Created directory: " + theDir.getAbsolutePath());
            } catch (SecurityException se) {
                log.log(ERROR, "Failed to create output directory.");
                throw new UploadReportException("Service misconfigured.", se);
            }
        } else {
            log.log(DEBUG, "output directory already exists: "
                    + theDir.getAbsolutePath());
        }
    }

    /**
     * Create directory to store IRIX-XML reports
     * with delivery errors, if not yet exists.
     *
     * @throws UploadReportException if the directory cannot be created.
     */
    protected void createOutputErrorDir() throws UploadReportException {
        File theDir = new File(outputDir);
        File theErrorDir = new File(outputDir
                + "/" + outputErrorDir);
        if (theDir.exists()) {
            if (!theErrorDir.exists()) {
                log.log(DEBUG, "creating directory: "
                        + theErrorDir.getAbsolutePath());
                try {
                    theErrorDir.mkdir();
                    log.log(DEBUG, "Created directory: "
                            + theErrorDir.getAbsolutePath());
                } catch (SecurityException se) {
                    log.log(ERROR, "Failed to create outputError directory.");
                    throw new UploadReportException("Service misconfigured."
                            , se);
                }
            } else {
                log.log(DEBUG, "errorOutput directory already exists: "
                        + theErrorDir.getAbsolutePath());
            }
        } else {
            log.log(WARNING, "output directory does not exists: "
                    + theDir.getAbsolutePath());
        }
    }

    /**
     * Create directory to store IRIX-XML reports
     * that have been successfully been delivered, if not yet exists.
     *
     * @throws UploadReportException if the directory cannot be created.
     */

    protected void createOutputSaveDir() throws UploadReportException {
        File theDir = new File(outputDir);
        File theSaveDir = new File(outputDir + "/" + outputSaveDir);
        if (theDir.exists()) {
            if (!theSaveDir.exists()) {
                log.log(DEBUG, "creating directory: "
                        + theSaveDir.getAbsolutePath());
                try {
                    theSaveDir.mkdir();
                    log.log(DEBUG, "Created directory: "
                            + theSaveDir.getAbsolutePath());
                } catch (SecurityException se) {
                    log.log(ERROR, "Failed to create outputError directory.");
                    throw new UploadReportException("Service misconfigured."
                            , se);
                }
            } else {
                log.log(DEBUG, "saveOutput directory already exists: "
                        + theSaveDir.getAbsolutePath());
            }
        } else {
            log.log(WARNING, "output directory does not exists: "
                    + theDir.getAbsolutePath());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void uploadReport(ReportType report) throws UploadReportException {
        if (!initialized) {
            // Necessary because the servlet context is not accessible in ctor
            init();
            createOutputDir();
            createOutputErrorDir();
            createOutputSaveDir();
        }
        validateReport(report);

        String fileName = report.getIdentification().getReportUUID() + ".xml";
        if (outputDir.length() > 0) {
            try {
                PrintWriter writer = new PrintWriter(outputDir + "/" + fileName,
                        "UTF-8");
                JAXBContext jaxbContext = JAXBContext.newInstance(
                        ReportType.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                        true);
                jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                jaxbMarshaller.marshal(new ObjectFactory().createReport(report),
                        writer);

                writer.close();
            } catch (IOException e) {
                log.log(ERROR, "Failed to write report to file: '" + outputDir
                        + "/" + fileName + "'");
                throw new UploadReportException(
                        "Failed to write report to file: '"
                                + outputDir + "/" + fileName + "'", e);
            } catch (JAXBException e) {
                log.log(ERROR, "Failed to handle requested report." + e.toString());
                throw new UploadReportException("Failed to parse the report.",
                        e);
            }
        } else {
            log.log(DEBUG, "Ignoring request because output-dir is not writable"
                    + " or not configured.");
        }

        try {
            deliverReport(report);
            log.log(DEBUG, "Successfully delivered report.");
            log.log(DEBUG, "Moving File to outputSaveDir.");
            try {
                Path outputPath = Paths.get(outputDir + "/" + fileName);
                Path outputSavePath = Paths.get(outputDir + "/"
                        + outputSaveDir + "/" + fileName);
                Files.move(outputPath, outputSavePath);
            } catch (IOException i) {
                log.log(ERROR, "Failed to move File to outputSaveDir: "
                        + i.toString());
            }
        } catch (UploadReportException e) {
            log.log(ERROR, "Failed to deliver report: " + e.toString());
            log.log(ERROR, "Moving File to outputErrorDir.");
            try {
                Path outputPath = Paths.get(outputDir + "/" + fileName);
                Path outputErrorPath = Paths.get(outputDir + "/"
                        + outputErrorDir + "/" + fileName);
                Files.move(outputPath, outputErrorPath);
            } catch (IOException i) {
                log.log(ERROR, "Failed to move File to outputErrorDir: "
                        + i.toString());
            }
        }

        return;
    }

    /**
     * {@inheritDoc}
     */
    public void deliverReport(ReportType report)
            throws UploadReportException {
        if (!initialized) {
            // Necessary because the servlet context is not accessible in ctor
            init();
        }


        try {
            IrixBroker dib = new de.bfs.irixbroker
                    .IrixBroker(bfsIrixBrokerProperties);
            // NOW leaving irix-webservice here and entering irix-broker
            dib.deliverIrixBroker(report);
        } catch (Exception e) {
            throw new UploadReportException("IrixBrokerException: " + e, e);
        }

        return;
    }


    /**
     * Validate element against the dokpool meta data schema if it has an
     * according name.
     *
     * @param element the {@link org.w3c.dom.Element} to be validated.
     * @throws org.xml.sax.SAXException     if the Dokpool schema file cannot be
     *                                      parsed.
     * @throws jakarta.xml.bind.JAXBException if an error was encountered
     *                                      while creating the JAXBContext.
     */
    public void validateMeta(Element element)
            throws SAXException, JAXBException {
        if (!(element.getTagName().endsWith(DOKPOOL_ELEMENT_NAME))) {
            log.log(DEBUG, "Ignoring Annotation element: " + element.getTagName());
            return;
        }
        SchemaFactory schemaFactory = SchemaFactory.newInstance(
                XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(dokpoolSchemaFile);
        JAXBContext jaxbContext = JAXBContext.newInstance(DokpoolMeta.class);

        Unmarshaller u = jaxbContext.createUnmarshaller();
        u.setSchema(schema);

        Object dokMetaObj = u.unmarshal(element.getOwnerDocument());
    }

    /**
     * Validate a report against the IRIX Schema.
     *
     * @param report an object representing the IRIX-XML report.
     * @throws UploadReportException if report validation failed.
     */
    protected void validateReport(ReportType report)
            throws UploadReportException {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(
                    XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(irixSchemaFile);
            JAXBContext jaxbContext = JAXBContext.newInstance(
                    ReportType.class);

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setSchema(schema);
            marshaller.marshal(new ObjectFactory().createReport(report),
                    new DefaultHandler());
            AnnexesType annex = report.getAnnexes();
            if (annex != null && annex.getAnnotation() != null) {
                for (AnnotationType anno : annex.getAnnotation()) {
                    if (anno.getAny() == null) {
                        continue;
                    }
                    for (Element ele : anno.getAny()) {
                        if (ele == null) {
                            continue;
                        }
                        validateMeta(ele);
                    }
                }
            }
        } catch (SAXException | JAXBException e) {
            log.log(DEBUG, "Validation failed: " + e);
            throw new UploadReportException("Failed to validate report: " + e,
                    e);
        }
    }
}
