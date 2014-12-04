package com.brainhands.brainchat.utill;

/**
 * @author Mine_Bit[Brain Hands]
 * @version 0.1
 *
 * В данном классе содержаться математические алгоритмы программы
 * Если вы решили использовать этот класс для своих нужд, не забудьте указать в авторе его создателя
 *
 */

public class MathUtill {

    //Метод возвращающий рандомное число типа int:
    public static int GetRandom(int Min, int Max){
        int Random = 0;
        while(true){
            double a = Math.random();
            if (Max < 10){
                double b = a * 10;
                Random =(int) b;
                if(Random >= Min & Random <= Max){
                    return Random;
                }
            }if(Max < 100 & Max >= 10){
                double b = a * 100;
                Random =(int) b;
                if(Random >= Min & Random <= Max){
                    return Random;
                }
            }if(Max < 1000 & Max >= 100){
                double b = a * 1000;
                Random =(int) b;
                if(Random >= Min & Random <= Max){
                    return Random;
                }
            }if(Max < 10000 & Max >= 1000){
                double b = a * 10000;
                Random =(int) b;
                if(Random >= Min & Random <= Max){
                    return Random;
                }
            }if(Max < 100000 & Max >= 10000){
                double b = a * 100000;
                Random =(int) b;
                if(Random >= Min & Random <= Max){
                    return Random;
                }
            }if(Max < 1000000 & Max >= 100000){
                double b = a * 1000000;
                Random =(int) b;
                if(Random >= Min & Random <= Max){
                    return Random;
                }
            }if(Max < 10000000 & Max >= 1000000){
                double b = a * 10000000;
                Random =(int) b;
                if(Random >= Min & Random <= Max){
                    return Random;
                }
            }if(Max < 100000000 & Max >= 10000000){
                double b = a * 100000000;
                Random =(int) b;
                if(Random >= Min & Random <= Max){
                    return Random;
                }
            }
        }
    }
}
