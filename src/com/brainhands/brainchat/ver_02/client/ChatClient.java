package com.brainhands.brainchat.ver_02.client;

import com.brainhands.brainchat.utill.Crypto;
import com.brainhands.brainchat.ver_02.client.gui.StartFrame;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
 
/**
* Класс-клиент чат-сервера. Работает в консоли. Командой с консоли shutdown посылаем сервер в оффлайн
*/

public class ChatClient {

	final public static String Version = "0.2"; // Переменная хранит в себе версию чата
	final public static String BuilDVersion = "0034"; // Переменная содержит номер сборки
	public static String nickname = "User"; //Дефолтное значение для имени пользователя
	public static String host = "127.0.0.1"; //Дефолтное значение хоста TODO перед выпуском заменить на ip сервера
	final Socket s; // это будет сокет для сервера
	static BufferedReader socketReader; // буферизированный читатель с сервера
	static BufferedWriter socketWriter; // буферизированный писатель на сервер
	static BufferedReader userInput; // буферизированный читатель пользовательского ввода с консоли

	/**
	 * Конструктор объекта клиента
	 *
	 * @param host - IP адрес или localhost или доменное имя
	 * @param port - порт, на котором висит сервер
	 * @throws java.io.IOException - если не смогли приконнектиться, кидается исключение, чтобы
	 *                             предотвратить создание объекта
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

	public static void main(String[] args) { // входная точка программы
		StartFrame.View();

		System.out.println("Brain Chat | " + Version + "| by Brain Hands");
		System.out.println("Build: " + BuilDVersion);
		System.out.println("Welcome to Chat!");
	}

	public static String get_time() {
		Date d = new Date();
		SimpleDateFormat format = new SimpleDateFormat("hh:mm");
		String returner = format.format(d);
		return returner;
	}

	/**
	 * метод, где происходит главный цикл чтения сообщений с консоли и отправки на сервер
	 */

	public void run(String toSend) {
		//System.out.println("Write for send (For exit press Enter):");
		while (true) {
			String userString = null;
			if (toSend == null || toSend.length() == 0 || s.isClosed()) {
				close(); // ...закрываем коннект.
				break; // до этого break мы не дойдем, но стоит он, чтобы компилятор не ругался
			} else { //...иначе...
				try {
					String Crypted_String = Crypto.Cripting(toSend);
					socketWriter.write(Crypted_String); //пишем строку пользователя
					socketWriter.write("\n"); //добавляем "новою строку", дабы readLine() сервера сработал
					socketWriter.flush(); // отправляем
				} catch (IOException e) {
					close(); // в любой ошибке - закрываем.
				}
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

	/**
	 * Вложенный приватный класс асинхронного чтения
	 */

	private class Receiver implements Runnable {

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
					System.out.println(">>" + Crypto.Recripting(line));
				}
			}
		}
	}

	public static void logIn(String Login, String Pass) throws IOException {
		try {
			new ChatClient(host, 45000).run(Login+"@"+"1"+"@"+Pass);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void Registration(String username, String password){

	}
}
 