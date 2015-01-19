package com.brainhands.brainchat.ver_02.server;

/**
 * Created by Василевский on 15.12.2014.
 */
public class User {
    public int id;
    public static String username;
    public static String pass;
    User(int in_id, String in_username, String in_pass){
        id = in_id;
        username = in_username;
        pass = in_pass;
    }
}
