<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform
        version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:fo="http://www.w3.org/1999/XSL/Format"
        xmlns:svg="http://www.w3.org/2000/svg">
    <!-- Namespace prefixes used in any match or select statements herein must be defined above -->

    <xsl:output method="xml"  indent="no"/>

    <!-- Specify the value prefix to match -->
    <xsl:param name="xpathPrefixIdentifier" select="'$XML/'"/>

    <!-- Identity Transform : copies the input to output-->
    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>

    <!-- When the identity transform mathces this node, the following template overrides the identity transform's (preventing copy) -->
    <xsl:template match="xsl:if/fo:block-container/fo:block/fo:instream-foreign-object/svg:svg/svg:text/svg:textPath[starts-with(self::node(),$xpathPrefixIdentifier)]" >
        <!-- We want to copy this element declaration and its attributes to the output. -->
        <!-- This copy statement will copy only the textPath element declaration to the output-->
        <xsl:copy>
            <!-- Copy all attributes of the matched textPath node -->
            <xsl:apply-templates select="@*"/>
            <!-- Create a child element, moving the original value into its select attribute -->
            <!-- By naming it this, we are subtly injecting xslt instructions into the output -->
            <xsl:element name="xsl:value-of">
                <xsl:attribute name="select"><xsl:value-of select="self::node()"/></xsl:attribute>
            </xsl:element>
        </xsl:copy>
    </xsl:template>
</xsl:transform>