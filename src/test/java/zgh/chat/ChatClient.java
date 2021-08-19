package zgh.chat;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.io.*;

/**
 * socket聊天客户端
 * @author zhangguihua(hua7381@163.com)
 * date: 2014年4月1日
 */
public class ChatClient extends Frame {
    
    /**
     * main方法启动单个或多个
     * @param args
     */
    public static void main(String[] args) {
        new ChatClient().launchFrame();
    }
    
    private DataOutputStream dos = null;
    private DataInputStream dis = null;
    private Socket skt = null;
    private TextField tfText = new TextField();
    private TextArea taContent = new TextArea();
    private boolean connected = false;
    private Thread thread = null;

    /**
     * 构造方法
     */
    ChatClient() {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                disconnect();
                setVisible(false);
                System.exit(0);
            }
        });

        tfText.addActionListener(new TFListener());
    }
    
    private class TFListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String str = tfText.getText().trim();
            //taContent.append(str + "\n");
            tfText.setText("");
            try {
                dos.writeUTF(str);
                dos.flush();
                //dos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void launchFrame() {
        this.setLocation(400, 400);
        setSize(300, 300);
        add(tfText, BorderLayout.SOUTH);
        add(taContent, BorderLayout.NORTH);
        pack();
        setVisible(true);
        connect();
    }

    public void connect() {
        try {
            skt = new Socket("127.0.0.1", 8888);
            dos = new DataOutputStream(skt.getOutputStream());
            dis = new DataInputStream(skt.getInputStream());
            
            System.out.println("connected!");
            connected = true;
            
            thread = new Thread(new Server());
            thread.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            connected = false;
            dos.close();
            dis.close();
            skt.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    class Server implements Runnable{
        public void run() {
            String str = null;
            while(connected) {
                try {
                    str = dis.readUTF();
                    taContent.append(str + "\n");
                    //System.out.println(dis.readUTF());
                } catch(SocketException e) {
                    System.out.println("client closed.");
                } catch (IOException e) {e.printStackTrace();}
            }
        }
    }
    
}
