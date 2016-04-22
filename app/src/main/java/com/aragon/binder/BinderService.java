package com.aragon.binder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BinderService extends Service {

    private Sub mBinder = new Sub() {
        @Override
        public void logPrint() {
            // TODO: 2016-04-21
        }

        @Override
        public Person getPerson(Person prePerson) {
            return new Person(prePerson.getAge(),prePerson.getGender(), prePerson.getName() + "service");
        }

    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
