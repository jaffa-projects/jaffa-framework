/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2015 JAFFA Development Group
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
package org.jaffa.rules;

import org.jaffa.config.PersistentConfig;
import org.jaffa.persistence.FakeModel;
import org.jaffa.rules.initializers.FieldInitializerTest;
import org.jaffa.rules.meta.ClassMetaData;
import org.jaffa.rules.realm.Realm;
import org.jaffa.rules.rulemeta.Rule;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class TestConfig extends PersistentConfig {
    @Bean
    public Realm businessRealm() {
        return realm("business")
                .regex("org.jaffa.persistence.IPersistent")
                .regex("org.jaffa.flexfields.FlexBean")
                .regex(TestModel.class.getName())
                .regex(FakeModel.class.getName())
                .register();
    }

    private Realm realm(String name) {
        Realm realm = new Realm(name);
        realm.setSource("RealmConfig");
        return realm;
    }

    @Bean
    public Rule labelRule() {
        // Specify label for a property or class..
        return commonRule("label")
                .precedenceLast()
                .textParameter("value")
                .parameter("value", true)
                .register();
    }

    @Bean
    public Rule mandatoryRule() {
        // Marks a property as mandatory
        return commonRule("mandatory")
                .executionRealms("business")
                .precedenceFirst()
                .register();
    }

    @Bean
    public Rule maxLengthRule() {
        // Marks a property as having a max-length
        return commonRule("max-length")
                .precedenceAll()
                .textParameter("value")
                .parameter("value", true)
                .register();
    }

    @Bean
    public Rule minLengthRule() {
        // Marks a property as having a min-length
        return commonRule("min-length")
                .precedenceAll()
                .textParameter("value")
                .parameter("value", true)
                .register();
    }

    @Bean
    public Rule caseTypeRule() {
        // Marks a property as having a case-type
        return commonRule("case-type")
                .precedenceLast()
                .textParameter("value")
                .parameter("value", true, "mixed", "mixed,upper,lower", true)
                .register();
    }

    @Bean
    public Rule maxValueRule() {
        // Marks a property as having a max-value
        return commonRule("max-value")
                .precedenceAll()
                .textParameter("value")
                .parameter("value", true)
                .register();
    }

    @Bean
    public Rule minValueRule() {
        // Marks a property as having a min-value
        return commonRule("min-value")
                .precedenceAll()
                .textParameter("value")
                .parameter("value", true)
                .register();
    }

    @Bean
    public Rule foreignKeyRule() {
        // When applied to a property, ensures that a valid foreign key is entered.
        // Apply it at class-level to support composite-keys.
        return commonRule("foreign-key")
                .executionRealms("business")
                .precedenceAll()
                .textParameter("value")
                        // Should be the fully qualified name of the class against which the foreign key check is to be performed.
                .parameter("domainObject", true)
                .parameter("component", false)
                .parameter("bypassCriteriaScreen", false)
                .parameter("dynamicParameters", false)
                .parameter("staticParameters", false)
                .parameter("targetFields", false)
                .parameter("criteriaFields", false)
                        // When applying at class-level, should contain a comma-separated list of fields which form the foreign-key.
                .parameter("value", false)
                .register();
    }

    @Bean
    public Rule genericForeignKeyRule() {
        // When applied to a property, prevents the setter from being executed if an invalid value is entered.
        return commonRule("generic-foreign-key")
                .executionRealms("business")
                .precedenceAll()
                        // Is used to lookup the set of valid values from the Generic Foreign Key table.
                .parameter("tableName", true)
                        // Is used to lookup the set of valid values from the Generic Foreign Key table.
                .parameter("fieldName", true)
                .parameter("domainClassName", false, "org.jaffa.modules.setup.domain.ValidFieldValue")
                .parameter("fieldNameForTable", false, "TableName")
                .parameter("fieldNameForField", false, "FieldName")
                .parameter("fieldNameForValue", false, "LegalValue")
                .register();
    }

    @Bean
    public Rule partialForeignKeyRule() {
        // Allows validation of a partial key.
        return commonRule("partial-foreign-key")
                .executionRealms("business")
                .precedenceAll()
                        // Defines the domain object class to validate the field agents.
                .parameter("domainObject", true)
                        // Defines the property name in the domain object to query on, if not provided will
                        // assume this is the same name as the property the rule is being applied to.
                .parameter("propertyName", false)
                .register();
    }

    @Bean
    public Rule primaryKeyRule() {
        return commonRule("primary-key")
                .executionRealms("business")
                .precedenceFirst()
                .textParameter("value")
                .parameter("value", true)
                .register();
    }

    @Bean
    public Rule candidateKeyRule() {
        return commonRule("candidate-key")
                .executionRealms("business")
                .precedenceAll()
                .textParameter("value")
                .parameter("value", true)
                .parameter("ignore-null", false, "false", "true,false")
                .register();
    }

    @Bean
    public Rule inListRule() {
        // Marks a property as having a in-list rule
        return commonRule("in-list")
                .precedenceLast()
                .textParameter("value")
                .parameter("value", true)
                .parameter("separator", false, ",")
                .register();
    }

    @Bean
    public Rule notInListRule() {
        // Marks a property as having a not-in-list rule
        return commonRule("not-in-list")
                .precedenceAll()
                .textParameter("value")
                .parameter("value", true)
                .parameter("separator", false, ",")
                .register();
    }

    @Bean
    public Rule commentRule() {
        // Marks a property as having a comment rule
        return commonRule("comment")
                .executionRealms("business")
                .precedenceLast()
                .textParameter("value")
                .parameter("value", true, "plain", "plain,lifo,fifo", true)
                .register();
    }

    @Bean
    public Rule patternRule() {
        // Marks a property as having a pattern rule
        return commonRule("pattern")
                .precedenceAll()
                .textParameter("value")
                .parameter("value", true)
                .register();
    }

    @Bean
    public Rule lookupRule() {
        // Marks a property as used in the objects lookup for the given scopes.
        return commonRule("lookup")
                .precedenceLast()
                .textParameter("scope")
                        // If specified, this should be a comma separated list of scopes that include this property.
                .parameter("scope", false, null, null, true)
                .register();
    }

    @Bean
    public Rule hiddenRule() {
        // Marks a property as hidden.
        return commonRule("hidden")
                .precedenceFirst()
                .register();
    }

    @Bean
    public Rule layoutRule() {
        // Specify layout for a property.
        return commonRule("layout")
                .precedenceLast()
                .textParameter("value")
                        // The layout.
                .parameter("value", true)
                        // If specified, then it should be an instance of IFormatter.
                        // If not specified, then the default formatter will be used.
                .parameter("formatterClass")
                .register();
    }

    @Bean
    public Rule readOnlyRule() {
        // Marks a property as read-only.
        return commonRule("read-only")
                .precedenceFirst()
                .register();
    }

    @Bean
    public Rule deleteDomainInfoRule() {
        // This marks a class as a delete-domain-class.
        return commonRule("delete-domain-info")
                .precedenceLast()
                        // Specify a delete domain name, unique to the corresponding domain.
                .parameter("name")
                .register();
    }

    @Bean
    public Rule initializeRule() {
        // This rule is used to initialize a property when a bean is instantiated.
        // The property can be initialized by invoking its setter method or by directly updating
        // a member variable of the bean.
        // The value can be literal or an expression to be evaluated at runtime.
        return commonRule("initialize")
                .precedenceLast()
                .textParameter("value")
                        // Should contain the initialization value for the property.
                .parameter("value", true)
                        // Contains the name of the member-variable, which will be directly initialized to the given value.
                        // The setter will not be used, if the member-variable is specified.
                .parameter("member", false)
                        // Indicates that the value contains an expression that should be evaluated at runtime.
                .parameter("expression", false, "false", "true,false")
                .register();
    }


    private Rule commonRule(String name) {
        return new Rule(name)
                // If specified, then the rule is applied only if the condition evaluates to true.
                .parameter("condition")
                        // Specified the scripting language used in the condition parameter.
                .parameter("language", false, "beanshell")
                        // If specified, then the rule is applied only if the variation in the current thread matches this parameter.
                .parameter("variation")
                        // If specified, and if the validation fails, then an ApplicationException with the given errorCode will be thrown.
                .parameter("errorCode")
                        // Contains a comma-separated list of fields, whose values are to be passed to the ApplicationException.
                .parameter("errorParameters");
    }

    @Bean
    public ClassMetaData testModelMeta() {
        ClassMetaData data = classMetaData(TestModel.class);
        data.property("field1")
                .label("[Field1]")
                .mandatory()
                .maxLength("10")
                .minLength("2")
                .caseType("upper")
                .foreignKey(TestModel.class.getName())
                .genericForeignKey(TestModel.class.getName(), "field1", null, TestValueClass.class.getName())
                .partialForeignKey(TestModel.class.getName())
                .primaryKey("field1")
                .candidateKey("field1", true)
                .comment("fifo")
                .pattern("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b")//IP Address Reg Exp;
                .inList("in list")
                .notInList("not in list")
                .initialize(FieldInitializerTest.FIELD1_INIT_VALUE);
        data.property("field2")
                .label("[Field2]")
                .maxValue("10")
                .minValue("2")
                .initialize(FieldInitializerTest.FIELD2_INIT_VALUE.toString(), "field2");
        data.property("field3")
                .initialize(Integer.toString(FieldInitializerTest.FIELD3_INIT_VALUE), "field3");
        data.setExecutionRealm("business");
        data.setVariation("DEF");
        data.register();
        return data;
    }

    @Bean
    public ClassMetaData fakeModelMeta() {
        ClassMetaData data = classMetaData(FakeModel.class);
        data.property("field1")
                .label("[Field1]")
                .mandatory();
        data.property("field2")
                .label("[Field2]")
                .initialize("123")
                .mandatory();
        data.setExecutionRealm("business");
        data.setVariation("DEF");
        data.register();
        return data;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public TestModel model() {
        return new TestModel();
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public FakeModel fakeModel() {
        return new FakeModel();
    }

    private ClassMetaData classMetaData(Class<?> clazz) {
        return classMetaData(clazz.getName());
    }

    private ClassMetaData classMetaData(String name) {
        return new ClassMetaData(name);
    }

    public static class TestValueClass {
        public String getLegalValue() {
            return "legal value";
        }
    }
}
