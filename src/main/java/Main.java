import View.MainMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        new MainMenu(null).setScanner(new Scanner(System.in)).execute();
        System.out.println("test run is Owk");
    }
}
