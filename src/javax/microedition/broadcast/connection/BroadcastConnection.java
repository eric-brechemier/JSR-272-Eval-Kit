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

import java.util.Hashtable;

import javax.microedition.io.Connection;

import javax.microedition.broadcast.connection.BroadcastConnectionListener;

public interface BroadcastConnection extends Connection
{
  public boolean isAvailable();
  public String getMimeType();
  public Hashtable getProperties();
  public void addListener(BroadcastConnectionListener listener);
  public void removeListener(BroadcastConnectionListener listener);
}