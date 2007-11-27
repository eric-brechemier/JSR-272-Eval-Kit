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

import java.util.Date;

import javax.microedition.broadcast.esg.BooleanAttribute;
import javax.microedition.broadcast.esg.DateAttribute;
import javax.microedition.broadcast.esg.NumericAttribute;
import javax.microedition.broadcast.esg.Query;
import javax.microedition.broadcast.esg.StringAttribute;

public class QueryComposer
{
  // ===========================================================
  // static methods defined by JSR 272 Specification
  // ===========================================================
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Generic Object Predicates
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  public static Query equivalent(Attribute attribute, Object value)
    throws NullPointerException, IllegalArgumentException
  {
    _checkNotNull(attribute,value);
    
    if (attribute instanceof BooleanAttribute)
    {
      if ( !(value instanceof Boolean) )
        throw new IllegalArgumentException("Boolean Attribute must only be compared with Boolean values.");
    }
    
    if (attribute instanceof DateAttribute)
    {
      if ( !(value instanceof Date) )
        throw new IllegalArgumentException("Date Attribute must only be compared with Date values.");
    }
    
    if (attribute instanceof NumericAttribute)
    {
      if ( !_getSingleton()._isNumericValue(value) )
        throw new IllegalArgumentException("Numeric Attribute must only be compared with numeric values.");
    }
    
    if (attribute instanceof StringAttribute)
    {
      if ( !(value instanceof String) )
        throw new IllegalArgumentException("String Attribute must only be compared with String values.");
    }
    
    return _getSingleton()._newEquivalentQuery(attribute,value);
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Boolean Predicates
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  // can be used in place of equivalent(attribute,new Boolean(value))
  // using isTrue(attribute) when value is true.
  // not(isTrue(attribute)) could be used when value is false.
  public static Query isTrue(BooleanAttribute attribute)
  {
    _checkAttributeNameNotNull(attribute);
    return _getSingleton()._newIsTrueQuery(attribute);
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Numeric Predicates
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  // specific method for the above equivalent(Attribute,Object) with double.
  // Actually, "equivalent" should really be seen as "equal" in this case,
  // based on following comment in the specification:
  // "Two values that are closely matching may not result in an equivalence due to precision errors. 
  //  Applications are advised to use a combination of greaterThan and lessThan for more controlled behavior."
  public static Query equivalent(NumericAttribute attribute, double value)
    throws NullPointerException
  {
    _checkAttributeNameNotNull(attribute);
    return _getSingleton()._newEquivalentQuery(attribute,value);
  }
  
  public static Query lessThan(NumericAttribute attribute, double value)
    throws NullPointerException
  {
    _checkAttributeNameNotNull(attribute);
    return _getSingleton()._newLessThanQuery(attribute,value);
  }
  
  public static Query greaterThan(NumericAttribute attribute, double value)
    throws NullPointerException
  {
    _checkAttributeNameNotNull(attribute);
    return _getSingleton()._newGreaterThanQuery(attribute,value);
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  // String Predicates
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  public static Query contains(StringAttribute attribute, String value)
    throws NullPointerException, IllegalArgumentException
  {
    _checkNotNull(attribute,value);
    
    if (value.length()==0)
      throw new IllegalArgumentException("value must not be an empty string.");
    
    return _getSingleton()._newContainsQuery(attribute,value);
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Date Predicates
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  public static Query before(DateAttribute attribute, Date value)
    throws NullPointerException
  {
    _checkNotNull(attribute,value);
    return _getSingleton()._newBeforeQuery(attribute,value);
  }
  
  public static Query after(DateAttribute attribute, Date value)
    throws NullPointerException
  {
    _checkNotNull(attribute,value);
    return _getSingleton()._newAfterQuery(attribute,value);
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Current Program Predicate
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  public static Query currentProgram()
  {
    return _getSingleton()._newCurrentProgramQuery(new Date());
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Logical Operators
  // Combining Predicates
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  public static Query not(Query a)
    throws NullPointerException
  {
    _checkNotNull("parameter of not()",a);
    return _getSingleton()._newNotQuery(a);
  }
  
  public static Query and(Query a, Query b)
    throws NullPointerException
  {
    _checkNotNull("left hand parameter of and()",a);
    _checkNotNull("right hand parameter of and()",b);
    return _getSingleton()._newAndQuery(a,b);
  }
  
  public static Query or(Query a, Query b)
    throws NullPointerException
  {
    _checkNotNull("left hand parameter of or()",a);
    _checkNotNull("right hand parameter of or()",b);
    return _getSingleton()._newOrQuery(a,b);
  }
  
  
  // ==================================================================
  // Helper Methods, not part of JSR 272 Specification
  // ==================================================================
  protected static void _checkNotNull(String valueName, Object value)
    throws NullPointerException
  {
    if (value==null)
      throw new NullPointerException(valueName+" must not be null.");
  }
  
  protected static void _checkAttributeNameNotNull(Attribute attribute)
    throws NullPointerException
  {
    _checkNotNull("attribute name",attribute);
  }
  
  protected static void _checkNotNull(Attribute attribute, Object value)
    throws NullPointerException
  {
    _checkAttributeNameNotNull(attribute);
    _checkNotNull("value",value);
  }
  
  // =====================================================================
  // Extension Point: Singleton
  // =====================================================================
  
  // I chose to define a Singleton, returning this base class,
  // which will be overloaded by actual derived implementation,
  // e.g. by setting in constructor of derived class
  // _queryComposerSingleton = this;
  // before the first call to getSingleton().
  // A method definition is added for each static method of this class.
  protected static QueryComposer _queryComposerSingleton;
  
  protected static final QueryComposer _getSingleton()
  {
    if (_queryComposerSingleton==null)
      _queryComposerSingleton = new QueryComposer();
    
    return _queryComposerSingleton;
  }
  
  // =======================================================================
  // Extension Point: Null Implementation of Factory Methods
  // =======================================================================
  
  protected static final Query _NULL_QUERY = new Query();
  
  // I chose to rename these methods following the pattern _new***Query
  // to emphasize the Factory pattern behind the design of QueryComposer
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Generic Object Predicates
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  // Generic Equal method for all Attribute types
  protected Query _newEquivalentQuery(Attribute attribute, Object value)
  {
    return _NULL_QUERY;
  }
  
  // Helper method used in QueryComposer#equivalent()
  // to check whether this implementation considers this value
  // as numeric data. Must be overloaded to accept more than
  // Integer, Long, Float, Double.
  protected boolean _isNumericValue(Object value)
  {
    return
    (
         value instanceof Integer
      || value instanceof Long
      || value instanceof Float
      || value instanceof Double
    );
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Boolean Predicates
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  protected Query _newIsTrueQuery(BooleanAttribute attribute)
  {
    return _NULL_QUERY;
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Numeric Predicates
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  // Same as equivalent(Attribute,Object) for native type double
  // Predicates on integers cannot use native type, but int values must be boxed in Integer.
  protected Query _newEquivalentQuery(Attribute attribute, double value)
  {
    return _NULL_QUERY;
  }
  
  // Note: No generic method has been defined for lowerThan and greaterThan,
  //       which means that only Equality predicates may be expressed for
  //       String, and a more fuzzy comparison using double for Integers.
  //       For Dates, before() and after() replace lowerThan and greaterThan.
  
  protected Query _newLessThanQuery(NumericAttribute attribute, double value)
  {
    return _NULL_QUERY;
  }
  
  protected Query _newGreaterThanQuery(NumericAttribute attribute, double value)
  {
    return _NULL_QUERY;
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  // String Predicates
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  protected Query _newContainsQuery(StringAttribute attribute, String value)
  {
    return _NULL_QUERY;
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Date Predicates
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  // Q: '<' or '<=' ?
  // A: Based on sample code in QueryComposer#currentProgram()
  // using QueryComposer.or(q1, q1e)
  // with 
  // q1 = QueryComposer.before(CommonMetadataSet.PROGRAM_START_TIME, now);
  // q1e = QueryComposer.equivalent(CommonMetadataSet.PROGRAM_START_TIME, now)
  // "before" must taken as strict inferior (not equal).
  // i.e. '<'
  protected Query _newBeforeQuery(DateAttribute attribute, Date value)
  {
    return _NULL_QUERY;
  }
  
  // Q: '>' or '>='
  // A: '>' (see above)
  protected Query _newAfterQuery(DateAttribute attribute, Date value)
  {
    return _NULL_QUERY;
  }
  
  // Current Program Predicate
  // equivalent to 
  //       Program.StartTime <= NOW()
  //   AND Program.EndTime > NOW()
  
  protected Query _newCurrentProgramQuery(Date now)
  {
    return _NULL_QUERY;
  }
  
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Logical Operators
  // Combining Predicates
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  protected Query _newNotQuery(Query a)
  {
    return _NULL_QUERY;
  }
  
  protected Query _newAndQuery(Query a, Query b)
  {
    return _NULL_QUERY;
  }
  
  protected Query _newOrQuery(Query a, Query b)
  {
    return _NULL_QUERY;
  }
  
  
}