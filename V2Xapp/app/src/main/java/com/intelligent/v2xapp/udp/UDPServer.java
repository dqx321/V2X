package com.intelligent.v2xapp.udp;

/**
 * Created by 长沙智能驾驶研究院cidi daiqinxue on 2018/3/30.
 */


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import android.util.Log;

public class UDPServer implements Runnable {
    private static final int PORT = 8888;
    private byte[] msg = new byte[2048];
    private boolean life = true;

    public UDPServer() {
    }

    public boolean isLife() {
        return life;
    }

    public void setLife(boolean life) {
        this.life = life;
    }

    @Override
    public void run() {
        DatagramSocket dSocket = null;
        DatagramPacket dPacket = new DatagramPacket(msg, msg.length);
        try {
            dSocket = new DatagramSocket(PORT);
            while (life) {
                try {
                    dSocket.receive(dPacket);
                    Log.d("tian msg sever received",
                            new String(dPacket.getData(), dPacket.getOffset(),
                                    dPacket.getLength())
                                    + "dPacket.getLength()="
                                    + dPacket.getLength());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
