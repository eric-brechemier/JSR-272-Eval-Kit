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

import java.util.Date;

import javax.microedition.broadcast.esg.Attribute;

public class DateAttribute extends Attribute
{
  public static final Date UNKNOWN_VALUE = null;
  
  public DateAttribute(String attribute) 
    throws NullPointerException
  {
    super(attribute);
  }
}