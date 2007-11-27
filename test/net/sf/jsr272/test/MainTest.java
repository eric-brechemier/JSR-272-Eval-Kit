/* 
==================================================================
 JSR 272 Mobile TV API -Evaluation Pack-
 http://sourceforge.net/projects/jsr272/
 
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
package net.sf.jsr272.test;

import java.util.Date;

import javax.microedition.broadcast.ServiceComponent;
import javax.microedition.broadcast.ServiceContext;
import javax.microedition.broadcast.ServiceContextListener;

import javax.microedition.broadcast.esg.CommonMetadataSet;
import javax.microedition.broadcast.esg.QueryComposer;
import javax.microedition.broadcast.esg.Service;
import javax.microedition.broadcast.esg.ServiceGuide;
import javax.microedition.broadcast.esg.ServiceGuideData;
import javax.microedition.broadcast.esg.ServiceGuideListener;

import javax.microedition.broadcast.platform.PlatformProvider;
import javax.microedition.broadcast.platform.PlatformProviderSelector;

import net.sf.jsr272.test.esg.ServiceGuideUtil;
import net.sf.jsr272.test.platform.PlatformProviderSelectorUtil;

public class MainTest implements ServiceGuideListener, ServiceContextListener
{
  public static void main(String[] args)
  {
    try
    {
      MainTest starter = new MainTest(args);
      starter.startTest();
    }
    catch(Exception e)
    {
      e.printStackTrace();
      System.err.println(e);
    }
  }
  
  protected int _platformProviderId;
  protected String _serviceProviderName;
  protected ServiceGuide _currentESG;
  protected Service _selectedChannel;
  protected boolean _zappingContextInitialized;
  
  public MainTest(String[] args)
  {
    if(args.length!=2)
      throw new IllegalArgumentException("Expected args: PlatformProviderId ServiceProviderName");
    
    int _platformProviderId = Integer.parseInt(args[0]);
    String _serviceProviderName = args[1];
    _currentESG = null;
    _selectedChannel = null;
    _zappingContextInitialized = false;
  }

  protected void startTest();
  {
    //TODO: replace log with log4j2me
    //TODO: insert junit/j2meunit asserts
    
    // 1. Bootstrap - Select the Operator or "Platform Provider"
    
    // 1.1 Start discovery for Platform Providers, usually scanning UHF,VHF frequency bands
    PlatformProviderSelector.discover();
    // 1.2 Get the list of Platform Providers (e.g. as announced in ESG Provider List)
    PlatformProvider[] availableProvider = PlatformProviderSelector.getAvailableProviders();
    // 1.3 Iterate over Providers and select the one with expected id
    //     an alternative would be to ask the user to choose her Operator
    boolean providerFound = false;
    for (int i=0; i<availableProvider.length; i++)
    {
      if ( availableProvider[i].getID() == _platformProviderId )
      {
        providerFound = true;
        PlatformProviderSelector.select(availableProvider[i]);
      }
    }
    assertTrue(providerFound,
               "Platform "
               +_platformProviderId
               +" could not be found. Choose one of ["
               +PlatformProviderSelectorUtil.getAvailableProviderListDescription()
               +"]"
              );
    
    // 1.4 Select the Electronic Service Guide corresponding to my operator.
    //     In multiple operator deployments with a common broadcaster,
    //     several service guides may be available.
    //     In this case, the common broacaster is "Platform Provider"
    //     and each Telecom Operator is "Service Provider" 
    //     with its own associated Electronic Service Guide (ESG).
    ServiceGuide[] availableESG = ServiceGuide.getAllServiceGuides();
    _currentESG = null;
    for (int i=0; i<availableESG.length; i++)
    {
      if ( _serviceProviderName.equals( availableESG[i].getServiceProvider() )  )
      {
        _currentESG = availableESG[i];
      }
    }
    assertNotNull(_currentESG,
                  "Service Provider "
                  +_serviceProviderName
                  +" could not be found. Choose one of ["
                  +ServiceGuideUtil.getAvailableESGListDescription()
                  +"]"
                 );
    
    // 2. Acquire/Update ESG metadata
    _currentESG.addListener(this, QueryComposer.currentProgram() );
    // asynchronous call
    // completion events will be received through ServiceGuideListener interface
    _currentESG.forceUpdate();
  } 
  
  public void serviceGuideUpdated(String event, ServiceGuideData serviceGuideData, Object eventData)
  {
    if ( SERVICE_GUIDE_UPDATE_COMPLETED.equals(event) )
    {
      _currentESG.removeListener(this);
      updateCompleted();
    }
    else if( ServiceGuideListener.SERVICE_GUIDE_UPDATE_FAILED.equals(event) )
    {
      _currentESG.removeListener(this);
      fail("ESG Update failed. "+eventData);
    }
  }
  
  protected void updateCompleted()
  {
    // 3. Query the ESG for the Channel List
    Service[] channel = _currentESG.findServices(null);
    assertTrue(channel.length >= 1,"No channel description found in current ESG.");
    
    // 3.1 Iterate over Channels to display the list of Channel Information
    System.out.println("Channel List");
    for (int i=0; i<channel.length; i++)
    {
      String channelName = channel[i].getStringValue(CommonMetadataSet.SERVICE_NAME);
      String description = channel[i].getStringValue(CommonMetadataSet.SERVICE_DESCRIPTION);
      System.out.println(i+": "+channelName+" - "+description);
    }

    // 3.2 Query the ESG for current programs
    ProgramEvent[] currentProgram = _currentESG.findPrograms( QueryComposer.currentProgram() );
    assertTrue(currentProgram.length >= 1,"No program description found in current ESG.");
    
    // 3.3 Iterate over Programs to display the current program information on all channels
    System.out.println("Current Programs");
    for (int i=0; i<currentProgram.length; i++)
    {
      String channelName = currentProgram[i].getStringValue(CommonMetadataSet.SERVICE_NAME);
      Date startTime = currentProgram[i].getDateValue(CommonMetadataSet.PROGRAM_START_TIME);
      Date endTime = currentProgram[i].getDateValue(CommonMetadataSet.PROGRAM_END_TIME);
      String title = currentProgram[i].getStringValue(CommonMetadataSet.PROGRAM_NAME);
      String synopsis = currentProgram[i].getStringValue(CommonMetadataSet.PROGRAM_DESCRIPTION);
      System.out.println(i+": "+channelName+"["+startTime+"-"+endTime+"] "+title+" - "+synopsis);
    }
    
    // 3.4 Select channel to watch, fixed here to the first TV service.
    //    An alternative would be to ask the user to choose which channel to watch ;)
    
    boolean tvServiceFound = false;
    for (int i=0; i<channel.length && !tvServiceFound; i++)
    {
      if (  "tv".equals( channel[i].getStringValue(CommonMetadataSet.SERVICE_TYPE_NAME) )  )
      {
        _selectedChannel = channel[i];
        tvServiceFound = true;
      }
    }
    assertTrue(tvServiceFound,"No TV Channel found in ESG.");
    
    // 4. Zap to selected channel
    // 4.1 Initialize the Service Context for Zapping
    ServiceContext zappingContext = ServiceContext.getDefaultContext();
    zappingContext.addListener(this);
    
  }  
  
  public void contextUpdate(ServiceContext context, String event, Object data)
  {
    if ( !context.equals( ServiceContext.getDefaultContext() ) )
      return;
    
    if ( CONTEXT_STOPPED.equals(event) )
    {
      if (!_zappingContextInitialized)
      {
        _zappingContextInitialized = true;
        contextInstanceInitialized(context);
      }
      else
      {
        contextStopped(context);
      }
    }
    else if ( SELECTION_INITIATED.equals(event) )
    {
      serviceSelectionInitiated(context);
    }
    else if ( COMPONENTS_IDENTIFIED.equals(event) )
    {
      ServiceComponent[] defaultServiceComponent = (ServiceComponent[])data;
      defaultserviceComponentsSelected(context, defaultServiceComponent);
    }
    else if ( COMPONENTS_SET.equals(event) )
    {
      serviceComponentsSelectionCompleted(context);
    }
    else if ( PLAYERS_REALIZED.equals(event) )
    {
      playersInitialized(context);
    }
    else if ( PRESENTATION_STARTED.equals(event) )
    {
      playersStarted(context);
    }
    
    else if ( CONTEXT_CLOSED.equals(event) )
    {
      contextClosed(context);
    }
  }
  
  protected void contextInstanceInitialized(ServiceContext context)
  {
    // 4.2 Apply Channel Selection
    context.select(channel[i]);
  }
  
  protected void serviceSelectionInitiated(ServiceContext context)
  {
  }
  
  protected void defaultserviceComponentsSelected(ServiceContext context, ServiceComponent[] component)
  {
    boolean videoComponentFound = false;
    boolean audioComponentFound = false;
    for (int i=0; i<component.length; i++)
    {
      switch( component[i].getType() )
      {
        case ServiceComponent.VIDEO:
          videoComponentFound = true;
          break;
          
        case ServiceComponent.AUDIO:
          audioComponentFound = true;
          break;
      }
    }
    assertTrue(videoComponentFound,"No Video Component found for TV Channel.");
    assertTrue(audioComponentFound,"No Audio Component found for TV Channel.");
    
    // A good place to modify selected components
  }
  
  protected void serviceComponentsSelectionCompleted(ServiceContext context)
  {
    // A good place to modify selected components
  }
  
  protected void playersInitialized(ServiceContext context)
  {
  }
  
  protected void playersStarted(ServiceContext context)
  {
    // 6. End the test by stopping the zappingContext
    context.stop();
  }
  
  protected void contextStopped(ServiceContext context)
  {
    // Test completed
    System.out.println("Test Completed.");
  }
  
  protected void contextClosed(ServiceContext context)
  {
    // Default Instance cannot be closed
    fail("unexpected close of zapping context.");
    context.removeListener(this);
  }
}