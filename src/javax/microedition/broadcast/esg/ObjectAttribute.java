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
package javax.microedition.broadcast.esg;

import javax.microedition.broadcast.esg.Attribute;

public class ObjectAttribute extends Attribute
{
  public static final Object UNKNOWN_VALUE = null;
  
  public ObjectAttribute(String attribute)
    throws NullPointerException
  {
    super(attribute);
  }
}