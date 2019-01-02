package com.gin.xjh.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gin.xjh.aidl.IAidl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtNum1,mEtNum2;
    private Button mBtSubmit;
    private TextView mTvRes;
    private IAidl iAidl;

    private ServiceConnection conn = new ServiceConnection() {
        //绑定服务时
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //拿到了远程的服务的代理
            iAidl = IAidl.Stub.asInterface(iBinder);
        }

        //服务断开时
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //回收资源
            iAidl = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        bindService();
        initEvent();
    }

    private void initView() {
        mEtNum1 = findViewById(R.id.ed_num1);
        mEtNum2 = findViewById(R.id.ed_num2);
        mBtSubmit = findViewById(R.id.submit);
        mTvRes = findViewById(R.id.tv_res);
    }

    private void bindService() {
        //获取到服务端
        Intent intent = new Intent();
        //显式Intent启动服务
        //ComponentName组件名
        intent.setComponent(new ComponentName("com.gin.xjh.aidl","com.gin.xjh.aidl.IRemoteService"));
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
    }

    private void initEvent() {
        mBtSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int num1 = Integer.parseInt(mEtNum1.getText().toString());
        int num2 = Integer.parseInt(mEtNum2.getText().toString());
        try {
            int res = iAidl.add(num1, num2);
            mTvRes.setText(res+"");
        } catch (RemoteException e) {
            e.printStackTrace();
            mTvRes.setText("错误");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
