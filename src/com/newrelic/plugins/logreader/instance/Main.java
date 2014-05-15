package com.newrelic.plugins.logreader.instance;

import com.newrelic.metrics.publish.Runner;
import com.newrelic.metrics.publish.configuration.ConfigurationException;

public class Main {	
    public static void main(String[] args) {
    	Runner runner = new Runner();
    	runner.add(new LogReaderAgentFactory());

		try {
	    	runner.setupAndRun(); 				// Never returns
		} catch (ConfigurationException e) {
			e.printStackTrace();
    		System.err.println("Error configuring New Relic Agent");
    		System.exit(1);
		}
    	
    }
}
