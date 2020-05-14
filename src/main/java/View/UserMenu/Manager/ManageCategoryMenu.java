package View.UserMenu.Manager;

import Controller.Client.CategoryController;
import Models.Product.Category;
import View.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ManageCategoryMenu extends Menu {

    public ManageCategoryMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String manageCategoryMenu = "";
        manageCategoryMenu += "1.Add [category]\n";
        manageCategoryMenu += "2.Edit [category]\n";
        manageCategoryMenu += "3.Remove [category]\n";
        manageCategoryMenu += "4.Help\n";
        manageCategoryMenu += "5.Back";
        System.out.println(manageCategoryMenu);
    }

    @Override
    public void execute() {
        CategoryController.getInstance().printAllCategories();
        String command;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (Pattern.matches("add (\\w+ )*\\w+", command)) {
                addCategory(command.substring(4));
            } else if (Pattern.matches("remove (\\w+ )*\\w+", command)) {
                removeCategory(command.substring(7));
            } else if (Pattern.matches("edit (\\w+ )*\\w+", command)) {
                editCategory(command.substring(5));
            } else if (command.equalsIgnoreCase("help")) {
                help();
            }
        }
        back();
    }

    private void editCategory(String name) {
        String categoryName = getNameForOperation("category name", name);
        Category category = CategoryController.getInstance().getCategoryWithName(categoryName);
        String command = "";
        HashMap<String, ArrayList<String>> featuresOfCategory = category.getFeatures();
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            String s;
            if (command.equalsIgnoreCase("add feature")) {
                category = addFeatureToCategory(category);
            } else if (command.equalsIgnoreCase("remove feature") && category.getFeatures() != null) {
                category = removeFeature(category);
            } else if (command.equalsIgnoreCase("add mode to feature")) {
                addModeToFeatureOfCategory(category);
            } else if (command.equalsIgnoreCase("help")) {
                String help = "1.add feature\n";
                help += "2.remove feature\n" + "3.add mode to feature\n" + "4.help\n" + "5.back";
                System.out.println(help);
            }
        }
    }

    private Category addModeToFeatureOfCategory(Category category) {
        String s;
        String allFeatures = "";
        ArrayList<String> listOfFeatures = new ArrayList<>();
        int i = 1;
        for (String feature : category.getFeatures().keySet()) {
            listOfFeatures.add(feature);
            allFeatures += i + "." + feature + "\n";
            i++;
        }
        allFeatures = allFeatures.substring(0, allFeatures.length() - 1);
        System.out.println(allFeatures);
        System.out.println("pick one feature");
        while (true) {
            s = scanner.nextLine().trim();
            String input;
            if (Pattern.matches("\\d+", s) && Integer.parseInt(s) <= category.getFeatures().keySet().size() && Integer.parseInt(s) > 0) {
                System.out.println("Enter a mode to add to feature");
                ArrayList<String> featureModes = category.getFeatures().get(listOfFeatures.get(Integer.parseInt(s) - 1));
                while (!(input = scanner.nextLine().trim()).equalsIgnoreCase("/done")) {
                    if (Pattern.matches("(\\w+ )*\\w+", input) && !featureModes.contains(input)) {
                        featureModes.add(input);
                        System.out.println("Enter another one or type /done to finish adding modes.");
                    } else if (featureModes.contains(input)) {
                        System.out.println("you already entered that mode or type /done to finish adding modes..");
                    } else {
                        System.out.println("Enter In correct Form or type /done to finish adding modes.");
                    }
                }
                category.getFeatures().replace(listOfFeatures.get(Integer.parseInt(s) - 1), featureModes);
                break;
            } else {
                System.out.println("Enter a number in range!!");
            }
        }
        CategoryController.getInstance().editCategoryFeatures(category, "adM");
        System.out.println("Feature edited.");
        return category;
    }

    private Category removeFeature(Category category) {
        String s;
        String allFeatures = "";
        ArrayList<String> listOfFeatures = new ArrayList<>();
        int i = 1;
        for (String feature : category.getFeatures().keySet()) {
            listOfFeatures.add(feature);
            allFeatures += i + "." + feature + "\n";
            i++;
        }
        allFeatures = allFeatures.substring(0, allFeatures.length() - 1);
        System.out.println(allFeatures);
        System.out.println("pick one feature to remove");
        while (true) {
            s = scanner.nextLine().trim();
            if (Pattern.matches("\\d+", s) && Integer.parseInt(s) <= category.getFeatures().keySet().size() && Integer.parseInt(s) > 0) {
                category.getFeatures().remove(listOfFeatures.get(Integer.parseInt(s) - 1));
                break;
            } else {
                System.out.println("Enter a number in range!!");
            }
        }
        CategoryController.getInstance().editCategoryFeatures(category, "del");
        System.out.println("Feature deleted.");
        return category;
    }

    private Category addFeatureToCategory(Category category) {
        HashMap<String, ArrayList<String>> featuresOfCategory = category.getFeatures();
        String s;
        System.out.println("Please enter a feature Name.");
        while (!(s = scanner.nextLine().trim()).equalsIgnoreCase("/done")) {
            if (Pattern.matches("(\\w+ )*\\w+", s) && !featuresOfCategory.containsKey(s)) {
                String featureName = s;
                ArrayList<String> featureModes = new ArrayList<>();
                System.out.println("Please Enter different modes of feature");
                while (!(s = scanner.nextLine().trim()).equalsIgnoreCase("/done")) {
                    if (Pattern.matches("(\\w+ )*\\w+", s) && !featureModes.contains(s)) {
                        featureModes.add(s);
                        System.out.println("Enter another one or type /done to finish adding modes.");
                    } else if (featureModes.contains(s)) {
                        System.out.println("you already entered that mode.");
                    } else {
                        System.out.println("Enter In correct Form");
                    }
                }
                category.getFeatures().put(featureName, featureModes);
                break;
            } else if (featuresOfCategory.containsKey(s)) {
                System.out.println("The feature exists.");
            } else {
                System.out.println("Enter In Correct Form.you can just use alphabetical characters.");
            }
        }
        CategoryController.getInstance().editCategoryFeatures(category, "add");
        System.out.println("Category edited");
        return category;
    }

    private void addCategory(String name) {
        HashMap<String, ArrayList<String>> featuresOfCategory = new HashMap();
        String categoryName = getName("categories name", name);
        String s;
        System.out.println("You need to define features for you'r category.");
        System.out.println("Please enter a feature Name.");
        while (!(s = scanner.nextLine().trim()).equalsIgnoreCase("/done")) {
            if (Pattern.matches("(\\w+ )*\\w+", s) && !featuresOfCategory.containsKey(s)) {
                String featureName = s;
                ArrayList<String> featureModes = new ArrayList<>();
                System.out.println("Please Enter different modes of feature");
                while (!(s = scanner.nextLine().trim()).equalsIgnoreCase("/done")) {
                    if (Pattern.matches("(\\w+ )*\\w+", s) && !featureModes.contains(s)) {
                        featureModes.add(s);
                        System.out.println("Enter another one or type /done to finish adding modes.");
                    } else if (featureModes.contains(s)) {
                        System.out.println("you already entered that mode.");
                    } else {
                        System.out.println("Enter In correct Form");
                    }
                }
                featuresOfCategory.put(featureName, featureModes);
                System.out.println("Please enter another feature name , or type /done to finish adding features.");
            } else if (featuresOfCategory.containsKey(s)) {
                System.out.println("you already entered that feature.");
            } else {
                System.out.println("Enter In Correct Form.you can just use alphabetical characters.");
            }
        }
        CategoryController.getInstance().addNewCategory(new Category(categoryName, featuresOfCategory));
    }

    private void removeCategory(String name) {
        String categoryName = getNameForOperation("category name", name);
        CategoryController.getInstance().removeCategory(categoryName);
    }

    private String getName(String nameKind, String firstName) {
        String name = firstName;
        CategoryController.getInstance().updateAllCategories();
        while (true) {
            if (Pattern.matches("(\\w+ )*\\w+", name) && !CategoryController.getInstance().isThereCategoryWithThisName(name)) {
                break;
            } else if (CategoryController.getInstance().isThereCategoryWithThisName(name)) {
                System.out.println("Category with this name already exists.");
            } else {
                System.out.println(nameKind + " is Invalid");
            }
            System.out.println("Enter " + nameKind);
            name = scanner.nextLine().trim();
        }
        return name;
    }

    private String getNameForOperation(String nameKind, String firstName) {
        String name = firstName;
        CategoryController.getInstance().updateAllCategories();
        while (true) {
            if (Pattern.matches("(\\w+ )*\\w+", name) && CategoryController.getInstance().isThereCategoryWithThisName(name)) {
                break;
            } else if (!CategoryController.getInstance().isThereCategoryWithThisName(name)) {
                System.out.println("There is no category with this name");
            } else {
                System.out.println(nameKind + " is Invalid");
            }
            System.out.println("Enter " + nameKind);
            name = scanner.nextLine().trim();
        }
        return name;
    }


}
