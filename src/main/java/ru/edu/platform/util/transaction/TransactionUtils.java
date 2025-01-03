package ru.edu.platform.util.transaction;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class TransactionUtils {

    public static <T> T doInTransaction(TransactionDefinition definition, PlatformTransactionManager txMgr, TransactionCallback<T> callback) {

        TransactionTemplate transactionTemplate;
        if (definition instanceof TransactionTemplate other) {
            transactionTemplate = other;
        } else {
            transactionTemplate = new TransactionTemplate(txMgr, definition);
        }
        return transactionTemplate.execute(callback);
    }

    public static <T> T doInTransaction(TransactionTemplate template, TransactionCallback<T> callback) {
        return template.execute(callback);
    }


    public static <T> T doInTransaction(
            int propagation,
            int isolationLevel,
            boolean readOnly,
            PlatformTransactionManager txMgr,
            TransactionCallback<T> callback) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(txMgr);
        transactionTemplate.setPropagationBehavior(propagation);
        transactionTemplate.setIsolationLevel(isolationLevel);
        transactionTemplate.setReadOnly(readOnly);
        return doInTransaction(transactionTemplate, txMgr, callback);
    }

    public static <T> T doInTransaction(int propagation,
                                        int isolationLevel,
                                        PlatformTransactionManager txMgr,
                                        TransactionCallback<T> callback) {
        return doInTransaction(propagation, isolationLevel, false, txMgr, callback);
    }

    public static <T> T doInTransaction(int propagation, PlatformTransactionManager txMgr, TransactionCallback<T> callback) {
        return doInTransaction(propagation, TransactionDefinition.ISOLATION_DEFAULT, txMgr, callback);
    }

    /**
     * Executes transaction in {@link TransactionDefinition#PROPAGATION_REQUIRED} propagation
     * and in {@link TransactionDefinition#ISOLATION_DEFAULT} isolation.
     *
     * @param txMgr    transaction manager
     * @param callback statements to execute
     */
    public static <T> T doInTransaction(PlatformTransactionManager txMgr, TransactionCallback<T> callback) {
        return doInTransaction(TransactionDefinition.PROPAGATION_REQUIRED, txMgr, callback);
    }
}
