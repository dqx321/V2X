package com.intelligent.v2xapp.udp;

import android.os.Handler;
import android.os.Message;

import com.intelligent.v2xapp.activity.main.MainBean;
import com.intelligent.v2xapp.activity.main.WarningBean;
import com.intelligent.v2xapp.util.DataUtil;
import com.intelligent.v2xapp.util.ExecutorUtil;
import com.intelligent.v2xapp.util.TagUtils;
import com.vise.common_utils.log.LogUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

/**
 * Created by daiqinxue on 2018/1/16.
 */

public class UdpSocket {
    private DatagramSocket socket;
    public static final int UDPPORT = 8888;
    private Handler handler;
    private boolean getMessage;

    public UdpSocket(Handler handler, int port) throws SocketException {
        this.handler = handler;
        getMessage = true;
        if (socket == null) {
            socket = new DatagramSocket(port);
        }
    }

    public void CloseUdp() {
        getMessage=false;
        socket.close();
    }

    byte data[] = new byte[1024];

    public void initSocket() {
        ExecutorUtil.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    DatagramPacket datagramPackage = new DatagramPacket(data, data.length);
                    while (getMessage) {
                        socket.receive(datagramPackage);
                        byte[] bytes = datagramPackage.getData();
                        Message msg1 = handler.obtainMessage();
                        msg1.obj = bytesToHexString(bytes);
                        msg1.what = 10;
                        handler.sendMessage(msg1);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getUdpData() {
        try {
            DatagramPacket udpPacket = new DatagramPacket(data, data.length);
            while (getMessage) {

                try {
                    socket.receive(udpPacket);
                } catch (Exception e) {

                }
                //接收到的byte[]
                byte[] m = Arrays.copyOf(udpPacket.getData(), udpPacket.getLength());

            }

            socket.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void sendUdp(final String message, final String address, final int port) {
        ExecutorUtil.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress serverAddress = null;
                    serverAddress = InetAddress.getByName(address);
                    byte data[] = message.getBytes();
                    DatagramPacket datagramPacket = new DatagramPacket(data, data.length, serverAddress, port);
                    socket.send(datagramPacket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static String bytesToHexString(byte[] bytes) {
        String result = "";
        for (int i = 0; i < bytes.length; i++) {

            String hexString = Integer.toHexString(bytes[i] & 0xFF);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            result += hexString.toUpperCase();
        }
        return result;
    }

}
