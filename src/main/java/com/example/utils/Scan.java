package com.example.utils;

import java.util.Scanner;

public class Scan {

    public static String getString(String msg){
        System.out.print(msg);
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

}
