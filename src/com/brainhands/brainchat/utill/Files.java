package com.brainhands.brainchat.utill;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * @author Mine_Bit[Brain Hands]
 * @version 0.1
 * 
 * Данный класс содержит методы для работы с файлами
 * Если вы решили использовать этот класс для своих нужд, не забудьте указать в авторе и его создателя
 * 
 */

public class Files {

    static boolean FileFound = true;
    
    //Метод для создания дериктории:
    public static void MakeDir(String dir){
        File myPath = new File(dir);
        myPath.mkdir();
        myPath.mkdirs();
        System.out.println("Дериктория \""+dir+"\" успешно создана!");
    }
    
    //Метод для удаления дериктории:
    public static void DelDir(String dir){
    	File myPath = new File(dir);
    	DeliteDir(myPath); 
    	System.out.println("Дериктория \""+dir+"\" успешно удалена!");
    }
    
    //Метод, который используется при удаленнии дериктории:
    static void DeliteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                File f = new File(dir, children[i]);
                DeliteDir(f);
            }
            dir.delete();
        } else dir.delete();
    }
    
    //Метод для записи строки в файл:
    public static void Write(String to_write, String file_name){
    	
    		try {
    			FileWriter sw = new FileWriter(file_name,true);
    			sw.write(to_write + "\n");
    			sw.close();
    		}catch (IOException e) {
				e.printStackTrace();
    		}

    }
    
    //Метод для чтения файла и записи каждой строки в массив, который он возвращает:
    public static String[] Read(String file_name){
        List<String> list = new ArrayList<String>();
        try{
                @SuppressWarnings("resource")
				Scanner scanner = new Scanner(new File(file_name));
                while (scanner.hasNextLine()){
                	list.add(scanner.nextLine());                	
                }
        }catch (FileNotFoundException e){
                FileFound = false;
                System.out.println("Ошибка! Файл для чтения не найден!");
                e.printStackTrace();
        }
        String[] array = new String[list.size()];
        for(int i = 0; i < list.size(); i++){
                array[i] = list.get(i);
        }
        return array;
    }
}
