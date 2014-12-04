package com.brainhands.brainchat.ver_02.client;

import com.brainhands.brainchat.ver_02.client.frames.StartFrame;

import javax.swing.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.io.*;
 
/**
* Класс-клиент чат-сервера. Работает в консоли. Командой с консоли shutdown посылаем сервер в оффлайн
*/

public class ChatClient {
	final Socket s; // это будет сокет для сервера
	final BufferedReader socketReader; // буферизированный читатель с сервера
	final BufferedWriter socketWriter; // буферизированный писатель на сервер
	public static String nickname = "User";
	public static String host = "127.0.0.1"; //TODO При продакшине поменять локалхост на ip сервера
	
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
		socketWriter = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));;
		new Thread(new Receiver()).start();// создаем и запускаем нить асинхронного чтения из сокета
	}
 
	/**
	* метод, где происходит главный цикл чтения сообщений с консоли и отправки на сервер
	*/
	
	public void run() {
		System.out.println("Напишите что-нибудь, чтобы отправить (Для выхода зажмите Enter):");
		while (true) {
			String userString = null;
				try {
					socketWriter.write(userString); //пишем строку пользователя
					socketWriter.flush(); // отправляем
				} catch (IOException e) {
					close(); // в любой ошибке - закрываем.
			}
		}
	}
    public void SendToServer() {
        System.out.println("Напишите что-нибудь, чтобы отправить (Для выхода зажмите Enter):");
        while (true) {
            String userString = null;
            try {
                socketWriter.write(userString); //пишем строку пользователя
                socketWriter.flush(); // отправляем
            } catch (IOException e) {
                close(); // в любой ошибке - закрываем.
            }
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
 
	public static void main(String[] args) { // входная точка программы

        try {
            new Connection(host, 15000); // Пробуем приконнетиться...

           // Connection.SendToServer(); //TODO Отправка первичного сообщения иницилизации
        } catch (IOException e) { // если объект не создан...
            System.out.println("Невозможно соединиться. Сервер запущен?"); // сообщаем...
        }
        //Запуск стартового окна входа:
		StartFrame.run();

		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		nickname = input.next();


	}

    public static void SetUpConnect(String host, int port){

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
					System.out.println("Соединение потеряно!"); // а сюда мы попадем в случае ошибок сети.
					close(); // ну и закрываем сокет (кстати, вызвается метод класса ChatClient, есть доступ)
				}
				if (line == null) { // строка будSuет null если сервер прикрыл коннект по своей инициативе, сеть работает
					System.out.println("Сервер зарыл соединетние!");
					close(); // ...закрываемся
				} else { // иначе печатаем то, что прислал сервер.
					System.out.println(">>" + line);
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
}
 