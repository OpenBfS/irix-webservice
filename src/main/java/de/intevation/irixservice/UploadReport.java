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

import org.iaea._2012.irix.format.ReportType;
import org.iaea._2012.irix.format.ObjectFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@WebService(endpointInterface = "de.intevation.irixservice.UploadReportInterface")
public class UploadReport implements UploadReportInterface {

    private static Logger log = Logger.getLogger(UploadReport.class);

    @Resource
    private WebServiceContext context;

    String outputDir;

    boolean mInitialized;

    public UploadReport() {
        mInitialized = false;
    }

    protected void init() {
        ServletContext sc;
        try {
            sc = (ServletContext) context.getMessageContext().get(
                MessageContext.SERVLET_CONTEXT);
        } catch (IllegalStateException e) {
            System.err.println("Failed to get servlet context.");
            return;
        }

        String file = sc.getInitParameter("log4j-properties");
        String log4jProperties = sc.getRealPath(file);
        PropertyConfigurator.configure(log4jProperties);

        outputDir = sc.getInitParameter("storage-dir");
        log.debug("Using: " + outputDir + " as Storage location.");

        File theDir = new File(outputDir);
        if (!theDir.exists()) {
            log.debug("creating directory: " + outputDir);
            boolean result = false;

            try {
                theDir.mkdir();
            } catch(SecurityException se){
                log.error("Failed to create output directory. All requests to the service will be ignored.");
            }
        }
        mInitialized = true;
    }

    @Override
    public void uploadReport(ReportType report) {
        if (!mInitialized) {
            // Necessary because the servlet context is not accessible in ctor
            init();
        }
        String fileName = report.getIdentification().getReportUUID() + ".xml";
        try {
            PrintWriter writer = new PrintWriter(outputDir + "/" + fileName, "UTF-8");
            JAXBContext jaxbContext = JAXBContext.newInstance(ReportType.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            jaxbMarshaller.marshal(new ObjectFactory().createReport(report), writer);

            writer.close();
        } catch (IOException e) {
            log.error("Failed to write report to file: '" + outputDir + "/" + fileName + "'");
        } catch (JAXBException e) {
            log.error("Failed to handle requested report." + e.toString());
        }

        if (outputDir == null) {
            log.debug("Ignoring request because output-dir is not writable or configured.");
            return;
        }
        return;
    }
}
