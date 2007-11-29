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
import javax.microedition.broadcast.esg.DataUnavailableException;
import javax.microedition.broadcast.esg.DateAttribute;
import javax.microedition.broadcast.esg.MetadataSet;
import javax.microedition.broadcast.esg.NumericAttribute;
import javax.microedition.broadcast.esg.ObjectAttribute;
import javax.microedition.broadcast.esg.StringAttribute;

public interface ServiceGuideData
{
  public boolean equals(Object data);
  public MetadataSet[] getAvailableMetadataSets();
  public boolean getBooleanValue(BooleanAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException;
  // Note: BUG IN THE SPECIFICATION 
  // public boolean getBooleanValues(BooleanAttribute attribute);
  // should read
  public boolean[] getBooleanValues(BooleanAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException;
  public Date getDateValue(DateAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException;
  public Date[] getDateValues(DateAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException;
  public double getDoubleValue(NumericAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException;
  public double[] getDoubleValues(NumericAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException;
  public long getLongValue(NumericAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException;
  public long[] getLongValues(NumericAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException;
  public Object getObjectValue(ObjectAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException;
  public Object[] getObjectValues(ObjectAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException;
  public String getStringValue(StringAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException;
  public String[] getStringValues(StringAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException;
}