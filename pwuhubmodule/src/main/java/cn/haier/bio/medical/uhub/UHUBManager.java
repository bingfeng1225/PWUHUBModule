package cn.haier.bio.medical.uhub;

import cn.qd.peiwen.pwtools.EmptyUtils;

public class UHUBManager {
    private UHUBSerialport serialPort;
    private static UHUBManager manager;

    public static UHUBManager getInstance() {
        if (manager == null) {
            synchronized (UHUBManager.class) {
                if (manager == null)
                    manager = new UHUBManager();
            }
        }
        return manager;
    }

    private UHUBManager() {

    }

    public void init(String path) {
        if (EmptyUtils.isEmpty(this.serialPort)) {
            this.serialPort = new UHUBSerialport();
            this.serialPort.init(path);
        }
    }

    public void enable() {
        if (EmptyUtils.isNotEmpty(this.serialPort)) {
            this.serialPort.enable();
        }
    }

    public void disable() {
        if (EmptyUtils.isNotEmpty(this.serialPort)) {
            this.serialPort.disable();
        }
    }

    public void release() {
        if (EmptyUtils.isNotEmpty(this.serialPort)) {
            this.serialPort.release();
            this.serialPort = null;
        }
    }

    public void reset() {
        if (EmptyUtils.isNotEmpty(this.serialPort)) {
            this.serialPort.reset();
        }
    }
}
