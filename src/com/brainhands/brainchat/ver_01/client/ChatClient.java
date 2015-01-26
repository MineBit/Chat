package com.brainhands.brainchat.ver_01.client;

import com.brainhands.brainchat.utill.Crypto;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
* Класс-клиент чат-сервера. Работает в консоли. Командой с консоли shutdown посылаем сервер в оффлайн
*/

public class ChatClient {
	final public static String Version = "0.1.2S";
	final public static String BuilDVersion = "0033";
	final Socket s; // это будет сокет для сервера
	final BufferedReader socketReader; // буферизированный читатель с сервера
	final BufferedWriter socketWriter; // буферизированный писатель на сервер
	final BufferedReader userInput; // буферизированный читатель пользовательского ввода с консоли
	
	public static String nickname = "User";
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
		System.out.println("Write for send (For exit press Enter):");
		while (true) {
			String userString = null;
			try {
				userString = userInput.readLine(); // читаем строку от пользователя
			} catch (IOException ignored) {} // с консоли эксепшена не может быть в принципе, игнорируем
			//если что-то не так или пользователь просто нажал Enter...
			if (userString == null || userString.length() == 0 || s.isClosed()) {
				close(); // ...закрываем коннект.
				break; // до этого break мы не дойдем, но стоит он, чтобы компилятор не ругался
			} else { //...иначе...
				try {
					String Crypted_String = Crypto.Cripting("[" + get_time() + "] " + nickname + ": " + userString);
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
 
	public static void main(String[] args) { // входная точка программы
		System.out.println("Brain Chat | "+Version+"| by Brain Hands");
		System.out.println("Build: " + BuilDVersion);
		System.out.println("Welcome to Chat!");
		System.out.println("Enter your nickname:");
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		nickname = input.next();
		
		System.out.println("Use default server [1], or use server ip[2]?");
		@SuppressWarnings("resource")
		Scanner input2 = new Scanner(System.in);
		String in_string = input2.next();
		if(Integer.parseInt(in_string) == 2){
			System.out.println("Type server ip:");
			@SuppressWarnings("resource")
			Scanner input3 = new Scanner(System.in);
			host = input3.next();
		}
		try {
			new ChatClient(host, 45000).run(); // Пробуем приконнетиться...
		} catch (IOException e) { // если объект не создан...
			System.out.println("Connected Error!"); // сообщаем...
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
					System.out.println(">>" + Crypto.Recripting(line));
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
 