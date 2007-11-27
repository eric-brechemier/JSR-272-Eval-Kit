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

public abstract class Attribute
{
  protected String _name;
  
  protected Attribute(String name) throws NullPointerException
  {
    if (name==null)
      throw new NullPointerException("Attribute name shall not be null");
    
    // Note: JSR 272 Specification mentions in Attribute#getName() that 
    //       "The return value cannot be null or an empty string."
    //       While NullPointerException is defined to be thrown for *Attribute constructors
    //       when attribute name is null, nothing is defined for the case where it is an 
    //       empty String.
    if ( name.length()==0 )
      throw new IllegalArgumentException("Attribute name shall not be an empty String");
    
    _name = name;
  }
  
  public String getName()
  {
    return _name;
  }
  
  // <overrides class="Object">
  
  public boolean equals(Object obj)
  {
    if ( !(obj instanceof Attribute) )
      return false;
    
    Attribute att = (Attribute)obj;
    
    return 
    (
         this.getClass().equals( att.getClass() ) 
      && this.getName().equals( att.getName() ) 
    );
  }
  
  public int hashCode()
  {
    return super.hashCode();
  }
  
  // </overrides>
  
}