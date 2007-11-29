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
package net.sf.jsr272.stub.esg;

import java.util.Date;
import java.util.Hashtable;

import javax.microedition.broadcast.esg.Attribute;
import javax.microedition.broadcast.esg.CommonMetadataSet;
import javax.microedition.broadcast.esg.ProgramEvent;
import javax.microedition.broadcast.esg.Query;
import javax.microedition.broadcast.esg.Service;
import javax.microedition.broadcast.esg.ServiceGuide;
import javax.microedition.broadcast.esg.ServiceGuideListener;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

// a stub implementation of ServiceGuide
// filling in available service guide providers with static stub data.
public class ServiceGuideStub extends ServiceGuide
{
  public static final String STUB_LOGGER = "";
  public static final Level LOG_LEVEL = Level.INFO;
  protected static Logger _log;
  
  public static void initDefaultServiceGuide(String serviceProviderName)
  {
    _log = Logger.getLogger(STUB_LOGGER);
    _log.setLevel(LOG_LEVEL);
    
    _defaultServiceGuideSingleton = new ServiceGuideStub(serviceProviderName);
    _log.info("Initialized ServiceGuideStub as Singleton.");
  }
  
  protected ServiceGuideStub(String serviceProviderName)
    throws IllegalStateException
  {
    super(serviceProviderName);
  }
  
  protected ServiceStub[] _serviceStub = null;
  protected ProgramEventStub[] _programStub = null;
  
  protected void _processServiceGuideUpdate()
    throws Exception
  {
    _serviceStub = new ServiceStub[5];
    _serviceStub[0] = new ServiceStub(this,new Hashtable());
    _serviceStub[1] = new ServiceStub(this,new Hashtable());
    _serviceStub[2] = new ServiceStub(this,new Hashtable());
    _serviceStub[3] = new ServiceStub(this,new Hashtable());
    _serviceStub[4] = new ServiceStub(this,new Hashtable());
    
    _serviceStub[0].setStringValue(CommonMetadataSet.SERVICE_ID,"service://jsr272.sf.net/test/service00");
    _serviceStub[0].setStringValue(CommonMetadataSet.SERVICE_NAME,"Test! Preview");
    _serviceStub[0].setStringValue(CommonMetadataSet.SERVICE_TYPE_NAME,"tv");
    _serviceStub[0].setStringValue(CommonMetadataSet.SERVICE_TYPE,"my:CS:2007:tv");
    _serviceStub[0].setStringValue(CommonMetadataSet.SERVICE_DESCRIPTION,"Test! Preview Channel, a free channel for the whole family");
    _serviceStub[0].setBooleanValue(CommonMetadataSet.SERVICE_IS_FREE,true);
    _serviceStub[0].setBooleanValue(CommonMetadataSet.SERVICE_IS_PROTECTED,false);
    
    _serviceStub[1].setStringValue(CommonMetadataSet.SERVICE_ID,"service://jsr272.sf.net/test/service01");
    _serviceStub[1].setStringValue(CommonMetadataSet.SERVICE_NAME,"Test! Channel One");
    _serviceStub[1].setStringValue(CommonMetadataSet.SERVICE_TYPE_NAME,"tv");
    _serviceStub[1].setStringValue(CommonMetadataSet.SERVICE_TYPE,"my:CS:2007:tv");
    _serviceStub[1].setStringValue(CommonMetadataSet.SERVICE_DESCRIPTION,"Test! Channel One - TV Channel part of Regular Package");
    _serviceStub[1].setBooleanValue(CommonMetadataSet.SERVICE_IS_FREE,false);
    _serviceStub[1].setBooleanValue(CommonMetadataSet.SERVICE_IS_PROTECTED,true);
    
    _serviceStub[2].setStringValue(CommonMetadataSet.SERVICE_ID,"service://jsr272.sf.net/test/service02");
    _serviceStub[2].setStringValue(CommonMetadataSet.SERVICE_NAME,"Test! Channel Two");
    _serviceStub[2].setStringValue(CommonMetadataSet.SERVICE_TYPE_NAME,"tv");
    _serviceStub[2].setStringValue(CommonMetadataSet.SERVICE_TYPE,"my:CS:2007:tv");
    _serviceStub[2].setStringValue(CommonMetadataSet.SERVICE_DESCRIPTION,"Test! Channel Two - TV Channel part of Regular Package");
    _serviceStub[2].setBooleanValue(CommonMetadataSet.SERVICE_IS_FREE,false);
    _serviceStub[2].setBooleanValue(CommonMetadataSet.SERVICE_IS_PROTECTED,true);
    
    _serviceStub[3].setStringValue(CommonMetadataSet.SERVICE_ID,"service://jsr272.sf.net/test/service03");
    _serviceStub[3].setStringValue(CommonMetadataSet.SERVICE_NAME,"Test! Channel Three");
    _serviceStub[3].setStringValue(CommonMetadataSet.SERVICE_TYPE_NAME,"tv");
    _serviceStub[3].setStringValue(CommonMetadataSet.SERVICE_TYPE,"my:CS:2007:tv");
    _serviceStub[3].setStringValue(CommonMetadataSet.SERVICE_DESCRIPTION,"Test! Channel Three - TV Channel part of Regular Package");
    _serviceStub[3].setBooleanValue(CommonMetadataSet.SERVICE_IS_FREE,false);
    _serviceStub[3].setBooleanValue(CommonMetadataSet.SERVICE_IS_PROTECTED,true);
    
    _serviceStub[4].setStringValue(CommonMetadataSet.SERVICE_ID,"service://jsr272.sf.net/test/service04");
    _serviceStub[4].setStringValue(CommonMetadataSet.SERVICE_NAME,"Test! Channel Four");
    _serviceStub[4].setStringValue(CommonMetadataSet.SERVICE_TYPE_NAME,"tv");
    _serviceStub[4].setStringValue(CommonMetadataSet.SERVICE_TYPE,"my:CS:2007:tv");
    _serviceStub[4].setStringValue(CommonMetadataSet.SERVICE_DESCRIPTION,"Test! Channel Four - TV Channel part of Regular Package");
    _serviceStub[4].setBooleanValue(CommonMetadataSet.SERVICE_IS_FREE,false);
    _serviceStub[4].setBooleanValue(CommonMetadataSet.SERVICE_IS_PROTECTED,true);
    
    _programStub = new ProgramEventStub[6];
    _programStub[0] = new ProgramEventStub(this,_serviceStub[0],new Hashtable());
    _programStub[1] = new ProgramEventStub(this,_serviceStub[0],new Hashtable());
    _programStub[2] = new ProgramEventStub(this,_serviceStub[1],new Hashtable());
    _programStub[3] = new ProgramEventStub(this,_serviceStub[2],new Hashtable());
    _programStub[4] = new ProgramEventStub(this,_serviceStub[3],new Hashtable());
    _programStub[5] = new ProgramEventStub(this,_serviceStub[4],new Hashtable());
    
    Date now = new Date();
    Date nextHalfHour = new Date( now.getTime() + 1800*1000 );
    Date nextHour = new Date( now.getTime() + 3600*1000 );
    Date next2Hours = new Date( now.getTime() + 2*3600*1000 );
    Date prevHalfHour = new Date( now.getTime() - 1800*1000 );
    Date prevHour = new Date(now.getTime() - 3600*1000 );
    Date prev2Hours = new Date(now.getTime() - 2 * 3600*1000 );
    
    _programStub[0].setStringValue(CommonMetadataSet.PROGRAM_ID,"program://jsr272.sf.net/test/service00/program00");
    _programStub[0].setStringValue(CommonMetadataSet.PROGRAM_NAME,"Dance with me tonight");
    _programStub[0].setStringValue(CommonMetadataSet.PROGRAM_DESCRIPTION,"Dance show featuring greatest songs of Franck Sinatra performed by 80's singers");
    _programStub[0].setDateValue(CommonMetadataSet.PROGRAM_START_TIME,prevHour);
    _programStub[0].setDateValue(CommonMetadataSet.PROGRAM_END_TIME,nextHalfHour);
    
    _programStub[1].setStringValue(CommonMetadataSet.PROGRAM_ID,"program://jsr272.sf.net/test/service00/program01");
    _programStub[1].setStringValue(CommonMetadataSet.PROGRAM_NAME,"Music and Lyrics");
    _programStub[1].setStringValue(CommonMetadataSet.PROGRAM_DESCRIPTION,"A modern romance (2007) featuring Hugh Grant and Drew Barrymore");
    _programStub[1].setDateValue(CommonMetadataSet.PROGRAM_START_TIME,nextHalfHour);
    _programStub[1].setDateValue(CommonMetadataSet.PROGRAM_END_TIME,next2Hours);
    
    _programStub[2].setStringValue(CommonMetadataSet.PROGRAM_ID,"program://jsr272.sf.net/test/service01/program02");
    _programStub[2].setStringValue(CommonMetadataSet.PROGRAM_NAME,"Test! Program Two");
    _programStub[2].setStringValue(CommonMetadataSet.PROGRAM_DESCRIPTION,"A Program for One or Two");
    _programStub[2].setDateValue(CommonMetadataSet.PROGRAM_START_TIME,prevHour);
    _programStub[2].setDateValue(CommonMetadataSet.PROGRAM_END_TIME,next2Hours);
    
    _programStub[3].setStringValue(CommonMetadataSet.PROGRAM_ID,"program://jsr272.sf.net/test/service02/program03");
    _programStub[3].setStringValue(CommonMetadataSet.PROGRAM_NAME,"Test! Program Three");
    _programStub[3].setStringValue(CommonMetadataSet.PROGRAM_DESCRIPTION,"One, Two, Three Lullaby");
    _programStub[3].setDateValue(CommonMetadataSet.PROGRAM_START_TIME,prev2Hours);
    _programStub[3].setDateValue(CommonMetadataSet.PROGRAM_END_TIME,next2Hours);
    
    _programStub[4].setStringValue(CommonMetadataSet.PROGRAM_ID,"program://jsr272.sf.net/test/service03/program04");
    _programStub[4].setStringValue(CommonMetadataSet.PROGRAM_NAME,"Test! Program Four");
    _programStub[4].setStringValue(CommonMetadataSet.PROGRAM_DESCRIPTION,"Four for ever");
    _programStub[4].setDateValue(CommonMetadataSet.PROGRAM_START_TIME,nextHalfHour);
    _programStub[4].setDateValue(CommonMetadataSet.PROGRAM_END_TIME,nextHour);
    
    _programStub[5].setStringValue(CommonMetadataSet.PROGRAM_ID,"program://jsr272.sf.net/test/service04/program05");
    _programStub[5].setStringValue(CommonMetadataSet.PROGRAM_NAME,"Test! Program Five");
    _programStub[5].setStringValue(CommonMetadataSet.PROGRAM_DESCRIPTION,"Gimme Five");
    _programStub[5].setDateValue(CommonMetadataSet.PROGRAM_START_TIME,nextHalfHour);
    _programStub[5].setDateValue(CommonMetadataSet.PROGRAM_END_TIME,nextHour);
    
    _notifyServiceGuideListener(ServiceGuideListener.SERVICE_GUIDE_BULK_CHANGED,null,null);
  }
  
  protected void _runQueryOnServices(Query query, Attribute[] sortBy, int startOffset, int length)
  {
    if (_serviceStub==null)
      return;
    
    _resultService.addElement(_serviceStub[0]);
    _resultService.addElement(_serviceStub[1]);
    _resultService.addElement(_serviceStub[2]);
    _resultService.addElement(_serviceStub[3]);
    _resultService.addElement(_serviceStub[4]);
  }
  
  protected void _runQueryOnPrograms(Query query, Attribute[] sortBy, int startOffset, int length)
  {
    if (_programStub==null)
      return;
    
    _resultProgram.addElement(_programStub[0]);
    _resultProgram.addElement(_programStub[1]);
    _resultProgram.addElement(_programStub[2]);
    _resultProgram.addElement(_programStub[3]);
    _resultProgram.addElement(_programStub[4]);
    _resultProgram.addElement(_programStub[5]);
  }
  
}
