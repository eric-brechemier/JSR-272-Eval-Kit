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

import javax.microedition.broadcast.esg.BooleanAttribute;
import javax.microedition.broadcast.esg.DateAttribute;
import javax.microedition.broadcast.esg.MetadataSet;
import javax.microedition.broadcast.esg.NumericAttribute;
import javax.microedition.broadcast.esg.ObjectAttribute;
import javax.microedition.broadcast.esg.StringAttribute;

public interface ServiceGuideData
{
  public boolean equals(Object data);
  public MetadataSet[] getAvailableMetadataSets();
  public boolean getBooleanValue(BooleanAttribute attribute);
  public boolean getBooleanValues(BooleanAttribute attribute);
  public Date getDateValue(DateAttribute attribute);
  public Date[] getDateValues(DateAttribute attribute);
  public double getDoubleValue(NumericAttribute attribute);
  public double[] getDoubleValues(NumericAttribute attribute);
  public long getLongValue(NumericAttribute attribute);
  public long[] getLongValues(NumericAttribute attribute);
  public Object getObjectValue(ObjectAttribute attribute);
  public Object[] getObjectValues(ObjectAttribute attribute);
  public String getStringValue(StringAttribute attribute);
  public String[] getStringValues(StringAttribute attribute);
}