package View;

public class MainMenu extends Menu {


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

    }
}
