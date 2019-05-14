package com.example.liao.isuke.net;

import android.content.Intent;
import android.util.Log;

import com.example.liao.isuke.utils.CmdDataUtils;
import com.example.liao.isuke.utils.UIUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient implements Runnable {
    final int udpPort = 6000;
    private String hostIp = "192.168.1.255";  //192.168.4.1//4.255
    private DatagramSocket socket = null;
    private DatagramPacket packetSend, packetRcv;
    private boolean udpLife = true; //udp生命线程
    private byte[] msgRcv = new byte[1024]; //接收消息

    public UDPClient(String hostIp) {
        super();
        this.hostIp = hostIp;
    }

    //返回udp生命线程因子是否存活
    public boolean isUdpLife() {
        if (udpLife) {
            return true;
        }

        return false;
    }

    //更改UDP生命线程因子
    public void setUdpLife(boolean b) {
        udpLife = b;
    }

    //发送消息
    public boolean send(byte[] msgSend) {
        InetAddress hostAddress = null;
        try {
            hostAddress = InetAddress.getByName(hostIp);
        } catch (UnknownHostException e) {
            Log.i("udpClient", "未找到服务器");
            e.printStackTrace();
            return false;
        }

      /*  try {
            if (socket == null) {
                socket = new DatagramSocket();
                socket.setSoTimeout(5000);//设置超时为5s
            }
        } catch (SocketException e) {
            Log.i("udpClient", "建立发送数据报失败");
            e.printStackTrace();
            return false;
        }*/
        packetSend = new DatagramPacket(msgSend, msgSend.length, hostAddress, udpPort);

        try {
            socket.send(packetSend);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("udpClient", "发送失败");
            return false;
        }
        //   socket.close();
        return true;
    }

    @Override
    public void run() {

        try {
            if (socket == null) {
                socket = new DatagramSocket();
                socket.setSoTimeout(5000);//设置超时为5s
            }
        } catch (SocketException e) {
            Log.i("udpClient", "建立接收数据报失败");
            e.printStackTrace();
        }
        packetRcv = new DatagramPacket(msgRcv, msgRcv.length);
        while (udpLife) {
            try {
                Log.i("udpClient", "UDP监听");
                socket.receive(packetRcv);
                byte[] data = packetRcv.getData();

                String RcvMsg = new String(packetRcv.getData(), packetRcv.getOffset(), packetRcv.getLength());
                //将收到的消息发给主界面
                Intent RcvIntent = new Intent();
                RcvIntent.setAction("udpRcvMsg");
                RcvIntent.putExtra("udpRcvMsg", RcvMsg);
//                MainActivity.context.sendBroadcast(RcvIntent);
                UIUtils.print("udpClient.........." + CmdDataUtils.ByteArrayToHex(data));
                if (CmdDataUtils.ByteArrayToHex(data).equals("24 06 01 09 10 01"))//24 06 01 09 10 01

                    setUdpLife(false);
                Log.i("Rcv", RcvMsg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.i("udpClient", "UDP监听关闭");
        socket.close();
    }

    public void close() {
        socket.close();
    }
}