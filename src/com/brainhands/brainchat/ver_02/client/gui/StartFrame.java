package com.brainhands.brainchat.ver_02.client.gui;

import com.brainhands.brainchat.utill.Constans;
import com.brainhands.brainchat.utill.Other;
import com.brainhands.brainchat.ver_02.client.ChatClient;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mine_Bit[Brain Hands]
 * forum.brainhands.ru
 * brain-soft.org
 */

//Стартовое окно авторизации:
public class StartFrame extends JFrame {

    private JPanel contentPane;
    private static JTextField nickname_tf;
    private static JPasswordField passwordField;
    static StartFrame frame;

    //Метод для запуска окна:
    public static void View() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new StartFrame();
                    frame.setVisible(true);
                    frame.setTitle(Constans.Ver02Title);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //Конструктор окна:
    public StartFrame() {
        setTitle("BrainChat | Вход");
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
        EnterButton.addActionListener(new EnterButtonListener());

        JButton registration_button = new JButton("Регистрация");
        registration_button.addActionListener(new RegistrationButtonListener());
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap(99, Short.MAX_VALUE)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(Lb_1)
                                                .addGap(93))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(Lb_2)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(nickname_tf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(116))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                                .addComponent(Lb_3)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                                .addGap(10)
                                                                .addComponent(EnterButton)
                                                                .addGap(18)
                                                                .addComponent(registration_button)))
                                                .addGap(107))))
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
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(EnterButton)
                                        .addComponent(registration_button))
                                .addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);
    }

    //Метод для закрытия окна:
    public static void Close(){
        frame.setVisible(false);
    }

    //Метод для получения имени пользователя из поля ввода имени пользователя:
    static String GetUserName() {
        return nickname_tf.getText();
    }

    //Метод для получение пароля из поля ввода пароля:
    static String GetPassword(){
        return passwordField.getPassword().toString();
    }

    //Класс обработки нажатия на кнопку "Вход"
    static class EnterButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String nickname = GetUserName();
            String password = GetPassword();
            if(nickname.equals(null) || password.equals(null)){
                JOptionPane.showMessageDialog(null,"Поле \"имя пользователя\" \"пароль\" пустое!!!","Ошибка!",JOptionPane.ERROR_MESSAGE);
            }else if (nickname.length() < 3){
                JOptionPane.showMessageDialog(null,"Имя пользователя меньше 3 символов!!!","Ошибка!",JOptionPane.ERROR_MESSAGE);
            }else if (password.length() < 9){
                JOptionPane.showMessageDialog(null,"Ваш пароль меньше 9 символов!!!","Ошибка!",JOptionPane.ERROR_MESSAGE);
            }else{
                boolean is_auth_correct = ChatClient.LogIn(nickname, password);
                if (is_auth_correct){
                    Close();
                    MainFrame.View();

                }else JOptionPane.showMessageDialog(null,"Неправильный логин или пароль!","Ошибка!",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //Класс обравботки нажатия на кнопку "Регистрация"
    static class RegistrationButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            RegistrationFrame.View();
            Close();
        }
    }
}
