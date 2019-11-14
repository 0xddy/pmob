package utils;

import org.fusesource.jansi.Ansi;

public class C {

    public static void print(String str) {
        System.out.println(str);
    }

    public static void println(String str, Ansi.Color color) {
        System.out.println(str);
    }

    public static void print(String str, Ansi.Color color) {
        System.out.print(str);
    }

}
