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
package org.jaffa.config;

import org.jaffa.beans.factory.InitializerFactory;
import org.jaffa.loader.ResourceLoader;
import org.jaffa.rules.AopXmlLoader;
import org.jaffa.rules.JaffaRulesFrameworkException;
import org.jaffa.rules.commons.AopConstants;
import org.jaffa.rules.initializers.RuleInitializerFactory;
import org.jaffa.rules.rulemeta.DefaultRuleHelper;
import org.jaffa.rules.rulemeta.IRuleEvaluator;
import org.jaffa.rules.validators.*;
import org.jaffa.util.ConfigApiHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Java Configuration class for the Jaffa Rules
 */
@Configuration
@ComponentScan("org.jaffa.beans.factory.config")
public class JaffaRulesConfig {

     /**
     * Configure the validator factory
     *
     * @return validator factory
     */
    @Bean(name = "ruleValidatorFactory")
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public RuleValidatorFactory ruleValidatorFactory() {
        RuleValidatorFactory factory = new RuleValidatorFactory();
        factory.addValidatorTypes("mandatory", "max-length", "min-length", "case-type", "max-value", "min-value",
                "candidate-key", "comment", "foreign-key", "generic-foreign-key", "in-list",
                "not-in-list", "partial-foreign-key", "pattern", "primary-key");
        return factory;
    }

    /**
     * Configure the initializer factory
     *
     * @return initializer factory
     */
    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public InitializerFactory initializerFactory() {
        return new RuleInitializerFactory();
    }

    /**
     * Configure the Rule Helper
     *
     * @return Rule Helper
     */
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public IRuleEvaluator ruleHelper() {
        return new DefaultRuleHelper();
    }

    /**
     * Configure the Mandatory Validator
     *
     * @return Mandatory Validator
     */
    @Bean(name = "mandatory")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public MandatoryValidator mandatoryValidator() {
        MandatoryValidator validator = new MandatoryValidator();
        validator.setRuleEvaluator(ruleHelper());
        return validator;
    }

    /**
     * Configure the MaxLength Validator
     *
     * @return MaxLength Validator
     */
    @Bean(name = "max-length")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public MaxLengthValidator maxLengthValidator() {
        MaxLengthValidator validator = new MaxLengthValidator();
        validator.setRuleEvaluator(ruleHelper());
        return validator;
    }

    /**
     * Configure the MinLength Validator
     *
     * @return MinLength Validator
     */
    @Bean(name = "min-length")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public MinLengthValidator minLengthValidator() {
        MinLengthValidator validator = new MinLengthValidator();
        validator.setRuleEvaluator(ruleHelper());
        return validator;
    }

    /**
     * Configure the CaseType Validator
     *
     * @return CaseType Validator
     */
    @Bean(name = "case-type")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public CaseTypeValidator caseTypeValidator() {
        CaseTypeValidator validator = new CaseTypeValidator();
        validator.setRuleEvaluator(ruleHelper());
        return validator;
    }

    /**
     * Configure the MaxValue Validator
     *
     * @return MaxValue Validator
     */
    @Bean(name = "max-value")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public MaxValueValidator maxValueValidator() {
        MaxValueValidator validator = new MaxValueValidator();
        validator.setRuleEvaluator(ruleHelper());
        return validator;
    }

    /**
     * Configure the MinValue Validator
     *
     * @return MinValue Validator
     */
    @Bean(name = "min-value")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public MinValueValidator minValueValidator() {
        MinValueValidator validator = new MinValueValidator();
        validator.setRuleEvaluator(ruleHelper());
        return validator;
    }

    /**
     * Configure the CandidateKey Validator
     *
     * @return CandidateKey Validator
     */
    @Bean(name = "candidate-key")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public CandidateKeyValidator candidateKeyValidator() {
        CandidateKeyValidator validator = new CandidateKeyValidator();
        validator.setRuleEvaluator(ruleHelper());
        return validator;
    }

    /**
     * Configure the Comment Validator
     *
     * @return Comment Validator
     */
    @Bean(name = "comment")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public CommentValidator commentValidator() {
        CommentValidator validator = new CommentValidator();
        validator.setRuleEvaluator(ruleHelper());
        return validator;
    }

    /**
     * Configure the ForeignKey Validator
     *
     * @return ForeignKey Validator
     */
    @Bean(name = "foreign-key")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ForeignKeyValidator foreignKeyValidator() {
        ForeignKeyValidator validator = new ForeignKeyValidator();
        validator.setRuleEvaluator(ruleHelper());
        return validator;
    }

    /**
     * Configure the GenericForeignKey Validator
     *
     * @return GenericForeignKey Validator
     */
    @Bean(name = "generic-foreign-key")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public GenericForeignKeyValidator genericForeignKeyValidator() {
        GenericForeignKeyValidator validator = new GenericForeignKeyValidator();
        validator.setRuleEvaluator(ruleHelper());
        return validator;
    }

    /**
     * Configure the InList Validator
     *
     * @return InList Validator
     */
    @Bean(name = "in-list")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public InListValidator inListValidator() {
        InListValidator validator = new InListValidator();
        validator.setRuleEvaluator(ruleHelper());
        return validator;
    }

    /**
     * Configure the NotInList Validator
     *
     * @return NotInList Validator
     */
    @Bean(name = "not-in-list")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public NotInListValidator notInListValidator() {
        NotInListValidator validator = new NotInListValidator();
        validator.setRuleEvaluator(ruleHelper());
        return validator;
    }

    /**
     * Configure the PartialForeignKey Validator
     *
     * @return PartialForeignKey Validator
     */
    @Bean(name = "partial-foreign-key")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public PartialForeignKeyValidator partialForeignKeyValidator() {
        PartialForeignKeyValidator validator = new PartialForeignKeyValidator();
        validator.setRuleEvaluator(ruleHelper());
        return validator;
    }

    /**
     * Configure the Pattern Validator
     *
     * @return Pattern Validator
     */
    @Bean(name = "pattern")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public PatternValidator patternValidator() {
        PatternValidator validator = new PatternValidator();
        validator.setRuleEvaluator(ruleHelper());
        return validator;
    }

    /**
     * Configure the PrimaryKey Validator
     *
     * @return PrimaryKey Validator
     */
    @Bean(name = "primary-key")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public PrimaryKeyValidator primaryKeyValidator() {
        PrimaryKeyValidator validator = new PrimaryKeyValidator();
        validator.setRuleEvaluator(ruleHelper());
        return validator;
    }

    /**
     * Configures the AOP file System watcher to observe the paths passed in for the
     * jboss aop folder.
     */
    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    @Autowired
    public AopXmlLoader aopFolderWatcher(Environment env) throws JaffaRulesFrameworkException, IOException {

        // Check to see if this is supported. If explicitly disabled, then return early.
        if (env.containsProperty("jaffa.aop.springconfig.disabled") &&
                env.getProperty("jaffa.aop.springconfig.disabled").equals("true")) {
            return null;
        }

        String aopPath =
                env.containsProperty("jboss.aop.path") ?
                        env.getProperty("jboss.aop.path") :
                        AopConstants.DEFAULT_AOP_PATTERN;

        List<String> paths = Arrays.asList(aopPath.split(";"));
        AopXmlLoader aopXmlLoader = new AopXmlLoader(paths);
        loadAllCustomConfigurations(aopXmlLoader);
        AopXmlLoader.setInstance(aopXmlLoader);
        return aopXmlLoader;
    }

    /**
     * Loads all custom configurations in the custom config directory.
     * @throws IOException  When a file cannot be accessed or operations cannot be performed on it
     */
    public void loadAllCustomConfigurations(AopXmlLoader aopXmlLoader) throws IOException {
        // Load all zip files from the custom config directory.
        File customConfigDirectory = new File(ResourceLoader.customConfigPath);
        if(customConfigDirectory.exists()) {
            for (File file : customConfigDirectory.listFiles()) {
                if (file.getName().endsWith(ResourceLoader.ARCHIVE_EXTENSION)) {
                    loadCustomConfiguration(file, aopXmlLoader);
                }
            }
        }
    }

    /**
     * Loads a single custom configuration compressed file.
     * @param file  The compressed configuration archive
     * @throws IOException  When a file cannot be accessed or operations cannot be performed on it
     */
    public void loadCustomConfiguration(File file, AopXmlLoader aopXmlLoader) throws IOException {
        File zipRoot = ConfigApiHelper.extractToTemporaryDirectory(file);
        aopXmlLoader.processAopPath(zipRoot.getAbsolutePath());
        ConfigApiHelper.removeDirTree(zipRoot);
    }
}
