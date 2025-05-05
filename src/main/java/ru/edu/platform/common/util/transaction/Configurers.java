package ru.edu.platform.common.util.transaction;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

public class Configurers {
    public static TransactionTemplateConfigurer withPropagation(Propagation propagation) {
        return b -> b.setPropagationBehaviour(propagation.getPropagationBehaviour());
    }

    public static TransactionTemplateConfigurer withIsolation(Isolation isolation) {
        return b -> b.setIsolationLevel(isolation.getJavaxIsolationLevel());
    }

    public static TransactionTemplateConfigurer withTransactionManager(PlatformTransactionManager platformTransactionManager) {
        return b -> b.setPlatformTransactionManager(platformTransactionManager);
    }

    public static TransactionTemplateConfigurer withName(String name) {
        return b -> b.setName(name);
    }

    public static TransactionTemplateConfigurer withTimeout(int timeout) {
        return b -> b.setTimeout(timeout);
    }

    public static TransactionTemplateConfigurer withReadonly(boolean readonly) {
        return b -> b.setIsReadonly(readonly);
    }

    public static TransactionTemplateConfigurer readonly() {
        return withReadonly(true);
    }

    public static TransactionTemplate composeTransactionTemplate(TransactionTemplateConfigurer... configurers) {
        TransactionTemplateBuilder builder = new TransactionTemplateBuilder();
        for (TransactionTemplateConfigurer configurer : configurers) {
            configurer.configure(builder);
        }
        return builder.build();
    }
}
