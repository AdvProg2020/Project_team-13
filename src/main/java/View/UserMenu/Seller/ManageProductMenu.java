package View.UserMenu.Seller;

import Controller.Client.CategoryController;
import Controller.Client.ClientController;
import Controller.Client.OffsController;
import Controller.Client.ProductController;
import Models.Product.Category;
import Models.Product.Product;
import Models.UserAccount.Seller;
import View.Menu;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ManageProductMenu extends Menu {
    public ManageProductMenu(Menu parentMenu) {
        super(parentMenu);
    }

    public Product currentProduct;

    @Override
    public void help() {
        String ManageProductMenuOptions = "";
        ManageProductMenuOptions += "1.view [product Id]\n";
        ManageProductMenuOptions += "2.view buyers [product Id]\n";
        ManageProductMenuOptions += "3.edit [product Id]\n";
        ManageProductMenuOptions += "4.add product\n";
        System.out.println(ManageProductMenuOptions);
    }

    @Override
    public void execute() {
        ProductController.getInstance().getAllProductsFromServer();
        Seller seller = (Seller) ClientController.getInstance().getCurrentUser();
        System.out.println(seller.viewAllProducts());
        String command;
        while (!(command = scanner.nextLine()).trim().equalsIgnoreCase("back")) {
            if (command.matches("view @p\\d+")) {
                System.out.println(seller.viewProduct(getTheProductIdByCommand(command, 1)));
            } else if (command.matches("view buyers @p\\d+")) {
                Product product = ((Seller) ClientController.getInstance().getCurrentUser()).findProductWithID(command.split("\\s")[2]);
                if (product != null) {
                    showMessage(product.viewAllBuyers());
                } else {
                    printError("there is no product with this ID");
                }
            } else if (command.matches("edit @p\\d+")) {
                edit(seller, command);
            } else if (command.equals("help")) {
                help();
            } else if (command.matches("remove @p\\d+")) {
                Gson gson = new Gson();
                String sellerObject = gson.toJson(seller);
                ProductController.getInstance().removeProduct(getTheProductIdByCommand(command, 1), sellerObject);
            } else if (command.equals("add product")) {
                addProduct();
            } else {
                System.err.println("invalid command");
            }
        }
        back();
    }

    private void edit(Seller seller, String command) {
        int i = 0;
        for (Product product : seller.getAllProducts()) {
            i++;
            if (product.getProductId().equals(command.trim().substring(5))) {
                i++;
                currentProduct = new Product(ProductController.getInstance().
                        getProductWithId(command.substring(5)));
                editProduct();
                break;
            }
        }
        if (i < seller.getAllProducts().size()) {
            printError("there is no product with this id.");
        }
    }

    private void editProduct() {
        String command;
        System.out.println("Enter The Feature That You Want To Edit You Can Use The Help For Your Commands: ");
        while (!((command = scanner.nextLine().trim()).equalsIgnoreCase("back"))) {
            if (command.equalsIgnoreCase("Edit Product's Name")) {
                currentProduct.setProductName(getName("Product name"));
            } else if (command.equalsIgnoreCase("Edit Product Cost")) {
                currentProduct.setProductCost(Double.parseDouble(getDouble("Product Cost")));
            } else if (command.equalsIgnoreCase("Edit description")) {
                System.out.println("Enter Products description");
                String s = "", description = "";
                while (!(s = scanner.nextLine().trim()).equalsIgnoreCase("finish")) {
                    description += s;
                    description += "\\n";
                }
                currentProduct.setDescription(description);
            } else if (command.equalsIgnoreCase("Edit product company")) {
                currentProduct.setProductCompany(getName("Company Name"));
            } else if (command.equalsIgnoreCase("Edit available number of product")) {
                currentProduct.setNumberOfAvailableProducts(Integer.parseInt(getNumber("Available number of product")));
            } else if (command.equalsIgnoreCase("Edit category features")) {
                editCategory();
            } else if (command.equalsIgnoreCase("help")) {
                String help = "1.Edit Product's Name\n2.Edit Product Cost\n3.Edit description\n" +
                        "4.Edit product company\n5.Edit available number of product" +
                        "\n6.Edit category features\n7.Help\n8.Finish\n9.Back";
                System.out.println(help);
            } else if (command.equalsIgnoreCase("finish")) {
                ProductController.getInstance().editProduct(currentProduct);
                break;
            } else {
                System.out.println("invalid command");
            }
            System.out.println("Enter The Feature That You Want To Edit You Can Use The Help For Your Commands: ");

        }
    }

    private Category getCategoryName(String nameKind, String firstName) {
        String name = firstName;
        CategoryController.getInstance().updateAllCategories();
        while (true) {
            if (Pattern.matches("(\\w+ )*\\w+", name) && CategoryController.getInstance().isThereCategoryWithThisName(name)) {
                return CategoryController.getInstance().getCategoryWithName(name);
            } else if (!CategoryController.getInstance().isThereCategoryWithThisName(name)) {
                System.out.println("Category with this name doesn't exists.");
            } else {
                System.out.println("Category" + " is Invalid");
            }
            System.out.println("Enter " + "Category");
            name = scanner.nextLine().trim();
        }
    }

    private void addProduct() {
        ArrayList<String> fieldsOfProduct = new ArrayList<>();
        String category = getName("Products Category");
        Category productsCategory = getCategoryName("Category", category);
        fieldsOfProduct.add(getName("Company Name"));
        fieldsOfProduct.add(getName("Product Name"));
        System.out.println("Enter Products  description.when you finished enter finish in single line.");
        String s = "", description = "";
        while (!(s = scanner.nextLine().trim()).equalsIgnoreCase("finish")) {
            description += s;
            description += "\\n";
        }
        fieldsOfProduct.add(description);
        fieldsOfProduct.add(getDouble("Products Cost"));
        fieldsOfProduct.add(getNumber("Available number of product"));
        HashMap<String, String> productsFeatures = new HashMap<>();
        HashMap<String, ArrayList<String>> categoryFeatures = productsCategory.getFeatures();
        System.out.println("You need to define category features modes.");
        for (String feature : categoryFeatures.keySet()) {
            ArrayList<String> featureModes = categoryFeatures.get(feature);
            System.out.println("feature: " + feature);
            System.out.println("Please choose one mode");
            String modesStringForm = "";
            int i = 1;
            for (String featureMode : featureModes) {
                modesStringForm += i;
                modesStringForm += ".";
                modesStringForm += featureMode;
                modesStringForm += "\n";
                i++;
            }
            modesStringForm = modesStringForm.substring(0, modesStringForm.length() - 1);
            String modesNumber;
            System.out.println(modesStringForm);
            modesNumber = getNumber("number");
            while (Integer.parseInt(modesNumber) > featureModes.size() || Integer.parseInt(modesNumber) < 1) {
                System.out.println("Number is invalid.\nYou only have this chooses.");
                System.out.println(modesStringForm);
                modesNumber = getNumber("number");
            }
            productsFeatures.put(feature, featureModes.get(Integer.parseInt(modesNumber) - 1));
        }
        ProductController.getInstance().addProduct(fieldsOfProduct, productsFeatures, productsCategory);
    }

    private String editCategory() {
        String command;
        System.out.println("Now you need to choose features to reset.so pick one or enter back to get back.");
        HashMap<String, String> productsFeatures = new HashMap<>();
        CategoryController.getInstance().updateAllCategories();
        HashMap<String, ArrayList<String>> categoryFeatures = CategoryController.getInstance()
                .getCategoryWithName(currentProduct.getProductsCategory()).getFeatures();
        ArrayList<String> allFeaturesNames = new ArrayList<>();
        String featuresStringForm = "";
        int i = 1;
        for (String feature : categoryFeatures.keySet()) {
            allFeaturesNames.add(feature);
            featuresStringForm += i;
            featuresStringForm += ".";
            featuresStringForm += feature;
            featuresStringForm += "\n";
            i++;
        }
        featuresStringForm = featuresStringForm.substring(0, featuresStringForm.length() - 1);
        System.out.println(featuresStringForm);

        while (!(command = scanner.nextLine()).trim().equalsIgnoreCase("back")) {
            if (allFeaturesNames.contains(command)) {
                String feature = command;
                System.out.println("feature: " + feature);
                System.out.println("Please choose one mode");
                String modesStringForm = "";
                ArrayList<String> featureModes = categoryFeatures.get(feature);
                i = 1;
                for (String featureMode : featureModes) {
                    modesStringForm += i;
                    modesStringForm += ".";
                    modesStringForm += featureMode;
                    modesStringForm += "\n";
                    i++;
                }
                modesStringForm = modesStringForm.substring(0, modesStringForm.length() - 1);
                String modesNumber;
                System.out.println(modesStringForm);
                modesNumber = getNumber("number");
                while (Integer.parseInt(modesNumber) > featureModes.size() || Integer.parseInt(modesNumber) < 1) {
                    System.out.println("Number is invalid.\nYou only have this chooses.");
                    System.out.println(modesStringForm);
                    modesNumber = getNumber("number");
                }
                currentProduct.getFeaturesOfCategoryThatHas().replace(feature, featureModes.get(Integer.parseInt(modesNumber) - 1));
                System.out.println("you changed mode.");
            } else {
                System.out.println("you need to choose features to reset.so pick one or enter back to get back.");
            }
            System.out.println("now you can choose another feature to change or enter back to finish.");
            System.out.println(featuresStringForm);
        }
        return command;
    }

    private String getName(String nameKind) {
        String name;
        while (true) {
            System.out.println("Enter " + nameKind);
            name = scanner.nextLine().trim();
            if (Pattern.matches("(\\w+ )*\\w+", name)) {
                break;
            } else {
                System.out.println(nameKind + " is Invalid");
            }
        }
        return name;
    }

    private String getNumber(String numberKind) {
        String number;
        while (true) {
            System.out.println("Enter " + numberKind);
            number = scanner.nextLine().trim();
            if (Pattern.matches("\\d+", number)) {
                break;
            } else {
                System.out.println(numberKind + "is Invalid");
            }
        }
        return number;
    }

    private String getDouble(String numberKind) {
        String number;
        while (true) {
            System.out.println("Enter " + numberKind);
            number = scanner.nextLine().trim();
            if (Pattern.matches("\\d+\\.?\\d+", number)) {
                break;
            } else {
                System.out.println(numberKind + "is Invalid");
            }
        }
        return number;
    }

    private String getTheProductIdByCommand(String command, int num) {
        String[] array = command.split("\\s");
        return array[num];
    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
    }

    @Override
    public void printError(String error) {
        super.printError(error);
    }

}
