package View;

import Models.Product.Product;

public class MainMenu extends Menu {

    public MainMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String mainMenuOptions="";
        mainMenuOptions+="1.products\n";
        mainMenuOptions+="2.UserMenu\n";
        mainMenuOptions+="3.offs";
        System.out.println(mainMenuOptions);

    }

    @Override
    public void execute() {
        while (true) {
            String command=scanner.nextLine().trim();
            if (command.equalsIgnoreCase("products")) {

            } else if(command.equalsIgnoreCase("products")) {

            }else if(command.equalsIgnoreCase("products")) {

            }else if (command.equalsIgnoreCase("help")) {
                help();
            }else {
                System.out.println("Invalid Command");
            }
        }

    }
}
