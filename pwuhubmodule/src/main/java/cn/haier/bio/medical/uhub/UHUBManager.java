package cn.haier.bio.medical.uhub;

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
        if (this.serialPort == null) {
            this.serialPort = new UHUBSerialport();
            this.serialPort.init(path);
        }
    }

    public void enable() {
        if (null != this.serialPort) {
            this.serialPort.enable();
        }
    }

    public void disable() {
        if (null != this.serialPort) {
            this.serialPort.disable();
        }
    }

    public void release() {
        if (null != this.serialPort) {
            this.serialPort.release();
            this.serialPort = null;
        }
    }

    public void reset() {
        if (null != this.serialPort) {
            this.serialPort.reset();
        }
    }

    public void changeListener(IUHUBListener listener) {
        if (null != this.serialPort) {
            this.serialPort.changeListener(listener);
        }
    }
}
