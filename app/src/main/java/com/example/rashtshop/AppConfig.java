package com.example.rashtshop;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AppConfig extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("myDb")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
