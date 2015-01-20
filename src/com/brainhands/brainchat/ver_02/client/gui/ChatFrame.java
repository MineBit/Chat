package com.brainhands.brainchat.ver_02.client.gui;

import com.brainhands.brainchat.utill.Other;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Mine_Bit[Brain Hands]
 * forum.brainhands.ru
 * brain-soft.org
 */

public class ChatFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	static ChatFrame frame;

	private JPanel contentPane;
	private JTextField MessageField;

	//Метод для вызова окна:
	public static void View() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ChatFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Конструктор окна:
	public ChatFrame() {
		Other.initSystemLookAndFeel();
		setTitle("BrainChat | ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 498);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		Other.initSystemLookAndFeel();
		JPanel TopPanel = new JPanel();
		contentPane.add(TopPanel, BorderLayout.NORTH);
		
		JLabel Label_1 = new JLabel("Пользователей в комнате:");
		Label_1.setHorizontalAlignment(SwingConstants.CENTER);
		TopPanel.add(Label_1);
		
		JLabel RoomUsersCountLabel = new JLabel("0");
		TopPanel.add(RoomUsersCountLabel);
		
		JPanel BotPanel = new JPanel();
		contentPane.add(BotPanel, BorderLayout.SOUTH);
		
		MessageField = new JTextField();
		BotPanel.add(MessageField);
		MessageField.setColumns(15);
		
		JButton SendButton = new JButton("Отправить");
		BotPanel.add(SendButton);
		
		JPanel CenterPanel = new JPanel();
		contentPane.add(CenterPanel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		CenterPanel.add(scrollPane);
		
		JTextArea MessageArea = new JTextArea();
		MessageArea.setToolTipText("");
		MessageArea.setColumns(100);
		MessageArea.setEditable(false);
		MessageArea.setLineWrap(true);
		MessageArea.setTabSize(10);
		MessageArea.setRows(30);
		scrollPane.setViewportView(MessageArea);
	}

}
