<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 version="1.0"
 
 xmlns:diff="eric.brechemier.name/2007-12-29/test/diff"
 exclude-result-prefixes=""
>
  <xsl:param name="refDoc" />
  
  <!-- =============================================================
   a Utility for Testing and XML Diff: 
   this transform computes the difference of two XML documents, by
   checking the double-inclusion of document A in document B and 
   document B in document A, where
   - document A is the source document
   - document B is provided as a path in parameter refDoc
   
   This transform will output all differences found between the two
   documents, first the nodes found in A not in B in <diff:added>
   second the nodes found in B not in A in <diff:removed>.
   
   Copyright (c) 2007 Eric Bréchemier
   http://eric.brechemier.name
   Licensed under BSD License and/or MIT License.
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                           BSD License
                               ~~~
               http://creativecommons.org/licenses/BSD/
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                            MIT License
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Copyright (c) 2007 Eric Bréchemier <opensource@eric.brechemier.name>
    
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
  ================================================================= -->
  <xsl:output method="xml" indent="yes" />
  
  <xsl:template match="/">
    <xsl:variable name="refDocNode" select="document($refDoc)" />
    <xsl:variable name="sourceDocNode" select="." />
    <diff:compare old="{document-uri($refDocNode)}" new="{document-uri($sourceDocNode)}">
      <diff:added>
        <xsl:apply-templates mode="diff" select=".">
          <xsl:with-param name="refNode" select="$refDocNode" />
        </xsl:apply-templates>
      </diff:added>
      <diff:removed>
        <xsl:for-each select="$refDocNode">
          <xsl:apply-templates mode="diff" select=".">
            <xsl:with-param name="refNode" select="$sourceDocNode" />
          </xsl:apply-templates>
        </xsl:for-each>
      </diff:removed>
    </diff:compare>    
  </xsl:template>
  
  <xsl:template mode="diff" match="/">
    <xsl:param name="refNode" />
    <xsl:for-each select="child::node()">
      <xsl:variable name="position" select="position()" />
      <xsl:apply-templates mode="diff" select=".">
        <xsl:with-param name="refNode" select="$refNode/child::node()[position()=$position]" />
      </xsl:apply-templates>
    </xsl:for-each>
  </xsl:template>
  
  <xsl:template mode="diff" match="@*|child::node()">
    <xsl:param name="refNode" />
    
    <xsl:if test="not( .=$refNode and local-name()=$refNode/local-name() and namespace-uri()=$refNode/namespace-uri()  )">
      <diff:difference>
        <xsl:if test="not( .=$refNode )">
          <xsl:attribute name="diff:value1"><xsl:value-of select="."/></xsl:attribute>
          <xsl:attribute name="diff:value2"><xsl:value-of select="$refNode"/></xsl:attribute>
        </xsl:if>
        <xsl:if test="not( local-name()=$refNode/local-name()  )">
          <xsl:attribute name="diff:name1"><xsl:value-of select="local-name()"/></xsl:attribute>
          <xsl:attribute name="diff:name2"><xsl:value-of select="$refNode/local-name()"/></xsl:attribute>
        </xsl:if>
        <xsl:if test="not( namespace-uri()=$refNode/namespace-uri()  )">
          <xsl:attribute name="diff:uri1"><xsl:value-of select="namespace-uri()"/></xsl:attribute>
          <xsl:attribute name="diff:uri2"><xsl:value-of select="$refNode/namespace-uri()"/></xsl:attribute>
        </xsl:if>
        <xsl:attribute name="diff:path">
          <xsl:apply-templates mode="getPath" select="." />
        </xsl:attribute>
        <xsl:copy />
      </diff:difference>
    </xsl:if>
    
    <xsl:for-each select="@*">
      <xsl:variable name="position" select="position()" />
      <xsl:apply-templates mode="diff" select=".">
        <xsl:with-param name="refNode" select="$refNode/@*[position()=$position]" />
      </xsl:apply-templates>
    </xsl:for-each>
    
    <xsl:for-each select="child::node()">
      <xsl:variable name="position" select="position()" />
      <xsl:apply-templates mode="diff" select=".">
        <xsl:with-param name="refNode" select="$refNode/child::node()[position()=$position]" />
      </xsl:apply-templates>
    </xsl:for-each>
  </xsl:template>
  
  <xsl:template mode="getPath" match="/">
    <xsl:param name="path" />
    <xsl:value-of select="$path" />
  </xsl:template>
  
  <xsl:template mode="getPath" match="child::element()">
    <xsl:param name="path" />
    <xsl:apply-templates mode="getPath" select="parent::node()">
      <xsl:with-param name="path" select="concat('/',name(),$path)" />
    </xsl:apply-templates>
  </xsl:template>
  
  <xsl:template mode="getPath" match="attribute::*">
    <xsl:apply-templates mode="getPath" select="parent::*">
      <xsl:with-param name="path" select="concat('/@',name())" />
    </xsl:apply-templates>
  </xsl:template>
  
  <xsl:template mode="getPath" match="text()">
    <xsl:apply-templates mode="getPath" select="parent::*">
      <xsl:with-param name="path" select="concat('/text()[position()=',position(),']')" />
    </xsl:apply-templates>
  </xsl:template>
  
  <xsl:template mode="getPath" match="comment()">
    <xsl:apply-templates mode="getPath" select="parent::node()">
      <xsl:with-param name="path" select="concat('/comment()[position()=',position(),']')" />
    </xsl:apply-templates>
  </xsl:template>
  
  <xsl:template mode="getPath" match="processing-instruction()">
    <xsl:apply-templates mode="getPath" select="parent::*">
      <xsl:with-param name="path" select="concat('/processing-instruction::',name())" />
    </xsl:apply-templates>
  </xsl:template>
  
</xsl:transform>