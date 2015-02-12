package com.brainhands.brainchat.ver_01_02.client.gui;


import com.brainhands.brainchat.ver_01_02.client.ChatClient;
import com.brainhands.brainchat.ver_01_02.client.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatRoom {

	private JFrame frame;
	static JTextField textField;
    static JButton SendButton;
    public static JTextArea MessageArea;

    public static String message;
    public static boolean redy_to_send = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
        System.out.println("Brain Chat | "+ ChatClient.Version+"| by Brain Hands");
        System.out.println("Build: " + ChatClient.BuilDVersion);

        //Ввод имени пользователя с проверкой на пустоту:
        while(true){
            User.nickname = JOptionPane.showInputDialog(null, "Введите ваш Никнейм:","Brain Chat | Запрос данных", JOptionPane.QUESTION_MESSAGE);
            if (User.nickname != null){
                break;
            }else{
                JOptionPane.showMessageDialog(null,"Введенная строка пустая!","Ошибка!",JOptionPane.ERROR_MESSAGE);
            }
        }

        //Ввод ip сервера с проверкой на пустоту:
        while(true) {
            ChatClient.host = JOptionPane.showInputDialog(null, "Введите IP сервера:", "Brain Chat | Запрос данных", JOptionPane.QUESTION_MESSAGE);
            if (ChatClient.host != null) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Введенная строка пустая!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
            }
        }

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatRoom window = new ChatRoom();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

        ChatClient.Connection connection = new ChatClient.Connection();
        connection.run();
	}

	/**
	 * Create the application.
	 */
	public ChatRoom() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("Brain Chat | Version: "+ChatClient.Version);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(35);

        SendButton = new JButton("Отправить");
        SendButton.addActionListener(new SendButtonListener());
        panel.add(SendButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        MessageArea = new JTextArea();
		scrollPane.setViewportView(MessageArea);
	}

    static class SendButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            message = textField.getText();
            textField.setText("");
            redy_to_send = true;
        }
    }

}
