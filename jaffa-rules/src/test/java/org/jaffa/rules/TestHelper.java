package org.jaffa.rules;

import org.jaffa.config.JaffaRulesConfig;
import org.jaffa.rules.meta.MetaDataRepository;
import org.jaffa.rules.realm.RealmRepository;
import org.jaffa.rules.rulemeta.RuleRepository;
import org.jaffa.rules.variation.VariationRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestHelper {
    public static ApplicationContext context;

    public static void setupRepos() throws JaffaRulesFrameworkException {
        if (context != null) {
            ((ConfigurableApplicationContext) context).close();
        }

        RuleRepository.instance().clear();
        MetaDataRepository.instance().clear();
        RealmRepository.instance().clear();
        VariationRepository.instance().clear();

        context = new AnnotationConfigApplicationContext(JaffaRulesConfig.class);
    }
}
