package cn.haier.bio.medical.uhub;

import java.io.IOException;

import cn.qd.peiwen.pwlogger.PWLogger;
import cn.qd.peiwen.pwtools.EmptyUtils;
import cn.qd.peiwen.serialport.PWSerialPortHelper;
import cn.qd.peiwen.serialport.PWSerialPortListener;

public class UHUBSerialport implements PWSerialPortListener {
    private boolean enabled = false;
    private PWSerialPortHelper helper;

    public UHUBSerialport() {

    }

    public void init(String path) {
        if (EmptyUtils.isEmpty(this.helper)) {
            this.helper = new PWSerialPortHelper("HUBController");
            this.helper.setTimeout(0);
            this.helper.setPath(path);
            this.helper.setBaudrate(9600);
            this.helper.init(this);
        }
    }

    public void enable() {
        if (this.isInitialized() && !this.enabled) {
            this.enabled = true;
            this.helper.open();
        }
    }

    public void disable() {
        if (this.enabled && this.isInitialized()) {
            this.enabled = false;
            this.helper.close();
        }
    }

    public void release() {
        if (this.isInitialized()) {
            this.helper.release();
            this.helper = null;
        }
    }

    public void reset() {
        if (this.isInitialized()) {
            byte[] data = {(byte) 0xAF, (byte) 0xA6, 0x01, 0x01, 0x00, 0x57};
            this.helper.write(data);
        }
    }

    private boolean isInitialized() {
        if (EmptyUtils.isEmpty(this.helper)) {
            return false;
        }
        return true;
    }



    @Override
    public void onConnected(PWSerialPortHelper helper) {
        PWLogger.d("HUBController connected");
    }

    @Override
    public void onException(PWSerialPortHelper helper) {
        PWLogger.d("HUBController exception");
    }

    @Override
    public void onByteReceived(PWSerialPortHelper helper, byte[] buffer, int length) throws IOException {
        PWLogger.d("HUBController byte received");
    }
}
