package com.demoproject.internetbanking.util;

import com.demoproject.internetbanking.util.exception.NotFoundException;

public class ValidationUtil {
    private ValidationUtil() {
    }

    public static <T> Boolean compare(T object1, T object2){
        return object1.equals(object2);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found. " + msg);
        }
    }
}