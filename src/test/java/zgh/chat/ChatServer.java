package zgh.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * socket聊天服务端
 * @author zhangguihua(hua7381@163.com)
 * date: 2014年4月1日
 */
public class ChatServer {
    
    /**
     * main方法启动单个
     * @param args
     */
    public static void main(String[] args) {
        new ChatServer().start();
    }
    
    private boolean started = false;
    private List<Client> clients = new ArrayList<Client>();
    
    private void start() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(8888);
            started = true;
            System.out.println("server started!");
        } catch (BindException e) {
            System.out.println("server failed to start, because the port has been used!");
        } catch (IOException e) {
            System.out.println("server failed to start!");
        }
        
        Socket skt = null;
        while(started) {
            try {
                skt = ss.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Client clt = new Client(skt);
System.out.println("a client connected!");
            new Thread(clt).start();
        }
    }

    class Client implements Runnable {
        Socket skt = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        boolean bConnected = false;
        
        Client(Socket skt) {
            this.skt = skt;
            try {
                dis = new DataInputStream(skt.getInputStream());
                dos = new DataOutputStream(skt.getOutputStream());
                bConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        public void run() {
            clients.add(this);
            System.out.println("clients.size() = " + clients.size());
            try {
                while (bConnected) {
                    String str = dis.readUTF();
                    for (int i=0; i<clients.size(); i++) {
                        clients.get(i).dos.writeUTF(str);
                    }
                    System.out.println("the server get:" + str);
                }
            } catch(SocketException e) {
                System.out.println("a socket losed.");
            } catch (EOFException e) {
                System.out.println("a client closed!");
                clients.remove(this);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (skt != null)skt.close();
                    if (dis != null)dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
