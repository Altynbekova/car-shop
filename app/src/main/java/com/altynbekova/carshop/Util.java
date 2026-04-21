package com.altynbekova.carshop;

import java.lang.reflect.Field;

public class Util {
    private static final int DEFAULT_PHOTO_ID = R.drawable.baseline_broken_image_24;
    public static int getDrawableId(String photo) {
        int resourceId;

        try {

            Class<R.drawable> drawableClass = R.drawable.class;
            Field field = drawableClass.getField(photo);
            resourceId = field.getInt(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            resourceId = DEFAULT_PHOTO_ID;
        }

        return resourceId;
    }
}
