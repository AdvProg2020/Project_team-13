package View2;

import Controller.Client.ClientController;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Menu {
    protected Menu parentMenu;
    protected Scanner scanner;
    ClientController controlClient;
    ArrayList<Menu> subMenu;

    public Scanner getScanner() {
        return scanner;
    }

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

    public Menu getParentMenu() {
        return parentMenu;
    }

    public void printError(String error) {
        System.out.println("\u001B[31m"+error+"\u001B[0m");
    }

    public void showMessage(String message) {
        System.out.println("\u001B[34m"+message+"\u001B[0m");
    }

    public abstract void help();

    public abstract void execute();

}
