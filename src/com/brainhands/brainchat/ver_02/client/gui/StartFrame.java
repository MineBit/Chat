package com.brainhands.brainchat.ver_02.client.gui;

import com.brainhands.brainchat.utill.CONSTANS;
import com.brainhands.brainchat.utill.Other;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StartFrame extends JFrame {

    private JPanel contentPane;
    private JTextField nickname_tf;
    private JPasswordField passwordField;


    public StartFrame() {
        Other.initSystemLookAndFeel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 166);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel Lb_1 = new JLabel("Добро пожаловать в BrainChat!");
        Lb_1.setFont(new Font("Tahoma", Font.BOLD, 14));

        JLabel Lb_2 = new JLabel("Введите ваш ник:");

        nickname_tf = new JTextField();
        nickname_tf.setColumns(10);

        JLabel Lb_3 = new JLabel("Введите ваш пароль:");

        passwordField = new JPasswordField();
        passwordField.setColumns(10);

        JButton EnterButton = new JButton("Войти");
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap(99, Short.MAX_VALUE)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                                .addComponent(Lb_1)
                                                .addGap(93))
                                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                                .addComponent(Lb_3)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(107))
                                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                                .addComponent(Lb_2)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(nickname_tf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(116))
                                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                                .addComponent(EnterButton)
                                                .addGap(175))))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(Lb_1)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(Lb_2)
                                        .addComponent(nickname_tf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(Lb_3)
                                        .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(11)
                                .addComponent(EnterButton)
                                .addContainerGap(137, Short.MAX_VALUE))
        );
        contentPane.setLayout(gl_contentPane);
    }

    public static void View() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    StartFrame frame = new StartFrame();
                    frame.setVisible(true);
                    frame.setTitle(CONSTANS.Ver02Title);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
