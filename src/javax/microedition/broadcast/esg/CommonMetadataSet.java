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

import javax.microedition.broadcast.esg.MetadataSet;

public class CommonMetadataSet implements MetadataSet
{
  // Note: I think this class should define a Singleton.
  //       There is no meaning in having several instances
  //       of CommonMetadataSet;
  
  
  // Note: the actual values set to these constants are not defined in JSR 272 
  //       Specification. I chose to use CamelCaseString value of constant name 
  //       for consistency with values defined in JSR 272 for String constants.
  //       I chose to keep acronyms capitalized, e.g. ServiceComponentSDPRef.
  
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // SERVICE_* const are applicable to ProgramEvent, PurchaseObject, Service, ServiceComponent
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  public static final StringAttribute  SERVICE_ID = new StringAttribute("ServiceId");
  public static final StringAttribute  SERVICE_NAME = new StringAttribute("ServiceName");
  // a usable version of SERVICE_TYPE, with values restricted to "tv", "radio" and "data"
  public static final StringAttribute  SERVICE_TYPE_NAME = new StringAttribute("ServiceTypeName");
  // an extensible version of SERVICE_TYPE, e.g. based on TV-Anytime classification schemes
  public static final StringAttribute  SERVICE_TYPE = new StringAttribute("ServiceType");
  public static final StringAttribute  SERVICE_DESCRIPTION = new StringAttribute("ServiceDescription");
  public static final BooleanAttribute SERVICE_IS_FREE = new BooleanAttribute("ServiceIsFree");
  public static final BooleanAttribute SERVICE_IS_PROTECTED = new BooleanAttribute("ServiceIsProtected");
  public static final StringAttribute  SERVICE_GENRE = new StringAttribute("ServiceGenre");
  public static final StringAttribute  SERVICE_PARENTAL_RATING = new StringAttribute("ServiceParentalRating");
  public static final StringAttribute  SERVICE_REL_MATERIAL = new StringAttribute("ServiceRelMaterial");
  public static final StringAttribute  SERVICE_AUX_LOGO = new StringAttribute("ServiceAuxLogo");
  public static final StringAttribute  SERVICE_AUX_SOUND = new StringAttribute("ServiceAuxSound");
  public static final StringAttribute  SERVICE_AUX_CLIP = new StringAttribute("ServiceAuxClip");
  
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // SERVICE_COMPONENT_* const are applicable to ProgramEvent, Service, ServiceComponent
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  public static final StringAttribute  SERVICE_COMPONENT_ACCESS_ID = new StringAttribute("ServiceComponentAccessId");
  public static final StringAttribute  SERVICE_COMPONENT_ACCESS_APP_TYPE = new StringAttribute("ServiceComponentAccessAppType");
  public static final StringAttribute  SERVICE_COMPONENT_KEY_MANAG_SYS = new StringAttribute("ServiceComponentKeyManagSys");
  // Two ways to access the Access information part of Service Description Protocol (SDP) file.
  // 1. (most frequent, most convenient from hanset perspective) "inline"
  //    The SDP file content is included inline inside ESG metadata
  public static final StringAttribute  SERVICE_COMPONENT_SDP_STRING = new StringAttribute("ServiceComponentSDPString");
  // 2. (most convenient from server perspective) "out-of-band"
  //    The SDP file can be acquired from broadcast ESG or downloaded using HTTP
  public static final StringAttribute  SERVICE_COMPONENT_SDP_REF = new StringAttribute("ServiceComponentSDPRef");
  // 2+ (barely used) The SDP file can be acquired in a separate broadcast stream,
  //    and here is the SDP of that session, e.g. one SDP inline must be provided
  //    to indicate where to get the other SDP out-of-band...
  //    In theory in DVB-IPDC V1, this information is mandatory.
  //    In practice, this information is often left empty (or close to be empty) by server side.
  public static final StringAttribute  SERVICE_COMPONENT_SDP_STREAM = new StringAttribute("ServiceComponentSDPStream");
  // Note: SERVICE_COMPONENT_LANGUAGE would be identical to SERVICE_COMPONENT_AUD_LANG 
  //       when ServiceComponent#getType() returns ServiceComponent.AUDIO,
  //       and identical to SERVICE_COMPONENT_CLOSED_CAPTIONS_LANG
  //       when ServiceComponent#getType() returns ServiceComponent.SUBTITLES.
  //       I also expect SERVICE_COMPONENT_VID_OPEN_CAPTIONS_LANG and 
  //       SERVICE_COMPONENT_VID_SIGN_LANG to be identical to SERVICE_COMPONENT_LANGUAGE 
  //       when ServiceComponent#getType() returns ServiceComponent.VIDEO.
  public static final StringAttribute  SERVICE_COMPONENT_LANGUAGE = new StringAttribute("ServiceComponentLanguage");
  public static final StringAttribute  SERVICE_COMPONENT_AUD_LANG = new StringAttribute("ServiceComponentAudLang");
  public static final StringAttribute  SERVICE_COMPONENT_CLOSED_CAPTIONS_LANG = new StringAttribute("ServiceComponentClosedCaptionsLang");
  // Note: open captions are part of the video signal, closed captions are separated subtitles 
  //       only displayed when selected.
  public static final StringAttribute  SERVICE_COMPONENT_VID_OPEN_CAPTIONS_LANG = new StringAttribute("ServiceComponentVidOpenCaptionsLang");
  public static final StringAttribute  SERVICE_COMPONENT_VID_SIGN_LANG = new StringAttribute("ServiceComponentVidSignLang");
  public static final NumericAttribute SERVICE_COMPONENT_AVERAGE_AUD_RATE = new NumericAttribute("ServiceComponentAverageAudRate",NumericAttribute.INTEGER_TYPE,false);
  public static final NumericAttribute SERVICE_COMPONENT_MAX_AUD_RATE = new NumericAttribute("ServiceComponentMaxAudRate",NumericAttribute.INTEGER_TYPE,false);
  public static final NumericAttribute SERVICE_COMPONENT_AVERAGE_VID_RATE = new NumericAttribute("ServiceComponentAverageVidRate",NumericAttribute.INTEGER_TYPE,false);
  public static final NumericAttribute SERVICE_COMPONENT_MAX_VID_RATE = new NumericAttribute("ServiceComponentMaxVidRate",NumericAttribute.INTEGER_TYPE,false);
  // Note: SERVICE_COMPONENT_MIME_TYPE would identical to SERVICE_COMPONENT_DOWNLOAD_FILE_FORMAT
  //       when ServiceComponent#getType() returns ServiceComponent.OTHER (corresponding to a file or stream)
  public static final StringAttribute  SERVICE_COMPONENT_MIME_TYPE = new StringAttribute("ServiceComponentMimeType");
  public static final StringAttribute  SERVICE_COMPONENT_DOWNLOAD_FILE_FORMAT = new StringAttribute("ServiceComponentDownloadFileFormat");
  
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // PROGRAM_* const are applicable to ProgramEvent, PurchaseObject, Service, ServiceComponent
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  public static final StringAttribute  PROGRAM_CONTENT_ID = new StringAttribute("ProgramContentId");
  // Program Title
  public static final StringAttribute  PROGRAM_CONTENT_NAME = new StringAttribute("ProgramContentName");
  // Program Synopsis
  public static final StringAttribute  PROGRAM_CONTENT_DESCRIPTION = new StringAttribute("ProgramContentDescription");
  public static final StringAttribute  PROGRAM_CONTENT_GENRE = new StringAttribute("ProgramContentGenre");
  public static final StringAttribute  PROGRAM_CONTENT_PARENTAL_RATING = new StringAttribute("ProgramContentParentalRating");
  public static final StringAttribute  PROGRAM_CONTENT_TYPE = new StringAttribute("ProgramContentType");
  public static final StringAttribute  PROGRAM_CONTENT_AUX_LOGO = new StringAttribute("ProgramContentAuxLogo");
  public static final StringAttribute  PROGRAM_CONTENT_AUX_SOUND = new StringAttribute("ProgramContentAuxSound");
  public static final StringAttribute  PROGRAM_CONTENT_AUX_CLIP = new StringAttribute("ProgramContentAuxClip");
  // ~~~~~~~~~~~~~~~
  // Program Event
  // ~~~~~~~~~~~~~~~
  // Note: PROGRAM_ID seems to be a duplicate of above PROGRAM_CONTENT_ID,
  //       PROGRAM_NAME seems to be a duplicate of above PROGRAM_CONTENT_NAME,
  //       PROGRAM_DESCRIPTION seems to be a duplicate of above PROGRAM_CONTENT_DESCRIPTION...
  //       The distinction between PROGRAM_CONTENT_ and PROGRAM_ prefix seems to correspond to the difference
  //       between program Content and program Event, the first one describing a Content description,
  //       and the event a specific instance scheduled on a specific date.
  public static final StringAttribute  PROGRAM_ID = new StringAttribute("ProgramId");
  // Program Title
  public static final StringAttribute  PROGRAM_NAME = new StringAttribute("ProgramName");
  // Program Synopsis
  public static final StringAttribute  PROGRAM_DESCRIPTION =  new StringAttribute("ProgramDescription");
  public static final DateAttribute    PROGRAM_START_TIME = new DateAttribute("ProgramStartTime");
  public static final DateAttribute    PROGRAM_END_TIME = new DateAttribute("ProgramEndTime");
  public static final BooleanAttribute PROGRAM_IS_FREE = new BooleanAttribute("ProgramIsFree");
  public static final BooleanAttribute PROGRAM_IS_PROTECTED = new BooleanAttribute("ProgramIsProtected");
  public static final DateAttribute    PROGRAM_DIST_START = new DateAttribute("ProgramDistStart");
  public static final DateAttribute    PROGRAM_DIST_END = new DateAttribute("ProgramDistEnd");
  public static final StringAttribute  PROGRAM_REL_MATERIAL = new StringAttribute("ProgramRelMaterial");
  
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // PURCHASE_* const are applicable to ProgramEvent, PurchaseObject, Service
  // Three different object types are hiding behind this PURCHASE_ entry point:
  // - Purchase itself, more commonly a "Product" that you can buy, with financial details
  // - Purchase Item, more commonly a "Bundle" or "Bouquet" of channels,
  //   (the only thing you can purchase in DVB-IPDC V1 standard)
  //   e.g. a packaged set of channels that you can subscribe.
  //   Note: this could also be a package of events in the case of pay-per-view events.
  // - Purchase Channel, a Portal typically a web site, where you can do the purchase
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  public static final StringAttribute  PURCHASE_ID = new StringAttribute("PurchaseId");
  // a String value which could include a complete description, not just a number
  // avoids any rounding issue in displayed price.
  public static final StringAttribute  PURCHASE_PRICE = new StringAttribute("PurchasePrice");
  public static final StringAttribute  PURCHASE_CURRENCY = new StringAttribute("PurchaseCurrency");
  public static final DateAttribute    PURCHASE_VALID_FROM = new DateAttribute("PurchaseValidFrom");
  public static final DateAttribute    PURCHASE_VALID_TO = new DateAttribute("PurchaseValidTo");
  public static final StringAttribute  PURCHASE_AUX_LOGO = new StringAttribute("PurchaseAuxLogo");
  public static final StringAttribute  PURCHASE_AUX_SOUND = new StringAttribute("PurchaseAuxSound");
  public static final StringAttribute  PURCHASE_AUX_CLIP = new StringAttribute("PurchaseAuxClip");
  // ~~~~~~~~~~~~~~
  // Purchase Item
  // ~~~~~~~~~~~~~~
  public static final StringAttribute  PURCHASE_ITEM_ID = new StringAttribute("PurchaseItemId");
  public static final StringAttribute  PURCHASE_ITEM_NAME = new StringAttribute("PurchaseItemName");
  public static final StringAttribute  PURCHASE_ITEM_DESCRIPTION = new StringAttribute("PurchaseItemDescription");
  public static final StringAttribute  PURCHASE_ITEM_PARENTAL_RATING = new StringAttribute("PurchaseItemParentalRating");
  public static final StringAttribute  PURCHASE_ITEM_REL_MATERIAL = new StringAttribute("PurchaseItemRelMaterial");
  // ~~~~~~~~~~~~~~~~~
  // Purchase Channel
  // ~~~~~~~~~~~~~~~~~
  public static final StringAttribute  PURCHASE_CHANNEL_ID = new StringAttribute("PurchaseChannelId");
  public static final StringAttribute  PURCHASE_CHANNEL_NAME = new StringAttribute("PurchaseChannelName");
  public static final StringAttribute  PURCHASE_CHANNEL_DESCRIPTION = new StringAttribute("PurchaseChannelDescription");
  public static final StringAttribute  PURCHASE_CHANNEL_PORTAL = new StringAttribute("PurchaseChannelPortal");
  // I expect the "contact" to be the operator, thus this may point to the website of the operator
  public static final StringAttribute  PURCHASE_CHANNEL_CONTACT = new StringAttribute("PurchaseChannelContact");
  
  // The order of Attribute in the arrays below is not defined in JSR 272 Specification
  protected static final Attribute[] _ALL_ATTRIBUTES = 
  {
    // SERVICE_*
    SERVICE_ID,
    SERVICE_NAME,
    SERVICE_TYPE_NAME,
    SERVICE_TYPE,
    SERVICE_DESCRIPTION,
    SERVICE_IS_FREE,
    SERVICE_IS_PROTECTED,
    SERVICE_GENRE,
    SERVICE_PARENTAL_RATING,
    SERVICE_REL_MATERIAL,
    SERVICE_AUX_LOGO,
    SERVICE_AUX_SOUND,
    SERVICE_AUX_CLIP,
    // SERVICE_COMPONENT_*
    SERVICE_COMPONENT_ACCESS_ID,
    SERVICE_COMPONENT_ACCESS_APP_TYPE,
    SERVICE_COMPONENT_KEY_MANAG_SYS,
    SERVICE_COMPONENT_SDP_STRING,
    SERVICE_COMPONENT_SDP_REF,
    SERVICE_COMPONENT_SDP_STREAM,
    SERVICE_COMPONENT_LANGUAGE,
    SERVICE_COMPONENT_AUD_LANG,
    SERVICE_COMPONENT_CLOSED_CAPTIONS_LANG,
    SERVICE_COMPONENT_VID_OPEN_CAPTIONS_LANG,
    SERVICE_COMPONENT_VID_SIGN_LANG,
    SERVICE_COMPONENT_AVERAGE_AUD_RATE,
    SERVICE_COMPONENT_MAX_AUD_RATE,
    SERVICE_COMPONENT_AVERAGE_VID_RATE,
    SERVICE_COMPONENT_MAX_VID_RATE,
    SERVICE_COMPONENT_MIME_TYPE,
    SERVICE_COMPONENT_DOWNLOAD_FILE_FORMAT,
    // PROGRAM_*, PROGRAM_CONTENT_*
    PROGRAM_CONTENT_ID,
    PROGRAM_CONTENT_NAME,
    PROGRAM_CONTENT_DESCRIPTION,
    PROGRAM_CONTENT_GENRE,
    PROGRAM_CONTENT_PARENTAL_RATING,
    PROGRAM_CONTENT_TYPE,
    PROGRAM_CONTENT_AUX_LOGO,
    PROGRAM_CONTENT_AUX_SOUND,
    PROGRAM_CONTENT_AUX_CLIP,
    // PROGRAM_*, PROGRAM_*
    PROGRAM_ID,
    PROGRAM_NAME,
    PROGRAM_DESCRIPTION,
    PROGRAM_IS_FREE,
    PROGRAM_IS_PROTECTED,
    PROGRAM_START_TIME,
    PROGRAM_END_TIME,
    PROGRAM_DIST_START,
    PROGRAM_DIST_END,
    PROGRAM_REL_MATERIAL,
    // PURCHASE_*, PURCHASE_* 
    PURCHASE_ID,
    PURCHASE_PRICE,
    PURCHASE_CURRENCY,
    PURCHASE_VALID_FROM,
    PURCHASE_VALID_TO,
    PURCHASE_AUX_LOGO,
    PURCHASE_AUX_SOUND,
    PURCHASE_AUX_CLIP,
    // PURCHASE_*, PURCHASE_ITEM_*,
    PURCHASE_ITEM_ID,
    PURCHASE_ITEM_NAME,
    PURCHASE_ITEM_DESCRIPTION,
    PURCHASE_ITEM_PARENTAL_RATING,
    PURCHASE_ITEM_REL_MATERIAL,
    // PURCHASE_*, PURCHASE_CHANNEL_*,
    PURCHASE_CHANNEL_ID,
    PURCHASE_CHANNEL_NAME,
    PURCHASE_CHANNEL_DESCRIPTION,
    PURCHASE_CHANNEL_PORTAL,
    PURCHASE_CHANNEL_CONTACT,
  };
  
  protected static final Attribute[] _ALL_SERVICE_ATTRIBUTES =
  {
    // SERVICE_*
    SERVICE_ID,
    SERVICE_NAME,
    SERVICE_TYPE_NAME,
    SERVICE_TYPE,
    SERVICE_DESCRIPTION,
    SERVICE_IS_FREE,
    SERVICE_IS_PROTECTED,
    SERVICE_GENRE,
    SERVICE_PARENTAL_RATING,
    SERVICE_REL_MATERIAL,
    SERVICE_AUX_LOGO,
    SERVICE_AUX_SOUND,
    SERVICE_AUX_CLIP,
  };
  
  protected static final Attribute[] _ALL_SERVICE_COMPONENT_ATTRIBUTES =
  {
    // SERVICE_COMPONENT_*
    SERVICE_COMPONENT_ACCESS_ID,
    SERVICE_COMPONENT_ACCESS_APP_TYPE,
    SERVICE_COMPONENT_KEY_MANAG_SYS,
    SERVICE_COMPONENT_SDP_STRING,
    SERVICE_COMPONENT_SDP_REF,
    SERVICE_COMPONENT_SDP_STREAM,
    SERVICE_COMPONENT_LANGUAGE,
    SERVICE_COMPONENT_AUD_LANG,
    SERVICE_COMPONENT_CLOSED_CAPTIONS_LANG,
    SERVICE_COMPONENT_VID_OPEN_CAPTIONS_LANG,
    SERVICE_COMPONENT_VID_SIGN_LANG,
    SERVICE_COMPONENT_AVERAGE_AUD_RATE,
    SERVICE_COMPONENT_MAX_AUD_RATE,
    SERVICE_COMPONENT_AVERAGE_VID_RATE,
    SERVICE_COMPONENT_MAX_VID_RATE,
    SERVICE_COMPONENT_MIME_TYPE,
    SERVICE_COMPONENT_DOWNLOAD_FILE_FORMAT,
  };
  
  protected static final Attribute[] _ALL_PROGRAM_ATTRIBUTES =
  {
    // PROGRAM_*, PROGRAM_CONTENT_*
    PROGRAM_CONTENT_ID,
    PROGRAM_CONTENT_NAME,
    PROGRAM_CONTENT_DESCRIPTION,
    PROGRAM_CONTENT_GENRE,
    PROGRAM_CONTENT_PARENTAL_RATING,
    PROGRAM_CONTENT_TYPE,
    PROGRAM_CONTENT_AUX_LOGO,
    PROGRAM_CONTENT_AUX_SOUND,
    PROGRAM_CONTENT_AUX_CLIP,
    // PROGRAM_*, PROGRAM_*
    PROGRAM_ID,
    PROGRAM_NAME,
    PROGRAM_DESCRIPTION,
    PROGRAM_IS_FREE,
    PROGRAM_IS_PROTECTED,
    PROGRAM_START_TIME,
    PROGRAM_END_TIME,
    PROGRAM_DIST_START,
    PROGRAM_DIST_END,
    PROGRAM_REL_MATERIAL,
  };
  
  protected static final Attribute[] _ALL_PURCHASE_ATTRIBUTES =
  {
    // PURCHASE_*, PURCHASE_* 
    PURCHASE_ID,
    PURCHASE_PRICE,
    PURCHASE_CURRENCY,
    PURCHASE_VALID_FROM,
    PURCHASE_VALID_TO,
    PURCHASE_AUX_LOGO,
    PURCHASE_AUX_SOUND,
    PURCHASE_AUX_CLIP,
    // PURCHASE_*, PURCHASE_ITEM_*,
    PURCHASE_ITEM_ID,
    PURCHASE_ITEM_NAME,
    PURCHASE_ITEM_DESCRIPTION,
    PURCHASE_ITEM_PARENTAL_RATING,
    PURCHASE_ITEM_REL_MATERIAL,
    // PURCHASE_*, PURCHASE_CHANNEL_*,
    PURCHASE_CHANNEL_ID,
    PURCHASE_CHANNEL_NAME,
    PURCHASE_CHANNEL_DESCRIPTION,
    PURCHASE_CHANNEL_PORTAL,
    PURCHASE_CHANNEL_CONTACT,
  };
  
  
  // <implements interface="MetadataSet">
  
  public String getDescription()
  {
    return "Common metadata set, JSR 272 specification version 1";
  }
  
  public Attribute[] getValidAttributes()
  {
    return _ALL_ATTRIBUTES;
  }
  
  public Attribute[] getValidServiceAttributes()
  {
    return _ALL_SERVICE_ATTRIBUTES;
  }
  
  public Attribute[] getValidServiceComponentAttributes()
  {
    return _ALL_SERVICE_COMPONENT_ATTRIBUTES;
  }
  
  public Attribute[] getValidProgramAttributes()
  {
    return _ALL_PROGRAM_ATTRIBUTES;
  }
  
  public Attribute[] getValidPurchaseAttributes()
  {
    return _ALL_PURCHASE_ATTRIBUTES;
  }
  
  // </implements>
  
}