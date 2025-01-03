package ru.edu.platform.util.transaction;

@FunctionalInterface
public interface TransactionTemplateConfigurer {
    void configure(TransactionTemplateBuilder definition);
}
