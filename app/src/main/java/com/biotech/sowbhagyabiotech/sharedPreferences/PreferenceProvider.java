package com.biotech.sowbhagyabiotech.sharedPreferences;

import java.util.List;
import java.util.Set;

public interface PreferenceProvider {

    void putString(String key, String value);

    String getString(String key, String defaultValue);


    void putBoolean(String key, boolean value);

    boolean getBoolean(String key, boolean defaultValue);


    void putInt(String key, int value);

    int getInt(String key, int defaultValue);


    void putFloat(String key, float value);

    float getFloat(String key, float defaultValue);


    void putDouble(String key, double value);

    double getDouble(String key, double defaultValue);

    void remove(String key);

    void clear();

    void putStringSet(String key, Set<String> value);

    Set<String> getStringSet(String key, Set<String> defaultValue);

    <T> void saveObject(String key, T object);

    <T> T getObject(String key, Class<T> object);

    <T> void saveObjectsList(String key, List<T> objectList);

    <T> List<T> getObjectsList(String key, Class<T> object);

    boolean containsKey(String key);

}
