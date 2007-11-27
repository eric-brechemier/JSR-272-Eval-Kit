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

public class Query
{
  // Note: in order to check whether the Query is valid or not,
  //       we would need a method to get the underlying Attribute
  //       and component queries. Alternatively, we would need to 
  //       add a boolean isValid(ServiceGuide) method here in
  //       order to implement checks on top of this class
  //       without knowing the underlying implementation.
  //
  //       Currently, it is not possible to check whether a Query
  //       is valid or not [1] without knowing the underlying 
  //       implementation.
  //
  //       Since Attribute is an abstract class, an Attribute used to
  //       create a Query must either be
  //       - a constant reference from the CommonMetadataSet
  //       - an attribute defined in MetadataSet#getValidAttributes()
  //         with a MetadataSet in ServiceGuide#getSupportedMetadataSets()
  //         based on the attributes supported by the database [2]:
  //
  //         From ServiceGuide#getSupportedMetadataSets() [2]
  //         "(...) the list of MetadataSet specs supported by the 
  //         database implementation. At a minimum, CommonMetadataSet
  //         will be returned."
  //
  //       To facilitate the test, a lookup method should also be added in
  //       MetadataSet to check quickly whether an attribute is defined,
  //       e.g. boolean MetadataSet#isValid(Attribute attribute)
  //       and the method could be generalized to all supported MetadataSets
  //       in ServiceGuide by iterating on the result of 
  //       ServiceGuide#getSupportedMetadataSets().
  //
  //       Based on above assumptions, the Query#isValid(ServiceGuide)
  //       method could look like:
  //
  //       boolean isValid(ServiceGuide serviceGuide)
  //       {
  //         if ( _isSimpleQuery() )
  //           return serviceGuide.isValid( _attribute() )
  //         else
  //         {
  //           for(int i=0; i<_components.length; i++)
  //           {
  //             if ( !_components[i].isValid(serviceGuide) )
  //               return false;
  //           }
  //           return true;
  //         }
  //
  //       All in all, validation is only one example of functionality to be 
  //       added to the empty Query class. 
  //       Adding an accept(Visitor visitor) method, based on the visitor pattern 
  //       would allow this class to be extensible with additional behaviors.
  //       In this case, the validation in ServiceGuide methods would look like:
  //
  //       (...) query.accept( new validationVisitor(this) );
  //
  //       Reference: [1] ServiceGuide.html#query_rules
  //                  [2] ServiceGuide#getSupportedMetadataSets()
  
  
}