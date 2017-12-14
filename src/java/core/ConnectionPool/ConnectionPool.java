/*
 * @(#)ConnectionPool.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */


package core.ConnectionPool;

import java.sql.*;
import java.util.*;
import java.io.*;


public class ConnectionPool {
  private int checkedOut;
  private Vector<Connection> freeConnections = new Vector<Connection>();
  private String name;
  private String URL;
  private String User;
  private String password;
  private int maxConns;
  private int initConns;
  private int timeOut;
  private LogWriter logWriter;

  public ConnectionPool (String name, String URL, String User,
    String passWord, int maxConns, int initConns, int timeOut, PrintWriter pw, int logLevel)
  {
      this.name = name;
      this.URL = URL;
      this.User = User;
      this.password = passWord;
      this.maxConns = maxConns;
      this.timeOut = timeOut > 0 ? timeOut : 5;

      logWriter = new LogWriter (name, logLevel, pw);
      initPool (initConns);

      logWriter.log ("New pool created", LogWriter.INFO);
      String lf = System.getProperty ("line.separator");
  }
  private void initPool (int initConns)
  {
    for (int i = 0; i < initConns; i++)
    {
      try
      {
        Connection pc = newConnection ();
        freeConnections.addElement (pc);
      }
      catch (SQLException e)
      { }
    }
  }

  public int getNumFreeCons() {
	return freeConnections.size();
  }

  public Connection getConnection () throws SQLException
  {
    try
    {
      return getConnection (timeOut * 1000);
    }
    catch (SQLException e)
    {
      logWriter.log (e, "Exception getting Connection", LogWriter.DEBUG);
      throw e;
    }
  }

  private synchronized Connection getConnection (long timeout) throws SQLException
  {
    // Get a Pooled connection from the cache or a new one.
    // Wait if all are checked out and the max limit has been reached

    long startTime = System.currentTimeMillis ();
    long remaining = timeout;
    Connection conn = null;
    while ((conn = getPooledConnection ()) == null)
    {
      try
      {
        wait (remaining);
      }
      catch (InterruptedException e)
      { }
      remaining = timeout - (System.currentTimeMillis () - startTime);
      if (remaining <= 0)
      {
        // Timeout has expired
        logWriter.log ("Time-out while waiting for connection ", LogWriter.DEBUG);
        throw new SQLException ("getConnection () timed-out");
      }
    }

    // Check if the Connection is still OK

    if (!isConnectionOK (conn))
    {
      // It was bad.  Try again with the remaining timeout
      logWriter.log ("Removed selected bad connection from pool", LogWriter.ERROR);
      return getConnection (remaining);
    }
    checkedOut++;
    return conn;
  }

  private boolean isConnectionOK (Connection conn)
  {
    Statement testStmt = null;
    try
    {
      if (!conn.isClosed ())
      {
        // Try to createStatement to see if its really alive
        testStmt = conn.createStatement ();
        testStmt.close ();
      }
      else
      {
        return false;
      }
    }
    catch (SQLException e)
    {
      if (testStmt != null)
      {
        try
        {
          testStmt.close ();
        }
        catch (SQLException se)
        { }
      }
      logWriter.log (e, "Pooled Connection was not okay", LogWriter.ERROR);
      return false;
    }
    return true;
  }

  private Connection getPooledConnection () throws SQLException
  {
    Connection conn = null;
    if (freeConnections.size () > 0)
    {
      // Pick the first Connection to the Vector
      // to get round robin usage
      conn = freeConnections.firstElement ();
      freeConnections.removeElementAt (0);
    }
    else
    {
      conn = newConnection ();
    }
    return conn;
  }

  private Connection newConnection () throws SQLException
  {
    Connection conn = null;
    Properties props = new Properties();
    props.put("SetBigStringTryClob", "true");
    if (User == null)
    {
      conn = DriverManager.getConnection (URL, props);
    }
    else
    {
      props.put("user", User );
      props.put("password", password);
      conn = DriverManager.getConnection (URL, props);
    }
    logWriter.log ("Opened a new connection", LogWriter.INFO);

    return conn;
  }

  public synchronized void freeConnection (Connection conn)
  {
    // Put the connection at the end of the Vector
    freeConnections.addElement (conn);
    checkedOut--;
    notifyAll ();
    logWriter.log ("Returned connection to Pool", LogWriter.INFO);
  }

  public synchronized void release ()
  {
    Enumeration<Connection> allConnections = freeConnections.elements ();
    while (allConnections.hasMoreElements ())
    {
      Connection conn = allConnections.nextElement ();
      try
      {
        conn.close ();
        logWriter.log ("Closed Connection", LogWriter.INFO);
      }
      catch (SQLException e)
      {
        logWriter.log (e, "Could not close connection ", LogWriter.ERROR);
      }
    }
    freeConnections.removeAllElements ();
  }

}

