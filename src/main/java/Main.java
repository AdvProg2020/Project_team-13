import Controller.Server.ServerController;
import View.MainMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
      ServerController.getInstance().runServer();
      new MainMenu(null).setScanner(new Scanner(System.in)).execute();
    }
}
