/* Copyright (C) 2015 by Bundesamt fuer Strahlenschutz
 * Software engineering by Intevation GmbH
 *
 * This file is Free Software under the GNU GPL (v>=3)
 * and comes with ABSOLUTELY NO WARRANTY!
 * See LICENSE.txt for details.
 */

package de.intevation.irixservice;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.Style;

import org.iaea._2012.irix.format.ReportType;

/**
 * {@link jakarta.jws.WebService} interface for uploading IRIX-XML reports.
 */
@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface UploadReportInterface {

    /**
     * {@link jakarta.jws.WebMethod} for uploading IRIX-XML reports to the
     * Web Service.
     *
     * @param value an object representing the IRIX-XML report.
     * @throws UploadReportException if the upload failed.
     */
    @WebMethod
    void uploadReport(ReportType value) throws UploadReportException;
}
