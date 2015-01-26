package com.brainhands.brainchat.ver_02.client;

import com.brainhands.brainchat.utill.Crypto;
import com.brainhands.brainchat.ver_02.client.gui.MainFrame;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * Created by Василевский on 26.01.2015.
 */
public class MainChat {
    final Socket s;
    final BufferedReader socketReader;
    final BufferedWriter socketWriter;

    public MainChat(String host, int port) throws IOException {
        s = new Socket(host, port);

        socketReader = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
        socketWriter = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
    }
        public void run(){
            while (true){
                if (MainFrame.redy_to_send == true){
                    String user_string = MainFrame.MessageField.getText();
                    if (user_string == null || user_string.length() == 0 || s.isClosed()){
                        JOptionPane.showMessageDialog(null, "Ошибка отправки!","Ошибка!",JOptionPane.ERROR_MESSAGE);
                    }else{
                        try{
                            String crypted_string = Crypto.Cripting(ChatClient.nickname+": "+user_string+"\n");
                            socketWriter.write(crypted_string);
                            socketWriter.flush();
                        }catch(IOException e){
                            JOptionPane.showMessageDialog(null, "Ошибка отправки!","Ошибка!",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    MainFrame.redy_to_send = false;
                }
            }
        }

    public synchronized void close() {//метод синхронизирован, чтобы исключить двойное закрытие.
        if (!s.isClosed()) { // проверяем, что сокет не закрыт...
            try {
                s.close(); // закрываем...
                System.exit(0); // выходим!
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
        }
    }

    private class Receiver implements Runnable {
        public void run() {
            while (!s.isClosed()) {
                String line = null;
                try {
                    line = socketReader.readLine();
                } catch (IOException e) {
                    if ("Socket closed".equals(e.getMessage())) {
                        break;
                    }
                    JOptionPane.showMessageDialog(null, "Ошибка подключения!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    close();
                }
                if (line == null) {
                    JOptionPane.showMessageDialog(null, "Сервер закрыл соединение!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    close();
                } else {
                    MainFrame.GeneralChatArea.append(Crypto.Recripting(line));
                }
            }
        }
    }
}
