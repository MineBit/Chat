package com.brainhands.brainchat.ver_01_02.client;

/**
 * Created by Василевский on 12.02.2015.
 */

import com.brainhands.brainchat.utill.Crypto;
import com.brainhands.brainchat.ver_01_02.client.gui.ChatRoom;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс-клиент чат-сервера. Работает в консоли. Командой с консоли shutdown посылаем сервер в оффлайн
 */

public class ChatClient {
    final public static String Version = "0.1.2G";
    final public static String BuilDVersion = "0045";
    final Socket s; // это будет сокет для сервера
    final BufferedReader socketReader; // буферизированный читатель с сервера
    final BufferedWriter socketWriter; // буферизированный писатель на сервер
    final BufferedReader userInput; // буферизированный читатель пользовательского ввода с консоли]

    static boolean is_joined = false;

    public static String host = "127.0.0.1";

    /**
     * Конструктор объекта клиента
     * @param host - IP адрес или localhost или доменное имя
     * @param port - порт, на котором висит сервер
     * @throws java.io.IOException - если не смогли приконнектиться, кидается исключение, чтобы
     * предотвратить создание объекта
     */

    public ChatClient(String host, int port) throws IOException {
        s = new Socket(host, port); // создаем сокет
        // создаем читателя и писателя в сокет с дефолной кодировкой UTF-8
        socketReader = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
        socketWriter = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
        // создаем читателя с консоли (от пользователя)
        userInput = new BufferedReader(new InputStreamReader(System.in));
        new Thread(new Receiver()).start();// создаем и запускаем нить асинхронного чтения из сокета
    }

    /**
     * метод, где происходит главный цикл чтения сообщений с консоли и отправки на сервер
     */

    public void run() {
        while (true) {
            if (ChatRoom.redy_to_send == true) {
                String userString = null;
                    if (is_joined == false) {
                        userString = "Пользователь " + User.nickname + " подключился!";
                        is_joined = true;
                    } else {
                        userString = ChatRoom.message; // читаем строку от пользователя
                    }
                //если что-то не так или пользователь просто нажал Enter...
                if (userString == null || userString.length() == 0 || s.isClosed()) {
                    close(); // ...закрываем коннект.
                    break; // до этого break мы не дойдем, но стоит он, чтобы компилятор не ругался
                } else { //...иначе...
                    try {
                        String Crypted_String = Crypto.Cripting("[" + get_time() + "] " + User.nickname + ": " + userString);
                        socketWriter.write(Crypted_String); //пишем строку пользователя
                        socketWriter.write("\n"); //добавляем "новою строку", дабы readLine() сервера сработал
                        socketWriter.flush(); // отправляем
                    } catch (IOException e) {
                        close(); // в любой ошибке - закрываем.
                    }
                }
            }
            ChatRoom.redy_to_send = false;
        }
    }
    /**
     * метод закрывает коннект и выходит из
     * программы (это единственный выход прервать работу BufferedReader.readLine(), на ожидании пользователя)
     */

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

    /**
     * Вложенный приватный класс асинхронного чтения
     */

    private class Receiver implements Runnable{

        /**
         * run() вызовется после запуска нити из конструктора клиента чата.
         */

        public void run() {
            while (!s.isClosed()) { //сходу проверяем коннект.
                String line = null;
                try {
                    line = socketReader.readLine(); // пробуем прочесть
                } catch (IOException e) { // если в момент чтения ошибка, то...
                    // проверим, что это не банальное штатное закрытие сокета сервером
                    if ("Socket closed".equals(e.getMessage())) {
                        break;
                    }
                    System.out.println("Connected Error!"); // а сюда мы попадем в случае ошибок сети.
                    close(); // ну и закрываем сокет (кстати, вызвается метод класса ChatClient, есть доступ)
                }
                if (line == null) { // строка будSuет null если сервер прикрыл коннект по своей инициативе, сеть работает
                    System.out.println("Server close connection!");
                    close(); // ...закрываемся
                } else { // иначе печатаем то, что прислал сервер.
                    ChatRoom.MessageArea.append(">>" + Crypto.Recripting(line)+"\n");
                }
            }
        }
    }

    public static String get_time(){
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        String returner = format.format(d);
        return returner;
    }

    public static class Connection implements Runnable{
        public void run() {
            try {
                new ChatClient(host, 45000).run(); // Пробуем приконнетиться...
            } catch (IOException e) { // если объект не создан...
                System.out.println("Connected Error!"); // сообщаем...
            }
        }
    }
}
