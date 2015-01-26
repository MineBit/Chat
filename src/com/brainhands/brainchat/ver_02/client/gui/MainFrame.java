package com.brainhands.brainchat.ver_02.client.gui;

import com.brainhands.brainchat.utill.Other;
import com.brainhands.brainchat.ver_02.client.ChatClient;
import com.brainhands.brainchat.ver_02.server.MainChatThread;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame {
	
	private static JFrame frmBrainchat;

	public static  JTextField MessageField;
	public static JTextArea GeneralChatArea;
	static JButton SendButton;
	static JButton NewRoomButton;
	static JButton ConnectButton;
	static JButton SettingsButton;

	public static boolean redy_to_send = false;

	//Метод для запуска окна:
	public static void View() {
		EventQueue.invokeLater(new Runnable() {
			public void run(){
				new MainFrame();
				MainFrame.frmBrainchat.setVisible(true);

			}
		});
		StartMainChatThread();
	}

	public MainFrame() {
		initialize();
	}

	private void initialize() {

		frmBrainchat = new JFrame();
		frmBrainchat.setTitle("BrainChat | "+ChatClient.Version);
		frmBrainchat.setResizable(false);
		frmBrainchat.setBounds(100, 100, 450, 300);
		frmBrainchat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Other.initSystemLookAndFeel();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		MessageField = new JTextField();
		MessageField.setColumns(10);
		
		SendButton = new JButton("Отправить");
		SendButton.addActionListener(new SendButtonListener());
		
		NewRoomButton = new JButton("Создать комнату");
		
		ConnectButton = new JButton("Подключиться");
		
		SettingsButton = new JButton("Настройки");

		GroupLayout groupLayout = new GroupLayout(frmBrainchat.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(SendButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(MessageField, Alignment.LEADING)
							.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
					.addGap(61)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(NewRoomButton)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(SettingsButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(ConnectButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)))
					.addContainerGap(64, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(13)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(NewRoomButton)
							.addGap(13)
							.addComponent(ConnectButton)
							.addGap(12)
							.addComponent(SettingsButton)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(MessageField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(SendButton)
					.addContainerGap())
		);
		
		GeneralChatArea = new JTextArea();
		GeneralChatArea.setEditable(false);
		scrollPane.setViewportView(GeneralChatArea);
		frmBrainchat.getContentPane().setLayout(groupLayout);
	}
	
	//Метод для закрытия окна(Завершает работу программы в целом):
	public static void Close(){
		frmBrainchat.setVisible(false);
		System.exit(0);
	}

	public static class SendButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			redy_to_send = true;
		}
	}

	static void StartMainChatThread(){
		MainChatThread  MCT = new MainChatThread();
		Thread main_chat_thread = new Thread(MCT);
		main_chat_thread.start();
	}
}
