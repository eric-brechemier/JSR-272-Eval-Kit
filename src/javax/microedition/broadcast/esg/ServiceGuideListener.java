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
package javax.microedition.broadcast.esg;

import javax.microedition.broadcast.esg.ServiceGuideData;

public interface ServiceGuideListener
{
  public static final String NEW_PROGRAM_LISTED = "NewProgramListed";
  public static final String NEW_SERVICE_LISTED = "NewServiceListed";
  public static final String PROGRAM_CHANGED = "ProgramChanged";
  public static final String PROGRAM_DELETED = "ProgramDeleted";
  public static final String PURCHASE_OBJECT_ADDED = "PurchaseObjectAdded";
  public static final String PURCHASE_OBJECT_CHANGED = "PurchaseObjectChanged";
  public static final String PURCHASE_OBJECT_DELETED = "PurchaseObjectDeleted";
  public static final String SERVICE_CHANGED = "ServiceChanged";
  public static final String SERVICE_DELETED = "ServiceDeleted";
  public static final String SERVICE_GUIDE_BULK_CHANGED = "ServiceGuideBulkChanged";
  public static final String SERVICE_GUIDE_UPDATE_COMPLETED = "ServiceGuideUpdateCompleted";
  public static final String SERVICE_GUIDE_UPDATE_FAILED = "ServiceGuideUpdateFailed";
  public static final String SERVICE_GUIDE_UPDATE_STARTED = "ServiceGuideUpdateStarted";
  
  public void serviceGuideUpdated(String event, ServiceGuideData serviceGuideData, Object eventData);
}