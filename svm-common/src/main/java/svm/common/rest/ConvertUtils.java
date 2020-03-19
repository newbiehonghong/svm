package svm.common.rest;

import java.util.Map;

public abstract class ConvertUtils {

    public static Object convert(Object object) {

        if (object instanceof Result) {
            return object;
        }

        if (object instanceof Map) {
            Map mapObject = (Map) object;
            Object status = mapObject.get("status");

            if (status instanceof Integer) {
                String error = (String) mapObject.get("error");
                String message = (String) mapObject.get("message");

                try {
                    return new Result<>(object, (Integer) status, message, error);
                } catch (Exception ignore) {
                }
            }
        }

        return new Result<>(object);
    }

}
