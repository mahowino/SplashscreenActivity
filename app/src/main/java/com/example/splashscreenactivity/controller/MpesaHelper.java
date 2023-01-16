package com.example.splashscreenactivity.controller;

public class MpesaHelper {
    public static double getCharges(double goodPrice){
        if (goodPrice<100)return 0;
        if (goodPrice>101 && goodPrice<=500)return 6;
        if (goodPrice>501 && goodPrice<=1000)return 12;
        if (goodPrice>1001 && goodPrice<=1500)return 22;
        if (goodPrice>1501 && goodPrice<=2500)return 32;
        if (goodPrice>2501 && goodPrice<=3500)return 51;
        if (goodPrice>3501 && goodPrice<=5000)return 55;
        if (goodPrice>5001 && goodPrice<=7500)return 75;
        if (goodPrice>7501 && goodPrice<=10000)return 87;
        if (goodPrice>10001 && goodPrice<=15000)return 97;
        if (goodPrice>15001 && goodPrice<=20000)return 102;
        if (goodPrice>20001 && goodPrice<=35000)return 105;
        if (goodPrice>35001 && goodPrice<=50000)return 105;
        if (goodPrice>50001 && goodPrice<=150000)return 105;
        return 105;
    }

}
