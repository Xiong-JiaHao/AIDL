package com.gin.xjh.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

public class IRemoteService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private IBinder iBinder = new IAidl.Stub() {

        @Override
        public int add(int num1, int num2) throws RemoteException {
            return num1 + num2;
        }
    };
}
