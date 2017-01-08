package org.genia.trainchecker.config;

import java.util.HashSet;
import java.util.Set;

public class JsonOptions {
static ThreadLocal<Set<String>> ignoredFields = new ThreadLocal<>();

    public static void ignore(String fieldToIgnore) {
        Set<String> threadIgnoredFields = ignoredFields.get();
        if (threadIgnoredFields == null) {
            ignoredFields.set(threadIgnoredFields = new HashSet<>());
        }
        threadIgnoredFields.add(fieldToIgnore);
    }

    public static boolean isIgnored(String field) {
        Set<String> threadIgnoredFields = ignoredFields.get();
        if (threadIgnoredFields == null) {
            ignoredFields.set(threadIgnoredFields = new HashSet<>());
        }

        return threadIgnoredFields.contains(field);
    }
}
