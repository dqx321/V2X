package com.intelligent.v2xapp.tcp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.intelligent.v2xapp.util.ExecutorUtil;
import com.intelligent.v2xapp.util.TagUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 长沙智能驾驶研究院cidi daiqinxue
 */

public class WebSocketService extends Service {
    private String TAG = WebSocketService.class.getName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        javawebsocket();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

//使用  startService(new Intent(NewViewActivity.this, WebSocketService.class));
    private void javawebsocket() {
        ExecutorUtil.execute(new Runnable() {
            @Override
            public void run() {
                WebSocketClient webSocketClient;
                URI uri = URI.create("ip");
                webSocketClient = new WebSocketClient(uri) {
                    @Override
                    public void onOpen(ServerHandshake handshakedata) {
//                        TagUtils.TagLog(TAG, handshakedata.getHttpStatus() + handshakedata.getHttpStatusMessage());

                    }

                    @Override
                    public void onMessage(String message) {

                    }

                    @Override
                    public void onClose(int code, String reason, boolean remote) {
//                        TagUtils.TagLog(TAG, code + reason);

                    }

                    @Override
                    public void onError(Exception ex) {
                        ex.printStackTrace();
//                        TagUtils.TagLog(TAG, ex.getMessage() + "+" + ex.getCause());

                    }
                }

                ;
                // 设置 socket 读取数据流的超时时间
                webSocketClient.setConnectionLostTimeout(8000);
                // 发送数据包，默认为 false，即客户端发送数据采用 Nagle 算法；
                // 但是对于实时交互性高的程序，建议其改为 true，即关闭 Nagle 算法，客户端每发送一次数据，无论数据包大小都会将这些数据发送出去
                webSocketClient.setTcpNoDelay(true);
                // 作用：每隔一段时间检查服务器是否处于活动状态，如果服务器端长时间没响应，自动关闭客户端socket
                // 防止服务器端无效时，客户端长时间处于连接状态
                webSocketClient.connect();
            }
        });
    }




}
