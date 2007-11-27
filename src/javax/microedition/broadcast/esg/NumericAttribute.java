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

import javax.microedition.broadcast.esg.Attribute;

public class NumericAttribute extends Attribute
{
  public static final int INTEGER_TYPE = 0;
  public static final int FIXED_POINT_TYPE = 1;
  public static final int FLOATING_POINT_TYPE = 2;
  
  protected int _type;
  protected boolean _signed;
  
  public NumericAttribute(String attribute, int type, boolean signed) 
    throws NullPointerException
  {
    super(attribute);
    
    if (type<INTEGER_TYPE || type>FLOATING_POINT_TYPE)
      throw new IllegalArgumentException("Type must be one of INTEGER_TYPE, FIXED_POINT_TYPE, FLOATING_POINT_TYPE");
    
    _type = type;
    _signed = signed;
  }
  
  public int getType()
  {
    return _type;
  }
  
  public boolean isSigned()
  {
    return _signed;
  }
  
  // <overrides class="Object">
  
  public boolean equals(Object obj)
  {
    if ( !(obj instanceof NumericAttribute) )
      return false;
    
    NumericAttribute num = (NumericAttribute)obj;
    
    return
    (
         getType() == num.getType()
      && isSigned() == num.isSigned()
      && super.equals(obj)
    );
  }
  
  // </overrides>
}