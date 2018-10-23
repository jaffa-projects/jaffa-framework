package org.jaffa.rules;

import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.config.JaffaRulesConfig;
import org.jaffa.loader.CoreLoaderConfig;
import org.jaffa.rules.meta.MetaDataRepository;
import org.jaffa.rules.realm.RealmRepository;
import org.jaffa.rules.rulemeta.RuleRepository;
import org.jaffa.rules.variation.VariationRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestHelper {
    public static ApplicationContext context;

    private static AnnotationConfigApplicationContext resourceLoaderConfig = new AnnotationConfigApplicationContext(CoreLoaderConfig.class);

    public static void setupRepos() throws JaffaRulesFrameworkException {
        shutdownRepos();

        RuleRepository.instance().clear();
        MetaDataRepository.instance().clear();
        RealmRepository.instance().clear();
        VariationRepository.instance().clear();

        context = new AnnotationConfigApplicationContext(JaffaRulesConfig.class);

    }

    public static void shutdownRepos() {
        if (context != null) {
            ((ConfigurableApplicationContext) context).close();
        }
        StaticContext.clearApplicationContext();
    }
}
