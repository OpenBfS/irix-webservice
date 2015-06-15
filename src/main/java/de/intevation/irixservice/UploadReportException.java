/* Copyright (C) 2015 by Bundesamt fuer Strahlenschutz
 * Software engineering by Intevation GmbH
 *
 * This file is Free Software under the GNU GPL (v>=3)
 * and comes with ABSOLUTELY NO WARRANTY!
 * See LICENSE.txt for details.
 */

package de.intevation.irixservice;

/**
 * Exception signaling any failure to upload an IRIX-XML report to the
 * associated Web Service.
 *
 */
public class UploadReportException extends Exception {

    /**
     * @param message the error message.
     * @param cause the {@link java.lang.Throwable}, which is the cause of the
     * exception.
     */
    public UploadReportException(String message, Throwable cause) {
        super(message, cause);
    }

}
