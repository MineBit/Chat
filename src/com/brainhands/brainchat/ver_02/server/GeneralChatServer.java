package com.brainhands.brainchat.ver_02.server;

import com.brainhands.brainchat.utill.Crypto;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Василевский on 26.01.2015.
 */
public class GeneralChatServer {
    private ServerSocket ss;
    private Thread serverThread;
    private int port;

    //очередь, где храняться все SocketProcessorы для рассылки
    BlockingQueue<SocketProcessor> q = new LinkedBlockingQueue<SocketProcessor>();

    public  GeneralChatServer(int port) throws IOException{
        ss = new ServerSocket(port);
        this.port = port;
    }

    void run(){
        serverThread = Thread.currentThread();
        System.out.println("Сервер основного чата запущен!");
        while (true){
            Socket s = getNewConn();
        }
    }

    private Socket getNewConn() {
        Socket s = null;
        try {
            s = ss.accept();
        } catch (IOException e) {
            shutdownServer(); // если ошибка в момент приема - "гасим" сервер
        }
        return s;
    }

    private synchronized void shutdownServer() {
        // обрабатываем список рабочих коннектов, закрываем каждый
        for (SocketProcessor s: q) {
            s.close();
        }
        if (!ss.isClosed()) {
            try {
                ss.close();
            } catch (IOException ignored) {}
        }
    }

    private class SocketProcessor implements Runnable{
        Socket s; // наш сокет
        BufferedReader br; // буферизировнный читатель сокета
        BufferedWriter bw; // буферизированный писатель в сокет

        /**
         * Сохраняем сокет, пробуем создать читателя и писателя. Если не получается - вылетаем без создания объекта
         * @param socketParam сокет
         * @throws IOException Если ошибка в создании br || bw
         */

        SocketProcessor(Socket socketParam) throws IOException {
            s = socketParam;
            br = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
            bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8") );
        }

        /**
         * Главный цикл чтения сообщений/рассылки
         */

        public void run() {
            while (!s.isClosed()) { // пока сокет не закрыт...
                String line = null;
                try {
                    line = br.readLine(); // пробуем прочесть.
                } catch (IOException e) {
                    close(); // если не получилось - закрываем сокет.
                }
                if (line == null) { // если строка null - клиент отключился в штатном режиме.
                    close(); // то закрываем сокет
                } else if ("shutdown".equals(line)) { // если поступила команда "погасить сервер", то...
                    serverThread.interrupt(); // сначала возводим флаг у северной нити о необходимости прерваться.
                    try {
                        new Socket("localhost", port); // создаем фейк-коннект (чтобы выйти из .accept())
                    } catch (IOException ignored) { //ошибки неинтересны
                    } finally {
                        shutdownServer(); // а затем глушим сервер вызовом его метода shutdownServer().
                    }
                } else { // иначе - банальная рассылка по списку сокет-процессоров
                    for (SocketProcessor sp:q) {
                        sp.send(line);
                    }
                }
            }
        }

        /**
         * Метод посылает в сокет полученную строку
         * @param line строка на отсылку
         */

        public synchronized void send(String line) {
            try{
                bw.write(Crypto.Cripting(Crypto.Recripting(line)+"\n")); // пишем строку
                bw.flush(); // отправляем
            } catch (IOException e) {
                close(); //если глюк в момент отправки - закрываем данный сокет.
            }
        }

        /**
         * метод аккуратно закрывает сокет и убирает его со списка активных сокетов
         */

        public synchronized void close() {
            q.remove(this); //убираем из списка
            if (!s.isClosed()) {
                try {
                    s.close(); // закрываем
                } catch (IOException ignored) {}
            }
        }

        /**
         * финализатор просто на всякий случай.
         * @throws Throwable
         */

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            close();
        }
    }
}
