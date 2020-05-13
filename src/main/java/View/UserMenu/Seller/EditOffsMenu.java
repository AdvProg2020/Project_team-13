package View.UserMenu.Seller;

import View.Menu;

public class EditOffsMenu extends Menu {
    public EditOffsMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void printError(String error) {
        super.printError(error);
    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
    }

    @Override
    public void help() {

    }

    @Override
    public void execute() {
        ViewOffsMenu viewOffsMenu=(ViewOffsMenu)parentMenu;
        String command=viewOffsMenu.getTheIdForEdit();



    }
}
