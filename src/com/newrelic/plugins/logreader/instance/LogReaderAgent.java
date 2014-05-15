package com.newrelic.plugins.logreader.instance;

import com.newrelic.metrics.publish.Agent;
import com.newrelic.metrics.publish.binding.Context;

import java.util.logging.Logger;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;




public class LogReaderAgent extends Agent {
    
	private static final String GUID = "com.newrelic.plugins.logreader.instance";
	private static final String version = "0.0.2-beta";
 
    private static final String SEPARATOR = "/";


    private String name;												// Agent Name
	private String host;												// Oracle Connection parameters
	private String logFileName;
	private String command;
	private String delimiter;
	private String category;
	private String unitOfMeasure;

  	static Logger logger;												// Local convenience variable
    long harvestCount = 0;
    Map<String, String> metricsData = new HashMap<String, String>();

    Object lock = new Object();
    
    
    
 	public LogReaderAgent(String name, String host, String logFileName, String command, 
 			String delimiter, String category, String unitOfMeasure) {
	   	super(GUID, version);

	   	this.name = name;												// Set local attributes for new class object
	   	this.host = host;
	   	this.logFileName = logFileName;
        this.command = command;
        this.delimiter = delimiter;										// name/value pair delimiter
        this.category = category;
        this.unitOfMeasure = unitOfMeasure;

	   	logger = Context.getLogger();				    				// Set logging to current Context
	}
	
 	
    @Override
	public void pollCycle() {
	 	logger.fine("Gathering Name/Value Metrics. " + getAgentInfo());
	 	
	 	//report metrics gathered by Tail to new relic
        logger.info("harvest #: " + ++harvestCount);
        if (command != null && !command.equals("")) generateMetrics();  
        metricsData = gatherMetrics();
        deleteFile(logFileName);
		reportMetrics(metricsData);
		metricsData.clear();
	}
    
    public void generateMetrics() {
    	try {  
	        Process p = Runtime.getRuntime().exec(command);
    		p.waitFor();
    		//System.out.println("Exit Code: " + p.exitValue());
	    }  catch(Exception e) {  
	        System.err.println("Failed to execute \"" + command + "\"");  
	        e.printStackTrace();  
	    } 
    }
    
    
    public Map<String, String> gatherMetrics() {
    	
    	Map<String, String> m = new HashMap<String, String>();
    	try {
			Scanner s = new Scanner(new File(logFileName));

       		while (s.hasNextLine()){
       			String[] tokens = s.nextLine().split(delimiter);
       			m.put(tokens[0], tokens[1]);
	     		//System.out.println("Metric Name: " + tokens[0] + "   --- Value: " + tokens[1]);
        	}
        	s.close();
    	} catch (FileNotFoundException e) {
            System.err.println("Unable to find file: " + logFileName);
        }  
    	return m;
    }
    
    
    public void reportMetrics(Map<String, String> metrics) {
    	
		for (String key : metrics.keySet()) {
			long value = java.lang.Long.parseLong(metrics.get(key));
            reportMetric(category + SEPARATOR + key, unitOfMeasure, value);
            logger.fine("Metric: " + category + SEPARATOR + key + "   " + unitOfMeasure + "=" + value);      
			//System.out.println("Category = " + category + " -- Key = " + key + " -- Value = " + value);
            
//            System.out.print(".");
		}
//        System.out.println("\n--- end ---------------------------------------------------------");
    }
     
    public void deleteFile(String logFileName) {	
    	try{
    		File file = new File(logFileName);
    		if(!file.delete()){
    			System.out.println("Delete operation is failed.");
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

	@Override
	public String getComponentHumanLabel() {
		return name;
	}
    
	private String getAgentInfo() {
		return "Agent Name: " + name + ". Agent Version: " + version;
	}
}
