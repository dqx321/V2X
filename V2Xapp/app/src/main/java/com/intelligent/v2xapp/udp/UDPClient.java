package com.intelligent.v2xapp.udp;

/**
 * Created by daiqinxue on 2018/2/2.
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.lang.String;
public class UDPClient extends Thread{
    public String m_serverIP;
    public String m_text;
    public UDPClient(String strServerIP, String strText)
    {
        m_serverIP = strServerIP;
        m_text = strText;
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        int TIMEOUT = 3000;
        int servPort = 8800;
        byte[] bytesToSend = m_text.getBytes();//"test_client".getBytes();
        try {
            InetAddress serverAddress = InetAddress.getByName(m_serverIP);
            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(TIMEOUT);
            DatagramPacket sendPacket = new DatagramPacket(bytesToSend,bytesToSend.length,serverAddress,servPort);
            socket.send(sendPacket);
            socket.close();
        } catch (SocketException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}