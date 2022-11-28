package com.wenqing.base.study.net.io.bio;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author xiaowei.zhou
 * @date 2022/11/18
 */
public class BioClient {

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            // 多客户端模拟
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    clientSocket();
                }
            };
            new Thread(runnable).start();

        }
    }

    private static void clientSocket() {
        try {
            Socket socket = new Socket("localhost", 8090);

            // 客户端-->服务端 输出
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);
            writer.write("客户端发送:" + "hello world");
            writer.flush();
            // 调用shutdown 通知对端请求完毕
            socket.shutdownOutput();

            writer.close();
            System.out.println("客户端发送:" + "hello world");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
