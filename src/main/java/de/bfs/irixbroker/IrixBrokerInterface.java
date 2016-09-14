/* Copyright (C) 2015 by Bundesamt fuer Strahlenschutz
 * Software engineering by Intevation GmbH
 *
 * This file is Free Software under the GNU GPL (v>=3)
 * and comes with ABSOLUTELY NO WARRANTY!
 * See LICENSE.txt for details.
 */

package de.bfs.irixbroker;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.iaea._2012.irix.format.ReportType;

/**
 * {@link WebService} interface for delivering IRIX-XML reports.
 */
@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface IrixBrokerInterface {

    /**
     * {@link WebMethod} for delivering IRIX-XML reports to a
     * Web Service.
     *
     * @param value an object representing the IRIX-XML report.
     * @throws IrixBrokerException if the upload failed.
     */
    @WebMethod
    void deliverIrixBroker(ReportType value) throws IrixBrokerException;
}