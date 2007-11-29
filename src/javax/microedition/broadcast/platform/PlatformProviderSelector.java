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

import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.broadcast.BroadcastServiceException;
import javax.microedition.broadcast.UnsupportedOperationException;

import javax.microedition.broadcast.platform.PlatformProvider;
import javax.microedition.broadcast.platform.PlatformProviderSelectorListener;

public class PlatformProviderSelector
{
  // ==================================================
  // Static Methods defined in JSR 272 Specification
  // ==================================================
  
  public static void discover()
  {
    _getSingleton()._startPlatformDiscovery();
  }
  
  public static void abort()
  {
    if ( _getSingleton()._stopPlatformDiscovery() )
      _getSingleton()._notifyPlatformProviderSelectorListener(_DISCOVERY_ABORTED);
  }
  
  public static void select(PlatformProvider pp)
    throws NullPointerException, IllegalArgumentException
  {
    if (pp==null)
      throw new NullPointerException("Null Platform Provider cannot be selected.");
      
    if ( !_getSingleton()._isPlatformProviderAvailable(pp) )
      throw new IllegalArgumentException("Platform Provider "+pp+" is not available.");
    
    _getSingleton()._setCurrentProvider(pp);
  }
  
  public static PlatformProvider createPlatformProvider(int frequency, long id, String name)
    throws UnsupportedOperationException, IllegalArgumentException, NullPointerException
  {
    if (frequency<=0)
      throw new IllegalArgumentException("Frequency must be greater than zero.");
      
    if (name==null)
      throw new NullPointerException("Platform Provider name must not be null.");
    
    return _getSingleton()._newPlatformProvider(frequency,id,name);
  }
  
  public static PlatformProvider[] getAvailableProviders()
  {
    return _getSingleton()._getAvailableProviders();
  }
  
  public static PlatformProvider getDefaultProvider()
    throws BroadcastServiceException
  {
    PlatformProvider defaultProvider = _getSingleton()._getDefaultProvider();
    
    if (defaultProvider==null)
      throw new BroadcastServiceException("No default Platform Provider available.");
      
    return defaultProvider;
  }
  
  public static PlatformProvider getCurrentProvider()
  {
    return _getSingleton()._getCurrentProvider();
  }
  
  public static Date getCurrentProviderDate()
    throws IllegalStateException
  {
    if ( getCurrentProvider()==null )
      throw new IllegalStateException("No Platformer Provider selected.");
      
    return _getSingleton()._getNetworkDate();
  }
  
  public static void addListener(PlatformProviderSelectorListener ppl)
  {
    _getSingleton()._addPlatformProviderSelectorListener(ppl);
  }
  
  public static void removeListener(PlatformProviderSelectorListener ppl)
  {
    _getSingleton()._removePlatformProviderSelectorListener(ppl);
  }
  
  // ===================================================
  // Helper Methods not part of JSR 272 Specification
  // ===================================================
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Extension Point: Singleton
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  protected static PlatformProviderSelector _singleton;
  
  protected static PlatformProviderSelector _getSingleton()
  {
    if (_singleton==null)
      _singleton = new PlatformProviderSelector();
    
    return _singleton;
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~
  // Constructor and Fields
  // ~~~~~~~~~~~~~~~~~~~~~~~~
  
  protected PlatformProvider _currentProvider;
  protected Vector _platformProvider;
  protected Vector _platformProviderSelectorListener;
  
  protected PlatformProviderSelector()
  {
    // alternatively, derived classes may load previously configured
    // Platform Provider from previous application run.
    _currentProvider = null;
    _platformProvider = new Vector();
    _platformProviderSelectorListener = new Vector();
  }
  
  protected boolean _isPlatformProviderAvailable(PlatformProvider platformProvider)
  {
    return _platformProvider.contains(platformProvider);
  }
  
  protected PlatformProvider[] _getAvailableProviders()
  {
    PlatformProvider[] availableProvider = new PlatformProvider[_platformProvider.size()];
    _platformProvider.copyInto(availableProvider);
    return availableProvider;
  }
  
  protected PlatformProvider _getCurrentProvider()
  {
    return _currentProvider;
  }
  
  protected void _setCurrentProvider(PlatformProvider platformProvider)
  {
    _currentProvider = platformProvider;
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Helper Methods: Platform Discovery
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  protected Thread _discoveryThread = null;
  
  // @return true if the platform discovery was already running
  protected synchronized boolean _startPlatformDiscovery()
  {
    if (_discoveryThread!=null)
      return true;
      
    _discoveryThread = new Thread
    (
      new Runnable()
      { 
        public void run()
        {
          _discoverPlatform();
        }
      }
    );
    _discoveryThread.start();
    return false;
  }
  
  protected void _discoverPlatform()
  {
    _processPlatformDiscovery();
    
    _notifyPlatformProviderSelectorListener(_DISCOVERY_COMPLETE);
    _stopPlatformDiscovery();
  }
  
  // @return false if the platform discovery was not running
  protected synchronized boolean _stopPlatformDiscovery()
  {
    if (_discoveryThread==null)
      return false;
      
    _discoveryThread.interrupt();
    _discoveryThread = null;
    return true;
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Extension Point: Null Implementation of Platform Discovery
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  protected void _processPlatformDiscovery()
  {
    // OK to do nothing (sometimes), e.g. when no receiption is available
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Extension Point: Null Default Platform Provider
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  // @return null if no default platform provider is available
  protected PlatformProvider _getDefaultProvider()
  {
    return _currentProvider;
  }
  
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Helper Methods: Managing List of Listeners
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  protected static final int _DISCOVERY_ABORTED = 100;
  protected static final int _DISCOVERY_COMPLETE = 100;
  
  protected void _addPlatformProviderSelectorListener(PlatformProviderSelectorListener listener)
  {
    if (listener==null)
      return;
    
    if ( _platformProviderSelectorListener.contains(listener) )
      return;
      
    _platformProviderSelectorListener.addElement(listener);
  }
  
  // @param progress [0-100] percentage of progress, 
  //        must be 100 when discovery has been completed or cancelled.
  protected void _notifyPlatformProviderSelectorListener(int progress)
  {
    int found = _platformProvider.size();
    boolean continuing = progress<_DISCOVERY_COMPLETE;
    
    for ( Enumeration e = _platformProviderSelectorListener.elements(); e.hasMoreElements(); )
    {
      PlatformProviderSelectorListener listener = (PlatformProviderSelectorListener)e.nextElement();
      listener.update(progress, found, continuing);
    }
  }
  
  protected void _removePlatformProviderSelectorListener(PlatformProviderSelectorListener listener)
  {
    if (listener==null)
      return;
    
    _platformProviderSelectorListener.removeElement(listener);
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Extension Point: Null Factory Method
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  protected PlatformProvider _newPlatformProvider(int frequency, long id, String name)
    throws UnsupportedOperationException
  {
    throw new UnsupportedOperationException("Not supported.");
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Extension Point: Null Network Date
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  // broadcast date
  // This null implementation returns current date on device.
  protected Date _getNetworkDate()
  {
    return new Date();
  }
  
}