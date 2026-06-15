package com.diora.core.mode;

public interface MsgEvent {

    void sendMsg(byte[] msg);
    void reciveMsg(byte[] msg);
}
