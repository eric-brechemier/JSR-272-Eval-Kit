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
import java.util.Enumeration;
import java.util.Hashtable;

import javax.microedition.broadcast.UnsupportedOperationException;

import javax.microedition.broadcast.esg.Attribute;
import javax.microedition.broadcast.esg.MetadataSet;
import javax.microedition.broadcast.esg.ProgramEvent;
import javax.microedition.broadcast.esg.Query;
import javax.microedition.broadcast.esg.QueryException;
import javax.microedition.broadcast.esg.Service;
import javax.microedition.broadcast.esg.ServiceGuideData;
import javax.microedition.broadcast.esg.ServiceGuideListener;

import javax.microedition.broadcast.platform.PlatformProvider;
import javax.microedition.broadcast.platform.PlatformProviderSelector;

public class ServiceGuide
{
  // ===========================================================
  // Methods defined by JSR 272 Specification
  // ===========================================================
  
  public static ServiceGuide getDefaultServiceGuide()
    throws IllegalStateException
  {
    return _getDefaultServiceGuideSingleton();
  }
  
  public static ServiceGuide[] getAllServiceGuides()
    throws IllegalStateException
  {
    ServiceGuide defaultServiceGuideSingleton = _getDefaultServiceGuideSingleton();
    return defaultServiceGuideSingleton._getAllServiceGuides();
  }
  
  public String getServiceProvider()
    throws IllegalStateException
  {
    _checkServiceGuideIsValid();
    return _getServiceProviderName();
  }
  
  // Note: incoherent comment in JSR 272 specification:
  // on one hand 
  // "(...) or if the given listener is already added, the method is silently ignored."
  //  on the other hand 
  // "If a listener is added for a second time but with a different filter argument, 
  //  the previous filter will be ignored and the new filter will be used for that listener."
  // Since the second part is more specific, it's a safe bet in my opinion.
  
  public void addListener(ServiceGuideListener listener, Query filter)
    throws IllegalStateException, QueryException
  {
    if (listener==null)
      return;
    
    _checkServiceGuideIsValid();
    _checkQueryIsValid(filter);
    
    _addServiceGuideListener(listener,filter);
  }
  
  public void removeListener(ServiceGuideListener listener)
    throws IllegalStateException
  {
    if (listener==null)
      return;
    
    _checkServiceGuideIsValid();
    
    _removeServiceGuideListener(listener);
  }
  
  public MetadataSet[] getSupportedMetadataSets()
    throws IllegalStateException
  {
    _checkServiceGuideIsValid();
    return _getSupportedMetadataSets();
  }
  
  // Optional: send UnsupportedOperationException when not implemented
  public Object[] getAllUniqueValues(Attribute attribute)
    throws UnsupportedOperationException, IllegalStateException, QueryException
  {
    _checkServiceGuideIsValid();
    _checkAttributeIsValid(attribute);
    
    return _getAllUniqueValues(attribute);
  }
  
  public boolean forceUpdate()
    throws IllegalStateException 
  {
    _checkServiceGuideIsValid();
    return _startServiceGuideUpdate();
  }
  
  public Date getLastUpdatedTime()
    throws IllegalStateException 
  {
    _checkServiceGuideIsValid();
    Date lastUpdateTime = _getLatestUpdateDate();
    if (lastUpdateTime==null)
      throw new IllegalStateException("No Service Guide update completed yet.");
    
    return lastUpdateTime;
  }
  
  public ProgramEvent[] findPrograms(Query query)
    throws QueryException
  {
    return findPrograms(query,null,0,0);
  }
  
  public ProgramEvent[] findPrograms(Query query, Attribute[] sortBy, int startOffset, int length)
    throws QueryException
  {
    _checkServiceGuideIsValid();
    _checkQueryIsValid(query);
    
    return _findProgramEvents(query,sortBy,startOffset,length);
  }
  
  public Service[] findServices(Query query)
    throws QueryException
  {
    return findServices(query,null,0,0);
  }
  
  public Service[] findServices(Query query, Attribute[] sortBy, int startOffset, int length)
    throws QueryException
  {
    _checkServiceGuideIsValid();
    _checkQueryIsValid(query);
    
    return _findServices(query,sortBy,startOffset,length);
  }
  
  
  
  // ===========================================================
  // Helper Methods: not part of JSR 272 Specification
  // ===========================================================
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Helper Methods checking assertions
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  protected void _checkServiceGuideIsValid()
    throws IllegalStateException
  {
    if ( !_isServiceGuideValid() )
      throw new IllegalStateException("Servide Guide is no longer valid due to change of Platform Provider.");
  }
  
  protected boolean _isServiceGuideValid()
  {
    return _platformProvider.equals( PlatformProviderSelector.getCurrentProvider() );
  }
  
  protected void _checkQueryIsValid(Query query)
    throws QueryException
  {
    if ( !_isQueryValid(query) )
      throw new QueryException("Query "+query+" is not valid.");
  }
  
  // Invalid queries are created with unknown Attributes, 
  // "not defined in the service guide specification" [1]
  // i.e. not part of an available MetadataSet.
  // In addition, 
  // "Queries that are invalid may be composed. An invalid query 
  // will cause a QueryException when used with the service guide."
  // Reference: [1] ServiceGuide.html#query_rules
  //
  // Since Attribute instances cannot be created "by hand" calling
  // the constructor (because it is an abstract class), implementations
  // that support only the CommonMetadataSet, which is mandatory,
  // will "see" only valid Attribute instances and thus valid Query
  // instances.
  //
  protected boolean _isQueryValid(Query query)
  {
    // I would like to write here
    // return query.isValid(this);
    // or
    // return query.visit( new validationVisitor(this) );
    return true;
  }
  
  protected void _checkAttributeIsValid(Attribute attribute)
    throws QueryException
  {
    if ( !_isAttributeValid(attribute) )
      throw new QueryException("Attribute "+attribute+" is not valid.");
  }
  
  // attribute is valid if not null and part of supported metadatasets
  protected boolean _isAttributeValid(Attribute attribute)
  {
    if (attribute==null)
      return false;
    
    return true;
    
    // a more generic implementation could look like this...
    // but here we know all non-null attributes are constant reference
    // from the CommonMetadataSet which must be supported...
    // ... so there is no need to test.
    /*
    MetadataSet[] metadataset = getSupportedMetadataSets();
    boolean found = false;
    for (int m=0; m<metadataset.length && !found; m++)
    {
      Attribute[] attribute_m = metadataset[m].getValidProgramAttributes();
      for (int i=0; i<attribute_m.length && !found; i++)
      {
        if ( attribute_m[i].equals(attribute) )
          found = true;
      }
    }
    return found;
    */
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Extension Point: Default ServiceGuide Singleton
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  protected static ServiceGuide _defaultServiceGuideSingleton;
  
  protected static ServiceGuide _getDefaultServiceGuideSingleton()
    throws IllegalStateException
  {
    if (_defaultServiceGuideSingleton==null)
      _defaultServiceGuideSingleton = new ServiceGuide("Null Service Provider");
    
    return _defaultServiceGuideSingleton;
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Protected Constructor and Fields
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  protected String _serviceProviderName;
  protected PlatformProvider _platformProvider;
  protected Hashtable _serviceGuideListener;
  protected Date _latestUpdateDate;
  
  protected ServiceGuide(String serviceProviderName)
    throws IllegalStateException
  {
    _platformProvider = PlatformProviderSelector.getCurrentProvider();
    if (_platformProvider==null)
      throw new IllegalStateException("No Platform Provider selected.");
      
    _serviceGuideListener = new Hashtable();
    _serviceProviderName = serviceProviderName;
  }
  
  protected String _getServiceProviderName()
  {
    return _serviceProviderName;
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Extension Point: List of Available Service Guides
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  protected ServiceGuide[] _getAllServiceGuides()
  {
    return new ServiceGuide[]{ _getDefaultServiceGuideSingleton() };
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Helper Methods managing ServiceGuideListener hash table
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  protected void _addServiceGuideListener(ServiceGuideListener listener, Query filter)
  {
    if ( _serviceGuideListener.containsKey(listener) )
      _serviceGuideListener.remove(listener);
      
    _serviceGuideListener.put(listener,filter);
  }
  
  protected void _removeServiceGuideListener(ServiceGuideListener listener)
  {
    _serviceGuideListener.remove(listener);
  }
  
  protected void _notifyServiceGuideListener(String event, ServiceGuideData serviceGuideData, Object eventData)
  {
    for (Enumeration e = _serviceGuideListener.keys(); e.hasMoreElements();) 
    {
      ServiceGuideListener serviceGuideListener = (ServiceGuideListener)e.nextElement();
      serviceGuideListener.serviceGuideUpdated(event,serviceGuideData,eventData);
    }
  }
  
  
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Helper Methods: Update Management
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  protected Thread _updateThread = null;
  
  // Triggers the Service Guide Update, if not already running
  // @return true if the update process is already running
  protected synchronized boolean _startServiceGuideUpdate()
  {
    if ( _updateThread != null )
      return true;
    
    _updateThread = new Thread
    (
      new Runnable()
      { 
        public void run()
        {
          _updateServiceGuide();
        }
      }
    );
    _updateThread.start();
    return false;
  }
  
  protected void _updateServiceGuide()
  {
    _notifyServiceGuideListener(ServiceGuideListener.SERVICE_GUIDE_UPDATE_STARTED,null,null);
    
    try
    {
      _processServiceGuideUpdate();
      _notifyServiceGuideListener(ServiceGuideListener.SERVICE_GUIDE_UPDATE_COMPLETED,null,null);
      _latestUpdateDate = new Date();
    }
    catch(Exception e)
    {
      _notifyServiceGuideListener(ServiceGuideListener.SERVICE_GUIDE_UPDATE_FAILED,null,e.getMessage());
    }
    
    _stopServiceGuideUpdate();
  }
  
  // Cancels the Service Guide update, if running
  // @return false if the update process was not running anymore
  protected synchronized boolean _stopServiceGuideUpdate()
  {
    if ( _updateThread == null )
      return false;
    
    _updateThread.interrupt();
    _updateThread = null;
    return true;
  }
  
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Extension Point: Null Implementation of ESG Update
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  // @throws Exception if the update fails, with as message 
  //         "a non-empty String stating the reason for the failure."
  protected void _processServiceGuideUpdate()
    throws Exception
  {
    // overriding methods should implement update here:
    // - acquire ESG updated
    // - update the database and send notifications of modifications
    //   using _notifyServiceGuideListener()
    //   listing individual changes if minor or
    //   SERVICE_GUIDE_BULK_CHANGED in case of important changes.
  }
  
  protected Date _getLatestUpdateDate()
  {
    return _latestUpdateDate;
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Extension Point: Null Implementation of Queries
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  protected static final ProgramEvent[] _EMPTY_RESULT_PROGRAM_EVENT = new ProgramEvent[0];
  protected static final Service[]      _EMPTY_RESULT_SERVICE       = new Service[0];
  protected static final Object[]       _EMPTY_RESULT_VALUES        = new Object[0];
  protected static final MetadataSet    _COMMON_METADATA_SET         = new CommonMetadataSet();
  
  protected ProgramEvent[] _findProgramEvents(Query query, Attribute[] sortBy, int startOffset, int length)
  {
    return _EMPTY_RESULT_PROGRAM_EVENT;
  }
  
  protected Service[] _findServices(Query query, Attribute[] sortBy, int startOffset, int length)
  {
    return _EMPTY_RESULT_SERVICE;
  }
  
  protected Object[] _getAllUniqueValues(Attribute attribute)
  {
    return _EMPTY_RESULT_VALUES;
  }
  
  protected MetadataSet[] _getSupportedMetadataSets()
  {
    return new MetadataSet[]{ _COMMON_METADATA_SET };
  }
  
}