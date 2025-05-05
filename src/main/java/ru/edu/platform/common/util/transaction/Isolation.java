package ru.edu.platform.common.util.transaction;

import org.springframework.transaction.TransactionDefinition;

public enum Isolation {
    DEFAULT(TransactionDefinition.ISOLATION_DEFAULT),
    READ_UNCOMMITTED(TransactionDefinition.ISOLATION_READ_UNCOMMITTED),
    READ_COMMITTED(TransactionDefinition.ISOLATION_READ_COMMITTED),
    REPEATABLE_READ(TransactionDefinition.ISOLATION_REPEATABLE_READ),
    SERIALIZABLE(TransactionDefinition.ISOLATION_SERIALIZABLE);

    private final int isolationLevel;

    Isolation(int i) {
        this.isolationLevel = i;
    }

    public int getJavaxIsolationLevel() {
        return isolationLevel;
    }
}
