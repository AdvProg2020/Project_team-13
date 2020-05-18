package View.ProductsAndOffsMenus;

import Controller.Client.CategoryController;
import Controller.Client.ProductController;
import Controller.Server.ProductCenter;
import Models.Product.Category;
import Models.Product.Product;
import Models.Product.ProductStatus;
import View.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class FilteringMenu extends Menu {

    public FilteringMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String filteringMenuHelp = "1.Filter\n2.Disable filters\n3.Current filters" +
                "\n4.Show available filters\n5.Help\n6.Back";
        System.out.println(filteringMenuHelp);
    }

    @Override
    public void execute() {
        String command;
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("filter")) {
                filter();
            } else if (command.equalsIgnoreCase("disable filters")) {
                disableFilters();
            } else if (command.equalsIgnoreCase("current filters")) {
                showCurrentFilters();
            } else if (command.equalsIgnoreCase("show available filters")) {
                String allFilters = "1.Filter by price range\n2.Filter by brand\n3.Filter by seller\n4.Filter by product status\n5.Filter by name\n6.Filter By Category Features";
                System.out.println(allFilters);
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else {
                System.out.println("command is invalid");
            }
        }

    }

    private void showCurrentFilters() {
        String allFilters = "";
        if (ProductController.getInstance().getCurrentCategory() != null) {
            allFilters += "Category: " + ProductController.getInstance().getCurrentCategory().getName() + "\n";
            allFilters += ProductController.getInstance().getPriceFiltersInStringForm();
            allFilters += ProductController.getInstance().getSellersFiltersInStringForm();
            allFilters += ProductController.getInstance().getBrandsFiltersInStringForm();
            allFilters += ProductController.getInstance().getProductsStatusFiltersInStringForm();
            allFilters += ProductController.getInstance().getNameToFilterInStringForm();
            allFilters += ProductController.getInstance().getCategoryFeaturesFiltersInStringForm();
        }
        System.out.println(allFilters);
    }

    private void disableFilters() {
        System.out.println("now you have this filters to filter.");
        String allFilters = "1.Disable price filter\n2.Disable brand filters\n3.disable seller filters\n4.disable product status filters\n5.disable name filters\n6.disable Category features filters";
        System.out.println(allFilters + "\nnow you need to enter a number 1-5 to filter products.");
        int a = 0;
        while (true) {
            a = Integer.parseInt(getNumber("Number"));
            if (a > 0 && a < 7) {
                if (a == 1) {
                    ProductController.getInstance().disablePriceFilter();
                    System.out.println("filters disabled");
                } else if (a == 2) {
                    disableBrandFilters();
                } else if (a == 3) {
                    disableSellerFilters();
                } else if (a == 4) {
                    disableProductStatusFilter();
                } else if (a == 5) {
                    ProductController.getInstance().disableNameFilter();
                } else if (a == 6) {
                    disableCategoryFeaturesFilters();
                }
                System.out.println("now you can delete more filters or replace them.if you want to finish disabling filters enter 99.");
            } else if (a == 99) {
                break;
            } else {
                System.out.println("Number must be in range 1-6");
            }
        }
    }

    private void filter() {
        if (ProductController.getInstance().getCurrentCategory() == null) {
            System.out.println("First you need to pick a category to filter.please enter a category");
            Category category = getCategoryName("category name", "");
            ProductController.getInstance().setCurrentCategory(category);
        }
        System.out.println("now you have this filters to filter.");
        String allFilters = "1.Filter by price range\n2.Filter by brand\n3.Filter by seller\n4.Filter by product status\n5.filter by name\n6.filter by category features";
        System.out.println(allFilters + "\nnow you need to enter a number 1-5 to filter products.");
        int a = 0;
        while (true) {
            a = Integer.parseInt(getNumber("Number"));
            if (a > 0 && a < 7) {
                if (a == 1) {
                    addPriceFilter();
                } else if (a == 2) {
                    addBrandFilters();
                } else if (a == 3) {
                    addSellerFilters();
                } else if (a == 4) {
                    addProductStatusFilter();
                } else if (a == 5) {
                    addNameFilter();
                } else if (a == 6) {
                    filterByCategoryFeatures();
                }
                System.out.println("now you can add more filters or replace them.if you want to finish filtering enter 99.");
            } else if (a == 99) {
                break;
            } else {
                System.out.println("Number must be in range 1-6");
            }
        }
    }

    private void filterByCategoryFeatures() {
        Category currentCategory = ProductController.getInstance().getCurrentCategory();
        HashMap<String, ArrayList<String>> categoryFeaturesToFilter = new HashMap<>();
        HashMap<String, ArrayList<String>> categoryFeatures = currentCategory.getFeatures();
        for (String feature : categoryFeatures.keySet()) {
            ArrayList<String> featureModes = categoryFeatures.get(feature);
            System.out.println("feature: " + feature);
            System.out.println("Please choose modes To filter");
            String modesStringForm = "";
            int i = 1;
            for (String featureMode : featureModes) {
                modesStringForm += i;
                modesStringForm += ".";
                modesStringForm += featureMode;
                modesStringForm += "\n";
                i++;
            }
            ArrayList<String> featureModesToFilter = new ArrayList<>();
            modesStringForm = modesStringForm.substring(0, modesStringForm.length() - 1);
            System.out.println(modesStringForm);
            System.out.println("Pick modes of this feature to filter and when you done enter 9999.");
            while (featureModesToFilter.size() != featureModes.size()) {
                String modeNumber = getNumber("mode number");
                if (Integer.parseInt(modeNumber) == 9999 || featureModesToFilter.size() == featureModes.size()) {
                    break;
                } else if (Integer.parseInt(modeNumber) > featureModes.size() || Integer.parseInt(modeNumber) < 1) {
                    System.out.println("Number is invalid.\nYou only have this chooses.");
                    System.out.println(modesStringForm);
                } else if (featureModesToFilter.contains(featureModes.get(Integer.parseInt(modeNumber) - 1))) {
                    System.out.println("enter different mode number or enter 9999 to finish.");
                    System.out.println(modesStringForm);
                } else {
                    featureModesToFilter.add(featureModes.get(Integer.parseInt(modeNumber) - 1));
                    System.out.println("if you want to finish choosing modes enter 9999");

                }
            }
            if (featureModes != null && !featureModes.isEmpty()) {
                categoryFeaturesToFilter.put(feature, featureModesToFilter);
            }
        }
        ProductController.getInstance().setCategoryFeaturesToFilter(categoryFeaturesToFilter);
        System.out.println("Filters added");
    }

    private void disableCategoryFeaturesFilters() {
        Category currentCategory = ProductController.getInstance().getCurrentCategory();
        HashMap<String, ArrayList<String>> categoryFeatures = ProductController.getInstance().getCategoryFeaturesToFilter();
        for (String feature : categoryFeatures.keySet()) {
            ArrayList<String> featureModes = categoryFeatures.get(feature);
            System.out.println("feature: " + feature);
            System.out.println("Please choose modes To delete");
            String modesStringForm = "";
            int i = 1;
            for (String featureMode : featureModes) {
                modesStringForm += i;
                modesStringForm += ".";
                modesStringForm += featureMode;
                modesStringForm += "\n";
                i++;
            }
            ArrayList<String> featureModesToFilter = categoryFeatures.get(feature);
            modesStringForm = modesStringForm.substring(0, modesStringForm.length() - 1);
            System.out.println(modesStringForm);
            String modesNumber;
            System.out.println("Pick modes of this feature to disable filter and when you done enter 9999.");
            while (true) {
                String modeNumber = getNumber("mode number");
                if (Integer.parseInt(modeNumber) == 9999 || featureModesToFilter.size() == 0) {
                    break;
                } else if (Integer.parseInt(modeNumber) > featureModesToFilter.size() || Integer.parseInt(modeNumber) < 1) {
                    System.out.println("Number is invalid.\nYou only have this chooses.");
                    System.out.println(modesStringForm);
                } else {
                    featureModesToFilter.remove(Integer.parseInt(modeNumber) - 1);
                    modesStringForm = "";
                    i = 1;
                    for (String featureMode : featureModesToFilter) {
                        modesStringForm += i;
                        modesStringForm += ".";
                        modesStringForm += featureMode;
                        modesStringForm += "\n";
                        i++;
                    }
                    System.out.println("if you want to finish choosing modes enter 9999");

                }
            }
            if (featureModes != null && !featureModes.isEmpty()) {
                categoryFeatures.replace(feature, featureModesToFilter);
            } else {
                categoryFeatures.remove(feature);
            }
        }
        ProductController.getInstance().setCategoryFeaturesToFilter(categoryFeatures);
        System.out.println("Filters edited");
    }

    private void addPriceFilter() {
        double max = 0.0, min = 0.0;
        System.out.println("Enter a number for minimum amount");
        min = Double.parseDouble(getNumber("Minimum amount"));
        while (true) {
            max = Double.parseDouble(getNumber("Maximum amount"));
            if (max >= min) {
                break;
            }
            System.out.println("Maximum need to bigger or equals to minimum.");
        }
        ProductController.getInstance().setMaxAndMinAmount(min, max);
        System.out.println("filter added.");
    }

    private void addBrandFilters() {
        ArrayList<String> allBrands = ProductController.getInstance().getAllBrands();
        String allBrandsStringForm = "";
        int i = 1;
        for (String brand : allBrands) {
            allBrandsStringForm += i;
            allBrandsStringForm += ".";
            allBrandsStringForm += brand;
            allBrandsStringForm += "\n";
            i++;
        }
        allBrandsStringForm = allBrandsStringForm.substring(0, allBrandsStringForm.length() - 1);
        System.out.println(allBrandsStringForm);
        ArrayList<String> allBrandsToFilter = new ArrayList<>();
        while (true) {
            String brandNumber = getNumber("brand number");
            if (Integer.parseInt(brandNumber) == 9999 || allBrands.size() == allBrandsToFilter.size()) {
                break;
            } else if (Integer.parseInt(brandNumber) > allBrands.size() || Integer.parseInt(brandNumber) < 1) {
                System.out.println("Number is invalid.\nYou only have this chooses.");
                System.out.println(allBrandsStringForm);
            } else if (allBrandsToFilter.contains(allBrands.get(Integer.parseInt(brandNumber) - 1))) {
                System.out.println("enter different number or enter 9999 to finish.");
                System.out.println(allBrandsStringForm);
            } else {
                allBrandsToFilter.add(allBrands.get(Integer.parseInt(brandNumber) - 1));
            }
            System.out.println("if you want to finish choosing brands enter 9999");
        }
        ProductController.getInstance().setAllBrandsToFilter(allBrandsToFilter);
        System.out.println("filter added.");
    }

    private void addSellerFilters() {
        ArrayList<String> allSellers = ProductController.getInstance().getAllSellers();
        String allSellersStringForm = "";
        int i = 1;
        for (String brand : allSellers) {
            allSellersStringForm += i;
            allSellersStringForm += ".";
            allSellersStringForm += brand;
            allSellersStringForm += "\n";
            i++;
        }
        allSellersStringForm = allSellersStringForm.substring(0, allSellersStringForm.length() - 1);
        System.out.println(allSellersStringForm);
        ArrayList<String> allSellersToFilter = new ArrayList<>();
        while (true) {
            String sellerNumber = getNumber("seller number");
            if (Integer.parseInt(sellerNumber) == 9999 || allSellersToFilter.size() == allSellers.size()) {
                break;
            } else if (Integer.parseInt(sellerNumber) > allSellers.size() || Integer.parseInt(sellerNumber) < 1) {
                System.out.println("Number is invalid.\nYou only have this chooses.");
                System.out.println(allSellersStringForm);
            } else if (allSellersToFilter.contains(allSellers.get(Integer.parseInt(sellerNumber) - 1))) {
                System.out.println("enter different number or enter 9999 to finish.");
                System.out.println(allSellersStringForm);
            } else {
                allSellersToFilter.add(allSellers.get(Integer.parseInt(sellerNumber) - 1));
            }
            System.out.println("if you want to finish choosing sellers enter 9999");
        }
        ProductController.getInstance().setAllSellersToFilter(allSellersToFilter);
        System.out.println("filter added.");
    }

    private void addProductStatusFilter() {
        ArrayList<ProductStatus> allProductStatusToFilter = new ArrayList<>();
        ArrayList<ProductStatus> allAvailableProductStatus = new ArrayList<>();
        allAvailableProductStatus.add(ProductStatus.accepted);
        allAvailableProductStatus.add(ProductStatus.inCreatingProgress);
        allAvailableProductStatus.add(ProductStatus.editing);
        String differentStatusOfProduct = "1.accepted\n2.inCreatingProgress\n3.editing";
        while (true) {
            String sellerNumber = getNumber("status number");
            if (Integer.parseInt(sellerNumber) == 9999 || allProductStatusToFilter.size() == 3) {
                break;
            } else if (Integer.parseInt(sellerNumber) > 3 || Integer.parseInt(sellerNumber) < 1) {
                System.out.println("Number is invalid.\nYou only have this chooses.");
                System.out.println(differentStatusOfProduct);
            } else if (allProductStatusToFilter.contains(allAvailableProductStatus.get(Integer.parseInt(sellerNumber) - 1))) {
                System.out.println("enter different number or enter 9999 to finish.");
                System.out.println(differentStatusOfProduct);
            } else {
                allProductStatusToFilter.add(allAvailableProductStatus.get(Integer.parseInt(sellerNumber) - 1));
            }
            System.out.println("if you want to finish choosing sellers enter 9999");
        }
        ProductController.getInstance().setAllStatusToFilter(allProductStatusToFilter);
        System.out.println("filter added.");
    }

    private void addNameFilter() {
        System.out.println("Enter a name to filter by that name.");
        String nameToFilter = getName("name");
        ProductController.getInstance().setNameToFilter(nameToFilter);
        System.out.println("filter added.");
    }

    private void disableBrandFilters() {
        ArrayList<String> allBrands = ProductController.getInstance().getAllBrandsToFilter();
        if (allBrands != null) {
            String allBrandsStringForm = "";
            int i = 1;
            for (String brand : allBrands) {
                allBrandsStringForm += i;
                allBrandsStringForm += ".";
                allBrandsStringForm += brand;
                allBrandsStringForm += "\n";
                i++;
            }
            allBrandsStringForm = allBrandsStringForm.substring(0, allBrandsStringForm.length() - 1);
            System.out.println(allBrandsStringForm);
            while (true) {
                String brandNumber = getNumber("brand number");
                if (Integer.parseInt(brandNumber) == 9999 || allBrands.size() == 0) {
                    break;
                } else if (Integer.parseInt(brandNumber) > allBrands.size() || Integer.parseInt(brandNumber) < 1) {
                    System.out.println("Number is invalid.\nYou only have this chooses.");
                    System.out.println(allBrandsStringForm);
                } else {
                    allBrands.remove(Integer.parseInt(brandNumber) - 1);
                    allBrandsStringForm = "";
                    if (allBrands != null && !allBrands.isEmpty()) {
                        i = 1;
                        for (String brand : allBrands) {
                            allBrandsStringForm += i;
                            allBrandsStringForm += ".";
                            allBrandsStringForm += brand;
                            allBrandsStringForm += "\n";
                            i++;
                        }
                        allBrandsStringForm = allBrandsStringForm.substring(0, allBrandsStringForm.length() - 1);
                    }
                }
                System.out.println("if you want to finish choosing brands enter 9999");
            }
            ProductController.getInstance().setAllBrandsToFilter(allBrands);
            System.out.println("filter edited.");
        }
    }

    private void disableSellerFilters() {
        ArrayList<String> allSellers = ProductController.getInstance().getAllSellersToFilter();
        if (allSellers != null && !allSellers.isEmpty()) {
            String allSellersStringForm = "";
            int i = 1;
            for (String brand : allSellers) {
                allSellersStringForm += i;
                allSellersStringForm += ".";
                allSellersStringForm += brand;
                allSellersStringForm += "\n";
                i++;
            }
            allSellersStringForm = allSellersStringForm.substring(0, allSellersStringForm.length() - 1);
            System.out.println(allSellersStringForm);
            while (true) {
                String sellerNumber = getNumber("seller number");
                if (Integer.parseInt(sellerNumber) == 9999 || allSellers.size() == 0) {
                    break;
                } else if (Integer.parseInt(sellerNumber) > allSellers.size() || Integer.parseInt(sellerNumber) < 1) {
                    System.out.println("Number is invalid.\nYou only have this chooses.");
                    System.out.println(allSellersStringForm);
                } else {
                    allSellers.remove(Integer.parseInt(sellerNumber) - 1);
                    allSellersStringForm = "";
                    if (allSellers != null && !allSellers.isEmpty()) {
                        i = 1;
                        for (String brand : allSellers) {
                            allSellersStringForm += i;
                            allSellersStringForm += ".";
                            allSellersStringForm += brand;
                            allSellersStringForm += "\n";
                            i++;
                        }
                        allSellersStringForm = allSellersStringForm.substring(0, allSellersStringForm.length() - 1);
                    }
                }
                System.out.println("if you want to finish deleting sellers enter 9999");
            }
            ProductController.getInstance().setAllSellersToFilter(allSellers);
            System.out.println("filter edited.");
        } else {
            System.out.println("There is no seller filter.");
        }
    }

    private void disableProductStatusFilter() {
        ArrayList<ProductStatus> allProductStatusToFilter = ProductController.getInstance().getAllProductStatusToFilter();
        if (allProductStatusToFilter != null && !allProductStatusToFilter.isEmpty()) {
            String differentStatusOfProduct = "";
            int i = 1;
            for (ProductStatus productStatus : allProductStatusToFilter) {
                differentStatusOfProduct += i;
                differentStatusOfProduct += ".";
                differentStatusOfProduct += productStatus.getName();
                differentStatusOfProduct += "\n";
                i++;
            }
            differentStatusOfProduct = differentStatusOfProduct.substring(0, differentStatusOfProduct.length() - 1);
            System.out.println(differentStatusOfProduct);
            while (true) {
                String productStatusNumber = getNumber("productStatus number");
                if (Integer.parseInt(productStatusNumber) == 9999 || allProductStatusToFilter.size() == 0) {
                    break;
                } else if (Integer.parseInt(productStatusNumber) > allProductStatusToFilter.size() || Integer.parseInt(productStatusNumber) < 1) {
                    System.out.println("Number is invalid.\nYou only have this chooses.");
                    System.out.println(differentStatusOfProduct);
                } else {
                    allProductStatusToFilter.remove(Integer.parseInt(productStatusNumber) - 1);
                    if (allProductStatusToFilter != null && !allProductStatusToFilter.isEmpty()) {
                        i = 1;
                        for (ProductStatus productStatus : allProductStatusToFilter) {
                            differentStatusOfProduct += i;
                            differentStatusOfProduct += ".";
                            differentStatusOfProduct += productStatus.getName();
                            differentStatusOfProduct += "\n";
                            i++;
                        }
                        differentStatusOfProduct = differentStatusOfProduct.substring(0, differentStatusOfProduct.length() - 1);
                    }
                }
                System.out.println("if you want to finish deleting productStatus enter 9999");
            }
            ProductController.getInstance().setAllStatusToFilter(allProductStatusToFilter);
            System.out.println("filter edited.");
        } else {
            System.out.println("There is no product status filter.");
        }
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

    private Category getCategoryName(String nameKind, String firstName) {
        String name = firstName;
        CategoryController.getInstance().updateAllCategories();
        while (true) {
            if (Pattern.matches("(\\w+ )*\\w+", name) && CategoryController.getInstance().isThereCategoryWithThisName(name)) {
                return CategoryController.getInstance().getCategoryWithName(name);
            } else if (!CategoryController.getInstance().isThereCategoryWithThisName(name)) {
                System.out.println("Category with this name doesn't exists.");
            } else {
                System.out.println(nameKind + " is Invalid");
            }
            System.out.println("Enter " + nameKind);
            name = scanner.nextLine().trim();
        }
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

    private String getDoubleNumber(String numberKind) {
        String number;
        while (true) {
            System.out.println("Enter " + numberKind);
            number = scanner.nextLine().trim();
            if (Pattern.matches("\\d+.?\\d+", number)) {
                break;
            } else {
                System.out.println(numberKind + "is Invalid");
            }
        }
        return number;
    }


}
