package com.brainhands.brainchat.utill;

import javax.swing.*;

/**
 * Created by Mine_Bit[Brain Hands]
 * forum.brainhands.ru
 * brain-soft.org
 */

public class Other {

    //Метод для отображения интерфейса согласно операционной системы:
    public static void initSystemLookAndFeel() {
        try {
            String systemLookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
            //Устанавливаем LookAndFeel
            UIManager.setLookAndFeel(systemLookAndFeelClassName);
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Can't use the specified look and feel on this platform.");
        } catch (Exception e) {
            System.err.println("Couldn't get specified look and feel, for some reason.");
        }
    }
}
