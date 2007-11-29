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

import java.util.Date;
import java.util.Hashtable;

import javax.microedition.broadcast.esg.Attribute;
import javax.microedition.broadcast.esg.BooleanAttribute;
import javax.microedition.broadcast.esg.DataUnavailableException;
import javax.microedition.broadcast.esg.DateAttribute;
import javax.microedition.broadcast.esg.MetadataSet;
import javax.microedition.broadcast.esg.NumericAttribute;
import javax.microedition.broadcast.esg.ObjectAttribute;
import javax.microedition.broadcast.esg.ServiceGuide;
import javax.microedition.broadcast.esg.ServiceGuideData;
import javax.microedition.broadcast.esg.StringAttribute;

// a stub implementation of Service, 
// filled with static ProgramEvent data
public class ServiceGuideDataStub implements ServiceGuideData
{
  protected ServiceGuide _serviceGuide;
  protected Hashtable _data;
  
  public ServiceGuideDataStub(ServiceGuide serviceGuide, Hashtable data)
  {
    _serviceGuide = serviceGuide;
    _data = data;
  }
  
  public boolean isDeleted()
  {
    return (_data==null);
  }
  
  // deletes inner data and returns empty Hashtable for recycle pool (if any)
  public Hashtable delete()
  {
    Hashtable forRecycling = _data;
    _data = null;
    return forRecycling;
  }
  
  public void setBooleanValue(BooleanAttribute attribute, boolean value)
    throws NullPointerException, IllegalArgumentException
  {
    setBooleanValues(attribute, new boolean[]{value});
  }
  
  public void setBooleanValues(BooleanAttribute attribute, boolean[] value)
    throws NullPointerException, IllegalArgumentException
  {
    _setAttributeValues(attribute,value);
  }
  
  public void setDateValue(DateAttribute attribute, Date value)
    throws NullPointerException, IllegalArgumentException
  {
    setDateValues(attribute,new Date[]{value});
  }
  
  public void setDateValues(DateAttribute attribute, Date[] value)
    throws NullPointerException, IllegalArgumentException
  {
    _setAttributeValues(attribute, value);
  }
  
  public void setDoubleValue(NumericAttribute attribute, double value)
    throws NullPointerException, IllegalArgumentException
  {
    _setAttributeValues(attribute,new long[]{Double.doubleToLongBits(value)});
  }
  
  public void setDoubleValues(NumericAttribute attribute, double[] value)
    throws NullPointerException, IllegalArgumentException
  {
    long[] longValue = new long[value.length];
    
    for (int i=0; i<longValue.length; i++)
    {
      longValue[i]=Double.doubleToLongBits(value[i]);
    }
    _setAttributeValues(attribute, longValue);
  }
  
  public void setLongValue(NumericAttribute attribute, long value)
    throws NullPointerException, IllegalArgumentException
  {
    setLongValues(attribute, new long[]{value});
  }
  
  public void setLongValues(NumericAttribute attribute, long[] value)
    throws NullPointerException, IllegalArgumentException
  {
    _setAttributeValues(attribute, value);
  }
  
  public void setObjectValue(ObjectAttribute attribute, Object value)
    throws NullPointerException, IllegalArgumentException
  {
    setObjectValues(attribute, new Object[]{value});
  }
  
  public void setObjectValues(ObjectAttribute attribute, Object[] value)
    throws NullPointerException, IllegalArgumentException
  {
    _setAttributeValues(attribute,value);
  }
  
  public void setStringValue(StringAttribute attribute, String value)
    throws NullPointerException, IllegalArgumentException
  {
    setStringValues(attribute, new String[]{value});
  }
  
  public void setStringValues(StringAttribute attribute, String[] value)
    throws NullPointerException, IllegalArgumentException
  {
    _setAttributeValues(attribute, value);
  }
  
  
  // <implements interface="ServiceGuideData">
  
  public boolean equals(Object data)
  {
    return super.equals(data);
  }
  
  public MetadataSet[] getAvailableMetadataSets()
  {
    if (_serviceGuide==null)
      return new MetadataSet[0];
    
    return _serviceGuide.getSupportedMetadataSets();
  }
  
  public boolean getBooleanValue(BooleanAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException
  {
    boolean[] data = getBooleanValues(attribute);
    return data[0];
  }
  
  public boolean[] getBooleanValues(BooleanAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException
  {
    boolean[] data = (boolean[])_getAttributeValues(attribute);
    return data;
  }
  
  public Date getDateValue(DateAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException
  {
    Date[] data = getDateValues(attribute);
    return data[0];
  }
  
  public Date[] getDateValues(DateAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException
  {
    Date[] data = (Date[])_getAttributeValues(attribute);
    return data;
  }
  
  public double getDoubleValue(NumericAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException
  {
    long longData = getLongValue(attribute);
    double doubleData = Double.longBitsToDouble(longData);
    return doubleData;
  }
  
  public double[] getDoubleValues(NumericAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException
  {
    long[] longData = getLongValues(attribute);
    double[] doubleData = new double[longData.length];
    
    for (int i=0; i<doubleData.length; i++)
    {
      doubleData[i]=Double.longBitsToDouble(longData[i]) ;
    }
    return doubleData;
  }
  
  public long getLongValue(NumericAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException
  {
    long[] data = getLongValues(attribute);
    return data[0];
  }
  
  public long[] getLongValues(NumericAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException
  {
    long[] data = (long[])_getAttributeValues(attribute);
    return data;
  }
  
  public Object getObjectValue(ObjectAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException
  {
    Object[] data = getObjectValues(attribute);
    return data[0];
  }
  
  public Object[] getObjectValues(ObjectAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException
  {
    Object[] data = (Object[])_getAttributeValues(attribute);
    return data;
  }
  
  public String getStringValue(StringAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException
  {
    String[] data = getStringValues(attribute);
    return data[0];
  }
  
  public String[] getStringValues(StringAttribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException
  {
    String[] data = (String[])_getAttributeValues(attribute);
    return data;
  }
  
  // </implements>
  
  // ================================================
  // Helper Methods
  // ================================================
  
  protected void _checkAttributeAndStateValid(Attribute attribute)
    throws NullPointerException, IllegalArgumentException
  {
    if (attribute==null)
      throw new NullPointerException("Cannot get value of null attribute.");
    
    if ( !_isAttributeValid(attribute) )
      throw new IllegalArgumentException("Attribute "+attribute+" is not valid.");
    
    // Note: Why IllegalArgumentException?
    if ( isDeleted() )
      throw new IllegalArgumentException("This data has been deleted.");
    
  }
  
  protected void _checkAttributeValidAndDataAvailable(Attribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException
  {
    _checkAttributeAndStateValid(attribute);
    
    if ( !_isDataAvailable(attribute) )
      throw new DataUnavailableException("No data available for attribute: "+attribute);
    
  }
  
  protected boolean _isDataAvailable(Attribute attribute)
  {
    return _data.containsKey(attribute);
  }
  
  protected void _checkAttributeValidAndRemovePreviousValue(Attribute attribute)
    throws NullPointerException, IllegalArgumentException
  {
    _checkAttributeAndStateValid(attribute);
    
    if ( _data.containsKey(attribute) )
      _data.remove(attribute);
    
  }
  
  // can be overriden by the different types of ServiceGuideData
  // to match the list of attributes that can be applied to
  // each kind of data item (ProgramEvent, Service, ...)
  protected boolean _isAttributeValid(Attribute attribute)
  {
    return true;
  }
  
  protected void _setAttributeValues(Attribute attribute, Object value)
    throws NullPointerException, IllegalArgumentException
  {
    _checkAttributeValidAndRemovePreviousValue(attribute);
    _data.put(attribute,value);
  }
  
  protected Object _getAttributeValues(Attribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException
  {
    _checkAttributeValidAndDataAvailable(attribute);
    return _data.get(attribute);
  }
  
  
}