/*
 * AgentServer.java
 *
 * Remote Execution Server
 *
 * Author: Govind Thirumalai
 */

import java.io.*;
import java.net.*;
import java.util.Properties;

public class AgentServer extends Thread {        
    private static int portNum = 0;
    private static boolean listen = true;
    private ServerSocket serverSocket = null;
    private static String prop_file = "AgentServer.properties";
    public final static String AGENT_RET_CODE = "AGENT_RETURN_CODE ";
    
    public static boolean debug = false;
    
    public static void debug(String buf) {
	if ( AgentServer.debug )
	    System.out.println(buf); 
    }
    
    public static void main(String[] args) {  
	if ( System.getProperty("DEBUG") != null ) 
	    AgentServer.debug = true;
	if(args.length==0 ) {
            System.err.println("Usage: AgentServer -p portnum");
	    System.exit(1);
	}

	for(int a=0; a < args.length; a++){
            if(args[a].equals("-p")){
                try {
                    portNum = Integer.parseInt(args[a+1]);
                } catch(Exception e){
                    System.err.println("Invalid port number specified.");
                    e.printStackTrace();
		    System.exit(1);
                }
            }
        }
	new AgentServer();
    }

    public AgentServer() {
        try {            
            serverSocket = new ServerSocket(portNum);                        
            System.out.println("AgentServer started and listening on port " + 
			       portNum);
        } catch(IOException e){
            System.err.println("Failed to listen on port: " + portNum);
            e.getMessage();
            System.exit(-1);
        }   
	this.start();
    }
         
    public void run() {
	while(listen) {
	    try {
		Socket client = serverSocket.accept();
                AgentServerThread ast = new AgentServerThread(client);            
	    }
	    catch (IOException ioe) {
		System.err.println("Failed to start processing thread");
		ioe.printStackTrace();
		System.exit(-1);
	    }
	}
    }
}
