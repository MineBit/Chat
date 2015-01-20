package com.brainhands.brainchat.ver_02.client.gui;

import com.brainhands.brainchat.utill.Other;
import com.brainhands.brainchat.ver_02.client.ChatClient;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Mine_Bit[Brain Hands]
 * forum.brainhands.ru
 * brain-soft.org
 */

public class NewRoomFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static NewRoomFrame frame;
	private JPanel contentPane;
	private JTextField RoomNameField;
	private JPasswordField passwordField;
	
	//Метод для запуска окна:
	public static void View() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new NewRoomFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Конструктор окна:
	public NewRoomFrame() {
		setTitle("BrainChat | "+ChatClient.Version);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 348, 215);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		Other.initSystemLookAndFeel();
		
		JLabel Label_1 = new JLabel("Введите номер комнаты:");
		Label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Label_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		RoomNameField = new JTextField();
		RoomNameField.setColumns(10);
		
		JLabel Label_2 = new JLabel("Введите пароль для комнаты:");
		Label_2.setHorizontalAlignment(SwingConstants.CENTER);
		Label_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		passwordField = new JPasswordField();
		
		JButton CreateButton = new JButton("Создать комнату");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(78)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(RoomNameField, Alignment.LEADING)
						.addComponent(Label_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
					.addContainerGap(67, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(55, Short.MAX_VALUE)
					.addComponent(Label_2, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
					.addGap(45))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(79, Short.MAX_VALUE)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
					.addGap(69))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(111, Short.MAX_VALUE)
					.addComponent(CreateButton)
					.addGap(100))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(Label_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(RoomNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(Label_2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(CreateButton)
					.addContainerGap(26, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	//Метод для закрытия окна:
	public static void Close(){
		frame.setVisible(false);
	}
}
