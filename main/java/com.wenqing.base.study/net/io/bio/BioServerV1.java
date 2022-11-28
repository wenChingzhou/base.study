package com.wenqing.base.study.net.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xiaowei.zhou
 * @date 2022/11/18
 */
public class BioServerV1 {

    /**
     * 缺点：性能差 一次只能接一个客 无并发
     * @param args
     */
    public static void main(String[] args) {
        try {
            int port = 8090;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务端启动，监听端口:" + port);

            while (true) {
                // 接客
                socketAccept(serverSocket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void socketAccept(ServerSocket serverSocket) {
        Socket clientSocket = null;
        try {
            // 阻塞 只到接收到新请求
            clientSocket = serverSocket.accept();
            int clientPort = clientSocket.getPort();
            System.out.println("服务端  接收到新请求:" + clientPort);
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String msg = null;
            while ((msg = bufferedReader.readLine()) != null) {
                System.out.println("服务端   已收到+" + msg + ",端口" + clientPort);
            }
            // 调用shutdown 通知对端请求完毕
            clientSocket.shutdownInput();
            bufferedReader.close();
            System.out.println("服务端  结束" + clientPort);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
