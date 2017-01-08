package org.genia.trainchecker.config;

import org.hibernate.cfg.EJB3NamingStrategy;

public class CustomNamingStrategy extends EJB3NamingStrategy {
    @Override
    public String classToTableName(String className) {
        return className.toLowerCase();
    }

    @Override
    public String tableName(String tableName) {
        return tableName.toLowerCase();
    }
}
