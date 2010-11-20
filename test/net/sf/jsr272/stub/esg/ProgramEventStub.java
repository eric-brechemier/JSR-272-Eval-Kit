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
package net.sf.jsr272.stub.esg;

import java.util.Hashtable;

import javax.microedition.broadcast.esg.Attribute;
import javax.microedition.broadcast.esg.DataUnavailableException;
import javax.microedition.broadcast.esg.ProgramEvent;
import javax.microedition.broadcast.esg.Service;
import javax.microedition.broadcast.esg.ServiceGuide;

import net.sf.jsr272.stub.esg.ServiceStub;
import net.sf.jsr272.stub.esg.ServiceGuideStub;
import net.sf.jsr272.stub.esg.ServiceGuideDataStub;

public class ProgramEventStub extends ServiceGuideDataStub implements ProgramEvent
{
  protected ServiceStub _service;
  
  public ProgramEventStub(ServiceGuideStub serviceGuide, ServiceStub service, Hashtable data)
  {
    super (serviceGuide,data);
    _service = service;
  }
  
  // <implements interface="ProgramEvent">
  
  public Service getService()
  {
    return _service;
  }
   
  // </implements>
  
  protected boolean _isDataAvailable(Attribute attribute)
  {
    return
      (
        super._isDataAvailable(attribute)
        ||
        _service._isDataAvailable(attribute)
      );
  }
  
  protected Object _getAttributeValues(Attribute attribute)
    throws NullPointerException, IllegalArgumentException, DataUnavailableException
  {
    _checkAttributeValidAndDataAvailable(attribute);
    
    if ( _data.containsKey(attribute) )
      return _data.get(attribute);
    else
      return _service._getAttributeValues(attribute);
      
  }
  
  
}
