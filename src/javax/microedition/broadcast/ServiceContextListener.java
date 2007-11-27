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

import javax.microedition.broadcast.ServiceContext;

public interface ServiceContextListener
{
  public static final String CONTEXT_STOPPED = "contextStopped";
  public static final String SELECTION_INITIATED = "selectionInitiated";
  public static final String COMPONENTS_IDENTIFIED = "componentsIdentified";
  public static final String COMPONENTS_SET = "componentsSet";
  public static final String PLAYERS_REALIZED = "playersRealized";
  public static final String PRESENTATION_STARTED = "presentationStarted";
  public static final String CONTEXT_CLOSED = "contextClosed";
  
  public static final String REASON_ALTERNATIVE_CONTENT = "alternativeContent";
  public static final String REASON_APPLICATION_REQUESTED = "applicationRequested";
  public static final String REASON_EQUIVALENT_SERVICE = "equivalentService";
  public static final String REASON_NO_RIGHT = "noRight";
  public static final String REASON_NORMAL_CONTENT = "normalContent";
  public static final String REASON_OTHER = "other";
  public static final String REASON_RESOURCE_UNAVAILABLE = "resourcesUnavailable";
  public static final String REASON_SELECTION_FAILED = "selectionFailed";
  public static final String REASON_SERVICE_UNAVAILABLE = "serviceUnavailable";
  public static final String REASON_SWITCH_FORCED = "switchForced";
  
  public void contextUpdate(ServiceContext context, String event, Object data);
  
}