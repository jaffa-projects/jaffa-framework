/*
 * *************************************************TAPESTRY PROPRIETARY VER 2.0
 *
 *   Copyright © 2015 Tapestry Solutions.
 *   THIS PROGRAM IS PROPRIETARY TO TAPESTRY SOLUTIONS.
 *   REPRODUCTION, DISCLOSURE, OR USE, IN WHOLE OR IN PART,
 *   UNDERTAKEN EXCEPT WITH PRIOR WRITTEN AUTHORIZATION OF
 *   TAPESTRY SOLUTIONS IS PROHIBITED.
 *
 * *************************************************TAPESTRY PROPRIETARY VER 2.0
 */

package org.jaffa.rules.fieldvalidators;

import org.jaffa.datatypes.ValidationException;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.FrameworkException;

/**
 * A validator for an object.
 *
 * @param <T> the type of the object.
 */
public interface Validator<T> {
    /**
     * Validates the object, throwing an exception for any problem encountered.
     *
     * @param object the object to validate.
     * @throws ValidationException if any validation fails.
     * @throws FrameworkException  if any framework error occurs.
     */
    void validate(T object) throws ApplicationException, FrameworkException;
}
