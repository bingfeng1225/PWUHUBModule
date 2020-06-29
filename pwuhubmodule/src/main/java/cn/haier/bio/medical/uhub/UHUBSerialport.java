package cn.haier.bio.medical.uhub;

import java.io.IOException;
import java.lang.ref.WeakReference;

import cn.qd.peiwen.serialport.PWSerialPortHelper;
import cn.qd.peiwen.serialport.PWSerialPortListener;
import cn.qd.peiwen.serialport.PWSerialPortState;

class UHUBSerialport implements PWSerialPortListener {
    private boolean enabled = false;
    private PWSerialPortHelper helper;
    private WeakReference<IUHUBListener> listener;

    public UHUBSerialport() {

    }

    public void init(String path) {
        if (this.helper == null) {
            this.helper = new PWSerialPortHelper("UHUBSerialPort");
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
        byte[] data = {(byte) 0xAF, (byte) 0xA6, 0x01, 0x01, 0x00, 0x57};
        if (!this.isInitialized() || !this.enabled) {
           return;
        }
        this.helper.write(data);
    }

    public void changeListener(IUHUBListener listener) {
        this.listener = new WeakReference<>(listener);
    }

    private boolean isInitialized() {
        if (this.helper == null) {
            return false;
        }
        return true;
    }

    @Override
    public void onConnected(PWSerialPortHelper helper) {
        if (!this.isInitialized() || !helper.equals(this.helper)) {
            return;
        }
        if (null != this.listener && null != this.listener.get()) {
            this.listener.get().onUHUBConnected();
        }
    }

    @Override
    public void onReadThreadReleased(PWSerialPortHelper helper) {
        if (!this.isInitialized() || !helper.equals(this.helper)) {
            return;
        }
        if (null != this.listener && null != this.listener.get()) {
            this.listener.get().onUHUBPrint("UHUBSerialPort read thread released");
        }
    }
    @Override
    public void onException(PWSerialPortHelper helper, Throwable throwable) {
        if (!this.isInitialized() || !helper.equals(this.helper)) {
            return;
        }
        if (null != this.listener && null != this.listener.get()) {
            this.listener.get().onUHUBException(throwable);
        }
    }

    @Override
    public void onStateChanged(PWSerialPortHelper helper, PWSerialPortState state) {
        if (!this.isInitialized() || !helper.equals(this.helper)) {
            return;
        }
        if (null != this.listener && null != this.listener.get()) {
            this.listener.get().onUHUBPrint("UHUBSerialPort state changed: " + state.name());
        }
    }

    @Override
    public void onByteReceived(PWSerialPortHelper helper, byte[] buffer, int length) throws IOException {

    }
}
