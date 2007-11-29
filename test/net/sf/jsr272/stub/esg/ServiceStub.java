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
package net.sf.jsr272.stub.esg;

import java.util.Hashtable;

import javax.microedition.broadcast.esg.Service;
import javax.microedition.broadcast.esg.ServiceGuide;
import javax.microedition.broadcast.esg.ProgramEvent;

// a stub implementation of Service, 
// filled with static ProgramEvent data
public class ServiceStub extends ServiceGuideDataStub implements Service
{
  public ServiceStub(ServiceGuide serviceGuide, Hashtable data)
  {
    super (serviceGuide,data);
  }
  
  // <implements interface="Service">
  
  public ProgramEvent[] getProgramEvents()
  {
    return new ProgramEvent[]
    {
      // new ProgramEventStub();
    };
  }
  
  // </implements>
    
}