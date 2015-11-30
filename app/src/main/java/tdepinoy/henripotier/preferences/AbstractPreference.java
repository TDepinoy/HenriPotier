package tdepinoy.henripotier.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class AbstractPreference {

    protected Context context;

    private SharedPreferences preferences;

    public AbstractPreference(Context context) {
        this.context = context;
    }

    protected SharedPreferences getSharedPreference() {
        if(preferences == null) {
            preferences = context.getSharedPreferences(getPreferenceName(), Context.MODE_PRIVATE);
        }
        return preferences;
    }

    protected abstract String getPreferenceName();

    protected String getString(String key, String defaultValue) {
        return getSharedPreference().getString(key, defaultValue);
    }

    protected void setString(String key, String value) {
        getSharedPreference().edit().putString(key, value).apply();
    }
}
