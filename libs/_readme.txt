android-support-v4.jar
	Provided by Google. Adds various features to 
	Android 2.2 that were introduced in later versions.

commons-net-3.1.jar
    https://commons.apache.org/net/
    License is Apache Open source
    	http://www.apache.org/licenses/
    Provides support for various network protocols such as:
        ftp, smtp, telnet, finger, wohis, echo, ntp, ...

acra-4.5.0-20121231.062415-1
    https://github.com/ACRA/acra 
    License is Apache Open source
    	http://www.apache.org/licenses/
    Provides a framework for reporting crashes, stack trace, etc.
	
gdata-client 1.0 (multiple)
gdata-docs-3.0 (multiple)
gdata-core-1.0
gdata-media-1.0
gdata-base-1.0
gdata-spreadsheet 3.0 (multiple)
guava-11.0.2
	https://code.google.com/p/gdata-java-client/
	License is Apache 2.0
		http://www.apache.org/licenses/LICENSE-2.0
	Provides a framework for accessing Google docs, getting a list of documents, etc.
	as well as doing queries on spreadsheets without additional libraries.

mail.jar
	License is Oracle Binary License Agreement
		http://download.oracle.com/otn-pub/java/licenses/OTN_JavaEE_Legacy_Binary-Code-License_30Jan2012.txt?AuthParam=1363964711_f0a1ca774273fbe4cf490fc44545f536
	Dependency for the gdata libraries. This library historically has not worked in Android
	for the purpose it was designed for, however the gdata libnry doesn't seem to use these
	non-functioning parts.
	
activation.jar
	License is Oracle Binary License Agreement
		http://www.oracle.com/technetwork/java/javase/downloads/java-se-archive-license-1382604.html
	Dependency for the gdata libraries, primarily for data objects.