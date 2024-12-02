package ru.edu.platform.util.transaction;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

public class TransactionTemplateBuilder {

    private int timeout = TransactionDefinition.TIMEOUT_DEFAULT;
    private int isolationLevel = TransactionDefinition.ISOLATION_DEFAULT;
    private int propagationBehaviour = TransactionDefinition.PROPAGATION_REQUIRED;
    private boolean isReadonly = false;
    private String name = null;
    private PlatformTransactionManager txMgr;

    TransactionTemplate build() {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTimeout(timeout);
        transactionTemplate.setIsolationLevel(isolationLevel);
        transactionTemplate.setReadOnly(isReadonly);
        transactionTemplate.setPropagationBehavior(propagationBehaviour);
        transactionTemplate.setName(name);
        transactionTemplate.setTransactionManager(txMgr);
        return transactionTemplate;
    }

    public TransactionTemplateBuilder setIsolationLevel(int isolationLevel) {
        this.isolationLevel = isolationLevel;
        return this;
    }

    public TransactionTemplateBuilder setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public TransactionTemplateBuilder setPropagationBehaviour(int propagationBehaviour) {
        this.propagationBehaviour = propagationBehaviour;
        return this;
    }

    public TransactionTemplateBuilder setIsReadonly(boolean isReadonly) {
        this.isReadonly = isReadonly;
        return this;
    }

    public TransactionTemplateBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TransactionTemplateBuilder setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
        this.txMgr = platformTransactionManager;
        return this;
    }
}
