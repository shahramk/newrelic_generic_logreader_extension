Apache Access Log plugin for New Relic (EARLY EVALUATION)
==========================================================
- - -
The Generic Log Reader plugin for New Relic opens a log file with name/value pairs (specified in config/logfile.instance.json), reads NAME:VALUE pairs from the file, and reports the name/value pairs from each line as metrics to New Relic.


##Requirements

*    A New Relic account. If you are not already a New Relic user, you can signup for a free account at http://newrelic.com.
*    Obtain the New Relic Generic Log Reader plugin..
*    A log file containing name/value pairs, separated by a separator character.
*    A configured Java Runtime (JRE) environment Version 1.7.
*    Network access to New Relic


##Additional Plugin Details:

*	The plugin reads the name/value log file once every minute
*	It reports all name/value pairs in the log file. 
*	If you append data to existing log file, the previous set of data will be reported multiple times. So make sure you overwrite the log file for each cycle.


##Installation

Linux example:

*    $ mkdir "/path/to/newrelic-logreader-plugin"
*    $ cd "/path/to/newrelic-logreader-plugin"
*    $ tar xfz newrelic_generic_logreader_extension*.tar.gz

*	This will extract a 'config' directory with 3 properties files, a README.TXT file, the LICENSE terms, and a jar file in the current directory.
*	Update config/newrelic.properties with your license key (obtained from your active new relic account's 'Account Settings' page).
*	Update config/logfile.instance.json with the required information.

###Sample logfile.instance.json file:

	[
	    {
			"name" : "Apache Access Log",
			"host" : "localhost",
			"logfilename" : "REPLACE_THIS_WITH_YOUR_APACHE_ACCESS_LOG_FILE",
			"command" : "FULLY_QUALIFIED_NAME_OF_AN_EXECUTABLE_COMMAND",
			"delimiter" : ":"
			"category" : "REPLACE_WITH_CATEGORY_NAME",
			"unitofmeasure" : "bytes"
	    }
	]

**Note:** Assign a unique meaningful name.
**Note:** The "host" property is not used at this time.
**Note:** "logfilename" field in config/logfile.instance.json must be set to the log file name you would like to use. 
**Note:** "command" is optional property. If used, the plugin executes it for each harvest to generate data in the log file specified by "logfilename" property
**Note:** For delimiter specify the name/value delimiting character (default is ":")
**Note:** For category specify the category name that you'd like the metrics to show up in New Relic
**Note:** For unitofmeasure specify the unit of measure for the set of metrics that are being captured by this instance of the plugin


##Execution

Linux example:

*    `$ java -jar newrelic_generic_logreader_extension*.jar`

----------------------------------------------------------------------------------------------------------------------------
