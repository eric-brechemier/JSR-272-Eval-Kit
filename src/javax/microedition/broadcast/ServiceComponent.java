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

import javax.microedition.broadcast.esg.ServiceGuideData;

public interface ServiceComponent extends ServiceGuideData
{
  public static final int OTHER = 0;
  public static final int AUDIO = 1;
  public static final int VIDEO = 2;
  public static final int SUBTITLES = 3;
  
  public int getType();
}