package View;

import Controller.Client.ClientController;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Menu {
    protected Menu parentMenu;
    Scanner scanner;
    ClientController controllClient;
    ArrayList<Menu> subMenu;

    public Menu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public Menu setScanner(Scanner scanner) {
        this.scanner = scanner;
        return this;
    }

    public void back() {
        ClientController.getInstance().setCurrentMenu(parentMenu);
        parentMenu.execute();
    }

    public void printError(String error) {
        System.out.println(error);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public abstract void help();

    public abstract void execute();

}
