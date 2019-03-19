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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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

package org.jaffa.rules.validators;

import org.apache.log4j.Logger;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.Formatter;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.RulesEngineException;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.security.SecurityManager;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.MessageHelper;

import java.util.List;

/**
 * When applied to a property, this will perform automatically concatenate to previous comments when the setter is invoked.
 */
public class CommentValidator<T> extends RuleValidator<T> {

    private static Logger log = Logger.getLogger(CommentValidator.class);

    /**
     * Constructs a validator with no rules.
     */
    public CommentValidator() {
        super("comment");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateProperty(T targetObject, String targetPropertyName, Object targetPropertyValue,
                                    List<RuleMetaData> rules) throws ApplicationException, FrameworkException {

        RuleMetaData rule = rules.get(0);

        if (log.isDebugEnabled()) {
            log.debug("Applying " + rule + " on " + targetPropertyValue);
        }

        Object initialValue = returnInitialValue(targetObject, targetPropertyName);
        String commentStyle = rule.getParameter(RuleMetaData.PARAMETER_VALUE);
        Object value = null;
        if ("fifo".equals(commentStyle)) {
            value = addCommentWithStamp((String) initialValue, (String) targetPropertyValue, false);
        } else if ("lifo".equals(commentStyle)) {
            value = addCommentWithStamp((String) initialValue, (String) targetPropertyValue, true);
        } else {
            value = targetPropertyValue;
        }

        if (value != null && !value.equals(targetPropertyValue)) {
            try {
                BeanHelper.setField(targetObject, targetPropertyName, value);
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Could not set the field: " + targetObject.getClass().getName() + "." + targetPropertyName, e);
                }
                return;
            }

            if (log.isDebugEnabled()) {
                log.debug("Changed value to '" + value + '\'');
            }
        }
    }

    /**
     * This combines old and additional comments.
     * A stamp containing the userId and current time will be inserted before the additional comment.
     * The additional comment will be inserted before the old comment if 'lifo' is true, otherwise it'll be appended.
     *
     * @param originalComment The original comment.
     * @param additionalComment The additional comment.
     * @param lifo This determines if the additional comment is inserted before or appended after the old comment.
     * @return The combination of the old comment and the additional comment.
     */
    private String addCommentWithStamp(String originalComment, String additionalComment, boolean lifo) {
        if (additionalComment == null || additionalComment.trim().length() == 0) {
            return originalComment;
        }

        // create a buffer for the generated comment, by estimating an initial capacity
        int capacity = (originalComment != null ? originalComment.length() : 0) + additionalComment.length() + 200;
        StringBuilder buffer = new StringBuilder(capacity);

        // create the stamp
        String user = (org.jaffa.security.SecurityManager.getPrincipal() != null && SecurityManager.getPrincipal().getName() != null) ? SecurityManager.getPrincipal().getName() : "";
        String messageKey = "label.Jaffa.Rules.Comments.FirstSeparator";
        if (originalComment != null)
            messageKey = "label.Jaffa.Rules.Comments.AdditionalSeparator";
        String text = MessageHelper.findMessage(messageKey, new Object[]{user, Formatter.format(new DateTime())});
        buffer.append(text).append("\n");

        // add the new comments
        buffer.append(additionalComment);

        // add the original comments before or after the new comments, based on the 'lifo' flag
        if (originalComment != null) {
            if (lifo) {
                buffer.append('\n').append('\n').append(originalComment);
            } else {
                buffer.insert(0, '\n').insert(0, '\n').insert(0, originalComment);
            }
        }

        return buffer.toString();
    }
}
