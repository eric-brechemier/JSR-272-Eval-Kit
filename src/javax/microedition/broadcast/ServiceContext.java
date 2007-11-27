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
package javax.microedition.broadcast;

import java.util.Vector;

import javax.microedition.broadcast.BroadcastServiceException;
import javax.microedition.broadcast.InsufficientResourcesException;
import javax.microedition.broadcast.ServiceComponent;
import javax.microedition.broadcast.ServiceContextListener;

import javax.microedition.broadcast.esg.Service;

import javax.microedition.broadcast.connection.BroadcastDatagramConnection;

import javax.microedition.media.Player;

// Comment: Why abstract??
public abstract class ServiceContext
{
  // =============================================================
  // Methods and Constant Fields defined in JSR 272 Specification
  // =============================================================
  
  public static final String LANGUAGE = "language";
  
  public static final int CLOSED = 0;
  public static final int STOPPED = 1;
  public static final int ACQUIRING_SERVICE = 2;
  public static final int SERVICE_ACQUIRED = 3;
  public static final int PREPARING_MEDIA = 4;
  public static final int PRESENTING = 5;
  
  public static ServiceContext createServiceContext()
    throws SecurityException
  {
    return _getDefaultServiceContextSingleton()._newServiceContext();
  }
  
  public static ServiceContext getDefaultContext()
    throws SecurityException
  {
    return _getDefaultServiceContextSingleton();
  }
  
  
  // Comment: Why abstract??
  public abstract void addListener(ServiceContextListener listener)
    throws IllegalStateException;
  
  public abstract void removeListener(ServiceContextListener listener)
    throws IllegalStateException;
  
  
  public abstract int getSignalQuality()
    throws IllegalStateException;
  
  
  public abstract int getState();
  
  
  public abstract Object getPreference(String key)
    throws IllegalStateException, NullPointerException;
  
  public abstract String[] getAllPreferenceKeys()
    throws IllegalStateException;
  
  public abstract Object setPreference(String key, Object value)
    throws IllegalStateException, NullPointerException;
  
  
  public abstract Service getService()
    throws IllegalStateException;
  
  public abstract void select(Service service)
    throws IllegalArgumentException, SecurityException, IllegalStateException, NullPointerException;
  
  public abstract void stop()
    throws IllegalStateException;
  
  
  public abstract ServiceComponent[] getSelectedComponents()
    throws IllegalStateException;
  
  public abstract ServiceComponent[] getUnselectedComponents()
    throws IllegalStateException;
  
  public abstract ServiceComponent[] getAllComponents()
    throws IllegalStateException;
  
  public abstract ServiceComponent[] getUnrecognizedComponents()
    throws IllegalStateException;
  
  public abstract void setComponents(ServiceComponent[] components)
    throws InsufficientResourcesException, IllegalStateException, BroadcastServiceException, NullPointerException;
  
  
  public abstract BroadcastDatagramConnection getBroadcastDatagramConnection(ServiceComponent component)
    throws BroadcastServiceException, SecurityException, IllegalStateException, NullPointerException;
  
  
  public abstract Player getPlayer(ServiceComponent component)
    throws BroadcastServiceException, IllegalStateException, NullPointerException;
  
  public abstract Player[] getPlayers()
    throws IllegalStateException;
  
  
  public abstract void close()
    throws java.lang.SecurityException;
  
  
  // =================================================
  // Helper Methods Not part of JSR 272 Specification
  // =================================================
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Extension Point: Singleton
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  // default ServiceContext instance used to override static methods of the abstract class
  protected static ServiceContext _defaultServiceContextSingleton;
  
  protected static ServiceContext _getDefaultServiceContextSingleton()
  {
    if (_defaultServiceContextSingleton==null)
      throw new NullPointerException("Default Service Context not initialized.");
      
    return _defaultServiceContextSingleton;
  }
  
  // WARNING...
  // The default Service Context Singleton must be set before any call to 
  // the ServiceContext#_getDefaultServiceContextSingleton() method occurs.
  protected static void _setDefaultServiceContextSingleton(ServiceContext serviceContext)
  {
    _defaultServiceContextSingleton = serviceContext;
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Extension Point: Abstract Methods to be defined in Derived Class
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  // Create new instances of ServiceContext for ServiceContext#createServiceContext()
  protected abstract ServiceContext _newServiceContext();
  
  
}