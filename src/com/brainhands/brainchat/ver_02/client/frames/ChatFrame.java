package com.brainhands.brainchat.ver_02.client.frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.Component;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;
import javax.swing.Box;

public class ChatFrame {

	private JFrame frmBrainChat;
	private JTextField message_tf;

	//Метод для запуска окна:
	public static void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatFrame window = new ChatFrame();
					window.frmBrainChat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ChatFrame() {
		initialize();
	}
	private void initialize() {
		frmBrainChat = new JFrame();
		frmBrainChat.setTitle("Brain Chat | 0.2");
		frmBrainChat.setBounds(100, 100, 550, 400);
		frmBrainChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmBrainChat.getContentPane().setLayout(gridBagLayout);
		
		JLabel first_lb = new JLabel("Основной чат:");
		GridBagConstraints gbc_first_lb = new GridBagConstraints();
		gbc_first_lb.gridwidth = 3;
		gbc_first_lb.insets = new Insets(0, 0, 5, 5);
		gbc_first_lb.gridx = 1;
		gbc_first_lb.gridy = 3;
		frmBrainChat.getContentPane().add(first_lb, gbc_first_lb);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridheight = 14;
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 3;
		frmBrainChat.getContentPane().add(verticalStrut, gbc_verticalStrut);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridheight = 10;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		frmBrainChat.getContentPane().add(scrollPane, gbc_scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		JLabel lb_2 = new JLabel("Посетите Наш Форум: forum.brainhands.ru");
		lb_2.setFont(new Font("Dialog", Font.BOLD, 10));
		GridBagConstraints gbc_lb_2 = new GridBagConstraints();
		gbc_lb_2.insets = new Insets(0, 0, 5, 0);
		gbc_lb_2.gridx = 4;
		gbc_lb_2.gridy = 4;
		frmBrainChat.getContentPane().add(lb_2, gbc_lb_2);
		
		JButton get_premium_button = new JButton("Получить Премиум");
		get_premium_button.setIcon(new ImageIcon(ChatFrame.class.getResource("/com/sun/java/swing/plaf/windows/icons/HardDrive.gif")));
		get_premium_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_get_premium_button = new GridBagConstraints();
		gbc_get_premium_button.insets = new Insets(0, 0, 5, 0);
		gbc_get_premium_button.gridx = 4;
		gbc_get_premium_button.gridy = 5;
		frmBrainChat.getContentPane().add(get_premium_button, gbc_get_premium_button);
		
		JLabel lb2 = new JLabel("Добро пожаловать в крипточат Brain Chat!");
		lb2.setFont(new Font("Dialog", Font.BOLD, 10));
		GridBagConstraints gbc_lb2 = new GridBagConstraints();
		gbc_lb2.insets = new Insets(0, 0, 5, 0);
		gbc_lb2.gridx = 4;
		gbc_lb2.gridy = 6;
		frmBrainChat.getContentPane().add(lb2, gbc_lb2);
		
		JLabel lb3 = new JLabel("Зачем нужен премиум?");
		lb3.setFont(new Font("Dialog", Font.BOLD, 11));
		GridBagConstraints gbc_lb3 = new GridBagConstraints();
		gbc_lb3.insets = new Insets(0, 0, 5, 0);
		gbc_lb3.gridx = 4;
		gbc_lb3.gridy = 7;
		frmBrainChat.getContentPane().add(lb3, gbc_lb3);
		
		JLabel lb4 = new JLabel("☑Общение на Премиум серверах");
		lb4.setFont(new Font("Dialog", Font.BOLD, 11));
		GridBagConstraints gbc_lb4 = new GridBagConstraints();
		gbc_lb4.insets = new Insets(0, 0, 5, 0);
		gbc_lb4.gridx = 4;
		gbc_lb4.gridy = 8;
		frmBrainChat.getContentPane().add(lb4, gbc_lb4);
		
		JLabel lb5 = new JLabel("☑Соединение через сеть TOR");
		lb5.setFont(new Font("Dialog", Font.BOLD, 11));
		GridBagConstraints gbc_lb5 = new GridBagConstraints();
		gbc_lb5.insets = new Insets(0, 0, 5, 0);
		gbc_lb5.gridx = 4;
		gbc_lb5.gridy = 9;
		frmBrainChat.getContentPane().add(lb5, gbc_lb5);
		
		message_tf = new JTextField();
		GridBagConstraints gbc_message_tf = new GridBagConstraints();
		gbc_message_tf.gridwidth = 3;
		gbc_message_tf.insets = new Insets(0, 0, 5, 5);
		gbc_message_tf.fill = GridBagConstraints.HORIZONTAL;
		gbc_message_tf.gridx = 1;
		gbc_message_tf.gridy = 14;
		frmBrainChat.getContentPane().add(message_tf, gbc_message_tf);
		message_tf.setColumns(10);
		
		JButton send_tf = new JButton("Отправить");
		send_tf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_send_tf = new GridBagConstraints();
		gbc_send_tf.gridwidth = 3;
		gbc_send_tf.insets = new Insets(0, 0, 5, 5);
		gbc_send_tf.gridx = 1;
		gbc_send_tf.gridy = 15;
		frmBrainChat.getContentPane().add(send_tf, gbc_send_tf);
		
		JMenuBar menuBar = new JMenuBar();
		frmBrainChat.setJMenuBar(menuBar);
		
		JMenu chat_menu = new JMenu("Чат");
		menuBar.add(chat_menu);
		
		JMenuItem chat_new_item = new JMenuItem("Создать");
		chat_menu.add(chat_new_item);
		
		JMenuItem chat_conn_item = new JMenuItem("Подключиться");
		chat_menu.add(chat_conn_item);
		
		JMenu profile_menu = new JMenu("Профиль");
		menuBar.add(profile_menu);
		
		JMenuItem profile_view_item = new JMenuItem("Просмотреть");
		profile_menu.add(profile_view_item);
		
		JMenuItem profile_set_item = new JMenuItem("Настроить");
		profile_menu.add(profile_set_item);
		
		JMenu settings_menu = new JMenu("Настройки");
		menuBar.add(settings_menu);
		
		JMenuItem settings_open_menu = new JMenuItem("Открыть");
		settings_menu.add(settings_open_menu);
		
		JMenuItem settings_info_menu = new JMenuItem("Инфо");
		settings_menu.add(settings_info_menu);
		
		JMenuItem exit_button = new JMenuItem("Выход");
		menuBar.add(exit_button);
	}

}
