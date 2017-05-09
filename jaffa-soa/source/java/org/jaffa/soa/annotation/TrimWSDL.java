package org.jaffa.soa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be applied to any WebService implementation class.
 * The presence of this annotation will indicate to the WebService implementation to trim the WSDL
 * based on the targetScope rules in the given graph class.
 */
@Inherited
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface TrimWSDL {

    /**
     * The graph class, whose targetScope will be used to find the list of class and fields to be excluded from the WSDL.
     * @return 
     */
    public String graphClassName();
}
