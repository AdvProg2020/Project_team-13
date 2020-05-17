package Controller.Client;

import Models.Product.Category;
import Models.Product.Product;
import Models.Product.ProductStatus;
import Models.UserAccount.Customer;
import Models.UserAccount.Seller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

public class ProductController {
    private ArrayList<Product> allProducts, allProductsAfterFilter;
    private ArrayList<String> allBrandsToFilter, allSellersToFilter;
    private ArrayList<ProductStatus> allProductStatusToFilter;
    private HashMap<String, ArrayList<String>> categoryFeaturesToFilter;
    private static ProductController productController;
    private double max, min;
    private Category currentCategory;
    private boolean isPriceFilterActive = false, isNameFilterActive = false, kindOfSort, isSortActivated = false;
    private String nameToFilter, currentSort;

    private ProductController() {
    }

    public static ProductController getInstance() {
        if (productController == null) {
            productController = new ProductController();
        }
        productController.getAllProductsFromServer();
        return productController;
    }

    public Product getProductWithId(String productId){
        for (Product product : allProducts) {
            if(product.getProductId().equals(productId)){
                return product;
            }
        }
        return null;
    }

    public void addProduct(ArrayList<String> fields, HashMap<String, String> featuresOfCategory, Category category) {
        Product product = new Product(fields.get(0), null, fields.get(1), (Seller) ClientController.getInstance().getCurrentUser()
                , Double.parseDouble(fields.get(3)), category.getName(), fields.get(2), Integer.parseInt(fields.get(4))
                , featuresOfCategory);
        Gson gson = new Gson();
        String product0 = gson.toJson(product);
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("AddProduct", product0));
    }

    /*public void editProduct(String productId, String userName, String field, String newValue){

        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage());
    }*/

    public String getPriceFiltersInStringForm() {
        if (isPriceFilterActive) {
            return ("Price Filter: maximum price= " + max + " minimum price=" + min + "\n");
        } else return "";
    }

    public String getBrandsFiltersInStringForm() {
        if (allBrandsToFilter != null && !allBrandsToFilter.isEmpty()) {
            String brandsStringForm = "All brands used to filter:\n";
            int i = 1;
            for (String brand : allBrandsToFilter) {
                brandsStringForm += i + "." + brand + "\n";
                i++;
            }
            return brandsStringForm;
        } else return "";
    }

    public String getSellersFiltersInStringForm() {
        if (allSellersToFilter != null && !allSellersToFilter.isEmpty()) {
            String stringForm = "All sellers used to filter:\n";
            int i = 1;
            for (String seller : allSellersToFilter) {
                stringForm += i + "." + seller + "\n";
                i++;
            }
            return stringForm;
        } else return "";
    }

    public String getProductsStatusFiltersInStringForm() {
        if (allProductStatusToFilter != null && !allProductStatusToFilter.isEmpty()) {
            String stringForm = "All productStatus used to filter:\n";
            int i = 1;
            for (ProductStatus productStatus : allProductStatusToFilter) {
                stringForm += i + "." + productStatus.getName() + "\n";
                i++;
            }
            return stringForm;
        } else return "";
    }

    public String getNameToFilterInStringForm() {
        if (nameToFilter != null) {
            return ("products filtered by name: " + nameToFilter + "\n");
        }
        return "";
    }

    public String getCategoryFeaturesFiltersInStringForm() {
        if (categoryFeaturesToFilter != null && !categoryFeaturesToFilter.isEmpty()) {
            String stringForm = "All features used to filter:\n";
            int j = 1;
            for (String feature : categoryFeaturesToFilter.keySet()) {
                stringForm += j + "." + feature + "  modes:\n";
                int i = 1;
                for (String seller : categoryFeaturesToFilter.get(feature)) {
                    stringForm += "\t" + j + "." + i + ". " + seller + "\n";
                    i++;
                }
                j++;
            }
            return stringForm;
        } else return "";
    }

    public void removeProduct(String productId, String seller) {
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("deleteProduct", productId + "/" + seller));
    }

    public String getCurrentSort() {
        if (isSortActivated) {
            String sortKind = "";
            if (kindOfSort) {
                sortKind = "ascending";
            } else {
                sortKind = "descending";
            }
            return "current sort: " + this.currentSort + " " + sortKind;
        }
        return "no sort selected";
    }

    public String disableSort() {
        if (isSortActivated) {
            isSortActivated = false;
            return "current sort disabled.";
        } else {
            return "no sort selected";
        }
    }

    public void printAllProducts() {
        getAllProductsFromServer();
        for (Product product : allProducts) {
            ClientController.getInstance().getCurrentMenu().showMessage(product.viewProduct());
        }
    }

    public void showProductsAfterFilterAndSort() {
        filterProducts();
        sortProducts();
        ArrayList<Product> allProducts = allProductsAfterFilter;
        String productsInViewFormat = "";
        for (Product product : allProducts) {
            productsInViewFormat += product.getProductId() + "\t" + product.getProductName() + "\t" + product.getProductCost() + "\t" + product.getProductStatus().toString() + "\n";
        }
        if (allProducts != null && !allProducts.isEmpty()) {
            ClientController.getInstance().getCurrentMenu().showMessage(productsInViewFormat.substring(0, productsInViewFormat.length() - 1));
        }

    }

    public void filterProducts() {
        allProductsAfterFilter = allProducts;
        filterByCategory();
        filterByCategoryFeatures();
        filterByPrice();
        filterByBrand();
        filterBySeller();
        filterByProductStatus();
        filterByName();
    }

    private void filterByCategory() {
        if (currentCategory != null && !allProductsAfterFilter.isEmpty()) {
            ArrayList<Product> productsNeedToDeletedFromList = new ArrayList<>();
            for (Product product : allProductsAfterFilter) {
                if (!product.getProductsCategory().equalsIgnoreCase(currentCategory.getName())) {
                    productsNeedToDeletedFromList.add(product);
                }
            }
            if (productsNeedToDeletedFromList != null && !productsNeedToDeletedFromList.isEmpty()) {
                for (Product product : productsNeedToDeletedFromList) {
                    allProductsAfterFilter.remove(product);
                }
            }
        }
    }

    private void filterByPrice() {
        if (isPriceFilterActive) {
            List<Product> lst = allProductsAfterFilter;
            Product[] productsAfterPriceFilter =
                    lst.stream().filter(e -> e.getProductCost() <= max && e.getProductCost() >= min).toArray(Product[]::new);
            allProductsAfterFilter = new ArrayList<Product>(Arrays.asList(productsAfterPriceFilter));
        }

    }

    private void filterByBrand() {
        if (allBrandsToFilter != null && !allBrandsToFilter.isEmpty()) {
            List<Product> lst = allProductsAfterFilter;
            Product[] productsAfterPriceFilter =
                    lst.stream().filter(e -> allBrandsToFilter.contains(e.getProductCompany())).toArray(Product[]::new);
            allProductsAfterFilter = new ArrayList<Product>(Arrays.asList(productsAfterPriceFilter));
        }
    }

    private void filterBySeller() {
        if (allSellersToFilter != null && !allSellersToFilter.isEmpty()) {
            List<Product> lst = allProductsAfterFilter;
            Product[] productsAfterPriceFilter =
                    lst.stream().filter(e -> allSellersToFilter.contains(e.getSeller())).toArray(Product[]::new);
            allProductsAfterFilter = new ArrayList<Product>(Arrays.asList(productsAfterPriceFilter));
        }
    }

    private void filterByProductStatus() {
        if (allProductStatusToFilter != null && !allProductStatusToFilter.isEmpty()) {
            List<Product> lst = allProductsAfterFilter;
            Product[] productsAfterPriceFilter =
                    lst.stream().filter(e -> allProductStatusToFilter.contains(e.getProductStatus())).toArray(Product[]::new);
            allProductsAfterFilter = new ArrayList<Product>(Arrays.asList(productsAfterPriceFilter));
        }
    }

    private void filterByName() {
        if (isNameFilterActive && nameToFilter != null) {
            List<Product> lst = allProductsAfterFilter;
            Product[] productsAfterPriceFilter =
                    lst.stream().filter(e -> e.getProductName().toLowerCase().contains(nameToFilter.toLowerCase())).toArray(Product[]::new);
            allProductsAfterFilter = new ArrayList<Product>(Arrays.asList(productsAfterPriceFilter));
        }
    }

    public HashMap<String, ArrayList<String>> getCategoryFeaturesToFilter() {
        return categoryFeaturesToFilter;
    }

    public void filterByCategoryFeatures() {
        if (categoryFeaturesToFilter != null && !categoryFeaturesToFilter.isEmpty()) {
            List<Product> lst = allProductsAfterFilter;
            Product[] productsAfterPriceFilter =
                    lst.stream().filter(e -> checkProductHaveFeatureMode(e)).toArray(Product[]::new);
            allProductsAfterFilter = new ArrayList<Product>(Arrays.asList(productsAfterPriceFilter));
        }
    }

    private boolean checkProductHaveFeatureMode(Product product) {
        for (String s : categoryFeaturesToFilter.keySet()) {
            if (!categoryFeaturesToFilter.get(s).contains(product.getFeaturesOfCategoryThatHas().get(s))) {
                return false;
            }
        }
        return true;
    }

    public void setAllFiltersNull() {
        allBrandsToFilter = null;
        allSellersToFilter = null;
        allProductStatusToFilter = null;
        max = 0;
        max = 0;
        currentCategory = null;
        isPriceFilterActive = false;
        isSortActivated = false;
    }

    public void disablePriceFilter() {
        isPriceFilterActive = false;
    }

    public void disableNameFilter() {
        isNameFilterActive = false;
    }

    public void setMaxAndMinAmount(double min, double max) {
        this.max = max;
        this.min = min;
        isPriceFilterActive = true;
    }

    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }

    public void setAllBrandsToFilter(ArrayList<String> allBrandsToFilter) {
        this.allBrandsToFilter = allBrandsToFilter;

    }

    public void setAllSellersToFilter(ArrayList<String> allSellersToFilter) {
        this.allSellersToFilter = allSellersToFilter;

    }

    public void setAllStatusToFilter(ArrayList<ProductStatus> allProductStatusToFilter) {
        this.allProductStatusToFilter = allProductStatusToFilter;

    }

    public void setNameToFilter(String name) {
        this.nameToFilter = name;
        isNameFilterActive = true;
    }

    public void setCategoryFeaturesToFilter(HashMap<String, ArrayList<String>> categoryFeaturesToFilter) {
        this.categoryFeaturesToFilter = categoryFeaturesToFilter;
    }

    public ArrayList<String> getAllBrandsToFilter() {
        return allBrandsToFilter;
    }

    public ArrayList<String> getAllSellersToFilter() {
        return allSellersToFilter;
    }

    public void setCurrentSort(String currentSort, boolean kindOfSort) {
        isSortActivated = true;
        this.currentSort = currentSort;
        this.kindOfSort = kindOfSort;
    }

    public ArrayList<ProductStatus> getAllProductStatusToFilter() {
        return allProductStatusToFilter;
    }

    public void sortProducts() {
        if (isSortActivated) {
            ArrayList<Product> allProductsAfterSort = new ArrayList<>(allProductsAfterFilter);
            if (currentSort.equals("newest")) {
                if (!kindOfSort) {
                    for (int i = allProductsAfterSort.size() - 1; i >= 0; i--) {
                        allProductsAfterFilter.set(allProductsAfterSort.size() - i - 1, allProductsAfterSort.get(i));
                    }
                }
            } else if (currentSort.equals("price")) {
                Collections.sort(allProductsAfterFilter, new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        if (kindOfSort) {
                            return (int) (o1.getProductCost() - o2.getProductCost());
                        } else {
                            return (int) (o2.getProductCost() - o1.getProductCost());
                        }
                    }
                });

            } else if (currentSort.equals("score")) {

                Collections.sort(allProductsAfterFilter, new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        if (kindOfSort) {
                            return (int) (o1.getAverageScore() - o2.getAverageScore());
                        } else {
                            return (int) (o2.getAverageScore() - o1.getAverageScore());
                        }
                    }
                });

            }
        }
    }

    public void updateAllProducts(String json) {
        Type productListType = new TypeToken<ArrayList<Product>>() {
        }.getType();
        allProducts = new Gson().fromJson(json, productListType);
    }

    public void getAllProductsFromServer() {
        ClientController.getInstance().sendMessageToServer("@getAllProductsForManager@");
    }

    public Category getCurrentCategory() {
        return currentCategory;
    }

    public void removeProductForManager(String productId) {
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("removeProductForManager", productId));
    }

    public ArrayList<String> getAllBrands() {
        if (currentCategory != null) {
            ArrayList<String> allBrands = new ArrayList<>();
            for (Product product : currentCategory.getAllProducts()) {
                if (!allBrands.contains(product.getProductCompany())) {
                    allBrands.add(product.getProductCompany());
                }
            }
            return allBrands;
        }
        return null;
    }

    public ArrayList<String> getAllSellers() {
        if (currentCategory != null) {
            ArrayList<String> allSellers = new ArrayList<>();
            for (Product product : currentCategory.getAllProducts()) {
                if (!allSellers.contains(product.getSeller())) {
                    allSellers.add(product.getSeller());
                }
            }
            return allSellers;
        }
        return null;
    }

    public String getTheProductDetails(ArrayList<String> allProducts) {
        String allDetails = "";
        for (String product1 : allProducts) {
            Product product=getProductWithId(product1);
            allDetails += product.productInfoFor() + "\n";
        }
        return allDetails.substring(0,allDetails.length()-1);
    }

    public void makeProductsViewForm() {
//        String productsInviewForm
        for (Product product : allProducts) {

        }
    }
    public Product findProductAfterFilter(String productID){
        allProductsAfterFilter=allProducts;
        filterProducts();
        for (Product product : allProductsAfterFilter) {
            if(product.getProductId().equals(productID)){
                return product;
            }
        }
        return null;
    }


    public void showAllBuyersForThisProduct(String productId){
       Seller seller=(Seller)ClientController.getInstance().getCurrentUser();
        if (seller.productExists(productId)) {
            System.out.println("There Is No Product With This Id For This Seller.");
        }else if (seller.getProductByID(productId).getAllBuyers() == null) {
            System.out.println("There Is No Buyer For This Product");
        }else {
            for (Customer buyer : seller.getProductByID(productId).getAllBuyers()) {
                System.out.println(buyer.viewPersonalInfo());
                System.out.println("\n\n");
            }
        }
    }
}
