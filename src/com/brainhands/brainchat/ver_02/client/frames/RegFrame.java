package com.brainhands.brainchat.ver_02.client.frames;

import com.brainhands.brainchat.utill.Constans;
import com.brainhands.brainchat.utill.Other;
import com.brainhands.brainchat.ver_02.client.Algoritms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class RegFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private JPanel contentPane;
	private static JTextField login_tf;
	private static JPasswordField password_tf;
	private static JTextField mail_tf;
    private static RegFrame frame;

	/**
	 * Launch the application.
	 */
	public static void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new RegFrame();
                    Other.initSystemLookAndFeel();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegFrame() {
		setTitle(Constans.Ver02Title+" | Регистрация");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 218);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel first_lb = new JLabel("Зарегитрируйтесь!");
		GridBagConstraints gbc_first_lb = new GridBagConstraints();
		gbc_first_lb.insets = new Insets(0, 0, 5, 0);
		gbc_first_lb.gridwidth = 7;
		gbc_first_lb.gridx = 0;
		gbc_first_lb.gridy = 0;
		contentPane.add(first_lb, gbc_first_lb);
		
		JLabel login_lb = new JLabel("Логин:");
		GridBagConstraints gbc_login_lb = new GridBagConstraints();
		gbc_login_lb.anchor = GridBagConstraints.WEST;
		gbc_login_lb.insets = new Insets(0, 0, 5, 5);
		gbc_login_lb.gridx = 1;
		gbc_login_lb.gridy = 1;
		contentPane.add(login_lb, gbc_login_lb);
		
		login_tf = new JTextField();
		GridBagConstraints gbc_login_tf = new GridBagConstraints();
		gbc_login_tf.gridwidth = 3;
		gbc_login_tf.insets = new Insets(0, 0, 5, 5);
		gbc_login_tf.fill = GridBagConstraints.HORIZONTAL;
		gbc_login_tf.gridx = 2;
		gbc_login_tf.gridy = 1;
		contentPane.add(login_tf, gbc_login_tf);
		login_tf.setColumns(10);
		
		JLabel pass_lb = new JLabel("Пароль:");
		GridBagConstraints gbc_pass_lb = new GridBagConstraints();
		gbc_pass_lb.anchor = GridBagConstraints.WEST;
		gbc_pass_lb.insets = new Insets(0, 0, 5, 5);
		gbc_pass_lb.gridx = 1;
		gbc_pass_lb.gridy = 2;
		contentPane.add(pass_lb, gbc_pass_lb);
		
		password_tf = new JPasswordField();
		GridBagConstraints gbc_password_tf = new GridBagConstraints();
		gbc_password_tf.gridwidth = 3;
		gbc_password_tf.insets = new Insets(0, 0, 5, 5);
		gbc_password_tf.fill = GridBagConstraints.HORIZONTAL;
		gbc_password_tf.gridx = 2;
		gbc_password_tf.gridy = 2;
		contentPane.add(password_tf, gbc_password_tf);
		
		JLabel mail_lb = new JLabel("Почта:");
		GridBagConstraints gbc_mail_lb = new GridBagConstraints();
		gbc_mail_lb.anchor = GridBagConstraints.WEST;
		gbc_mail_lb.insets = new Insets(0, 0, 5, 5);
		gbc_mail_lb.gridx = 1;
		gbc_mail_lb.gridy = 3;
		contentPane.add(mail_lb, gbc_mail_lb);
		
		mail_tf = new JTextField();
		GridBagConstraints gbc_mail_tf = new GridBagConstraints();
		gbc_mail_tf.gridwidth = 3;
		gbc_mail_tf.insets = new Insets(0, 0, 5, 5);
		gbc_mail_tf.fill = GridBagConstraints.HORIZONTAL;
		gbc_mail_tf.gridx = 2;
		gbc_mail_tf.gridy = 3;
		contentPane.add(mail_tf, gbc_mail_tf);
		mail_tf.setColumns(10);
		
		JButton reg_button = new JButton("Зарегистрироваться");
		GridBagConstraints gbc_reg_button = new GridBagConstraints();
		gbc_reg_button.insets = new Insets(0, 0, 5, 0);
		gbc_reg_button.gridwidth = 7;
		gbc_reg_button.gridx = 0;
		gbc_reg_button.gridy = 4;
        reg_button.addActionListener(new RegButtonListener());
		contentPane.add(reg_button, gbc_reg_button);
		
		JLabel label = new JLabel("Регистрируясь, вы принимаете правила использования BrainChat.");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridwidth = 9;
		gbc_label.gridx = 0;
		gbc_label.gridy = 5;
		contentPane.add(label, gbc_label);
	}

    private static class RegButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String username = login_tf.getText();
            String mail = mail_tf.getText();
            char[] pass = password_tf.getPassword();
            String pass2 = String.valueOf(pass);
            Algoritms.Registration(username, mail, pass2);
            frame.setVisible(false);
        }
    }
}
