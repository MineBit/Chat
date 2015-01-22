package com.brainhands.brainchat.ver_02.server;

import com.brainhands.brainchat.utill.Crypto;
import com.brainhands.brainchat.utill.Files;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Mine_Bit[Brain Hands]
 * forum.brainhands.ru
 * brain-soft.org
 */

public class ChatServer {
	private ServerSocket ss; // сам сервер-сокет
	private Thread serverThread; // главная нить обработки сервер-сокета
	private int port; // порт сервер сокета.

	//очередь, где храняться все SocketProcessorы для рассылки
	BlockingQueue<SocketProcessor> q = new LinkedBlockingQueue<SocketProcessor>();

	/**
	 * Конструктор объекта сервера
	 * @param port Порт, где будем слушать входящие сообщения.
	 * @throws java.io.IOException Если не удасться создать сервер-сокет, вылетит по эксепшену, объект Сервера не будет создан
	 */

	public ChatServer(int port) throws IOException {
		ss = new ServerSocket(port); // создаем сервер-сокет
		this.port = port; // сохраняем порт.
	}

	/**
	 * главный цикл прослушивания/ожидания коннекта.
	 */

	void run() {
		serverThread = Thread.currentThread(); // со старта сохраняем нить (чтобы можно ее было interrupt())
		System.out.println("Сервер запущен!");
		while (true) { //бесконечный цикл, типа...
			Socket s = getNewConn(); // получить новое соединение или фейк-соедиение
			if (serverThread.isInterrupted()) { // если это фейк-соединение, то наша нить была interrupted(),
				// надо прерваться
				break;
			} else if (s != null){ // "только если коннект успешно создан"...
				try {
					final SocketProcessor processor = new SocketProcessor(s); // создаем сокет-процессор
					final Thread thread = new Thread(processor); // создаем отдельную асинхронную нить чтения из сокета
					thread.setDaemon(true); //ставим ее в демона (чтобы не ожидать ее закрытия)
					thread.start(); //запускаем
					q.offer(processor); //добавляем в список активных сокет-процессоров
				} //тут прикол в замысле. Если попытка создать (new SocketProcessor()) безуспешна,
				// то остальные строки обойдем, нить запускать не будем, в список не сохраним
				catch (IOException ignored) {} // само же исключение создания коннекта нам не интересно.
			}
		}
	}

	/**
	 * Ожидает новое подключение.
	 * @return Сокет нового подключения
	 */

	private Socket getNewConn() {
		Socket s = null;
		try {
			s = ss.accept();
		} catch (IOException e) {
			shutdownServer(); // если ошибка в момент приема - "гасим" сервер
		}
		return s;
	}

	/**
	 * метод "глушения" сервера
	 */

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

	/**
	 * входная точка программы
	 * @param args
	 * @throws java.io.IOException
	 */

	public static void main(String[] args) throws IOException {
		//TODO Написать выбор (первая загрузка-непервая загрузка) чтобы не плодились ферсты
		System.out.println("Brain Chat Sever |0.2| by Brain Hands");
		Files.MakeDir("BrainChatServerFiles");
		Files.Write(Crypto.Cripting("First:1"),"BrainChatServerFiles/users.bbf");
		String[] users = Files.Read("BrainChatServerFiles/users.bbf");
		if(users.length == 0){
			System.out.println("База данных пуста!");
		}else System.out.println("Количество зарегистрированных пользователей: "+users.length);
		new ChatServer(45000).run(); // если сервер не создался, программа
		// вылетит по эксепшену, и метод run() не запуститься
	}

	/**
	 * вложенный класс асинхронной обработки одного коннекта.
	 */

	private class SocketProcessor implements Runnable{
		Socket s; // наш сокет
		BufferedReader br; // буферизировнный читатель сокета
		BufferedWriter bw; // буферизированный писатель в сокет

		/**
		 * Сохраняем сокет, пробуем создать читателя и писателя. Если не получается - вылетаем без создания объекта
		 * @param socketParam сокет
		 * @throws java.io.IOException Если ошибка в создании br || bw
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
						sp.send(Crypto.Recripting(line));
					}
				}
			}
		}

		/**
		 * Метод посылает в сокет полученную строку
		 * @param line строка на отсылку
		 */

		public synchronized void send(String line) {
			String send_to_user = null;
			String[] str = line.split("@");
			String [] parsed_users_file = Files.Read("BrainChatServerFiles/users.bbf");
			switch (Integer.parseInt(str[2])){
				case 0:
					//Если пришел запрос с кодом регистрации:
					boolean is_username_used = false;
					for (int i = 0; i < parsed_users_file.length; i++){
						String recrypted_line = Crypto.Recripting(parsed_users_file[i]);
						String[] usernames_parsed = recrypted_line.split(":");
						if(usernames_parsed[0].equals(str[3])){
							is_username_used = true;
						}
					}
					if(is_username_used){
						send_to_user = str[0]+"@"+str[1]+"@"+str[2]+"@0"; //Отправляем ноль, значит имя пользователя занято
					}else{
						//Если все ок, то регистрируем пользователя, и отправляем единицу
						AddNewUser(str[3],str[4]);
						send_to_user = str[0]+"@"+str[1]+"@"+str[2]+"@1";
					}
					break;
				case 1:
					//Если пришел запрос с кодом регистрации:
					boolean is_password_correct = false;
					for (int i = 0; i < parsed_users_file.length; i++){
						String recrypted_line = Crypto.Recripting(parsed_users_file[i]);
						String[] lines_parsed = recrypted_line.split(":");
						if (lines_parsed[0].equals(str[3]) && lines_parsed[1].equals(str[4])){
							is_password_correct = true;
						}
					}
					if (is_password_correct){
						send_to_user = str[0]+"@"+str[1]+"@"+str[2]+"@0"; //Отправляем ноль, значит логин/пароль неверные
					}else{
						send_to_user = str[0]+"@"+str[1]+"@"+str[2]+"@1"; //Отправляем ноль, значит авторизация прошла успешно
					}
					break;
				case 2:
					if (Integer.parseInt(str[3]) == 2){
					send_to_user = "from_server@all@2@"+str[1]+": "+str[4];
					}
					break;
			}
			try{
				bw.write(Crypto.Cripting((send_to_user))); // пишем строку
				bw.write("\n"); // пишем перевод строки
				bw.flush(); // отправляем

			} catch (IOException e) {
				close(); //если глюк в момент отправки - закрываем данный сокет.
			}
		}


		public void Registration(String user_name, String password){

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

	//Метод для регистрации нового пользователя на сервере:
	static void AddNewUser(String username, String password){
		Files.Write(Crypto.Cripting(username+":"+password),"BrainChatServerFiles/users.bbf");
	}
}
