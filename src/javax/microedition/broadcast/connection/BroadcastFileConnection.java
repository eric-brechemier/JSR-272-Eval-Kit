/*===================================================================
  JSR 272 Specification is
  Copyright 2007 Motorola Inc. and Nokia Corporation. 
  All Rights Reserved.
=====================================================================
  This source code is 
  Copyright (c) 2007 Eric Bréchemier <jsr272@eric.brechemier.name>
  Licensed under BSD License and/or MIT License.
  See: http://creativecommons.org/licenses/BSD/
===================================================================*/
package javax.microedition.broadcast.connection;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.util.Enumeration;

import javax.microedition.io.InputConnection;

import javax.microedition.broadcast.connection.BroadcastConnection;

public interface BroadcastFileConnection extends BroadcastConnection, InputConnection
{
  
  public String getName();
  public String getPath();
  public String getURL();
  
  public long lastModified()
    throws IOException;
  
  public long fileSize()
    throws IOException;
  public InputStream openInputStream()
    throws IOException;
  public DataInputStream openDataInputStream()
    throws IOException;
  
  public boolean isDirectory()
    throws IOException;
  
  public Enumeration list()
    throws IOException;
  
  public Enumeration list(String filter)
    throws IOException;
  
  public long directorySize(boolean includeSubDirs)
    throws IOException;
  
}