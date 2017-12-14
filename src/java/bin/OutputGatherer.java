import java.io.*;
import java.net.*;

public class OutputGatherer extends Thread {
    private InputStream m_stream = null;
    private PrintWriter out = null;
    
    public OutputGatherer(InputStream stream, PrintWriter out) {
	this.m_stream = stream;
	this.out = out;
	setDaemon(true);
    }
    
    public void run() {
	byte[] bytes = new byte[1024];
	
	StringBuffer buffer = new StringBuffer();
	
	try {
	    int read = 0;
	    while ((read = m_stream.read(bytes)) != -1) {
		buffer.append(new String(bytes, 0, read));
		if ( AgentServer.debug )
		    System.out.println(buffer);
		out.print(buffer);
		out.flush();
		buffer.delete(0, read);
	    }
	    m_stream.close();
	}
	catch (IOException e) {
	    System.err.println(e.getMessage());
	}
    }
}
