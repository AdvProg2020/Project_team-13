package View.ProductsAndOffsMenus;

import Controller.Client.ClientController;
import Controller.Client.ProductController;
import Models.Comment;
import Models.CommentStatus;
import Models.Product.Product;
import Models.UserAccount.Customer;
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
        String commentMenuHelp = "";
        commentMenuHelp += "1.Add Comment\n";
        commentMenuHelp += "2.Back\n";
        showMessage(commentMenuHelp);
    }

    @Override
    public void execute() {
        showMessage("The Average Score Of This Product: " + String.valueOf(ClientController.getInstance().getCurrentProduct().getAverageScore()));
        if (ClientController.getInstance().getCurrentProduct().getCommentList() == null) {
            ClientController.getInstance().getCurrentProduct().setCommentList(new ArrayList<>());
        }
        if (ClientController.getInstance().getCurrentProduct().getCommentList().isEmpty()) {
            showMessage("There Is No Comment For This Product");
        }
        for (Comment comment : ClientController.getInstance().getCurrentProduct().getCommentList()) {
            showMessage(comment.toString());
            showMessage("\n");
        }
        String command;
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("Add Comment")) {
                ProductController.getInstance().addComment(getComment());
            } else {
                printError("invalid command");
            }
        }
        back();
    }

    public Comment getComment() {
        String title = "", content = "";
        System.out.println("Enter Title");
        title = scanner.nextLine().trim();
        System.out.println("Enter Content");
        content = scanner.nextLine().trim();
        Comment comment=new Comment("",CommentStatus.unChecked,true,"","");
        if (ClientController.getInstance().getCurrentUser() != null && ClientController.getInstance().getCurrentUser().getType().equals("@Customer")) {
            if (((Customer) (ClientController.getInstance().getCurrentUser())).findProductWithId(ClientController.getInstance().getCurrentProduct().getProductId()) != null) {
                comment = new Comment(ClientController.getInstance().getCurrentProduct().getProductId(), CommentStatus.unChecked, true, title, content);
            }
        } else {
            comment = new Comment(ClientController.getInstance().getCurrentProduct().getProductId(), CommentStatus.unChecked, false, title, content);
        }

        return comment;
    }
}
