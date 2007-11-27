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

import javax.microedition.broadcast.connection.BroadcastConnection;

public interface BroadcastConnectionListener
{
  public static final String AVAILABLE = "AVAILABLE";
  public static final String INVALIDATED = "INVALIDATED";
  public static final String UPDATED = "UPDATED";
  
  void update(BroadcastConnection connection, String event, Object eventData);
  
}