package com.babayan.service.currency.util.helpers;

import java.util.Collection;

public class CollectionHelper {

    public static boolean isBlank(Collection collection) {
        return collection == null || collection.size() == 0;
    }
}
