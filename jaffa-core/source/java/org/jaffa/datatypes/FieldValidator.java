/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002 JAFFA Development Group
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Redistribution and use of this software and associated documentation ("Software"),
 * with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 * 3.	The name "JAFFA" must not be used to endorse or promote products derived from
 * 	this Software without prior written permission. For written permission,
 * 	please contact mail to: jaffagroup@yahoo.com.
 * 4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 * 	appear in their names without prior written permission.
 * 5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

package org.jaffa.datatypes;

import org.jaffa.datatypes.exceptions.*;
import org.jaffa.metadata.*;
import java.util.regex.*;
import java.text.*;
import org.apache.log4j.*;
import java.io.*;
import org.jaffa.util.StringHelper;

/** This class has convenience methods for validating data.
 */
public class FieldValidator {
    private static final Logger log  = Logger.getLogger(FieldValidator.class);

    /** This method validates the input field value based on the meta data.
     * This will not check the mandatory property.
     * It then returns the parsed object corresponding to the input field.
     * @param field the value to validate.
     * @param meta the meta object based on which the validation is to be performed.
     * @throws MandatoryFieldException if the field is null. This should never happen.
     * @throws TooMuchDataException if the length of the field exceeds the limit specified in the meta data.
     * @throws TooLittleDataException if the length of the field is below the value specified in the meta data.
     * @throws PatternMismatchException if the field does not conform to the pattern specified in the meta data.
     * @throws BelowMinimumException if the field has a value less than the minimum value specified in the meta data.
     * @throws ExceedsMaximumException if the field has a value greater than the maximum value specified in the meta data.
     * @throws FormatCurrencyException if the field has an invalid currency format. This can happen for Currency fields only.
     * @throws FormatDateOnlyException if the field has an invalid date format. This can happen for DateOnly fields only.
     * @throws FormatDateTimeException if the field has an invalid datetime format. This can happen for DateTime fields only.
     * @throws FormatDecimalException if the field has an invalid decimal format. This can happen for Decimal fields only.
     * @throws FormatIntegerException if the field has an invalid integer format. This can happen for Integer fields only.
     * @return the parsed object corresponding to the input field.
     */
    public static Object validateData(String field, FieldMetaData meta)
    throws MandatoryFieldException, TooMuchDataException, TooLittleDataException
    , PatternMismatchException, BelowMinimumException, ExceedsMaximumException
    , FormatCurrencyException, FormatDateOnlyException, FormatDateTimeException
    , FormatDecimalException, FormatIntegerException {
        return validateData(field, meta, false);
    }

    /** This method validates the input field value based on the meta data.
     * It then returns the parsed object corresponding to the input field.
     * @param field the value to validate.
     * @param meta the meta object based on which the validation is to be performed.
     * @param checkMandatory determines if the mandatory check is to be performed.
     * @throws MandatoryFieldException if checkMandatory is true and the field is null.
     * @throws TooMuchDataException if the length of the field exceeds the limit specified in the meta data.
     * @throws TooLittleDataException if the length of the field is below the value specified in the meta data.
     * @throws PatternMismatchException if the field does not conform to the pattern specified in the meta data.
     * @throws BelowMinimumException if the field has a value less than the minimum value specified in the meta data.
     * @throws ExceedsMaximumException if the field has a value greater than the maximum value specified in the meta data.
     * @throws FormatCurrencyException if the field has an invalid currency format. This can happen for Currency fields only.
     * @throws FormatDateOnlyException if the field has an invalid date format. This can happen for DateOnly fields only.
     * @throws FormatDateTimeException if the field has an invalid datetime format. This can happen for DateTime fields only.
     * @throws FormatDecimalException if the field has an invalid decimal format. This can happen for Decimal fields only.
     * @throws FormatIntegerException if the field has an invalid integer format. This can happen for Integer fields only.
     * @return the parsed object corresponding to the input field.
     */
    public static Object validateData(String field, FieldMetaData meta,
    boolean checkMandatory)
    throws MandatoryFieldException, TooMuchDataException, TooLittleDataException
    , PatternMismatchException, BelowMinimumException, ExceedsMaximumException
    , FormatCurrencyException, FormatDateOnlyException, FormatDateTimeException
    , FormatDecimalException, FormatIntegerException {
        Object output = null;
        String dataType = meta.getDataType();
        if ( dataType.equals(Defaults.STRING) ) {
            output = validateString(field, (StringFieldMetaData) meta, checkMandatory);

        } else if ( dataType.equals(Defaults.BOOLEAN) ) {
            output = validateBoolean(field, (BooleanFieldMetaData) meta, checkMandatory);

        } else if ( dataType.equals(Defaults.CURRENCY) ) {
            output = validateCurrency(field, (CurrencyFieldMetaData) meta, checkMandatory);

        } else if ( dataType.equals(Defaults.DATEONLY) ) {
            output = validateDateOnly(field, (DateOnlyFieldMetaData) meta, checkMandatory);

        } else if ( dataType.equals(Defaults.DATETIME) ) {
            output = validateDateTime(field, (DateTimeFieldMetaData) meta, checkMandatory);

        } else if ( dataType.equals(Defaults.DECIMAL) ) {
            output = validateDecimal(field, (DecimalFieldMetaData) meta, checkMandatory);

        } else if ( dataType.equals(Defaults.INTEGER) ) {
            output = validateInteger(field, (IntegerFieldMetaData) meta, checkMandatory);

        } else if ( dataType.equals(Defaults.RAW) ) {
            output = validateRaw(field, (RawFieldMetaData) meta, checkMandatory);

        } else {
            // this should never happen
            String str = "Unknown DataType - " + dataType + ". Unable to perform field validation";
            log.error(str);
            throw new UnknownDataTypeRuntimeException(str);
        }
        return output;
    }


    /** This method validates the input field value based on the meta data.
     * It then returns the parsed object corresponding to the input field.
     * @param field the value to validate.
     * @param meta the meta object based on which the validation is to be performed.
     * @param checkMandatory determines if the mandatory check is to be performed.
     * @throws MandatoryFieldException if checkMandatory is true and the field is null.
     * @throws TooMuchDataException if the length of the field exceeds the limit specified in the meta data.
     * @throws TooLittleDataException if the length of the field is below the value specified in the meta data.
     * @throws PatternMismatchException if the field does not conform to the pattern specified in the meta data.
     * @return the parsed object corresponding to the input field.
     */
    public static String validate(String field, StringFieldMetaData meta,
    boolean checkMandatory)
    throws MandatoryFieldException, TooMuchDataException, TooLittleDataException
    , PatternMismatchException {
        String output = field;
        if (field != null && field.length() > 0 ) {
            // convert to required case
            String caseType = meta.getCaseType();
            if (caseType != null) {
                if ( caseType.equals(StringFieldMetaData.UPPER_CASE) )
                    output = field.toUpperCase();
                else if ( caseType.equals(StringFieldMetaData.LOWER_CASE) )
                    output = field.toLowerCase();
                else
                    output = field;
            }

            // convert \r\n to just \n or else the length-validations may fail
            if (output.indexOf("\r\n") > -1)
                output = StringHelper.replace(output, "\r\n", "\n");
            
            // check length
            Integer length = meta.getLength();
            if ( length != null && output.length() > length.intValue() )
                throw new TooMuchDataException(meta.getLabelToken(), new Object[] {length});

            // check minimum length
            Integer minLength = meta.getMinLength();
            if ( minLength != null && output.length() < minLength.intValue() )
                throw new TooLittleDataException(meta.getLabelToken(), new Object[] {minLength});

            // validate pattern
            String pattern = meta.getPattern();
            if (pattern != null) {
                try {
                    // @todo: the pattern can probably be cached
                    if (!Pattern.matches(pattern, output))
                        throw new PatternMismatchException(meta.getLabelToken(), new Object[] {pattern});
                } catch (PatternSyntaxException e) {
                    // This should never happen
                    String str = "Invalid syntax for Regular Expression " + pattern;
                    log.error(str, e);
                    throw new InvalidPatternRuntimeException(str, e);
                }
            }
        }

        // check mandatory
        if (checkMandatory) {
            Boolean bool = meta.isMandatory();
            if ( bool != null && bool.booleanValue() ) {
                if ( output == null || output.length() == 0 )
                    throw new MandatoryFieldException(meta.getLabelToken());
            }
        }

        return output;
    }


    /** This method validates the input field value based on the meta data.
     * It then returns the parsed object corresponding to the input field.
     * @param field the value to validate.
     * @param meta the meta object based on which the validation is to be performed.
     * @param checkMandatory determines if the mandatory check is to be performed.
     * @throws MandatoryFieldException if checkMandatory is true and the field is null.
     * @return the parsed object corresponding to the input field.
     */
    public static Boolean validate(Boolean field, BooleanFieldMetaData meta,
    boolean checkMandatory)
    throws MandatoryFieldException {
        // check mandatory
        if (checkMandatory) {
            Boolean bool = meta.isMandatory();
            if ( bool != null && bool.booleanValue() ) {
                if ( field == null )
                    throw new MandatoryFieldException(meta.getLabelToken());
            }
        }
        return field;
    }

    /** This method validates the input field value based on the meta data.
     * It then returns the parsed object corresponding to the input field.
     * @param field the value to validate.
     * @param meta the meta object based on which the validation is to be performed.
     * @param checkMandatory determines if the mandatory check is to be performed.
     * @throws MandatoryFieldException if checkMandatory is true and the field is null.
     * @throws TooMuchDataException if the length of the field exceeds the limit specified in the meta data.
     * @throws BelowMinimumException if the field has a value less than the minimum value specified in the meta data.
     * @throws ExceedsMaximumException if the field has a value greater than the maximum value specified in the meta data.
     * @return the parsed object corresponding to the input field.
     */
    public static Currency validate(Currency field, CurrencyFieldMetaData meta,
    boolean checkMandatory)
    throws MandatoryFieldException, TooMuchDataException
    , BelowMinimumException, ExceedsMaximumException {
        if (field != null) {
            // check the intSize/fracSize
            if ( !checkLength(field.getValue(), meta.getIntSize(), meta.getFracSize() ) )
                throw new TooMuchDataException(meta.getLabelToken(), new Object[] {meta.getIntSize() + ", " + meta.getFracSize()});

            // check the min-value
            Currency min = meta.getMinValue();
            if (min != null) {
                if ( min.compareTo(field) > 0 )
                    throw new BelowMinimumException(meta.getLabelToken(), new Object[] {meta.getMinValue()});
            }

            // check the max-value
            Currency max  = meta.getMaxValue();
            if (max != null) {
                if ( max.compareTo(field) < 0 )
                    throw new ExceedsMaximumException(meta.getLabelToken(), new Object[] {meta.getMaxValue()});
            }
        }

        // check mandatory
        if (checkMandatory) {
            Boolean bool = meta.isMandatory();
            if ( bool != null && bool.booleanValue() ) {
                if ( field == null )
                    throw new MandatoryFieldException(meta.getLabelToken());
            }
        }
        return field;
    }


    /** This method validates the input field value based on the meta data.
     * It then returns the parsed object corresponding to the input field.
     * @param field the value to validate.
     * @param meta the meta object based on which the validation is to be performed.
     * @param checkMandatory determines if the mandatory check is to be performed.
     * @throws MandatoryFieldException if checkMandatory is true and the field is null.
     * @throws BelowMinimumException if the field has a value less than the minimum value specified in the meta data.
     * @throws ExceedsMaximumException if the field has a value greater than the maximum value specified in the meta data.
     * @return the parsed object corresponding to the input field.
     */
    public static DateOnly validate(DateOnly field, DateOnlyFieldMetaData meta,
    boolean checkMandatory)
    throws MandatoryFieldException, BelowMinimumException
    , ExceedsMaximumException {
        if (field != null) {
            // check the min-value
            DateOnly min = meta.getMinValue();
            if (min != null) {
                if ( min.compareTo(field) > 0 )
                    throw new BelowMinimumException(meta.getLabelToken(), new Object[] {meta.getMinValue()});
            }

            // check the max-value
            DateOnly max  = meta.getMaxValue();
            if (max != null) {
                if ( max.compareTo(field) < 0 )
                    throw new ExceedsMaximumException(meta.getLabelToken(), new Object[] {meta.getMaxValue()});
            }
        }

        // check mandatory
        if (checkMandatory) {
            Boolean bool = meta.isMandatory();
            if ( bool != null && bool.booleanValue() ) {
                if ( field == null )
                    throw new MandatoryFieldException(meta.getLabelToken());
            }
        }
        return field;
    }

    /** This method validates the input field value based on the meta data.
     * It then returns the parsed object corresponding to the input field.
     * @param field the value to validate.
     * @param meta the meta object based on which the validation is to be performed.
     * @param checkMandatory determines if the mandatory check is to be performed.
     * @throws MandatoryFieldException if checkMandatory is true and the field is null.
     * @throws BelowMinimumException if the field has a value less than the minimum value specified in the meta data.
     * @throws ExceedsMaximumException if the field has a value greater than the maximum value specified in the meta data.
     * @return the parsed object corresponding to the input field.
     */
    public static DateTime validate(DateTime field, DateTimeFieldMetaData meta,
    boolean checkMandatory)
    throws MandatoryFieldException, BelowMinimumException
    , ExceedsMaximumException {
        if (field != null) {
            // check the min-value
            DateTime min = meta.getMinValue();
            if (min != null) {
                if ( min.compareTo(field) > 0 )
                    throw new BelowMinimumException(meta.getLabelToken(), new Object[] {meta.getMinValue()});
            }

            // check the max-value
            DateTime max  = meta.getMaxValue();
            if (max != null) {
                if ( max.compareTo(field) < 0 )
                    throw new ExceedsMaximumException(meta.getLabelToken(), new Object[] {meta.getMaxValue()});
            }
        }

        // check mandatory
        if (checkMandatory) {
            Boolean bool = meta.isMandatory();
            if ( bool != null && bool.booleanValue() ) {
                if ( field == null )
                    throw new MandatoryFieldException(meta.getLabelToken());
            }
        }
        return field;
    }


    /** This method validates the input field value based on the meta data.
     * It then returns the parsed object corresponding to the input field.
     * @param field the value to validate.
     * @param meta the meta object based on which the validation is to be performed.
     * @param checkMandatory determines if the mandatory check is to be performed.
     * @throws MandatoryFieldException if checkMandatory is true and the field is null.
     * @throws TooMuchDataException if the length of the field exceeds the limit specified in the meta data.
     * @throws BelowMinimumException if the field has a value less than the minimum value specified in the meta data.
     * @throws ExceedsMaximumException if the field has a value greater than the maximum value specified in the meta data.
     * @return the parsed object corresponding to the input field.
     */
    public static Double validate(Double field, DecimalFieldMetaData meta,
    boolean checkMandatory)
    throws MandatoryFieldException, TooMuchDataException
    , BelowMinimumException, ExceedsMaximumException {
        if (field != null) {
            // check the intSize/fracSize
            if ( !checkLength(field, meta.getIntSize(), meta.getFracSize() ) )
                throw new TooMuchDataException(meta.getLabelToken(), new Object[] {meta.getIntSize() + ", " + meta.getFracSize()});

            // check the min-value
            Double min = meta.getMinValue();
            if (min != null) {
                if ( min.compareTo(field) > 0 )
                    throw new BelowMinimumException(meta.getLabelToken(), new Object[] {meta.getMinValue()});
            }

            // check the max-value
            Double max  = meta.getMaxValue();
            if (max != null) {
                if ( max.compareTo(field) < 0 )
                    throw new ExceedsMaximumException(meta.getLabelToken(), new Object[] {meta.getMaxValue()});
            }
        }

        // check mandatory
        if (checkMandatory) {
            Boolean bool = meta.isMandatory();
            if ( bool != null && bool.booleanValue() ) {
                if ( field == null )
                    throw new MandatoryFieldException(meta.getLabelToken());
            }
        }
        return field;
    }


    /** This method validates the input field value based on the meta data.
     * It then returns the parsed object corresponding to the input field.
     * @param field the value to validate.
     * @param meta the meta object based on which the validation is to be performed.
     * @param checkMandatory determines if the mandatory check is to be performed.
     * @throws MandatoryFieldException if checkMandatory is true and the field is null.
     * @throws TooMuchDataException if the length of the field exceeds the limit specified in the meta data.
     * @throws BelowMinimumException if the field has a value less than the minimum value specified in the meta data.
     * @throws ExceedsMaximumException if the field has a value greater than the maximum value specified in the meta data.
     * @return the parsed object corresponding to the input field.
     */
    public static Long validate(Long field, IntegerFieldMetaData meta,
    boolean checkMandatory)
    throws MandatoryFieldException, TooMuchDataException
    , BelowMinimumException, ExceedsMaximumException {
        if (field != null) {
            // check the intSize/fracSize
            if ( !checkLength(new Double( field.doubleValue() ), meta.getIntSize(), null) )
                throw new TooMuchDataException(meta.getLabelToken(), new Object[] {meta.getIntSize()});

            // check the min-value
            Long min = meta.getMinValue();
            if (min != null) {
                if ( min.compareTo(field) > 0 )
                    throw new BelowMinimumException(meta.getLabelToken(), new Object[] {meta.getMinValue()});
            }

            // check the max-value
            Long max  = meta.getMaxValue();
            if (max != null) {
                if ( max.compareTo(field) < 0 )
                    throw new ExceedsMaximumException(meta.getLabelToken(), new Object[] {meta.getMaxValue()});
            }
        }

        // check mandatory
        if (checkMandatory) {
            Boolean bool = meta.isMandatory();
            if ( bool != null && bool.booleanValue() ) {
                if ( field == null )
                    throw new MandatoryFieldException(meta.getLabelToken());
            }
        }
        return field;
    }


    /** This method validates the input field value based on the meta data.
     * It then returns the parsed object corresponding to the input field.
     * @param field the value to validate.
     * @param meta the meta object based on which the validation is to be performed.
     * @param checkMandatory determines if the mandatory check is to be performed.
     * @throws MandatoryFieldException if checkMandatory is true and the field is null.
     * @return the parsed object corresponding to the input field.
     */
    public static byte[] validate(byte[] field, RawFieldMetaData meta,
    boolean checkMandatory)
    throws MandatoryFieldException {
        // check mandatory
        if (checkMandatory) {
            Boolean bool = meta.isMandatory();
            if ( bool != null && bool.booleanValue() ) {
                if ( field == null )
                    throw new MandatoryFieldException(meta.getLabelToken());
            }
        }
        return field;
    }


    // *** PRIVATE METHODS ***

    private static String validateString(String field, StringFieldMetaData meta,
    boolean checkMandatory)
    throws MandatoryFieldException, TooMuchDataException, TooLittleDataException
    , PatternMismatchException {
        // no need for parsing
        return validate( field, meta, checkMandatory );
    }


    private static Boolean validateBoolean(String field, BooleanFieldMetaData meta,
    boolean checkMandatory)
    throws MandatoryFieldException, PatternMismatchException {
        Boolean output = null;
        if (field != null && field.length() > 0 ) {
            // validate pattern
            String pattern = meta.getPattern();
            if (pattern != null) {
                Pattern p = null;
                try {
                    // @todo: the pattern can probably be cached
                    p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
                } catch (PatternSyntaxException e) {
                    // This should never happen
                    String str = "Invalid syntax for Regular Expression " + pattern;
                    log.error(str, e);
                    throw new InvalidPatternRuntimeException(str, e);
                }
                // *** NOTE ***
                // It is assumed that the pattern will be of the type - (a|b|c)|(d|e|f)
                // This means the value in the 1st bracket represents a TRUE
                // & the value in the second bracket means a FALSE
                Matcher m = p.matcher(field);
                if (m.matches()) {
                    if (field.equals(m.group(1)))
                        output = new Boolean(true);
                    else
                        output = new Boolean(false);
                } else {
                    throw new PatternMismatchException(meta.getLabelToken(), new Object[] {pattern});
                }
            } else
                output = Parser.parseBoolean( field, meta.getLayout() );
        }
        return validate(output, meta, checkMandatory);
    }


    private static Currency validateCurrency(String field, CurrencyFieldMetaData meta,
    boolean checkMandatory)
    throws MandatoryFieldException, TooMuchDataException
    , BelowMinimumException, ExceedsMaximumException, FormatCurrencyException {
        Currency output = null;
        if (field != null && field.length() > 0 ) {
            // parse the input
            try {
                output = Parser.parseCurrency( field, meta.getLayout() );
            } catch (FormatCurrencyException e) {
                e.setField(meta.getLabelToken());
                throw e;
            }
        }
        return validate(output, meta, checkMandatory);
    }


    private static DateOnly validateDateOnly(String field, DateOnlyFieldMetaData meta,
    boolean checkMandatory)
    throws MandatoryFieldException, BelowMinimumException
    , ExceedsMaximumException, FormatDateOnlyException {
        DateOnly output = null;
        if (field != null && field.length() > 0 ) {
            // parse the input
            try {
                output = Parser.parseDateOnly( field, meta.getLayout() );
            } catch (FormatDateOnlyException e) {
                e.setField(meta.getLabelToken());
                throw e;
            }
        }
        return validate(output, meta, checkMandatory);
    }


    private static DateTime validateDateTime(String field, DateTimeFieldMetaData meta,
    boolean checkMandatory)
    throws MandatoryFieldException, BelowMinimumException
    , ExceedsMaximumException, FormatDateTimeException {
        DateTime output = null;
        if (field != null && field.length() > 0 ) {
            // parse the input
            try {
                output = Parser.parseDateTime( field, meta.getLayout() );
            } catch (FormatDateTimeException e) {
                e.setField(meta.getLabelToken());
                throw e;
            }
        }
        return validate(output, meta, checkMandatory);
    }


    private static Double validateDecimal(String field, DecimalFieldMetaData meta,
    boolean checkMandatory)
    throws MandatoryFieldException, TooMuchDataException
    , BelowMinimumException, ExceedsMaximumException, FormatDecimalException {
        Double output = null;
        if (field != null && field.length() > 0 ) {
            // parse the input
            try {
                output = Parser.parseDecimal( field, meta.getLayout() );
            } catch (FormatDecimalException e) {
                e.setField(meta.getLabelToken());
                throw e;
            }
        }
        return validate(output, meta, checkMandatory);
    }


    private static Long validateInteger(String field, IntegerFieldMetaData meta,
    boolean checkMandatory)
    throws MandatoryFieldException, TooMuchDataException
    , BelowMinimumException, ExceedsMaximumException, FormatIntegerException {
        Long output = null;
        if (field != null && field.length() > 0 ) {
            // parse the input
            try {
                output = Parser.parseInteger( field, meta.getLayout() );
            } catch (FormatIntegerException e) {
                e.setField(meta.getLabelToken());
                throw e;
            }
        }
        return validate(output, meta, checkMandatory);
    }


    private static byte[] validateRaw(String field, RawFieldMetaData meta,
    boolean checkMandatory)
    throws MandatoryFieldException {
        byte[] output = null;
        if (field != null && field.length() > 0 )
            output = Parser.parseRaw(field);
        return validate(output, meta, checkMandatory);
    }



    private static boolean checkLength(Double decimal, Integer intSize, Integer fracSize) {
        if ( decimal != null && (intSize != null || fracSize != null) ) {
            double value = Math.abs( decimal.doubleValue() );

            if ( intSize != null ) {
                // any of the 2 alternatives shown here, work !!!
                double intLimit = Math.pow( 10, intSize.intValue() );
                if ( (long) value >= (long) intLimit ) return false;
                //String intString = "" + (long) value;
                //if ( !doCheck( intString, intSize.intValue() ) ) return false;
            }

            if ( fracSize != null ) {
                /** @todo */
                // should find a much more efficient way of finding the no. of fractional digits
                StringBuffer buf = new StringBuffer();
                FieldPosition fp = new FieldPosition(NumberFormat.FRACTION_FIELD);
                NumberFormat df = NumberFormat.getNumberInstance();
                df.setGroupingUsed(false);
                df.setMaximumFractionDigits(20); // THIS SHOULD BE SUFFICIENT
                df.format( value, buf, fp );
                String fracString = buf.substring( fp.getBeginIndex(), fp.getEndIndex() );
                if ( !doCheck( fracString, fracSize.intValue() ) )
                    return false;
            }
        }
        return true;
    }

    private static boolean doCheck(String input, int length) {
        if ( input == null || input.length() == 0)
            return true;
        else {
            return input.length() > length ? false : true;
        }
    }
}

