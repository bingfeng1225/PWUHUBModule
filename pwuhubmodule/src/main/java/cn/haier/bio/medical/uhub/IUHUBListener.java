package cn.haier.bio.medical.uhub;

public interface IUHUBListener {
    void onUHUBConnected();
    void onUHUBPrint(String message);
    void onUHUBException(Throwable throwable);
}
