package View.ProductsAndOffsMenus;

import Controller.Client.ClientController;
import Models.Comment;
import View.Menu;

import java.util.ArrayList;

public class CommentMenu extends Menu {
    public CommentMenu(Menu parentMenu) {
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
        String commentMenuHelp="";
        commentMenuHelp+="1.Add Comment\n";
        commentMenuHelp+="2.Back\n";
        showMessage(commentMenuHelp);
    }

    @Override
    public void execute() {
        showMessage("The Average Score Of This Product: "+ ClientController.getInstance().getCurrentProduct().getAverageScore()+"\n");
        if (ClientController.getInstance().getCurrentProduct().getCommentList()==null) {
            ClientController.getInstance().getCurrentProduct().setCommentList(new ArrayList<>());
        }
        if (ClientController.getInstance().getCurrentProduct().getCommentList().isEmpty()) {
            showMessage("There Is No Comment For This Product");
            back();
        }
        for (Comment comment : ClientController.getInstance().getCurrentProduct().getCommentList()) {
            showMessage(comment.toString());
            showMessage("\n");
        }
        String command;
//        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
//            if (command.equalsIgnoreCase("Add Comment")) {
//
//            }else {
//                printError("invalid command");
//            }
//        }
        back();
    }
}
