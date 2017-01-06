package io.coreflodev.openchat;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class RxPluginTestRule implements TestRule {
    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxAndroidPlugins.reset();
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
                RxJavaPlugins.reset();
                RxJavaPlugins.setIoSchedulerHandler(schedulerCallable -> Schedulers.trampoline());

                base.evaluate();

                RxAndroidPlugins.reset();
                RxJavaPlugins.reset();
            }
        };
    }
}
