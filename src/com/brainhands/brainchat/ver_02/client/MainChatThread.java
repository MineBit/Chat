package com.brainhands.brainchat.ver_02.client;

import java.io.IOException;

/**
 * Created by Василевский on 26.01.2015.
 */
public class MainChatThread implements Runnable {
    public void run(){
        try {
            new MainChat(ChatClient.host, 44999).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
