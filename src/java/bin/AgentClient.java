/*
 * @(#)AgentClient.java
 *
 * Remote Agent 
 *
 * Author: Govind Thirumalai
 */

import java.io.*;
import java.net.*;

public class AgentClient {        
    private static String hostName = null;
    private static int portNum = 0;
    private static String cmdToExecuteRemotely = null;
    private static Socket agentSocket = null;
    private static PrintWriter out = null;
    private static BufferedReader in = null;
    public static boolean debug = false;
    
    public static void debug(String buf) {
	if ( AgentClient.debug )
	    System.out.println(buf); 
    }
    
    public static void main(String[] args) {
	if ( System.getProperty("DEBUG") != null ) 
	    AgentClient.debug = true;
	AgentClient.debug("CLIENT: Begin client execution");        
        //accept only args
        if(args.length==0) {
            System.err.println("Usage: AgentClient -h hostname -p portnum");
	    System.exit(1);
	}
        for(int a=0; a < args.length; a++){
            if(args[a].equals("-h")){
                hostName = args[a+1];
            }
            if(args[a].equals("-p")){
                try {
                    portNum = Integer.parseInt(args[a+1]);
                }catch(Exception e){
                    System.err.println("Invalid port number specified.");
                    e.printStackTrace();
		    System.exit(1);
                }
            }
        }

        cmdToExecuteRemotely = args[args.length-1];
	AgentClient.debug("CLIENT: Variables initialized successfully");        
        connectToServer();
	AgentClient.debug("CLIENT: Connection to server established successfully");
        int retVal = processCmd();
        AgentClient.debug("CLIENT: Processing completed at Server successfully");
	cleanUp();
        AgentClient.debug("CLIENT: End client execution");
	System.exit(retVal);
    }    
    
    public static void connectToServer(){
        try {
            agentSocket = new Socket(hostName, portNum);
            out = new PrintWriter(agentSocket.getOutputStream(), true);
	    out.flush();
            in = new BufferedReader(new InputStreamReader(
							  agentSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + hostName);
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: " + hostName);
            System.exit(-1);
        }        
    }
    
    public static int processCmd(){
        //call after calling connectToServer() only
	int retCode = 1000;
        out.println(cmdToExecuteRemotely);		
        try{
	    String readLine = "";
	    while ( (readLine = in.readLine()) != null ) {
		AgentClient.debug("READLINE: " + readLine);
		int idx = readLine.indexOf(AgentServer.AGENT_RET_CODE);
		if ( idx != -1 ) {
		    retCode = Integer.parseInt(readLine.substring(AgentServer.AGENT_RET_CODE.length()));
		    if(retCode ==0 ) {
			AgentClient.debug("SUCCESS: Command: " + cmdToExecuteRemotely +
					  " executed successfully at Server side.");
		    }
		    else {
			AgentClient.debug("FAILURE: Command: " + cmdToExecuteRemotely +
					  " failed to execute successfully at Server side.");         
		    }
		}
		else
		    System.out.println(readLine);
	    }
	} catch(Exception e){
	    if (AgentClient.debug)
		e.printStackTrace();
	    System.err.println("ERROR: Failed communicating with server. " + 
			       "Err. msg: " + e.getMessage());
	    System.exit(-1);
	}
	return retCode;
    }
    
    public static void cleanUp(){
        //call this after processCmd()
        try{
            out.close();
            in.close();	
            agentSocket.close();
        }catch(Exception e){
	    if (AgentClient.debug)
		e.printStackTrace();
            System.err.println("ERROR: Cleaning up operation failed. " + 
			       "Err. msg: " + e.getMessage());
        }
    }
    
}
