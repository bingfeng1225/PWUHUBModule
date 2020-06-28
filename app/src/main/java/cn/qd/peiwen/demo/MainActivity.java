package cn.qd.peiwen.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import cn.haier.bio.medical.uhub.IUHUBListener;
import cn.haier.bio.medical.uhub.UHUBManager;
import cn.qd.peiwen.logger.PWLogger;

public class MainActivity extends AppCompatActivity implements IUHUBListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.registerUsbReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterUsbReceiver();
        UHUBManager.getInstance().release();
    }

    private void registerUsbReceiver(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(uhubReceiver, filter);
    }

    private void unregisterUsbReceiver(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(uhubReceiver, filter);
    }


    private BroadcastReceiver uhubReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) {
                return;
            }
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                Toast.makeText(context, "USB已插入",Toast.LENGTH_SHORT).show();
            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                Toast.makeText(context, "USB已拔出",Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                String path  = "/dev/ttyS7";
                UHUBManager.getInstance().init(path);
                UHUBManager.getInstance().changeListener(this);
                break;
            case R.id.button2:
                UHUBManager.getInstance().enable();
                break;
            case R.id.button3:
                UHUBManager.getInstance().reset();
                break;
            case R.id.button4:
                UHUBManager.getInstance().disable();
                break;
            case R.id.button5:
                UHUBManager.getInstance().release();
                break;
        }
    }

    @Override
    public void onUHUBConnected() {
        PWLogger.debug("UHUBSerialPort Connected");
    }

    @Override
    public void onUHUBPrint(String message) {
        PWLogger.debug("" + message);
    }

    @Override
    public void onUHUBException(Throwable throwable) {
        PWLogger.error(throwable);
    }
}
