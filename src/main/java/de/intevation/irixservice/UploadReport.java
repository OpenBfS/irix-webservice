/* Copyright (C) 2015 by Bundesamt fuer Strahlenschutz
 * Software engineering by Intevation GmbH
 *
 * This file is Free Software under the GNU GPL (v>=3)
 * and comes with ABSOLUTELY NO WARRANTY!
 * See LICENSE.txt for details.
 */

package de.intevation.irixservice;

import javax.jws.WebService;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

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
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.w3c.dom.Element;

/**
 * {@link javax.jws.WebService} interface implementation for uploading
 * IRIX-XML reports.
 *
 */
@WebService(
    endpointInterface = "de.intevation.irixservice.UploadReportInterface")
public class UploadReport implements UploadReportInterface {

    private static Logger log = Logger.getLogger(UploadReport.class);

    /** Path to the irixSchema xsd file. */
    private static final String IRIX_SCHEMA_LOC =
        "/WEB-INF/irix-schema/IRIX.xsd";

    /** Path to the Dokpool extension xsd file. */
    private static final String DOKPOOL_SCHEMA_LOC =
        "/WEB-INF/irix-schema/Dokpool-3.xsd";

    /** The element name to identify a DokpoolMeta element. */
    private static final String DOKPOOL_ELEMENT_NAME = "DokpoolMeta";

    @Resource
    private WebServiceContext context;

    /** Directory where reports should be stored. */
    public String outputDir;

    /** Properties for BfS IrixBroker. */
    public Properties bfsIrixBrokerProperties;

    /** The IRIX XSD-schema file. */
    public File irixSchemaFile;
    /** The Dokpool XSD-schema file. */
    public File dokpoolSchemaFile;

    /** Has {@link init()} successfully been called? */
    public boolean initialized;

    public UploadReport() {
        initialized = false;
    }

    /**
     * Get servlet context and initialize log4j and other parameters from
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
            System.err.println("Failed to get servlet context.");
            throw new UploadReportException(
                "Failed to get servlet context.", e);
        }

        String file = sc.getInitParameter("log4j-properties");
        String log4jProperties = sc.getRealPath(file);
        PropertyConfigurator.configure(log4jProperties);

        // TODO make configurable - no filesaving if storage-dir is not set
        outputDir = sc.getInitParameter("storage-dir");
        log.debug("Using: " + outputDir + " as Storage location.");

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
                return;
            }
        } catch (Exception e) {
            log.debug(e.toString());
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
            log.debug("creating directory: " + outputDir);
            boolean result = false;

            try {
                theDir.mkdir();
            } catch (SecurityException se) {
                log.error("Failed to create output directory.");
                throw new UploadReportException("Service misconfigured.", se);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void uploadReport(ReportType report) throws UploadReportException {
        if (!initialized) {
            // Necessary because the servlet context is not accessible in ctor
            init();
            createOutputDir();
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
                log.error("Failed to write report to file: '" + outputDir
                    + "/" + fileName + "'");
                throw new UploadReportException(
                    "Failed to write report to file: '"
                    + outputDir + "/" + fileName + "'", e);
            } catch (JAXBException e) {
                log.error("Failed to handle requested report." + e.toString());
                throw new UploadReportException("Failed to parse the report.",
                    e);
            }
        } else {
            log.debug("Ignoring request because output-dir is not writable"
                + " or not configured.");
        }

        try {
            deliverReport(report);
        } catch (UploadReportException e) {
            log.error("Failed to deliver report: " + e.toString());
        }

        return;
    }

    /** {@inheritDoc} */
    public void deliverReport(ReportType report)
            throws UploadReportException {
        if (!initialized) {
            // Necessary because the servlet context is not accessible in ctor
            init();
        }



        try {
            IrixBroker dib = new de.bfs.irixbroker
                    .IrixBroker(bfsIrixBrokerProperties);
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
     * @throws org.xml.sax.SAXException if the Dokpool schema file cannot be
     * parsed.
     * @throws javax.xml.bind.JAXBException if an error was encountered
     * while creating the JAXBContext.
     */
    public void validateMeta(Element element)
        throws SAXException, JAXBException {
        if (!(element.getTagName().endsWith(DOKPOOL_ELEMENT_NAME))) {
            log.debug("Ignoring Annotation element: " + element.getTagName());
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
            if (annex.getAnnotation() != null) {
                for (AnnotationType anno: annex.getAnnotation()) {
                    if (anno.getAny() == null) {
                        continue;
                    }
                    for (Element ele: anno.getAny()) {
                        if (ele == null) {
                            continue;
                        }
                        validateMeta(ele);
                    }
                }
            }
        } catch (SAXException | JAXBException e) {
            log.debug("Validation failed: " + e);
            throw new UploadReportException("Failed to validate report: " + e,
                e);
        }
    }
}
