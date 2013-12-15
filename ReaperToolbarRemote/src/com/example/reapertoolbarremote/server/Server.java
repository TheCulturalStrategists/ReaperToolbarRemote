package com.example.reapertoolbarremote.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Server extends Thread {
        private ServerSocket listener = null;
        private static Handler mHandler;
        private boolean running = true;
        
        public static LinkedList<Socket> clientList = new LinkedList<Socket>();
        
    public Server(String ip, int port, Handler handler) throws IOException {
                super();
                mHandler = handler;
                InetAddress ipadr = InetAddress.getByName(ip);
                listener = new ServerSocket(port,0,ipadr);
        }
    
    private static void send(String s) {
        Message msg = new Message();
        Bundle b = new Bundle();
        b.putString("msg", s);
        msg.setData(b);
        mHandler.sendMessage(msg);
    }
    
        @Override
        public void run() {
                while( running ) {
                        try {
                                send("Waiting for connections.");
                                Socket client = listener.accept();
                            send("New connection from " + client.getInetAddress().toString());
                                new ServerHandler(client).start();
                                clientList.add(client);
                        } catch (IOException e) {
                                send(e.getMessage());
                        }
                }
        }

        public void stopServer() {
                running = false;
                try {
                        listener.close();
                } catch (IOException e) {
                        send(e.getMessage());
                }
        }
        
        public synchronized static void remove(Socket s) {
            send("Closing connection: " + s.getInetAddress().toString());
        clientList.remove(s);      
    }
}

