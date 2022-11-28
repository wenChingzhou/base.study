package com.wenqing.base.study.net.io.bio;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 多线程版
 *
 * @author xiaowei.zhou
 * @date 2022/11/18
 */
public class BioServerV2 {

    /**
     * 多线程版或线程池版
     * 性能提升有限 因为线程有限 线程切换性能差  但TPS已经能明显高于V1版
     * 如何优化呢：
     * 1、面向缓存 -- accept()这个方法都对缓存
     * 2、Channel -->Socket
     * 3、Select选择器 -->
     * Server -> Thread -> socket
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            int port = 8090;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务端启动，监听端口:" + port);

            while (true) {
                // 接客
                new ServerThreadReader(serverSocket).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ServerThreadReader extends Thread {
        ServerSocket serverSocket;

        public ServerThreadReader(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            BioServerV1.socketAccept(serverSocket);
        }
    }

}
