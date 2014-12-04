package com.brainhands.brainchat.ver_02.client;

import com.brainhands.brainchat.ver_02.client.frames.ChatFrame;
import com.brainhands.brainchat.ver_02.client.frames.StartFrame;

public class Algoritms {

    public static void LoginIn(String username, String pass){
    //TODO Здесь должен быть процесс авторизации

        ChatFrame.run();
    }
    public static void Registration(String username, String mail, String pass){
    //TODO Здесь должен быть процесс регистрации
        StartFrame.run();
    }
}
