/* Copyright (C) 2015 by Bundesamt fuer Strahlenschutz
 * Software engineering by Intevation GmbH
 *
 * This file is Free Software under the GNU GPL (v>=3)
 * and comes with ABSOLUTELY NO WARRANTY!
 * See LICENSE.txt for details.
 */

package de.intevation.irixservice;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.iaea._2012.irix.format.ReportType;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface UploadReportInterface {

    @WebMethod
    void uploadReport(ReportType value) throws UploadReportException;
}
