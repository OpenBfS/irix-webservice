# IRIX-Webservice

A Servlet (jakarta.jws.WebService interface implementation) that handles
IRIXReports and uploads them to specific servers. The actual upload is performed
by IRIXBroker.

further information:

 - [Changelog](Changelog.md)
 - [License](LICENSE)

## Building

To install the webservice simply build it with:

```bash
mvn clean package
```

and install the packaged webapplication from:

```bash
target/irix-webservice.war
```

in the Servlet Container of your choice.

## Testing

For an actual upload to a Dokpool instance, copy the file
`./src/main/webapp/WEB_INF/bfs-irixbroker.properties` to this
directory, adapt it to your instance and then run:

```bash
mvn test
```

Note that tests will not fail even without `bfs-irixbroker.properties`,
but you will see exceptions in the logs.

## Usage

With the default configuration the servlet will
be available under:

```bash
irix-webservice/upload-report
```

The WSDL file can be obtained through:

```bash
irix-webservice/upload-report?wsdl
```

### Test Service via curl

 - Find example IRIX-xml docs in `./examples` (there are some futher example in `./old_examples`
   that do no longer fit to the current Dokpool XML schema).

```bash
curl -X POST -H "Content-Type: text/xml; charset=utf-8" \
    -H 'SOAPAction:"http://irixservice.intevation.de/UploadReportInterface/uploadReportRequest"' \
    --data @png-pdf-test.xml http://localhost/irix-webservice/upload-report
```

 - Because dokpool-client refuses to upload a Report having a UUID already imported, the
   <id:ReportUUID>....</id:ReportUUID> has to be edited in the Report, if already succesfully imported.
 - To transform irix:Report xml-Files (e.g. as produced as output by irix-client) you can use xsltproc and the 
   irixReport2irixwsUploadReport.xslt in ./examples Folder. Use it like this:

```bash
xsltproc -o irixwsReportUpload.xml irixReport2irixwsUploadReport.xslt irixReport.xml
```
