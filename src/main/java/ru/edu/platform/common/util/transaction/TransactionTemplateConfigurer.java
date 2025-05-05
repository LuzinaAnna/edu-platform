package ru.edu.platform.common.util.transaction;

@FunctionalInterface
public interface TransactionTemplateConfigurer {
    void configure(TransactionTemplateBuilder definition);
}
