package com.brainhands.brainchat.ver_02.client;

import com.brainhands.brainchat.utill.Crypto;
import com.brainhands.brainchat.utill.MathUtill;
import com.brainhands.brainchat.ver_02.client.gui.MainFrame;
import com.brainhands.brainchat.ver_02.client.gui.StartFrame;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mine_Bit[Brain Hands]
 * forum.brainhands.ru
 * brain-soft.org
 */

public class ChatClient {

	final public static String Version = "0.2"; // Переменная хранит в себе версию чата
	final public static String BuilDVersion = "0036"; // Переменная содержит номер сборки

	public static String nickname = null; //Дефолтное значение для имени пользователя
	public static String host = "127.0.0.1"; //Дефолтное значение хоста TODO перед выпуском заменить на ip сервера
	public static int user_personal_id = MathUtill.GetRandom(10000000,99999999); //Персональнальный индификационный номер клиента, генерируется заново при каждом новом запуске программы
	public static boolean is_general_chat_work = false;
	final Socket s;
	static BufferedReader socketReader; //Буферизированный читатель с сервера
	static BufferedWriter socketWriter; //Буферизированный писатель на сервер


	//Конструктор обьекта:
	public ChatClient(String host, int port) throws IOException {
		s = new Socket(host, port);
		//Создаем читателя и писателя в сокет с дефолной кодировкой UTF-8
		socketReader = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
		socketWriter = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
		new Thread(new Receiver()).start();//Создаем и запускаем нить асинхронного чтения из сокета
	}

	public static void main(String[] args) {
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

	//Метод, где происходит отправка сообщений на сервер:
	public void run(String toSend) {
		while (true) {
			if (toSend == null || toSend.length() == 0 || s.isClosed()) {
				close(); // ...закрываем коннект.
				break; // до этого break мы не дойдем, но стоит он, чтобы компилятор не ругался
			} else {
				try {
					if (MainFrame.redy_to_send){
						toSend = user_personal_id+"@"+nickname+"@2@2@"+MainFrame.MessageField.getText();
						MainFrame.redy_to_send = false;
					}
					String Crypted_String = Crypto.Cripting(toSend);
					socketWriter.write(Crypted_String); //пишем строку пользователя
					socketWriter.write("\n"); //добавляем "новою строку", дабы readLine() сервера сработал
					socketWriter.flush(); // отправляем
				} catch (IOException e) {close();}
			}
		}
	}

	/**
	 * Этот коментарий не удалять, пока не будет написанн чат!!!
	 * метод закрывает коннект и выходит из
	 * программы (это единственный выход прервать работу BufferedReader.readLine(), на ожидании пользователя)
	 */

	//Метод закрывает соединение с сервером
	//Метод синхронизированн, чтобы избежать двойного закрытия:
	public synchronized void close() {
		if (!s.isClosed()) { // проверяем, что сокет не закрыт...
			try {
				s.close(); // закрываем...
			} catch (IOException ignored) {
				ignored.printStackTrace();
			}
		}
	}

	//Вложенный приватный класс асинхронного чтения
	private class Receiver implements Runnable {
		// Этот метод автоматически вызовется после запуска нити из конструктора клиента чата:
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
					if(is_general_chat_work && Integer.parseInt(parsed[2]) == 2) {
						MainFrame.GeneralChatArea.append(Crypto.Recripting(parsed[3]));
					}if(is_general_chat_work == false && Integer.parseInt(parsed[2]) != 2 && Integer.parseInt(parsed[0]) == user_personal_id && nickname.equals(parsed[1])){
						switch (Integer.parseInt(parsed[2])) {
							case 0:
								FromServerStrings.string_from_code_0 = null;
								FromServerStrings.string_from_code_0 = Crypto.Recripting(line);
								close();
							case 1:
								FromServerStrings.string_from_code_1 = null;
								FromServerStrings.string_from_code_1 = Crypto.Recripting(line);
								close();
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
		} catch (IOException e) {e.printStackTrace();}
	}

	//Метод для авторизации:
	public static boolean LogIn(String username, String password){
		nickname = username;
		SendToServer(Integer.toString(user_personal_id)+"@"+nickname+"@1@"+username+"@"+password);
		while(true){
			if(FromServerStrings.string_from_code_1 != null){
				String[] parsed = FromServerStrings.string_from_code_1.split("@");
				switch (Integer.parseInt(parsed[3])){
					case 0:
						nickname = null;
						return false;
					case 1:
						nickname = username;
						return true;
					default:
						JOptionPane.showMessageDialog(null, "Неизвестная ошибка авторизации!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
						return false;
				}
			}
		}
	}

	//Метод для регистрации:
	public static boolean Registration(String username, String password){
		nickname = username;
		SendToServer(Integer.toString(user_personal_id)+"@"+nickname+"@0@"+username+"@"+password);
		while (true) {
			if (FromServerStrings.string_from_code_0 != null) {
				String[] parsed = FromServerStrings.string_from_code_0.split("@");
				switch (Integer.parseInt(parsed[3])) {
					case 0:
						nickname = null;
						return false;
					case 1:
						nickname = username;
						return true;
					default:
						JOptionPane.showMessageDialog(null, "Неизвестная ошибка регистрации!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
						return false;
				}
			}
		}

	}
}
 