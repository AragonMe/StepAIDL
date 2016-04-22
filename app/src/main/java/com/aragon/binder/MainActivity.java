package com.aragon.binder;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";
    private ICustom mService;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = Sub.asInterface(service);
            mService.logPrint();
            Person person = null;
            try {
                person = mService.getPerson(new Person(11, 1, "aragon"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (null != person){
                Log.e(TAG,"name of person : " + person.getName());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService(new Intent(this, BinderService.class), serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        unbindService(serviceConnection);
        super.onStop();
    }
}
