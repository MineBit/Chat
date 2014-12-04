package com.brainhands.brainchat.ver_02.client.frames;

import java.awt.*;
import javax.swing.*;

import com.brainhands.brainchat.utill.*;
import com.brainhands.brainchat.ver_02.client.Algoritms;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.event.*;

public class StartFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static JTextField nickname_tf;
	private static JPasswordField password_field;
    private static StartFrame frame;
	
	public static void run() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Other.initSystemLookAndFeel();
                    frame = new StartFrame();
                    frame.setVisible(true);
                    frame.setTitle(Constans.Ver02Title);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

	public StartFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 432, 298);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lb_1 = new JLabel("Добро пожаловать в Brain Chat");
		lb_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		getContentPane().add(lb_1, "2, 2, 29, 1, center, default");
		
		JLabel null_lb = new JLabel("");
		getContentPane().add(null_lb, "32, 2");
		
		JLabel lb_2 = new JLabel("Войдите или зарегистрируйтесь!");
		getContentPane().add(lb_2, "2, 4, 30, 1, center, default");
		
		JLabel enter_lb = new JLabel("Вход:");
		enter_lb.setFont(new Font("Tahoma", Font.PLAIN, 15));
		enter_lb.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(enter_lb, "2, 6, 30, 1, center, default");
		
		JLabel nickname_lb = new JLabel("Никнейм:");
		getContentPane().add(nickname_lb, "2, 8, right, default");
		
		nickname_tf = new JTextField();
		getContentPane().add(nickname_tf, "4, 8, 27, 1, fill, default");
		nickname_tf.setColumns(10);
		
		JLabel pass_lb = new JLabel("Пароль:");
		getContentPane().add(pass_lb, "2, 10, right, default");
		
		password_field = new JPasswordField();
		getContentPane().add(password_field, "4, 10, 27, 1, fill, default");
		
		JButton in_button = new JButton("Вход");
		in_button.addActionListener(new InButtonListener());
		getContentPane().add(in_button, "2, 12, 30, 1, center, default");
		
		JButton reg_button = new JButton("Регистрация");
        reg_button.addActionListener(new RegButtonListener());
		getContentPane().add(reg_button, "2, 14, 30, 1, center, default");

		
		JLabel copyright_lb = new JLabel("Created by Brain Soft | Mine Bit ");
		getContentPane().add(copyright_lb, "2, 16, 30, 1, center, default");
		
		JLabel site_lb = new JLabel("brainsoft.org");
		getContentPane().add(site_lb, "2, 18, 30, 1, center, default");
		
		JLabel lblNewLabel = new JLabel("forum.brainhands.ru | Гигабайты бесплатного софта!");
		getContentPane().add(lblNewLabel, "2, 20, 30, 1, center, default");
	}

    private static class InButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String username = nickname_tf.getText();

            char[] pass = password_field.getPassword();
            String pass2 = String.valueOf(pass);
            Algoritms.LoginIn(username, pass2);
            frame.setVisible(false);
        }
    }

    private static class RegButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            RegFrame.run();
            frame.setVisible(false);
        }
    }

}
