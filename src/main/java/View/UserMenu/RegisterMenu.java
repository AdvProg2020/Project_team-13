package View.UserMenu;

import Controller.Client.ClientController;
import Controller.Client.ManagerController;
import Controller.Client.MessageController;
import Controller.Client.RegisterController;
import Models.UserAccount.Seller;
import View.Menu;
import com.google.gson.Gson;

import java.util.Scanner;
import java.util.regex.Pattern;

public class RegisterMenu extends Menu {
    public RegisterMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {

    }

    @Override
    public void execute() {
        while (true) {
            System.out.println("Enter UserType");
            String userType = scanner.nextLine().trim();
            if (userType.equalsIgnoreCase("Manager")) {
                System.out.println("Enter UserName");
                String userName = scanner.nextLine().trim();
                System.out.println("Enter Password");
                String passWord = scanner.nextLine().trim();
                break;
            } else if (userType.equalsIgnoreCase("Customer")) {
                System.out.println("Enter UserName");
                String userName = scanner.nextLine().trim();
                System.out.println("Enter Password");
                String passWord = scanner.nextLine().trim();
                break;
            } else if (userType.equalsIgnoreCase("Seller")) {
                String username, password, firstName, lastName, email, phoneNumber, companyName;
                while (true) {
                    System.out.println("Enter UserName");
                    username = scanner.nextLine().trim();
                    if (checkuserNameIsvalid(username)) {
                        break;
                    } else {
                        System.out.println("Username is invalid:use alphabetCharacters and atleast 4words");
                    }
                }
                while (true) {
                    System.out.println("Enter Password");
                    password = scanner.nextLine().trim();
                    if (checkuserNameIsvalid(password)) {
                        break;
                    } else {
                        System.out.println("password is invalid:password must contains atleast 8 characters and less than 19 characterrs");
                    }
                }
                while (true) {
                    System.out.println("Enter firstName");
                    firstName = scanner.nextLine().trim();
                    if (checkNameIsvalid(firstName)) {
                        break;
                    } else {
                        System.out.println("firstName is invalid:name must consistOf [a-z] and [A-Z]");
                    }
                }
                while (true) {
                    System.out.println("Enter lastName");
                    lastName = scanner.nextLine().trim();
                    if (checkNameIsvalid(lastName)) {
                        break;
                    } else {
                        System.out.println("lastName is invalid:name must consistOf [a-z] and [A-Z]");
                    }
                }
                while (true) {
                    System.out.println("Enter email");
                    email = scanner.nextLine().trim();
                    if (checkEmailIsvalid(email)) {
                        break;
                    } else {
                        System.out.println("Please enter a valid email address.");
                    }
                }
                while (true) {
                    System.out.println("Enter PhoneNumber");
                    phoneNumber = scanner.nextLine().trim();
                    if (Pattern.matches("09\\d+", phoneNumber) && phoneNumber.length() < 11) {
                        break;
                    } else {
                        System.out.println("Please enter a valid phoneNumber address.");
                    }
                }
                while (true) {
                    System.out.println("Enter PhoneNumber");
                    phoneNumber = scanner.nextLine().trim();
                    if (Pattern.matches("09\\d+", phoneNumber) && phoneNumber.length() < 11) {
                        break;
                    } else {
                        System.out.println("Please enter a valid phoneNumber address.");
                    }
                }
                while (true) {
                    System.out.println("Enter Company Name");
                    companyName = scanner.nextLine().trim();
                    if (Pattern.matches("\\w+", companyName)) {
                        break;
                    } else {
                        System.out.println("Please enter a valid companyName address.");
                    }
                }

                Seller seller = new Seller(username, password, firstName, lastName, email, phoneNumber, 0, companyName, false);
                RegisterController.getInstance().createNewUserAccount(seller);

                break;
            } else System.out.println("Invalid userType");
        }

    }

    private boolean checkuserNameIsvalid(String word) {
        if (Pattern.matches("\\w+", word) && word.length() >= 4) {
            return true;
        }
        return false;
    }

    private boolean checkPasswordIsvalid(String word) {
        if (word.length() > 8 && word.length() < 18) {
            return true;
        }
        return false;
    }

    private boolean checkNameIsvalid(String name) {
        if (Pattern.matches("([a-z]|[A-z])+", name) && !name.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean checkEmailIsvalid(String email) {
        if (Pattern.matches("\\w+\\.?\\w*@\\w+\\.\\w+", email)) {
            return true;
        }
        return false;
    }
}
