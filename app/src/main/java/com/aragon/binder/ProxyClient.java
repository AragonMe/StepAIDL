package com.aragon.binder;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public class ProxyClient implements ICustom {
    public static final int TRANSACTION_LogService = (IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_getPerson = (IBinder.FIRST_CALL_TRANSACTION + 1);

    private IBinder mRemote;

    ProxyClient(IBinder mRemote) {
        this.mRemote = mRemote;
    }

    @Override
    public IBinder asBinder() {
        return mRemote;
    }

    @Override
    public void logPrint() {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        try{
            _data.writeInterfaceToken(Sub.DESCRIPTOR);
            mRemote.transact(TRANSACTION_LogService,_data,_reply,0);
            _reply.readException();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

      }

    @Override
    public Person getPerson(Person pre) throws RemoteException {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        Person _result;
        try {
            _data.writeInterfaceToken(Sub.DESCRIPTOR);
            if ((pre != null)) {
                _data.writeInt(1);
                pre.writeToParcel(_data, 0);
            } else {
                _data.writeInt(0);
            }
            mRemote.transact(TRANSACTION_getPerson, _data, _reply, 0);
            _reply.readException();
            if ((0 != _reply.readInt())) {
                _result = Person.CREATOR.createFromParcel(_reply);
            } else {
                _result = null;
            }
        } finally {
            _reply.recycle();
            _data.recycle();
        }
        return _result;
    }

}
