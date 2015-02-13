package com.brainhands.brainchat.ver_01_02.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatFrame {

	private JFrame frmBrainChat;

	public static JTextField InMessageField;
    public static String message = null;
    public static JButton ExitButton;
    public static JButton SendButton;
    public static JTextArea MessagesArea;
    public static boolean redy_to_send = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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

	/**
	 * Create the application.
	 */
	public ChatFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBrainChat = new JFrame();
		frmBrainChat.setTitle("Brain Chat | Version: 0.1.2");
		frmBrainChat.setResizable(false);
		frmBrainChat.setIconImage(Toolkit.getDefaultToolkit().getImage(ChatFrame.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		frmBrainChat.setBounds(100, 100, 560, 614);
		frmBrainChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel FooterPane = new JPanel();
		frmBrainChat.getContentPane().add(FooterPane, BorderLayout.NORTH);
		
		JLabel footer_tittle_string = new JLabel("Brain Chat | Version: 0.1.2 | Coded by Brain Hands");
		FooterPane.add(footer_tittle_string);
		
		JPanel DownPanel = new JPanel();
		frmBrainChat.getContentPane().add(DownPanel, BorderLayout.SOUTH);

        ExitButton = new JButton("Выход");
        ExitButton.addActionListener(new ExitButtonLisener());
		DownPanel.add(ExitButton);
		
		InMessageField = new JTextField();
		DownPanel.add(InMessageField);
		InMessageField.setColumns(20);
		
		SendButton = new JButton("Отправить");
		DownPanel.add(SendButton);
		
		JPanel CentralPane = new JPanel();
		frmBrainChat.getContentPane().add(CentralPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(UIManager.getBorder("Button.border"));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		CentralPane.add(scrollPane);
		
		JTextArea MessagesArea = new JTextArea();
		MessagesArea.setDropMode(DropMode.INSERT);
		MessagesArea.setLineWrap(true);
		MessagesArea.setEditable(false);
		MessagesArea.setRows(30);
		MessagesArea.setColumns(40);
		scrollPane.setViewportView(MessagesArea);
	}

    static class SendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            message = InMessageField.getText();
            InMessageField.setText("");
            redy_to_send = true;
        }
    }
    static class ExitButtonLisener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            System.out.println("Завершение работы приложения!");
            System.exit(0);
        }
    }

}
