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
package net.sf.jsr272.stub.platform;

import javax.microedition.broadcast.esg.Service;

import javax.microedition.broadcast.platform.PlatformProvider;
import javax.microedition.broadcast.platform.PlatformProviderSelector;

import net.sf.jsr272.stub.esg.ServiceStub;
import net.sf.jsr272.stub.esg.ServiceGuideStub;

import net.sf.jsr272.stub.platform.PlatformProviderStub;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

// a stub implementation of PlatformProviderSelector
// filling in available platform provider with static stub data.
public class PlatformProviderSelectorStub extends PlatformProviderSelector
{
  public static final String STUB_LOGGER = "";
  public static final Level LOG_LEVEL = Level.INFO;
  protected static Logger _log;
  
  public static void useSingleton()
  {
    _log = Logger.getLogger(STUB_LOGGER);
    _log.setLevel(LOG_LEVEL);
    
    _singleton = new PlatformProviderSelectorStub();
    _log.info("Initialized PlatformProviderSelectorStub as Singleton.");
  }
  
  // return true if the Service is valid for current Platform Provider
  // (i.e. not from a different Platform Provider)
  public static boolean isValid(Service service)
  {
    if ( getCurrentProvider()==null )
      return false;
    
    if ( !(service instanceof ServiceStub) )
      return false;
    
    ServiceStub serviceStub = (ServiceStub) service;
    return (  getCurrentProvider().equals( serviceStub.getPlatformProvider() )  );
  }
  
  protected void _processPlatformDiscovery()
  {
    _log.info("Platform Discovery Started: filling stub data in available providers.");
    
    _platformProvider.addElement( PlatformProviderStub.newPlatformProvider(101,"Platform 101") );
    _notifyPlatformProviderSelectorListener(25); // 25% progress
    
    _platformProvider.addElement( PlatformProviderStub.newPlatformProvider(777,"Platform 777") );
    _notifyPlatformProviderSelectorListener(50); // 50% progress
    
    _platformProvider.addElement( PlatformProviderStub.newPlatformProvider(42,"Platform Forty-Two") );
    _notifyPlatformProviderSelectorListener(75); // 75% progress
    
    _log.info("Platform Discovery Completed. Available Providers: "+_platformProvider.size());
  }
  
  protected void _setCurrentProvider(PlatformProvider platformProvider)
  {
    super._setCurrentProvider(platformProvider);
    if ( platformProvider.getID()==42 )
      ServiceGuideStub.initDefaultServiceGuide("Test!");
    else
        ServiceGuideStub.initDefaultServiceGuide( "Service Guide for Platform Provider "+platformProvider.getID() );
  }
  
}
