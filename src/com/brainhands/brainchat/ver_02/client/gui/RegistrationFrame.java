package com.brainhands.brainchat.ver_02.client.gui;

import com.brainhands.brainchat.ver_02.client.ChatClient;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	static RegistrationFrame frame;
	
	private JPanel contentPane;
	static JTextField login_tf;
	static JTextField password_tf;
	static JTextField re_password_tf;

	/**
	 * Метод для запуска окна:
	 */
	public static void View() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new RegistrationFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Конструктор окна:
	 */
	public RegistrationFrame() {
		setTitle("BrainChat | Регистрация");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 194);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel first_lb = new JLabel("Для регистрации заполните следующие поля:");
		first_lb.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel login_lb = new JLabel("Логин:");
		
		login_tf = new JTextField();
		login_tf.setColumns(10);
		
		password_tf = new JTextField();
		password_tf.setColumns(10);
		
		JLabel password_lb = new JLabel("Пароль:");
		
		JLabel re_password_lb = new JLabel("Повторите пароль:");
		
		re_password_tf = new JTextField();
		re_password_tf.setColumns(10);
		
		JButton RegestrationButton = new JButton("Зарегистрироваться");
		RegestrationButton.addActionListener(new RegButtonListener());
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(70)
					.addComponent(first_lb)
					.addContainerGap(59, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(143, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(login_lb)
						.addComponent(password_lb)
						.addComponent(re_password_lb))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(password_tf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(login_tf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(re_password_tf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(145))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(249, Short.MAX_VALUE)
					.addComponent(RegestrationButton)
					.addGap(138))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(first_lb)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(login_lb)
						.addComponent(login_tf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(password_tf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(password_lb))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(re_password_lb)
						.addComponent(re_password_tf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(RegestrationButton)
					.addContainerGap(118, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	static class RegButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			String login = login_tf.getText();
			String password = password_tf.getText();
			String re_password = re_password_tf.getText();
			if(login.length() > 3){
				if(re_password.equals(password)){
					if (password.length() > 9) {
						int return_code = ChatClient.Registration(login, password);
					}else{
						//TODO Переделать окна ошибок на нормальные
						JOptionPane.showMessageDialog(null,"Введенный пароль меньше 9 символов! Это не безопастно!","Ошибка!",JOptionPane.ERROR_MESSAGE);
					}
				}else{
					//TODO Переделать окна ошибок на нормальные
					JOptionPane.showMessageDialog(null,"Логин не введен, или меньше 3 символов!!!","Ошибка!",JOptionPane.ERROR_MESSAGE);
				}
			}else{
				//TODO Переделать окна ошибок на нормальные
				JOptionPane.showMessageDialog(null,"Введенные Вами пароли не совпадают!!!","Ошибка!",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
