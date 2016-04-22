package com.aragon.binder;

import android.os.IInterface;
import android.os.RemoteException;

public interface ICustom extends IInterface {

    void logPrint();
    Person getPerson(Person prePerson) throws RemoteException;
}
