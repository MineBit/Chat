package com.brainhands.brainchat.ver_02.client;

import com.brainhands.brainchat.utill.Crypto;
import com.brainhands.brainchat.utill.MathUtill;
import com.brainhands.brainchat.ver_02.client.gui.StartFrame;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
 
/**
* Класс-клиент чат-сервера. Работает в консоли. Командой с консоли shutdown посылаем сервер в оффлайн
*/

public class ChatClient {

	final public static String Version = "0.2"; // Переменная хранит в себе версию чата
	final public static String BuilDVersion = "0034"; // Переменная содержит номер сборки

	public static String nickname = null; //Дефолтное значение для имени пользователя
	public static String host = "127.0.0.1"; //Дефолтное значение хоста TODO перед выпуском заменить на ip сервера
	public static int user_personal_id = MathUtill.GetRandom(10000000,99999999);

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

	//Метод для получения текущего времени на компьютере:
	public static String get_time() {
		Date d = new Date();
		SimpleDateFormat format = new SimpleDateFormat("hh:mm");
		String returner = format.format(d);
		return returner;
	}

	/**
	 * метод, где происходит отправка сообщений на сервер
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
				if (line == null) { // строка будет null если сервер прикрыл коннект по своей инициативе, сеть работает
					System.out.println("Server close connection!");
					close(); // ...закрываемся
				} else { // иначе печатаем то, что прислал серверa
					System.out.println("Принято с сервера:" + Crypto.Recripting(line));
					String[] parsed = Crypto.Recripting(line).split("@");
					if (Integer.parseInt(parsed[0]) == user_personal_id && nickname.equals(parsed[1])){
						switch (Integer.parseInt(parsed[2])){
							case 0:
								FromServerStrings.string_from_code_0 = null;
								FromServerStrings.string_from_code_0 = Crypto.Recripting(line);
							case 1:
								FromServerStrings.string_from_code_1 = null;
								FromServerStrings.string_from_code_1 = Crypto.Recripting(line);
						}
					}
				}
			}
		}
	}

	//Метод для отправки данных на сервер:
	public static void SendToServer(String to_send){
		try {
			new ChatClient(host, 45000).run(to_send);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Метод для авторизации:
	public static void logIn(String Login, String Pass) throws IOException {
		SendToServer(Login+"@1@"+Pass);
		//TODO Недописан
	}

	//Метод для регистрации:
	public static int Registration(String username, String password){
		nickname = username;
		SendToServer(Integer.toString(user_personal_id)+"@"+nickname+"@0@"+username+"@"+password);
		FromServerStrings.string_from_code_0 = null;
		while (true){
			if(FromServerStrings.string_from_code_0 != null){
				//String[] 0
				break;
			}
		}
		return 0;
	}
}
 