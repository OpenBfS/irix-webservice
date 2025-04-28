# Changelog

## Version 3.x

### 3.3.0 *2025-04-28*

 - update schema (liberal doksys)
 - Version 3.3.0 of dokpool-client and IRIXBroker
   - This fixes an error where event lists with one
   inactive and one active event where completely rejected.
   Now the active event is assigned correctly.

### 3.2 *2025-03-27*

 - update schema to reflect RODOS changes
 - corresponding changes in IRIXBroker and dokpool-client (both 3.2.0)

### 3.1 *2025-01-28*

 - jaxws, javax.xml.bind, jaxb to current versions (libs and plugins)
 - remove dependency on log4j (use System.Logger instead)
 - update Dokpool Client to 3.1.0 (XMLRPC->REST)
 - deployment tested with Java 11 and 21 (build version still 11)

### 3.0.0 *2023-10-24* (changes vs. 2.7.1)

 - start of upgrade to Java 11, Java build version: 11
 - rpm-maven-plugin removed

## Version 2.x

### 2.8.0 *2024-12-16*

 - update Dokpool Client to 2.3.0 (XMLRPC->REST)

### 2.7.1 *2022-07-26*

 - update log4j dependency

### 2.7.0 *2021-07-07*

 - Dokpool schema 2.6.0
 - IRIXBroker 2.7
 - update commons-io+junit

### 2.6.0 *2020-07-10*

 - IRIXBroker 2.6


### 2.5.0 *2019-12-18*

 - Dokpool schema 2.5.0
 - IRIXBroker 2.5
 - Dokpool-Client 2.2.0

### 2.2.0 *2019-10-08*

 - rpm maven plugin

### 2.1.2 *2018-12-20*

 - IRIXBroker 2.1.1

### 2.1.0 *2018-12-19*

 - Doksys support
 - added type to example xml

### 2.0.0 *2018-12-17*

 - many breaking changes
 - many more examples
 - first working status for Rodos and Rei support
 - Java 8 and IRIX Broker / Dokpool Client 2.x

## Version 1.x

### 1.4.0 *2017-08-30*

 - irix-schema as submodule
 - pass properties for IRIXBroker
 - move IRIXBroke code to extra repo

### 1.2.0 *2016-02-22*

 - first tagged version in this repo
