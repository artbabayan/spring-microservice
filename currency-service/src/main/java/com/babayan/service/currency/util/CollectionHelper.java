package com.babayan.service.currency.util;

import java.util.Collection;

/**
 * @author by artbabayan
 */
public class CollectionHelper {
    public static boolean isBlank(Collection collection) {
        return collection == null || collection.size() == 0;
    }
}
