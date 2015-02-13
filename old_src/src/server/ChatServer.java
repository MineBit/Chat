//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class ChatServer {
    ArrayList ClientOutputStreams;

    public static int SocketNum;

    public static int ID = 0;
    public static String Tittle = "BrainChatServer | 0.0.2.5";
    public static String SocketAsk;
    String FileDir = "/logs/l";
    JFrame GFrame;
    JPanel PaneText;
    JPanel PaneButtons;
    JLabel StateWork;
    JLabel ServerIPlb;
    JButton Start;
    JButton Stop;
    public InetAddress ServerIP;
    Client[] client = new Client[10000];

    public static int server_port = 5000;

    ChatServer(String tittle, int w, int h) {
        this.GFrame = new JFrame(tittle);
        this.GFrame.setSize(w, h);
        this.GFrame.setDefaultCloseOperation(3);
        this.GFrame.setLayout(new FlowLayout());
        this.PaneText = new JPanel();
        this.PaneButtons = new JPanel();
        this.StateWork = new JLabel("Сервер выключен!");
        this.PaneText.add(this.StateWork);
        this.ServerIPlb = new JLabel("IP сервера: Неизвесно");
        this.Start = new JButton("Запустить сервер");
        this.PaneButtons.add(this.Start);
        this.Start.addActionListener(new ChatServer.StartButtonListener());
        this.Stop = new JButton("Остановить сервер");
        this.PaneButtons.add(this.Stop);
        this.Stop.addActionListener(new ChatServer.StopButtonListener());
        this.Stop.setFocusPainted(false);
        this.GFrame.add(this.PaneText);
        this.GFrame.add(this.PaneButtons);
        this.GFrame.setVisible(true);
    }

    public void tellEveryone(String message) {
        Iterator it = this.ClientOutputStreams.iterator();

        while(it.hasNext()) {
            try {
                PrintWriter ex = (PrintWriter)it.next();
                ex.println(message);
                ex.flush();
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {

        SocketAsk = JOptionPane.showInputDialog((Component)null, "Введите номер порта, на котором запуститься сервер чата:", Tittle, 1);
        if(SocketAsk == null) {
            JOptionPane.showMessageDialog((Component)null, "Error! Server set default port(5000)!", Tittle, 1);
            SocketNum = 5000;
        } else {
            SocketNum = Integer.parseInt(SocketAsk);
        }

        new ChatServer(Tittle, 300, 200);
    }

    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket sock;

        public ClientHandler(Socket clientSOcket) {
            try {
                this.sock = clientSOcket;
                InputStreamReader ex = new InputStreamReader(this.sock.getInputStream());
                this.reader = new BufferedReader(ex);
            } catch (Exception var4) {
                var4.printStackTrace();
            }

        }

        public void run() {
            while(true) {
                try {
                    String message;
                    if((message = this.reader.readLine()) != null) {
                        ChatServer.this.tellEveryone(message);
                        continue;
                    }
                } catch (Exception var3) {
                    var3.printStackTrace();
                }

                return;
            }
        }
    }

    public class Server implements Runnable {
        public Server() {
        }

        public void run() {
            ChatServer.this.ClientOutputStreams = new ArrayList();

            try {
                ServerSocket ex = new ServerSocket(ChatServer.SocketNum);
                ChatServer.this.ServerIP = ex.getInetAddress();
                ChatServer.this.ServerIPlb.setText("IP сервера: " + ChatServer.this.ServerIP);

                while(true) {
                    Socket ClientSocket = ex.accept();
                    System.out.println("Client №" + ChatServer.ID + " connected!");
                    Client var10000 = ChatServer.this.client[ChatServer.ID];
                    Client.ID = ChatServer.ID;
                    PrintWriter writer = new PrintWriter(ClientSocket.getOutputStream());
                    System.out.println("Client №" + ChatServer.ID + " NetIP: " + ClientSocket.getInetAddress());
                    var10000 = ChatServer.this.client[ChatServer.ID];
                    Client.NetIP = ClientSocket.getInetAddress();
                    System.out.println("Client №" + ChatServer.ID + " LanIP: " + ClientSocket.getLocalAddress());
                    var10000 = ChatServer.this.client[ChatServer.ID];
                    Client.LanIP = ClientSocket.getLocalAddress();
                    ChatServer.this.ClientOutputStreams.add(writer);
                    Thread t = new Thread(ChatServer.this.new ClientHandler(ClientSocket));
                    t.start();
                    ++ChatServer.ID;
                }
            } catch (Exception var5) {
                JOptionPane.showMessageDialog((Component)null, "Ошибка! Данный порт полный!", ChatServer.Tittle, 0);
                var5.printStackTrace();
            }
        }
    }

    public class StartButtonListener implements ActionListener {
        public StartButtonListener() {
        }

        public void actionPerformed(ActionEvent ae) {
            ChatServer.this.Start.setFocusPainted(false);
            ChatServer.this.Stop.setFocusPainted(true);
            ChatServer.this.StateWork.setText("Сервер работает!");
            Thread ValidTread = new Thread(ChatServer.this.new Server());
            ValidTread.start();
        }
    }

    public class StopButtonListener implements ActionListener {
        public StopButtonListener() {
        }

        public void actionPerformed(ActionEvent ev) {
            String FileDir = "/logs/logs.txt";
            String Mess = null;

            for(int ex = 0; ex <= ChatServer.ID; ++ex) {
                Client var10000 = ChatServer.this.client[ex];
                String os = Integer.toString(Client.ID);
                Mess = Mess + "Client №" + ex + "/n";
                Mess = Mess + "ID: " + os + "/n";
                StringBuilder var9 = (new StringBuilder(String.valueOf(Mess))).append("NetIP: ");
                Client var10001 = ChatServer.this.client[ex];
                Mess = var9.append(Client.NetIP).append("/n").toString();
                var9 = (new StringBuilder(String.valueOf(Mess))).append("LanIP: ");
                var10001 = ChatServer.this.client[ex];
                Mess = var9.append(Client.LanIP).append("/n").toString();
            }

            try {
                FileOutputStream var7 = new FileOutputStream(FileDir);
                ObjectOutputStream var8 = new ObjectOutputStream(var7);
                var8.writeObject(Mess);
                var8.close();
            } catch (Exception var6) {
                var6.printStackTrace();
            }

            JOptionPane.showConfirmDialog((Component)null, "Спасибо за использование нашего чата!", ChatServer.Tittle, 0);
            System.exit(0);
        }
    }

    public static void ConsolePrint(String to_print){
        System.out.println(to_print);
    }

    //Метод для записи логов. Выводит логи в консоль
    //TODO Сделать вывод логов в файл, но так, чтобы в логах не было сообщений
    public static void LogPrint(String log_to_print){
        System.out.println("[PROG_LOG] - ("+get_time()+") >>"+log_to_print);
    }

    public static String get_time(){
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        String returner = format.format(d);
        return returner;
    }

}
