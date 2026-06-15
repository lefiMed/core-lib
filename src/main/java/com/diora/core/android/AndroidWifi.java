package com.diora.core.android;

public interface AndroidWifi {

    void advertiseMe(String role);

    void discoverPeer(String role);

    void connectWithPeer(String address);

    void sendMsg(final byte[] msg);

}
