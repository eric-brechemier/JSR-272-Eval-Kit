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
package javax.microedition.broadcast.platform;

public class PlatformProvider
{
  protected long _id;
  protected String _name;
  
  protected PlatformProvider(long id, String name)
  {
    if (name==null)
      throw new IllegalArgumentException("Platform Provider Name must not be null.");
    
    _id = id;
    _name = name;
  }
  
  public long getID()
  {
    return _id;
  }
  
  // <overrides class="Object">
  
  public String toString()
  {
    return _name;
  }
  
  // </overrides>
}