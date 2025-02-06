package com.biotech.sowbhagyabiotech.sharedPreferences;

import android.content.Context;

import java.util.List;
import java.util.Set;

public class SecuredSharedPreferenceWrapper implements PreferenceProvider {

    private PreferenceProvider preferenceProvider;

    public SecuredSharedPreferenceWrapper(Context context, String sharedPreferenceName) {
        preferenceProvider = new SharedPreferencesClass(context,sharedPreferenceName);
    }

    @Override
    public void putString(String key, String value) {
        preferenceProvider.putString(key, value);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return preferenceProvider.getString(key, defaultValue);
    }


    @Override
    public void putBoolean(String key, boolean value) {
        preferenceProvider.putBoolean(key, value);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return preferenceProvider.getBoolean(key, defaultValue);
    }


    @Override
    public void putInt(String key, int value) {
        preferenceProvider.putInt(key, value);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return preferenceProvider.getInt(key, defaultValue);
    }


    @Override
    public void putFloat(String key, float value) {
        preferenceProvider.putFloat(key, value);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return preferenceProvider.getFloat(key, defaultValue);
    }


    @Override
    public void putDouble(String key, double value) {
        preferenceProvider.putDouble(key, value);
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        return preferenceProvider.getDouble(key, defaultValue);
    }

    @Override
    public void remove(String key) {
        preferenceProvider.remove(key);
    }

    @Override
    public void clear() {
        preferenceProvider.clear();
    }

    @Override
    public void putStringSet(String key, Set<String> value) {
        preferenceProvider.putStringSet(key, value);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return preferenceProvider.getStringSet(key,defaultValue);
    }


    @Override
    public <T> void saveObject(String key, T object) {
        preferenceProvider.saveObject(key, object);
    }

    @Override
    public <T> T getObject(String key, Class<T> object) {
        return preferenceProvider.getObject(key, object);
    }

    @Override
    public <T> void saveObjectsList(String key, List<T> objectList) {
        preferenceProvider.saveObjectsList(key, objectList);
    }

    @Override
    public <T> List<T> getObjectsList(String key, Class<T> object) {
        return preferenceProvider.getObjectsList(key, object);
    }

    @Override
    public boolean containsKey(String key) {
        return preferenceProvider.containsKey(key);
    }


}
