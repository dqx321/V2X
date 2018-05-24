package com.intelligent.v2xapp.udp;

/**
 * Created by daiqinxue on 2018/2/2.
 */

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;

import com.intelligent.v2xapp.MyApplication;
import com.intelligent.v2xapp.util.DataUtil;
import com.vise.common_utils.log.LogUtils;

import static com.intelligent.v2xapp.activity.V2XMainActivity.MAINACTIVITY_ACTION;
import static com.intelligent.v2xapp.activity.V2XMainActivity.UdpData;

/**
 * UdpHelper帮助类
 *
 * @author 陈喆榕
 */
public class UdpHelper implements Runnable {
    public Boolean IsThreadDisable = false;//指示监听线程是否终止
    private static WifiManager.MulticastLock lock;
    private Handler handler;

    public UdpHelper(WifiManager manager, Handler handler) {
        this.handler = handler;
        this.lock = manager.createMulticastLock("UDPwifi");
    }

    private void sendmainbroad(String list) {
        Intent intent = new Intent(MAINACTIVITY_ACTION);
        intent.putExtra(UdpData, list);
        intent.putExtra("statu", 0);
        MyApplication.getContext().sendBroadcast(intent);//通过此方法将广播发送出去

    }

    private void sendMsg(String msg) {
        handler.sendMessage(handler.obtainMessage(2, msg + "\n"));
    }

    public void StartListen(String ip, int port, String mes) {
        // UDP服务器监听的端口

        // 接收的字节大小，客户端发送的数据不能超过这个大小
        byte[] message = new byte[100];
        try {

            // 建立Socket连接
            DatagramSocket datagramSocket = new DatagramSocket(port);
            datagramSocket.setBroadcast(true);
//            DatagramPacket datagramPacket = new DatagramPacket(message,
//                    message.length);

            byte[] messageByte = mes.getBytes();
            int msg_length = mes.length();
            DatagramPacket datagramPacket1 = new DatagramPacket(messageByte, msg_length, InetAddress.getByName(ip), port);
            datagramSocket.send(datagramPacket1);
            datagramPacket1.setData(message);
            datagramPacket1.setLength(message.length);
            while (!IsThreadDisable) {
                // 准备接收数据
                sendMsg("准备接受");
                this.lock.acquire();

                datagramSocket.receive(datagramPacket1);

                char[] a = DataUtil.getChars(datagramPacket1.getData());


                byte[] bytes = DataUtil.getBytes(a);
                String str = "";
                for (int j = 0; j < bytes.length; j++) {
                    //ConversionUtil.byte2int()方法用于获取每个数据项前4个byte,这4个字节就是每个数据项携带的日期字段值；obj.getTradeDate()为客户端提交的交易时间查询条件
                    str = str + bytes[j] + "";
                }
                sendMsg(str.replaceAll("00", ""));
                sendMsg(DataUtil.bytes2HexString(bytes).replaceAll("00", ""));

                String strMsg = new String(datagramPacket1.getData()).trim();
                sendMsg("UDP Demo" + datagramPacket1.getAddress()
                        .getHostAddress().toString()
                        + ":" + strMsg);
                this.lock.release();
                sendmainbroad(strMsg);

            }
        } catch (Exception e) {//IOException
            e.printStackTrace();
        }

    }

    int toUnsigned(char s) {

        return s & 0x0FFFF;

    }

    public void send(String ip, int server_port, String message) {
        message = (message == null ? "Hello IdeasAndroid!" : message);
        sendMsg("UDP发送数据:" + message);
        DatagramSocket s = null;
        try {
            s = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        InetAddress local = null;
        try {
            local = InetAddress.getByName("255.255.255.255");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int msg_length = message.length();
        byte[] messageByte = message.getBytes();
        DatagramPacket p = null;//新建一个DatagramPacket对象，将目标IP地址和端口号修改成//你要发送的目标地址及端口号
        try {
            p = new DatagramPacket(messageByte, msg_length, InetAddress.getByName(ip), server_port);
            s.send(p);
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
//        StartListe();
    }
}