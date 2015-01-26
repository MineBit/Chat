package com.brainhands.brainchat.ver_02.server;

import com.brainhands.brainchat.utill.Crypto;
import com.brainhands.brainchat.utill.Files;

/**
 * Created by Василевский on 26.01.2015.
 */
public class ServerLauncher {
    public static void main(String[] args){
        //TODO Написать выбор (первая загрузка-непервая загрузка) чтобы не плодились ферсты
        System.out.println("Brain Chat Sever |0.2| by Brain Hands");
        Files.MakeDir("BrainChatServerFiles");
        Files.Write(Crypto.Cripting("First:1"),"BrainChatServerFiles/users.bbf");
        String[] users = Files.Read("BrainChatServerFiles/users.bbf");
        if(users.length == 0){
            System.out.println("База данных пуста!");
        }else System.out.println("Количество зарегистрированных пользователей: "+users.length);
        StartServerThread();
        StartMainChatthread();

    }
    static void StartServerThread(){
        MainThread mt = new MainThread();
        Thread main_server_thread = new Thread(mt);
        main_server_thread.start();
    }

    static void StartMainChatthread(){
        MainChatThread mct = new MainChatThread();
        Thread main_chat_thread = new Thread(mct);
        main_chat_thread.start();
    }
}
