package com.newrelic.plugins.logreader.instance;


import java.util.Map;

import com.newrelic.metrics.publish.Agent;
import com.newrelic.metrics.publish.AgentFactory;
import com.newrelic.metrics.publish.configuration.ConfigurationException;

public class LogReaderAgentFactory extends AgentFactory {

	private static final String AGENT_DEFAULT_HOST = "localhost";		// Default values for Oracle Agent
	private static final String AGENT_DEFAULT_LOG = "stdin";
    private static final String AGENT_DEFAULT_FIELD_DELIMITER = ":";	// default name/value pair delimiter
    private static final String AGENT_DEFAULT_METRIC_CATEGORY = "General";	// default Metric Category

	private static final String AGENT_CONFIG_FILE = "logfile.instance.json";

	public LogReaderAgentFactory() {
		super(AGENT_CONFIG_FILE);
	}
	
	/**	
	 * Configure an agent based on an entry in the oracle json file.
	 * There may be multiple agents per Plugin - one per oracle instance
	 * 
	 */
	@Override
	public Agent createConfiguredAgent(Map<String, Object> properties) throws ConfigurationException {
		String name = (String) properties.get("name");
		String host = (String) properties.get("host");
		String logFileName = (String) properties.get("logfilename");
		String command = (String) properties.get("command");
		String delimiter = (String) properties.get("delimiter");
		String category = (String) properties.get("category");
		String unitOfMeasure = (String) properties.get("unitofmeasure");

		/**
		 * Use pre-defined defaults to simplify configuration
		 */
		if (host == null || "".equals(host)) host = AGENT_DEFAULT_HOST;
		if (logFileName == null || "".equals(logFileName)) logFileName = AGENT_DEFAULT_LOG;
		if (delimiter == null || "".equals(delimiter)) delimiter = AGENT_DEFAULT_FIELD_DELIMITER;
		if (unitOfMeasure == null || "".equals(unitOfMeasure)) unitOfMeasure = AGENT_DEFAULT_METRIC_CATEGORY;
		     
		return new LogReaderAgent(name, host, logFileName, command, delimiter, category, unitOfMeasure);
	}
}
