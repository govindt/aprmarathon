/*
 * AgentServerThread.java 
 *
 *
 * Author: Govind Thirumalai
 */

import java.net.*;
import java.io.*;
import java.util.*;

public class AgentServerThread extends Thread{
    private Socket socket = null;
    private PrintWriter out;
    private InputStream in;
    private final String FRAMEWORK_DIRNAME = "qa";
    private final String FRAMEWORK_TMP_DIRNAME = "tmp";
    private final String FRAMEWORK_BIN_DIRNAME = "bin";
    private boolean isUnix = true;
    private String unixSep = "/";
    private String INTERPRETER = "C:/cygwin/bin/sh";
    private String CYGWIN_SHELL="C:\\cygwin\\bin\\sh";
    String osName;
    
    public AgentServerThread(Socket socket) {
        super("AgentServerThread");
	osName = System.getProperty("os.name");
        this.socket = socket;
	AgentServer.debug("OS Name = " + osName );
	if ( osName.startsWith("Windows") )
	    isUnix = false;
	else
	    INTERPRETER="/bin/sh";
	try {
	    out = new PrintWriter(socket.getOutputStream(), true);
	    in = socket.getInputStream();
	} catch (Exception e) {
	    try { 
		this.socket.close();
	    } catch (Exception e1) {
		System.out.println(e1.getMessage());
	    }
	    return;
	}
	this.start();
    }
    
    public void run() {
	try {
	    String inputLine=null;	    
	    int returnValue = -1;
	    FileOutputStream local_out; // declare a file output object
	    PrintStream p; // declare a print stream object
	    // Connect print stream to the output stream
	    
	    try {
		Calendar cal =  Calendar.getInstance();
		String sep = System.getProperty("file.separator");
		String time = "" + cal.getTimeInMillis();
		String fileName = System.getProperty("java.io.tmpdir") + sep + time;
		AgentServer.debug("Will execute " + fileName);
		// Create a new file output stream
		local_out = new FileOutputStream(fileName);
		p = new PrintStream( local_out );
		p.println("#!" + INTERPRETER);
		p.println("");
		byte[] bytes = new byte[1024];
		int read = 0;			
		AgentServer.debug("Before Read");
		if ((read = in.read(bytes)) != -1) {
		    p.println(new String(bytes, 0, read));
		}
		p.println("exit $?");
		p.flush();
		p.close();
		
		/*
		 * Following code is needed to remove control M's when client is
		 * windows and server is solaris
		 */
		
		if ( isUnix ) {
		    Runtime rTime = Runtime.getRuntime();
		    String dosCmd = "dos2unix";
		    if ( osName.equals("HP-UX") )
			dosCmd = "dos2ux";
		    Process dos = rTime.exec(dosCmd + " " + fileName + " " + fileName);
		    dos.waitFor();
		}
		returnValue = process(fileName, out);
		File file = new File(fileName);
		if ( !AgentServer.debug )
		    file.delete();
	    } catch (Exception e) {
		System.err.println("Error in creating file");
	    }
	    
	    out.close();
	    in.close();
	    socket.close();
	} catch (IOException e) {
	    System.err.println("ERROR: An error occurred communicating with " +
			       "client. Error message: " + e.getMessage());
	    //            e.printStackTrace();
	}
    }
    
    private int process(String inputLine, PrintWriter socketOut){        
        AgentServer.debug("INFO: Server began processing command: " + inputLine);
        int retVal = -1;
        Runtime runTime = Runtime.getRuntime();
        try{            
	    Process process = null;
	    if ( ! isUnix ) {
		AgentServer.debug("INFO: Executing cmd /c " + CYGWIN_SHELL + " " + inputLine);
		process = runTime.exec(new String[] { "cmd.exe", "/c", CYGWIN_SHELL, inputLine});
	    } else {
		AgentServer.debug("INFO: Executing " + INTERPRETER + " " + inputLine);
		process = runTime.exec(INTERPRETER + " " + inputLine);
	    }
	    OutputGatherer in = new OutputGatherer(process.getInputStream(), socketOut);
	    OutputGatherer err = new OutputGatherer(process.getErrorStream(), socketOut);
	    in.start();
	    err.start();
	    retVal = process.waitFor();  
	    in.join();
	    err.join();
	    socketOut.println(AgentServer.AGENT_RET_CODE + retVal);	
	    out.flush();
        } catch(Exception e){
            e.printStackTrace();
            System.err.println("ERROR: Failed during execution of command. " +
			       "Error msg: " + e.getMessage());
            return retVal;
        }        
        AgentServer.debug("INFO: Server completed processing command: " + inputLine);
        return retVal;
    }
}
