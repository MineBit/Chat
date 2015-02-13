//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package ru.gusev_pavel.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleChatClient {
    public static String Tittle = "Brain Hands Chat | 0.0.2.5";
    static String ErrorTittle = "Ошибка!";
    JTextArea incoming;
    JPanel pane0;
    JPanel pane1;
    JPanel pane2;
    JPanel paneButtons;
    JTextField outgoing;
    JTextField nicknameIn;
    JTextField ipIn;
    JLabel mes;
    JLabel nickname;
    JLabel ip;
    JButton confirum;
    JButton ok;
    JButton exit;
    BufferedReader reader;
    PrintWriter writer;
    Socket sock;
    static String nicname;
    static String netip;
    static int port;

    public SimpleChatClient() {
    }

    public void go() {
        JOptionPane.showMessageDialog((Component)null, "<html><p align=center>Разработано Гусевым Павлом</p><p align=center>2014© Все права защищены</p></html>", Tittle, 1);
        netip = JOptionPane.showInputDialog((Component)null, "Введите IP сервера: ", Tittle, 3);
        if(netip == null) {
            netip = "127.0.0.1";
        }

        port = Integer.parseInt(JOptionPane.showInputDialog((Component)null, "Введите порт сервера: ", Tittle, 3));
        if(port == 0) {
            port = 5000;
            JOptionPane.showMessageDialog((Component)null, "Вы не выбрали порт! Порт выбран автоматически(5000)!", Tittle, 2);
        }

        nicname = JOptionPane.showInputDialog((Component)null, "Введите ваш ник: ", Tittle, 3);
        JFrame frame = new JFrame(Tittle);
        JPanel mainPanel = new JPanel();
        this.incoming = new JTextArea(15, 50);
        this.incoming.setLineWrap(true);
        this.incoming.setWrapStyleWord(true);
        this.incoming.setEditable(false);
        JScrollPane qScroller = new JScrollPane(this.incoming);
        qScroller.setVerticalScrollBarPolicy(22);
        qScroller.setHorizontalScrollBarPolicy(32);
        this.outgoing = new JTextField(20);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SimpleChatClient.SendButtonListener());
        mainPanel.add(qScroller);
        mainPanel.add(this.outgoing);
        mainPanel.add(sendButton);
        frame.getContentPane().add("Center", mainPanel);
        this.setUpNetworking();
        Thread readerThread = new Thread(new SimpleChatClient.IncomingReader());
        readerThread.start();
        frame.setSize(650, 500);
        frame.setVisible(true);
    }

    private void setUpNetworking() {
        try {
            this.sock = new Socket(netip, 5000);
            InputStreamReader ex = new InputStreamReader(this.sock.getInputStream());
            this.reader = new BufferedReader(ex);
            this.writer = new PrintWriter(this.sock.getOutputStream());
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public static void main(String[] args) {
        (new SimpleChatClient()).go();
    }

    class IncomingReader implements Runnable {
        IncomingReader() {
        }

        public void run() {
            while(true) {
                try {
                    String message;
                    if((message = SimpleChatClient.this.reader.readLine()) != null) {
                        SimpleChatClient.this.incoming.append(message + "\n");
                        continue;
                    }
                } catch (IOException var3) {
                    var3.printStackTrace();
                }

                return;
            }
        }
    }

    public class SendButtonListener implements ActionListener {
        public SendButtonListener() {
        }

        public void actionPerformed(ActionEvent ev) {
            try {
                SimpleChatClient.this.writer.println(SimpleChatClient.nicname + ": " + SimpleChatClient.this.outgoing.getText());
                SimpleChatClient.this.writer.flush();
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            SimpleChatClient.this.outgoing.setText("");
            SimpleChatClient.this.outgoing.requestFocus();
        }
    }
}
