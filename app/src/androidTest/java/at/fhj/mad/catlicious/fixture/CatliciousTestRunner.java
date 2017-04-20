package at.fhj.mad.catlicious.fixture;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

/**
 * Custom TestRunner for running database instrumentation tests.
 * In order to use this TestRunner in IntelliJ ensure that your "Run Configuration" is using this test runner and not
 * {@link AndroidJUnitRunner}.
 *
 * @author bnjm@harmless.ninja - 4/20/17.
 */
public class CatliciousTestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, MockApplication.class.getName(), context);
    }
}
