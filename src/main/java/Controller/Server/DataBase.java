package Controller.Server;

import Models.DiscountCode;
import Models.Offer;
import Models.Product.Category;
import Models.Product.Product;
import Models.Request;
import Models.UserAccount.Customer;
import Models.UserAccount.Manager;
import Models.UserAccount.Seller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

public class DataBase {
    private static DataBase dataBase;
    private String lastProductId;

    private DataBase() {

    }

    public static DataBase getInstance() {
        if (dataBase == null) {
            dataBase = new DataBase();
        }
        return dataBase;
    }

    public void updateAllCustomers(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allCustomers.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public void updateAllSellers(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allSellers.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public void updateAllManagers(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allManagers.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public void setLastProductIdFromDataBase() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("lastProductId.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String lastProductId = br.readLine().trim();
            ProductCenter.getInstance().setLastProductId(lastProductId);
            br.close();
            fileReader.close();
        } catch (IOException e) {

        }
    }

    public void setAllCategoriesFormDataBase() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("allCategories.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String allCategoriesInGsonForm = br.readLine().trim();
            Gson gson = new Gson();
            ArrayList<Category> allCategories = new ArrayList<>();
            Type categoryListType = new TypeToken<ArrayList<Category>>() {
            }.getType();
            allCategories = gson.fromJson(allCategoriesInGsonForm, categoryListType);
            CategoryCenter.getIncstance().setAllCategories(allCategories, true);
            br.close();
            fileReader.close();
        } catch (IOException | NullPointerException e) {
        }
    }

    public void updateAllCategories(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allCategories.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public void setAllProductsFormDataBase() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("allProducts.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String allProductsInGsonForm = br.readLine().trim();
            Gson gson = new Gson();
            ArrayList<Product> allProducts = new ArrayList<>();
            Type productListType = new TypeToken<ArrayList<Product>>() {
            }.getType();
            allProducts = gson.fromJson(allProductsInGsonForm, productListType);
            ProductCenter.getInstance().setAllProducts(allProducts);
            br.close();
            fileReader.close();
        } catch (IOException | NullPointerException ignored) {
        }
    }

    public void updateAllProducts(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allProducts.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public void replaceProductId(String productId) {
        try {
            FileWriter fileWriter = new FileWriter("lastProductId.txt");
            fileWriter.write(productId);
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public void replaceLogId(String logId) {
        try {
            FileWriter fileWriter = new FileWriter("lastLogId.txt");
            fileWriter.write(logId);
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public void setAllUsersListFromDateBase() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("allCustomers.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String json;
            while ((json = br.readLine()) != null) {
                Gson gson = new Gson();
                Type userListType = new TypeToken<ArrayList<Customer>>() {
                }.getType();
                ArrayList<Customer> allUsers = gson.fromJson(json, userListType);
                UserCenter.getIncstance().setAllCustomer(allUsers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileReader = new FileReader("allSellers.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        br = new BufferedReader(fileReader);
        try {
            String json;
            while ((json = br.readLine()) != null) {
                Gson gson = new Gson();
                Type userListType = new TypeToken<ArrayList<Seller>>() {
                }.getType();
                ArrayList<Seller> allUsers = gson.fromJson(json, userListType);
                UserCenter.getIncstance().setAllSeller(allUsers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileReader = new FileReader("allManagers.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        br = new BufferedReader(fileReader);
        try {
            String json;
            while ((json = br.readLine()) != null) {
                Gson gson = new Gson();
                Type userListType = new TypeToken<ArrayList<Manager>>() {
                }.getType();
                ArrayList<Manager> allUsers = gson.fromJson(json, userListType);
                UserCenter.getIncstance().setAllManager(allUsers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateAllRequests(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allRequests.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public void updateAllDiscountCode(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allDiscountCodes.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public void setAllRequestsListFromDateBase() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("allRequests.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String json;
            while ((json = br.readLine()) != null) {
                Gson gson = new Gson();
                Type requestListType = new TypeToken<ArrayList<Request>>() {
                }.getType();
                ArrayList<Request> allRequests = gson.fromJson(json, requestListType);
                RequestCenter.getIncstance().setAllRequests(allRequests);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setAllDiscountCodesListFromDateBase() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("allDiscountCodes.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String json;
            while ((json = br.readLine()) != null) {
                Gson gson = new Gson();
                Type discountCodeListType = new TypeToken<ArrayList<DiscountCode>>() {
                }.getType();
                ArrayList<DiscountCode> allDiscountCodes = gson.fromJson(json, discountCodeListType);
                DiscountCodeCenter.getIncstance().setAllDiscountCodes(allDiscountCodes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void replaceRequestId(String lastRequestId) {
        try {
            FileWriter fileWriter = new FileWriter("lastRequestId.txt");
            fileWriter.write(lastRequestId);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public void replaceDiscountCodeId(String lastDiscountCodeId) {
        try {
            FileWriter fileWriter = new FileWriter("lastDiscountCodeId.txt");
            fileWriter.write(lastDiscountCodeId);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public void setLastRequestId() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("lastRequestID.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String lastRequestId;
            while ((lastRequestId = br.readLine()) != null) {
                RequestCenter.getIncstance().setLastRequestID(lastRequestId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLastLogId() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("lastLogID.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String lastLogId;
            while ((lastLogId = br.readLine()) != null) {
                CartCenter.getInstance().setLastLogId(lastLogId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLastDiscountCodeId() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("lastDiscountCodeID.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String lastDiscountCode;
            while ((lastDiscountCode = br.readLine()) != null) {
                DiscountCodeCenter.getIncstance().setLastDiscountCodeID(lastDiscountCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getAllUsersListFromDateBase() {
        FileReader fileReader = null;
        BufferedReader br;
        try {
            fileReader = new FileReader("allCustomers.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        br = new BufferedReader(fileReader);
        try {
            String json;
            while ((json = br.readLine()) != null) {
                ServerController.getInstance().sendMessageToClient("@allCustomers@" + json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileReader = new FileReader("allSellers.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        br = new BufferedReader(fileReader);
        try {
            String json;
            while ((json = br.readLine()) != null) {
                ServerController.getInstance().sendMessageToClient("@allSellers@" + json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileReader = new FileReader("allManagers.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        br = new BufferedReader(fileReader);
        try {
            String json;
            while ((json = br.readLine()) != null) {
                ServerController.getInstance().sendMessageToClient("@allManagers@" + json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getAllOffersFromDataBase() {
        FileReader fileReader = null;
        Scanner scanner;
        try {
            fileReader = new FileReader("allOffers.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner = new Scanner(fileReader);
        try {
            String json = null;
            while (scanner.hasNextLine()) {
                json = scanner.nextLine();
            }
            if (json != null) {
                ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("getAllOffers", json));
            } else {
                ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("Error", "There is no Product"));
            }
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            scanner.close();
        }
    }

    public void getAllProductsFromDataBase() {
        FileReader fileReader = null;
        Scanner scanner;
        try {
            fileReader = new FileReader("allProducts.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner = new Scanner(fileReader);
        try {
            String json = null;
            while (scanner.hasNextLine()) {
                json = scanner.nextLine();
            }
            if (json != null) {
                ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("getAllProductsForManager", json));
            } else {
                ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("Error", "There is no Product"));
            }
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            scanner.close();
        }
    }

    public void setLastOfferIdFromDataBase() {
        FileReader fileReader = null;
        Scanner scanner = null;
        try {
            fileReader = new FileReader("lastOfferId.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            scanner = new Scanner(fileReader);
        } catch (NullPointerException e) {
            e.getCause();
        }
        try {
            String lastOfferId;
            while (scanner.hasNextLine()) {
                lastOfferId = scanner.nextLine();
                OffCenter.getInstance().setLastOffId(lastOfferId);
            }
        } catch (NullPointerException nullPointer) {
            nullPointer.getCause();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException error) {
                error.getCause();
            }
            try {
                scanner.close();
            } catch (NullPointerException error2) {
                error2.getCause();
            }
        }

    }

    public void replaceOfferId(String lastOfferId) {
        try {
            FileWriter fileWriter = new FileWriter("lastOfferId.txt");
            fileWriter.write(lastOfferId);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public void updateAllOffers(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allOffers.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAllOffersFromDatabase() {
        FileReader fileReader = null;
        Scanner scanner = null;
        try {
            fileReader = new FileReader("allOffers.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            scanner = new Scanner(fileReader);
        } catch (NullPointerException nullPointer) {
            nullPointer.getCause();
        }
        try {
            String json;
            while (scanner.hasNextLine()) {
                String read = scanner.nextLine();
                Gson gson = new Gson();
                Type allOffersList = new TypeToken<ArrayList<Offer>>() {
                }.getType();
                ArrayList<Offer> allOffers = gson.fromJson(read, allOffersList);
                OffCenter.getInstance().setAllOffers(allOffers);
            }
        } catch (NullPointerException e) {
            e.getCause();
        } finally {
            try {
                fileReader.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
            try {
                scanner.close();
            } catch (NullPointerException e) {
                e.getCause();
            }
        }
    }
}
