package com.aragon.binder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract class Sub extends Binder implements ICustom {
    public static final String DESCRIPTOR = "com.aragon.binder.ICustom";

    public abstract void logPrint();

    public Sub() {
        this.attachInterface(this,DESCRIPTOR);
    }

    public static ICustom asInterface(IBinder obj){
        if(obj == null){
            return null;
        }
        IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if((iin != null) && (iin instanceof ICustom)){
            return (ICustom)iin;
        }
        return new ProxyClient(obj);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;
            case ProxyClient.TRANSACTION_LogService:
                data.enforceInterface(DESCRIPTOR);
                this.logPrint();
                reply.writeNoException();
                return true;

            case ProxyClient.TRANSACTION_getPerson : {
                data.enforceInterface(DESCRIPTOR);
                Person _arg0;
                if ((0 != data.readInt())) {
                    _arg0 = Person.CREATOR.createFromParcel(data);
                } else {
                    _arg0 = null;
                }
                Person _result = this.getPerson(_arg0);
                reply.writeNoException();
                if ((_result != null)) {
                    reply.writeInt(1);
                    _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                } else {
                    reply.writeInt(0);
                }
                return true;
            }
        }
        return super.onTransact(code, data, reply, flags);
    }

}
