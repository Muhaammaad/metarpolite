package com.muhaammaad.metarpolite.di.component;

import android.app.Application;

import com.muhaammaad.metarpolite.di.factory.SimpleWorkerFactory;
import com.muhaammaad.metarpolite.di.module.ActivityBindingModule;
import com.muhaammaad.metarpolite.di.module.ApplicationModule;
import com.muhaammaad.metarpolite.di.module.NetworkModule;
import com.muhaammaad.metarpolite.di.module.PersistenceModule;
import com.muhaammaad.metarpolite.di.module.ViewModelModule;
import com.muhaammaad.metarpolite.di.module.WorkerBindingModule;
import com.muhaammaad.metarpolite.global.MyApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;


@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                ApplicationModule.class,
                NetworkModule.class,
                PersistenceModule.class,
                ViewModelModule.class,
                WorkerBindingModule.class,
                ActivityBindingModule.class
        }
)
public interface AppComponent {
    @Component.Builder
    public interface Builder {
        @BindsInstance
        Builder application(Application application);

        Builder appModule(ApplicationModule appModule);


        AppComponent build();
    }
    SimpleWorkerFactory factory();

    public void inject(MyApplication assignmentApplication);
}
