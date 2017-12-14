/*
 * @(#)LogWriter.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */



package core.ConnectionPool;

import java.util.*;
import java.io.*;

public class LogWriter {

  public static final int NONE = 0;
  public static final int ERROR = 1;
  public static final int INFO = 2;
  public static final int DEBUG = 3;

  private String owner;
  private int logLevel;
  private PrintWriter pw;

  public LogWriter (String owner, int logLevel, PrintWriter pw)
  {
    this.owner = owner;
    this.logLevel = logLevel;
    this.pw = pw;
  }
  public LogWriter (String owner, int logLevel)
  {
    this.owner = owner;
    this.logLevel = logLevel;
  }
  public int getLogLevel ()
  {
    return logLevel;
  }
  public void log (String msg, int severityLevel)
  {
    if (severityLevel <= logLevel)
    {
      // Write the message to the current PrintWriter
      this.pw.print (msg);
    }
  }
  public void log (Throwable t, String msg, int severityLevel)
  {
    if (severityLevel <= logLevel)
    {
      // Write the message to the current PrintWriter
      // Log the Stack Trace of the throwable also
      this.pw.print (msg);
      t.printStackTrace (this.pw);
    }
  }
  public void setLogLevel (int logLevel)
  {
    this.logLevel = logLevel;
  }
  public void setPrintWriter (PrintWriter pw)
  {
    this.pw = pw;
  }
}

