package com.brainhands.brainchat.ver_02.server;

import java.io.IOException;

/**
 * Created by Василевский on 26.01.2015.
 */
public class MainThread implements Runnable {
    public void run(){
        try {
            new ChatServer(45000).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
