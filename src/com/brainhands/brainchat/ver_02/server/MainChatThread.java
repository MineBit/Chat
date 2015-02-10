package com.brainhands.brainchat.ver_02.server;

import java.io.IOException;

/**
 * Created by Василевский on 26.01.2015.
 */
public class MainChatThread implements Runnable{
    public void run(){
        try {
            new GeneralChatServer(9999).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
