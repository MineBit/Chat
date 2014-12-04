package com.brainhands.brainchat.ver_02.client;
import java.io.*;
import java.net.Socket;

/**
 * Класс-клиент чат-сервера. Работает в консоли. Командой с консоли shutdown посылаем сервер в оффлайн
 */

public class Connection {
    static Socket s; // это будет сокет для сервера
    static BufferedReader socketReader = null; // буферизированный читатель с сервера
    static BufferedWriter socketWriter = null; // буферизированный писатель на сервер
    public static String nickname = "User";
    public static String host = "127.0.0.1"; //TODO При продакшине поменять локалхост на ip сервера

    /**
     * Конструктор объекта клиента
     * @param host - IP адрес или localhost или доменное имя
     * @param port - порт, на котором висит сервер
     * @throws java.io.IOException - если не смогли приконнектиться, кидается исключение, чтобы
     * предотвратить создание объекта
     */

    public Connection(String host, int port) throws IOException {
        s = new Socket(host, port); // создаем сокет
        // создаем читателя и писателя в сокет с дефолной кодировкой UTF-8
        socketReader = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
        socketWriter = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));;
        new Thread(new Receiver()).start();// создаем и запускаем нить асинхронного чтения из сокета
    }

    /**
     * метод, где происходит главный цикл чтения сообщений с консоли и отправки на сервер
     */

    public static void SendToServer(String toSend){
            System.out.println("Отправка сообщения на сервер!");
            try {
                socketWriter.write(toSend); //пишем строку пользователя
                socketWriter.flush(); // отправляем
            } catch (IOException e) {
                close(); // в любой ошибке - закрываем.
        }
    }


    /**
     * метод закрывает коннект и выходит из
     * программы (это единственный выход прервать работу BufferedReader.readLine(), на ожидании пользователя)
     */

    public static synchronized void close() {//метод синхронизирован, чтобы исключить двойное закрытие.
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
     //run() вызовется после запуска нити из конструктора клиента чата.

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
                    System.out.println("Соединение потеряно!"); // а сюда мы попадем в случае ошибок сети.
                    close(); // ну и закрываем сокет (кстати, вызвается метод класса ChatClient, есть доступ)
                }
                if (line == null) { // строка будSuет null если сервер прикрыл коннект по своей инициативе, сеть работает
                    System.out.println("Сервер зарыл соединетние!");
                    close(); // ...закрываемся

                } else { // иначе печатаем то, что прислал сервер.
                    System.out.println(">>" + line); //TODO Здесь сообщение от сервера
                }
            }
        }
    }
}
