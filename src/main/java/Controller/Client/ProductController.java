package Controller.Client;

import Models.Comment;
import Models.Product.Category;
import Models.Product.Product;
import Models.Product.ProductStatus;
import Models.Score;
import Models.UserAccount.Seller;
import View.MessageKind;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

public class ProductController {
    private ArrayList<Product> allProducts, allProductsAfterFilter, allProductsAfterSort;
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
        return productController;
    }

    public Product getProductWithId(String productId) {
        for (Product product : allProducts) {
            if (product.getProductId().equals(productId)) {
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

    public void addProduct(Product product) {
        Gson gson = new Gson();
        String product0 = gson.toJson(product);
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("AddProduct", product0));
    }

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
            //      ClientController.getInstance().getCurrentMenu().showMessage(product.viewProduct());
        }
    }

    public void showProductsAfterFilterAndSort() {
        filterProducts();
        sortProducts();
        ArrayList<Product> allProducts = new ArrayList<>(allProductsAfterSort);
        String productsInViewFormat = "";
        if (allProductsAfterFilter != null && !allProductsAfterFilter.isEmpty()) {
            for (Product product : allProducts) {
                productsInViewFormat += "ProductID: " + product.getProductId() + "\t" + "Product name: " + product.getProductName() + "\t"
                        + "ProductCost: " + product.getProductCost() + "\t"
                        + "Product Status: " + product.getProductStatus().toString() + "\n";
            }
            if (allProducts != null && !allProducts.isEmpty()) {
                //          ClientController.getInstance().getCurrentMenu().showMessage(productsInViewFormat.substring(0, productsInViewFormat.length() - 1));
            }
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

    public void editProduct(Product product) {
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("editProduct", new Gson().toJson(product)));
    }

    private void filterByPrice() {
        if (isPriceFilterActive) {
            List<Product> lst = allProductsAfterFilter;
            Product[] productsAfterPriceFilter =
                    lst.stream().filter(e -> e.getProductCost() <= max && e.getProductCost() >= min).toArray(Product[]::new);
            allProductsAfterFilter = new ArrayList<>(Arrays.asList(productsAfterPriceFilter));
        }

    }

    private void filterByBrand() {
        if (allBrandsToFilter != null && !allBrandsToFilter.isEmpty()) {
            List<Product> lst = allProductsAfterFilter;
            Product[] productsAfterPriceFilter =
                    lst.stream().filter(e -> allBrandsToFilter.contains(e.getProductCompany())).toArray(Product[]::new);
            allProductsAfterFilter = new ArrayList<>(Arrays.asList(productsAfterPriceFilter));
        }
    }

    private void filterBySeller() {
        if (allSellersToFilter != null && !allSellersToFilter.isEmpty()) {
            List<Product> lst = allProductsAfterFilter;
            Product[] productsAfterPriceFilter =
                    lst.stream().filter(e -> allSellersToFilter.contains(e.getSeller())).toArray(Product[]::new);
            allProductsAfterFilter = new ArrayList<>(Arrays.asList(productsAfterPriceFilter));
        }
    }

    private void filterByProductStatus() {
        if (allProductStatusToFilter != null && !allProductStatusToFilter.isEmpty()) {
            List<Product> lst = allProductsAfterFilter;
            Product[] productsAfterPriceFilter =
                    lst.stream().filter(e -> allProductStatusToFilter.contains(e.getProductStatus())).toArray(Product[]::new);
            allProductsAfterFilter = new ArrayList<>(Arrays.asList(productsAfterPriceFilter));
        }
    }

    private void filterByName() {
        if (isNameFilterActive && nameToFilter != null) {
            List<Product> lst = allProductsAfterFilter;
            Product[] productsAfterPriceFilter =
                    lst.stream().filter(e -> e.getProductName().toLowerCase().contains(nameToFilter.toLowerCase())).toArray(Product[]::new);
            allProductsAfterFilter = new ArrayList<>(Arrays.asList(productsAfterPriceFilter));
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
        if (allProductsAfterFilter != null) {
            allProductsAfterSort = new ArrayList<>(allProductsAfterFilter);
            if (isSortActivated) {
                if (currentSort.equals("newest")) {
                    if (!kindOfSort) {
                        Collections.sort(allProductsAfterSort, (o1, o2) -> {
                            if (kindOfSort) {
                                return (o1.getProductId().compareTo(o2.getProductId()));
                            } else {
                                return (o2.getProductId().compareTo(o1.getProductId()));
                            }
                        });
                    }
                } else if (currentSort.equals("price")) {
                    Collections.sort(allProductsAfterSort, (o1, o2) -> {
                        if (kindOfSort) {
                            return (int) (o1.getProductCost() - o2.getProductCost());
                        } else {
                            return (int) (o2.getProductCost() - o1.getProductCost());
                        }
                    });
                } else if (currentSort.equals("score")) {

                    Collections.sort(allProductsAfterSort, new Comparator<Product>() {
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
        if(ClientController.getInstance().getCurrentUser() instanceof Seller) {
            ((Seller)ClientController.getInstance().getCurrentUser()).removeProduct(productId);
        }
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
        if (allProducts != null) {
            StringBuilder allDetails = new StringBuilder();
            for (String product1 : allProducts) {
                Product product = getProductWithId(product1);
                if (product != null) {
                    allDetails.append(product.productInfoFor()).append("\n");
                }
            }
            if (allDetails.length() > 0)
                return allDetails.substring(0, allDetails.length() - 1);
        }
        return "";
    }

    public Product findProductAfterFilter(String productID) {
        allProductsAfterFilter = new ArrayList<>(allProducts);
        filterProducts();
        for (Product product : allProductsAfterFilter) {
            if (product.getProductId().equals(productID)) {
                return product;
            }
        }
        return null;
    }

    public Product findProductAfterFilterInOffer(String productID) {
        allProductsAfterFilter = allProducts;
        filterProducts();
        for (Product product : allProductsAfterFilter) {
            if (product.getProductId().equals(productID) && product.getOffer() != null) {
                return product;
            } else if (product.getProductId().equals(productID) && product.getOffer() == null) {
                return null;
            }
        }
        return null;
    }

    public void addComment(Comment comment) {
        ClientController.getInstance().sendMessageToServer("@addComment@" + new Gson().toJson(comment));
    }

    public void showOffedProductsAfterFilterAndSort() {
        filterProducts();
        sortProducts();
        ArrayList<Product> allProducts = new ArrayList<>();
        String productsInViewFormat = "";
        for (Product product : allProductsAfterSort) {
            if (product.getOffer() != null) {
                allProducts.add(product);
            }
        }
        for (Product product : allProducts) {
            productsInViewFormat += "ProductID: " + product.getProductId() + "\t" + "Product name: " + product.getProductName() + "\t"
                    + "ProductCost before Off: " + product.getProductCost() + "\t"
                    + "ProductCost after Off: " + product.getCostAfterOff() + "\t"
                    + "Product Status: " + product.getProductStatus().toString() + "\n";
        }
        if (allProducts != null && !allProducts.isEmpty()) {
            //      ClientController.getInstance().getCurrentMenu().showMessage(productsInViewFormat.substring(0, productsInViewFormat.length() - 1));
        }

    }

    public void rating(String productID, int rate) {
        Product product = ((Customer) ClientController.getInstance().getCurrentUser()).findProductWithId(productID);
        if (product != null) {
            Score score = new Score(ClientController.getInstance().getCurrentUser().getUsername(), productID, rate);
            product.addScore(score);
            ClientController.getInstance().sendMessageToServer("@rate@" + new Gson().toJson(score));
        } else {
            ClientController.getInstance().getCurrentMenu().showMessage("there is no products with this id", MessageKind.ErrorWithoutBack);
        }
    }

    public void compareWithProduct(String productId) {
        if (getProductWithId(productId) == null) {
            ClientController.getInstance().getCurrentMenu().showMessage("there is no product with this id", MessageKind.ErrorWithoutBack);
        } else {
            Product product = ClientController.getInstance().getCurrentProduct();
            Product compareProduct = getProductWithId(productId);
            String attributes = "";
            attributes += "Product Ids:\t" + compareProduct.getProductId() + "\t\t" + product.getProductId() + "\n";
            attributes += "Product Names:\t" + compareProduct.getProductName() + "\t\t" + product.getProductName() + "\n";
            attributes += "Product Category:\t" + compareProduct.getProductsCategory() + "\t\t" + product.getProductsCategory() + "\n";
            attributes += "Product Sellers:\t" + compareProduct.getSeller() + "\t\t" + product.getSeller() + "\n";
            attributes += "Product Companies:\t" + compareProduct.getProductCompany() + "\t\t" + product.getProductCompany() + "\n";
            attributes += "Product Costs:\t" + compareProduct.getProductCost() + "\t\t" + product.getProductCost() + "\n";
            attributes += "Product Costs After Off:\t" + compareProduct.getCostAfterOff() + "\t\t" + product.getCostAfterOff() + "\n";
            attributes += "Product Descriptions:\t" + compareProduct.getDescription() + "\t\t" + product.getDescription() + "\n";
            //    ClientController.getInstance().getCurrentMenu().showMessage(attributes);
        }
    }
}
