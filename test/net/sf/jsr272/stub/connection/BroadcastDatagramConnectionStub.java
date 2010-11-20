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
package net.sf.jsr272.stub.connection;

import java.io.IOException;

import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.broadcast.ServiceComponent;
import javax.microedition.broadcast.connection.BroadcastConnectionListener;
import javax.microedition.broadcast.connection.BroadcastDatagramConnection;
import javax.microedition.broadcast.esg.CommonMetadataSet;
import javax.microedition.broadcast.esg.QueryException;

import javax.microedition.io.Datagram;

public class BroadcastDatagramConnectionStub implements BroadcastDatagramConnection
{
  protected ServiceComponent _serviceComponent;
  protected Vector _broadcastConnectionListener;
  
  public BroadcastDatagramConnectionStub(ServiceComponent serviceComponent)
  {
    _serviceComponent = serviceComponent;
    _broadcastConnectionListener = new Vector();
  }
  
  // <implements interface="BroadcastConnection">
  
  public boolean isAvailable()
  {
    return false;
  }
  
  public String getMimeType()
  {
    try
    {
      return _serviceComponent.getStringValue(CommonMetadataSet.SERVICE_COMPONENT_MIME_TYPE);
    }
    catch(QueryException qe)
    {
      return null;
    }
  }
  
  public Hashtable getProperties()
  {
    return null;
  }
  
  public void addListener(BroadcastConnectionListener listener)
  {
    if (listener==null)
      return;
    
    if ( _broadcastConnectionListener.contains(listener) )
      return;
    
    _broadcastConnectionListener.addElement(listener);
  }
  
  public void removeListener(BroadcastConnectionListener listener)
  {
    if (listener==null)
      return;
      
    _broadcastConnectionListener.removeElement(listener);
  }
  
  // </implements>
  
  // <implements interface="DatagramConnection">
  
  public int getMaximumLength() 
    throws IOException
  {
    throw new IOException("No datagram in Broadcast Datagram Connection Stub");
  }
    
  public int getNominalLength()
    throws IOException
  {
    throw new IOException("No datagram in Broadcast Datagram Connection Stub");
  }
  
  public void send(Datagram dgram)
    throws IOException
  {
    throw new IOException("BroadcastDatagramConnection is read-only (not server).");
  }
  
  public void receive(Datagram dgram)
    throws IOException
  {
    throw new IOException("No datagram in Broadcast Datagram Connection Stub");
  }
             
  public Datagram newDatagram(int size)
    throws IOException
  {
    throw new IOException("No datagram in Broadcast Datagram Connection Stub");
  }

  public Datagram newDatagram(int size, String addr)
    throws IOException
  {
    throw new IOException("No datagram in Broadcast Datagram Connection Stub");
  }
    
  public Datagram newDatagram(byte[] buf, int size)
    throws IOException
  {
    throw new IOException("No datagram in Broadcast Datagram Connection Stub");
  }
    
  public Datagram newDatagram(byte[] buf, int size, String addr)
    throws IOException
  {
    throw new IOException("No datagram in Broadcast Datagram Connection Stub");
  }
  
  // </implements>
  
  // <implements interface="Connection">
  public void close()
    throws IOException
  {
    _serviceComponent = null;
    _broadcastConnectionListener.removeAllElements();
    _broadcastConnectionListener = null;
  }
  // </implements>
   
}
