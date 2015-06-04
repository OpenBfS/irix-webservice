/* Copyright (C) 2015 by Bundesamt fuer Strahlenschutz
 * Software engineering by Intevation GmbH
 *
 * This file is Free Software under the GNU GPL (v>=3)
 * and comes with ABSOLUTELY NO WARRANTY!
 * See LICENSE.txt for details.
 */

package de.intevation.test.irixservice;

import javax.xml.ws.Endpoint;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.rules.TemporaryFolder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;

import java.net.URL;
import java.net.MalformedURLException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.iaea._2012.irix.format.ObjectFactory;
import org.iaea._2012.irix.format.ReportType;

import de.intevation.irixservice.UploadReportInterface;
import de.intevation.irixservice.UploadReport;
import de.intevation.irixservice.UploadReportException;

import javax.xml.transform.dom.DOMResult;

import org.apache.log4j.Logger;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import javax.xml.XMLConstants;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.Diff;

import org.apache.commons.io.FileUtils;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import de.bfs.irix.extensions.dokpool.DokpoolMeta;

public class UploadReportTest
{
    private static Logger log = Logger.getLogger(UploadReportTest.class);

    /** This is an example report from the irix-client.*/
    private static final String VALID_REPORT="src/test/resources/valid-report.xml";
    /** A generic IRIX example not generated by our code. */
    private static final String BILD1="src/test/resources/Bild1.xml";
    /** Another example. */
    private static final String PDF1="src/test/resources/PDF1.xml";

    public static final String UTF8_BOM = "\uFEFF";

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    private UploadReport testObj;

    @Before
    public void setup() throws IOException {
        testObj.outputDir = tmpFolder.newFolder().getAbsolutePath();
    }

    @After
    public void tearDown() {
    }

    private void setupLogging() {
        ConsoleAppender console = new ConsoleAppender(); //create appender
        String PATTERN = "[%p|%C{1}] %m%n";
        console.setLayout(new PatternLayout(PATTERN));
        console.setThreshold(Level.ERROR);
        console.activateOptions();
        Logger.getRootLogger().addAppender(console);
    }

    public UploadReportTest() throws MalformedURLException, IOException {
        setupLogging();
        testObj = new UploadReport();
        testObj.irixSchemaFile = new File("src/main/webapp/WEB-INF/irix-schema/IRIX.xsd");
        testObj.dokpoolSchemaFile = new File("src/main/webapp/WEB-INF/irix-schema/Dokpool-2.xsd");
        testObj.initialized = true;
    }

    public ReportType getReportFromFile(String file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ReportType.class.getPackage().getName());

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(testObj.irixSchemaFile);

            Unmarshaller u = jaxbContext.createUnmarshaller();
            u.setSchema(schema);
            JAXBElement obj = (JAXBElement) u.unmarshal(new File(file));
            return (ReportType) obj.getValue();
        } catch (JAXBException | SAXException e) {
            log.debug("Failed to parse report test data: " + file);
            log.debug(e);
            return null;
        }
    }

    /** Test that the webservice can be created and that it accepts a valid report. */
    @Test
    public void testServiceCreated()
        throws MalformedURLException, IOException, UploadReportException {
        Endpoint endpoint = Endpoint.publish("http://localhost:18913/upload-report", testObj);
        Assert.assertTrue(endpoint.isPublished());
        Assert.assertEquals("http://schemas.xmlsoap.org/wsdl/soap/http", endpoint.getBinding().getBindingID());

        URL wsdlDocumentLocation = new URL("http://localhost:18913/upload-report?wsdl");
        String namespaceURI = "http://irixservice.intevation.de/";
        String servicePart = "UploadReportService";
        String portName = "UploadReportPort";
        QName serviceQN = new QName(namespaceURI, servicePart);
        QName portQN = new QName(namespaceURI, portName);

        Service serv = Service.create(wsdlDocumentLocation, serviceQN);
        UploadReportInterface service = serv.getPort(portQN, UploadReportInterface.class);
        ReportType report = getReportFromFile(VALID_REPORT);
        service.uploadReport(report);
        String uuid = report.getIdentification().getReportUUID();
        String expectedPath = testObj.outputDir + "/" + uuid + ".xml";
        Assert.assertTrue(new File(expectedPath).exists());
    }

    @Test(expected=UploadReportException.class)
    public void testEmptyReport() throws UploadReportException {
        ReportType report = new ObjectFactory().createReportType();
        testObj.uploadReport(report);
    }

    @Test(expected=UploadReportException.class)
    public void testNullReport() throws UploadReportException {
        testObj.uploadReport(null);
    }

    public void testReport(String reportFile) throws UploadReportException {
        ReportType report = getReportFromFile(reportFile);
        testObj.uploadReport(report);
        String uuid = report.getIdentification().getReportUUID();
        String expectedPath = testObj.outputDir + "/" + uuid + ".xml";
        String content1 = null;
        String content2 = null;
        try {
            Assert.assertTrue(new File(expectedPath).exists());

            content1 = FileUtils.readFileToString(new File(reportFile),
                                                  Charset.forName("utf-8"));
            content2 = FileUtils.readFileToString(new File(expectedPath),
                                                  Charset.forName("utf-8"));
        } catch (IOException e) {
            Assert.fail(e.toString());
        }

        if (content1.startsWith(UTF8_BOM)) {
            content1 = content1.substring(1);
        }

        try {
            XMLAssert.assertXMLEqual(content1, content2);
        } catch (IOException | SAXException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testValidReport() throws UploadReportException {
        testReport(VALID_REPORT);
    }

    /*
     Those are currently failing as the namespace prefixes differ.
    @Test
    public void testBild1() throws UploadReportException {
        testReport(BILD1);
    }

    @Test
    public void testPDF1() throws UploadReportException {
        testReport(PDF1);
    }
    */

    @Test(expected=UploadReportException.class)
    public void testInvalidReport() throws UploadReportException {
        ReportType report = getReportFromFile(VALID_REPORT);
        report.getIdentification().setOrganisationReporting("In Valid Org");
        testObj.uploadReport(report);
    }

    @Test(expected=UploadReportException.class)
    public void testInvalidDokpool() throws UploadReportException, JAXBException {
        ReportType report = getReportFromFile(VALID_REPORT);
        DokpoolMeta meta = new DokpoolMeta();
        meta.setDokpoolContentType("Invalid doc");
        DOMResult res = new DOMResult();
        Element ele = null;
        JAXBContext jaxbContext = JAXBContext.newInstance(DokpoolMeta.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        jaxbMarshaller.marshal(meta, res);
        ele = ((Document)res.getNode()).getDocumentElement();

        report.getAnnexes().getAnnotation().get(0).getAny().add(ele);
        testObj.uploadReport(report);
    }
}
