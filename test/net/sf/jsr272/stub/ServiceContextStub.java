/* 
==================================================================
 JSR 272 Mobile TV API -Evaluation Pack-
 https://github.com/eric-brechemier/JSR-272-Mobile-TV-API-Evaluation-Pack-
 
 Copyright (c) 2007 Eric Bréchemier <jsr272@eric.brechemier.name>
 Licensed under BSD License and/or MIT License.
==================================================================

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                         BSD License
                             ~~~
             http://creativecommons.org/licenses/BSD/
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                          MIT License
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  Copyright (c) 2007 Eric Bréchemier <jsr272@eric.brechemier.name>
  
  Permission is hereby granted, free of charge, to any person
  obtaining a copy of this software and associated documentation
  files (the "Software"), to deal in the Software without
  restriction, including without limitation the rights to use,
  copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the
  Software is furnished to do so, subject to the following
  conditions:

  The above copyright notice and this permission notice shall be
  included in all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
  OTHER DEALINGS IN THE SOFTWARE.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*/
package net.sf.jsr272.stub;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.io.Connector;

import javax.microedition.broadcast.BroadcastServiceException;
import javax.microedition.broadcast.InsufficientResourcesException;
import javax.microedition.broadcast.ServiceComponent;
import javax.microedition.broadcast.ServiceContext;
import javax.microedition.broadcast.ServiceContextListener;

import javax.microedition.broadcast.esg.CommonMetadataSet;
import javax.microedition.broadcast.esg.Service;

import javax.microedition.broadcast.connection.BroadcastDatagramConnection;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

import net.sf.jsr272.stub.ServiceComponentStub;

import net.sf.jsr272.stub.connection.BroadcastDatagramConnectionStub;

import net.sf.jsr272.stub.platform.PlatformProviderSelectorStub;

import net.sf.jsr272.stub.esg.ServiceStub;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class ServiceContextStub extends ServiceContext
{
  public static final String STUB_LOGGER = "";
  public static final Level LOG_LEVEL = Level.INFO;
  protected static Logger _log;
  
  public static void useSingleton()
  {
    _defaultServiceContextSingleton = new ServiceContextStub();
  }
  
  protected final int _STATE_UNSET = -1;
  protected int _state;
  protected boolean _isPreparing;
  protected Vector _serviceContextListener;
  protected Hashtable _preferences;
  protected ServiceStub _selectedService;
  protected ServiceComponentStub[] _serviceComponent;
  protected Vector _selectedServiceComponent;
  protected Hashtable _player;
  
  protected ServiceContextStub()
  {
    super();
    _log = Logger.getLogger(STUB_LOGGER);
    _log.setLevel(LOG_LEVEL);
    
    _serviceContextListener = new Vector();
    _state = _STATE_UNSET;
    _isPreparing = false;
    _selectedService = null;
    _serviceComponent = null;
    _selectedServiceComponent = null;
    _player = new Hashtable();
  }
  
  // <extends object="ServiceContext">
  
  public void addListener(ServiceContextListener listener)
    throws IllegalStateException
  {
    if ( getState()==CLOSED )
      throw new IllegalStateException("Cannot add listener on closed Service Context.");
    
    if ( listener==null || _serviceContextListener.contains(listener) )
      return;
    
    _serviceContextListener.addElement(listener);
  }
  
  public void removeListener(ServiceContextListener listener)
    throws IllegalStateException
  {
    if ( getState()==CLOSED )
      throw new IllegalStateException("Cannot remove listener on closed Service Context.");
    
    if ( listener==null || _serviceContextListener.contains(listener) )
      return;
    
    _serviceContextListener.removeElement(listener);
  }
  
  
  public int getSignalQuality()
    throws IllegalStateException
  {
    switch( getState() )
    {
      case STOPPED:
      case CLOSED:
        throw new IllegalStateException("Cannot get signal quality in STOPPED or CLOSED state.");
    }
    
    return 100;
  }
  
  
  public int getState()
  {
    return _state;
  }
  
  public String[] getAllPreferenceKeys()
    throws IllegalStateException
  {
    if ( getState()==CLOSED )
      throw new IllegalStateException("Cannot get set of supported preference keys while in CLOSED state.");
    
    return new String[]{LANGUAGE};
  }
  
  // Note: no details on returned value when this key is not set
  //       or not supported. Currently I chose to return null,
  //       but for consistence with setPreference, it should
  //       throw an IllegalArgumentException.
  public Object getPreference(String key)
    throws IllegalStateException, NullPointerException
  {
    if ( getState()==CLOSED )
      throw new IllegalStateException("Cannot get preference value while in CLOSED state.");
    
    if (key==null)
      throw new NullPointerException("Cannot get value for null preference key.");
    
    return _preferences.get(key);
  }
  
  // Note: no detail on returned value when this key was NOT previously set.
  // Note: this is not a standard setter (should return void),
  //       but this behavior corresponds exactly to "Object remove(Object key)"
  //       in java.util.Hashtable...
  public Object setPreference(String key, Object value)
    throws IllegalStateException, NullPointerException
  {
    if ( getState()==CLOSED )
      throw new IllegalStateException("Cannot set preference value while in CLOSED state.");
    
    if (key==null)
      throw new NullPointerException("Cannot set value for null preference key.");
    
    Object result = _preferences.remove(key);
    _preferences.put(key,value);
    return result;
  }
  
  
  // Note: inconsistent comment in ServiceContext#getService()
  //       "Returns: the current selected Service if the ServiceContext 
  //        is not in STOPPED  or CLOSED state. Otherwise returns null
  //        Throws: java.lang.IllegalStateException - Thrown if the ServiceContext  is closed"
  //       When ServiceContext is CLOSED, throw IllegalStateException or return null?
  //       When ServiceContext is STOPPED, return null
  //       Else return (non-null) Service
  // Note: What about the option to keep the last selected Service of last run?
  public Service getService()
    throws IllegalStateException
  {
    if ( getState()==CLOSED )
      throw new IllegalStateException("No selected Service in CLOSED state.");
    
    if ( getState()==STOPPED )
      return null;
    
    return _selectedService;
  }
  
  public void select(Service service)
    throws IllegalArgumentException, SecurityException, IllegalStateException, NullPointerException
  {
    if (
           !(service instanceof ServiceStub)
        || !PlatformProviderSelectorStub.isValid(service) 
       )
      throw new IllegalArgumentException("Incorrect service expired following change of Platform Provider.");
      
    if ( getState()==CLOSED )
      throw new IllegalStateException("Cannot select new service in CLOSED state.");
      
    if ( service==null )
      throw new NullPointerException("Cannot select null service.");
    
    if ( service==_selectedService )
      return;
    
    _selectedService = (ServiceStub)service;
    _startSetState(ACQUIRING_SERVICE);
  }
  
  // asynchronous ("This method returns immediately.")
  public void stop()
    throws IllegalStateException
  {
    if ( getState()==CLOSED )
      throw new IllegalStateException("No Service to stop in CLOSED state.");
    
    if ( getState()==STOPPED )
      return;
    
    _startSetState(STOPPED);
  }
  
  
  public ServiceComponent[] getSelectedComponents()
    throws IllegalStateException
  {
    if ( getState()==CLOSED )
      throw new IllegalStateException("Cannot get selected components in CLOSED state.");
    
    ServiceComponent[] selectedServiceComponent = new ServiceComponent[_selectedServiceComponent.size()];
    _selectedServiceComponent.copyInto(selectedServiceComponent);
    return selectedServiceComponent;
  }  
  
  public ServiceComponent[] getUnselectedComponents()
    throws IllegalStateException
  {
    if ( getState()==CLOSED )
      throw new IllegalStateException("Cannot get unselected components in CLOSED state.");
    
    ServiceComponent[] unselectedServiceComponent = new ServiceComponent[_serviceComponent.length-_selectedServiceComponent.size()];
    int offset = 0;
    for (int i=0; i<_serviceComponent.length && offset<unselectedServiceComponent.length; i++)
    {
      if ( !_selectedServiceComponent.contains(_serviceComponent[i]) )
      {
        unselectedServiceComponent[offset] = _serviceComponent[i];
        offset++;
      } 
    }
    
    return unselectedServiceComponent;
  }
  
  public ServiceComponent[] getAllComponents()
    throws IllegalStateException
  {
    if ( getState()==CLOSED )
      throw new IllegalStateException("Cannot get service components in CLOSED state.");
    
    return _serviceComponent;
  }
  
  public ServiceComponent[] getUnrecognizedComponents()
    throws IllegalStateException
  {
    if ( getState()==CLOSED )
      throw new IllegalStateException("Cannot get unrecognized service components in CLOSED state.");
    
    return new ServiceComponent[0];
  }
  
  public void setComponents(ServiceComponent[] components)
    throws InsufficientResourcesException, IllegalStateException, BroadcastServiceException, NullPointerException
  {
    switch( getState() )
    {
      case STOPPED:
      case ACQUIRING_SERVICE:
      case CLOSED:
        throw new IllegalStateException("Cannot select service components while context is Stopped, Acquiring Service, or Closed.");
    }
    
    if (components==null)
      throw new NullPointerException("Cannot select null component list.");
    
    _selectedServiceComponent.removeAllElements();
    
    for (int i=0; i<components.length; i++)
      _selectedServiceComponent.addElement(components[i]);
    
    _startSetState(PREPARING_MEDIA);
  }
  
  
  public BroadcastDatagramConnection getBroadcastDatagramConnection(ServiceComponent component)
    throws BroadcastServiceException, SecurityException, IllegalStateException, NullPointerException
  {
    return new BroadcastDatagramConnectionStub(component);
  }
  
  // Note: "IllegalStateException - Thrown if the ServiceContext is in PREPARING_MEDIA 
  //       and PLAYERS_REALIZED  has not been delivered (...)"
  //       but... what about access by different instances of ServiceContextListener,
  //       one that has not been delivered the PREPARING_MEDIA event vs one that has...
  //       I don't get the point of distinguishing a special case here,
  //       the state will changed immediatly after PLAYERS_REALIZED has been delivered anyway.
  //       (Update) I understand now a possible explanation for this special case,
  //       to make it legal to call getPlayers() for the third argument of the notification
  //       itself... which I discovered by triggering the Exception from this own class.
  //       I will distinguish this case using an additional boolean "sub-state" variable.
  public Player getPlayer(ServiceComponent component)
    throws BroadcastServiceException, IllegalStateException, NullPointerException
  {
    _checkPlayerState();
    
    if ( !_selectedServiceComponent.contains(component) )
      throw new BroadcastServiceException("Cannot get player, this service component is not being presented.");
    
    if (component==null)
      throw new NullPointerException("Cannot get player for null service component");
    
    return (Player)_player.get(component);
  }
  
  public Player[] getPlayers()
    throws IllegalStateException
  {
    _checkPlayerState();
    
    Player[] player = new Player[_player.size()];
    int i=0;
    for (Enumeration e=_player.keys(); e.hasMoreElements(); )
    {
      player[i++] = (Player)e.nextElement();
    }
    return player;
  }
  
  
  public void close()
    throws SecurityException
  {
    _startSetState(CLOSED);
  }
  
  // </extends>
  
  // ==============================================================
  // Helper Methods - Not part of JSR 272
  // ==============================================================
  
  protected void _checkPlayerState()
    throws IllegalStateException
  {
    if 
      (
            (getState()==PREPARING_MEDIA && _isPreparing)
        ||  (getState()!=PREPARING_MEDIA && getState()!=PRESENTING)
      )
    {
        throw new IllegalStateException("Can only get player in PREPARING_MEDIA* and PRESENTING states"
                                        +" (* after PLAYERS_REALIZED has been delivered).");
    }
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Extension Point: Abstract Methods to be defined in Derived Class
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  // Create new instances of ServiceContext for ServiceContext#createServiceContext()
  protected ServiceContext _newServiceContext()
  {
    return new ServiceContextStub();
  }
  
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Managing Listeners
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  protected void _notifyServiceContextListener(ServiceContext context, String event, Object data)
  {
    for (Enumeration e = _serviceContextListener.elements(); e.hasMoreElements();) 
    {
      ServiceContextListener serviceContextListener = (ServiceContextListener)e.nextElement();
      serviceContextListener.contextUpdate(context,event,data);
    }
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Managing State Update
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  // this Vector list is needed to keep up with state transitions occuring
  // during a callback triggered by a previous state transition.
  // I chose to queue the state transition until the previous one completed
  // in order to let all notifications for one state complete before the 
  // following state transition avoiding inconsistent behavior
  // with the interactions of different listeners.
  protected Vector _stateUpdateThreadQueue = new Vector();
  
  // @return true if state change was already running
  protected synchronized boolean _startSetState(final int state)
  {
    Thread stateUpdateThread = new Thread
    (
      new Runnable()
      { 
        public void run()
        {
          _setState(state);
        }
      }
    );
    
    boolean isStateUpdateOngoing = !_stateUpdateThreadQueue.isEmpty();
    _stateUpdateThreadQueue.addElement(stateUpdateThread);
    
    if ( isStateUpdateOngoing )
    {
      _log.trace("State change requested was queued cause a previous change is ongoing.");
      return true;
    }
    
    stateUpdateThread.start();
    return false;
  }
  
  // @return false if state change was not running
  protected synchronized boolean _stopSetState()
  {
    if (_stateUpdateThreadQueue.isEmpty())
      throw new IllegalStateException("_startSetState must be called before _stopSetState.");
    
    Thread stateUpdateThread = (Thread)_stateUpdateThreadQueue.elementAt(0);
    stateUpdateThread.interrupt();
    _stateUpdateThreadQueue.removeElement(stateUpdateThread);
    
    if ( _stateUpdateThreadQueue.isEmpty() )
      return false;
    
    stateUpdateThread = (Thread)_stateUpdateThreadQueue.elementAt(0);
    stateUpdateThread.start();
    return true;
  }
  
  // this method must only be called from run() in new Thread.
  // should maybe be defined in a separate Runnable class.
  protected void _setState(int state)
  {
    _log.trace("setState("+state+")");
    _state = state;
    
    switch(_state)
    {
      case STOPPED:
        _selectedService = null;
        String reason = null;
        switch( getState() )
        {
          case _STATE_UNSET:
            reason = ServiceContextListener.REASON_OTHER;
            break;
          
          default:
            reason = ServiceContextListener.REASON_APPLICATION_REQUESTED;
            _stopPlayingMedia();
        }
        
        _notifyServiceContextListener(this,ServiceContextListener.CONTEXT_STOPPED,reason);
        break;
      
      case ACQUIRING_SERVICE:
        _stopPlayingMedia();
        _log.debug("Sending SELECTION INITIATED notification");
        _notifyServiceContextListener(this,ServiceContextListener.SELECTION_INITIATED,ServiceContextListener.REASON_APPLICATION_REQUESTED);
        _startSetState(SERVICE_ACQUIRED);
        break;
      
      case SERVICE_ACQUIRED:
        _serviceComponent = new ServiceComponentStub[3];
        _serviceComponent[0] = new ServiceComponentStub(_selectedService.getServiceGuide(),new Hashtable(),ServiceComponent.VIDEO);
        _serviceComponent[1] = new ServiceComponentStub(_selectedService.getServiceGuide(),new Hashtable(),ServiceComponent.AUDIO);
        _serviceComponent[2] = new ServiceComponentStub(_selectedService.getServiceGuide(),new Hashtable(),ServiceComponent.SUBTITLES);
        
        _serviceComponent[0].setStringValue(CommonMetadataSet.SERVICE_COMPONENT_MIME_TYPE,"video/H264");
        _serviceComponent[0].setStringValue(CommonMetadataSet.SERVICE_COMPONENT_LANGUAGE,"en");
        
        _serviceComponent[1].setStringValue(CommonMetadataSet.SERVICE_COMPONENT_MIME_TYPE,"video/H264");
        _serviceComponent[1].setStringValue(CommonMetadataSet.SERVICE_COMPONENT_LANGUAGE,"en");
        
        _serviceComponent[1].setStringValue(CommonMetadataSet.SERVICE_COMPONENT_MIME_TYPE,"video/3gpp-tt");
        _serviceComponent[1].setStringValue(CommonMetadataSet.SERVICE_COMPONENT_LANGUAGE,"fr");
        
        _selectedServiceComponent = new Vector();
        _selectedServiceComponent.addElement(_serviceComponent[0]);
        _selectedServiceComponent.addElement(_serviceComponent[1]);
        
        _notifyServiceContextListener(this, ServiceContextListener.COMPONENTS_IDENTIFIED, getSelectedComponents() );        
        _startSetState(PREPARING_MEDIA);
        break;
      
      case PREPARING_MEDIA:
        
        try
        {  
          _notifyServiceContextListener(this, ServiceContextListener.COMPONENTS_SET, ServiceContextListener.REASON_OTHER);
          _preparePlayingMedia();
          _notifyServiceContextListener(this, ServiceContextListener.PLAYERS_REALIZED, getPlayers() );
          _startSetState(PRESENTING);
        }
        catch(Exception e)
        {
          _log.error("Failed while preparing media: "+e);
          _startSetState(STOPPED);
        }
        break;
      
      case PRESENTING:
        _startPlayingMedia();
        _notifyServiceContextListener(this, ServiceContextListener.PRESENTATION_STARTED, null);
        break;
      
      default:
        _log.error("Unexpected state transition: "+state);
        throw new IllegalArgumentException("Unsupported state requested internally: "+state);
    }
    _stopSetState();
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Extension Point: Media Player Management
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  protected void _preparePlayingMedia()
    throws BroadcastServiceException, IOException, MediaException
  {
    _isPreparing = true;
    _player.clear();
          
    for (Enumeration e=_selectedServiceComponent.elements(); e.hasMoreElements();)
    {
      ServiceComponent serviceComponent = (ServiceComponent)e.nextElement();
      BroadcastDatagramConnection connection = getBroadcastDatagramConnection(serviceComponent);
      InputStream in = _getInputStream(connection);
      String mimetype = connection.getMimeType();
      Player newPlayer = Manager.createPlayer(in,mimetype);
      _log.debug("new player created: "+newPlayer);
      
      if (newPlayer!=null)
      {
        newPlayer.realize();
        _player.put(serviceComponent,newPlayer);
      }
      // else throw some Exception... ?
    }
    _isPreparing = false;
  }
  
  protected InputStream _getInputStream(BroadcastDatagramConnection connection)
  {
     // TODO: create InputStream that reads data from DatagramConnection
     //       using the receive method...
    return new DataInputStream(null);
  }
  
  
  protected void _startPlayingMedia()
  {
    
  }
  
  protected void _stopPlayingMedia()
  {
    
  }
  
}
