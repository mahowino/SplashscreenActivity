package online.kenya.mvitu.controller;

public class MpesaHelper {
    public static double getCharges(double goodPrice){
        if (goodPrice<100)return 0;

        return 10;
    }

}
