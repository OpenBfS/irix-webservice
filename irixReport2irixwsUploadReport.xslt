<xsl:stylesheet version="1.0" 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:irix="http://www.iaea.org/2012/IRIX/Format"
xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" >
<xsl:output method="xml" version="1.0" encoding="utf-8" indent="yes"/>
<!--<xsl:strip-space elements="*"/>-->
<xsl:template match="/irix:Report">
<soapenv:Envelope>
    <soapenv:Header/>
    <soapenv:Body>
        <irixws:uploadReport xmlns:irixws="http://irixservice.intevation.de/"
                             xmlns:w3c="http://www.w3.org/2000/09/xmldsig#" version="1.0"
                             xmlns:id="http://www.iaea.org/2012/IRIX/Format/Identification"
                             xmlns:base="http://www.iaea.org/2012/IRIX/Format/Base"
                             xmlns:resp="http://www.iaea.org/2012/IRIX/Format/ResponseActions"
                             xmlns:req="http://www.iaea.org/2012/IRIX/Format/Requests"
                             xmlns:meteo="http://www.iaea.org/2012/IRIX/Format/Meteorology"
                             xmlns:medical="http://www.iaea.org/2012/IRIX/Format/MedicalInformation"
                             xmlns:irix="http://www.iaea.org/2012/IRIX/Format"
                             xmlns:cons="http://www.iaea.org/2012/IRIX/Format/ConsequenceInformation"
                             xmlns:annex="http://www.iaea.org/2012/IRIX/Format/Annexes"
                             xmlns:media="http://www.iaea.org/2012/IRIX/Format/MediaInformation"
                             xmlns:release="http://www.iaea.org/2012/IRIX/Format/ReleaseInformation"
                             xmlns:loc="http://www.iaea.org/2012/IRIX/Format/Locations"
                             xmlns:event="http://www.iaea.org/2012/IRIX/Format/EventInformation"
                             xmlns:meas="http://www.iaea.org/2012/IRIX/Format/Measurements">
            <arg0 version="1.0">
                <xsl:copy-of select="./*"/>
            </arg0>
        </irixws:uploadReport>
    </soapenv:Body>
</soapenv:Envelope>
</xsl:template>
</xsl:stylesheet>
