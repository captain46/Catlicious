package at.fhj.mad.catlicious.fixture;

import com.orm.SugarApp;
import com.orm.SugarContext;

/**
 * Mocked Android Application which should be used for database instrumentation testing only.
 * Initializes {@link SugarContext} which is needed for using SugarORM.
 *
 * @author bnjm@harmless.ninja - 4/20/17.
 */
public class MockApplication extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        SugarContext.init(this);
        super.onTerminate();
    }
}
