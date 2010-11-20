<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 version="1.0"
 
 xmlns:diff="eric.brechemier.name/2007-12-29/test/diff"
 exclude-result-prefixes=""
>
  <!-- =============================================================
   a Utility for Testing and XML Diff: 
   this transform is typically applied to the output of xmlDiff.xsl.
   It will fail with xsl:message terminate='yes' when at least one
   difference has been found.
   
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
  
  <xsl:template match="diff:added | diff:removed">
    <xsl:if test="diff:difference">
      <xsl:message terminate="yes">ERROR Difference found. Check <xsl:value-of select="document-uri(/)"/> for details</xsl:message>
    </xsl:if>
  </xsl:template>
  
</xsl:transform>