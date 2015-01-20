package com.brainhands.brainchat.utill;

/**
 * Created by Mine_Bit[Brain Hands]
 * forum.brainhands.ru
 * brain-soft.org
 */

public class Crypto {
	public static int[] Key1 = new int[]{
		0,//[Пробел]
		1,//й
		2,//ц
		3,//у
		4,//к
		5,//е
		6,//н
		7,//г
		8,//ш
		9,//щ
		10,//з
		11,//х
		12,//ъ
		13,//ф
		14,//ы
		15,//в
		16,//а
		17,//п
		18,//р
		19,//о
		20,//л
		21,//д
		22,//ж
		23,//э
		24,//я
		25,//ч
		26,//с
		27,//м
		28,//и
		29,//т
		30,//ь
		31,//б
		32,//ю
		33,//ё
		34,//Ё
		35,//Й
		36,//Ц
		37,//У
		38,//К
		39,//Е
		40,//Н
		41,//Г
		42,//Ш
		43,//Щ
		44,//З
		45,//Х
		46,//Ъ
		47,//Ф
		48,//Ы
		49,//В
		50,//А
		51,//П
		52,//Р
		53,//О
		54,//Л
        55,//Д
        56,//Ж
        57,//Э
        58,//Я
        59,//Ч
        60,//С
        61,//М
        62,//И
        63,//Т
        64,//Ь
        65,//Б
        66,//Ю
        67,//q
        68,//w
        69,//e
        70,//r
        71,//t
        72,//y
        73,//u
        74,//i
        75,//o
        76,//p
        77,//a
        78,//s
        79,//d
        80,//f
        81,//g
        82,//h
        83,//j
        84,//k
        85,//l
        86,//z
        87,//x
        88,//c
        89,//v
        90,//b
        91,//n
        92,//m
        93,//Q
        94,//W
        95,//E
        96,//R
        97,//T
        98,//Y
        99,//U
        100,//I
        101,//O
        102,//P
        103,//A
        104,//S
        105,//D
        106,//F
        107,//G
        108,//H
        109,//J
        110,//K
        111,//L
        112,//Z
        113,//X
        114,//C
        115,//V
        116,//B
        117,//N
        118,//M
        119,//[`]
        120,//[~]
        121,//[!]
        122,//[@]
        123,//[#]
        124,//[$]
        125,//[%]
        126,//[^]
        127,//[&]
        128,//[*]
        129,//[(]
        130,//[)]
        131,//[-]
        132,//[_]
        133,//[=]
        134,//[+]
        135,//[
        136,//{
        137,//]
        138,//}
        139,//[;]
        140,//[:]
        141,//[']
        142,//["]
        143,//[\]
        144,//[/]
        145,//[|]
        146,//[,]
        147,//[<]
        148,//[.]
        149,//[>]
        150,//[?]
        151,//1
        152,//2
        153,//3
        154,//4
        155,//5
        156,//6
        157,//7
        158,//8
        159,//9
        160,//0
    };

    public static char[] Transmit = new char[]{
            ' ','й','ц','у','к','е','н','г','ш','щ','з','х','ъ','ф','ы','в','а','п','р','о','л','д','ж','э','я','ч','с','м','и','т','ь','б','ю','ё','Ё','Й',
            'Ц','У','К','Е','Н','Г','Ш','Щ','З','Х','Ъ','Ф','Ы','В','А','П','Р','О','Л','Д','Ж','Э','Я','Ч','С','М','И','Т','Ь','Б','Ю','q','w','e','r','t',
            'y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m','Q','W','E','R','T','Y','U','I','O','P','A','S','D','F','G',
            'H','J','K','L','Z','X','C','V','B','N','M','`','~','!','@','#','$','%','^','&','*','(',')','-','_','=','+','[','{',']','}',';',':','\'','"','\\',
            '/','|',',','<','.','>','?','1','2','3','4','5','6','7','8','9','0'
    };

    public static String Cripting(String in_string){
        String returner = null;
        char[] seq = in_string.toCharArray();
        int key_int = MathUtill.GetRandom(1000,10000);
        returner = Integer.toString(key_int)+"#";
        for(int i = 0; i < seq.length; i++){
            for (int a = 0; a < Transmit.length; a++){
                if(seq[i] == Transmit[a]){
                    if (i != seq.length) {
                        returner = returner + Integer.toString(a * key_int) + "#";
                    }if (i == seq.length){
                        returner = returner + Integer.toString(a * key_int);
                    }
                }
            }
        }
        return returner;
    }

    public static String Recripting(String in_string){
        String returner = "";
        String[] str = in_string.split("#");
        int key_int = Integer.parseInt(str[0]);
        for(int i = 1; i < str.length; i++){
            int a = (Integer.parseInt(str[i]))/key_int;
            returner = returner + Transmit[a];
        }
        return returner;
    }
    public static void main(String[] args){
        System.out.println("Расшифровка: "+Recripting(Cripting("Привет, Это Павел!!!!")));
    }
}
