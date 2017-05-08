package at.fhj.mad.catlicious.utils;

import android.content.Context;

import com.orm.SugarContext;

/**
 * Created by Simone on 25.04.2017.
 * <p>
 * supports the functionality to create and destroy the context
 * before and after each DB transaction
 */

public abstract class DAOUtils {

    public static void createContext(Context context) {
        SugarContext.init(context);
    }

    public static void terminateContext() {
        SugarContext.terminate();
    }
}
