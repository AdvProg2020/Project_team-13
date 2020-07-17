package Controller.Server;

import Models.DiscountCode;
import Models.Offer;
import Models.Product.Category;
import Models.Product.Product;
import Models.Request;
import Models.UserAccount.Customer;
import Models.UserAccount.Manager;
import Models.UserAccount.Seller;
import Models.UserAccount.Supporter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DataBase {
    private static DataBase dataBase;
    private String lastProductId;

    private DataBase() {

    }

    public static DataBase getInstance() {
        if (dataBase == null) {
            synchronized (DataBase.class) {
                if (dataBase == null) {
                    dataBase = new DataBase();
                }
            }
        }
        return dataBase;
    }

    public synchronized void updateAllCustomers(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allCustomers.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public synchronized void updateAllSellers(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allSellers.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public synchronized void updateAllManagers(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allManagers.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public void updateAllSupporter(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allSupporter.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public void setLastAuctionIdFromDataBase() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("lastAuctionId.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String lastAuctionId = br.readLine().trim();
            AuctionCenter.getInstance().setLastAuctionId(lastAuctionId);
            br.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized void setLastProductIdFromDataBase() {
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
            System.out.println(e.getMessage());
        }
    }

    public synchronized void setAllCategoriesFormDataBase() {
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
            if (allCategories == null) {
                allCategories = new ArrayList<>();
            }
            int i = 0;
            for (Category category : allCategories) {
                if (category.getName().equals("File")) {
                    i++;
                    break;
                }
            }
            if (i == 0) {
                HashMap<String, ArrayList<String>> features = new HashMap<>();
                ArrayList<String> modes = new ArrayList<>();
                modes.add("TextFile");
                modes.add("ImageFile");
                modes.add("VideoFile");
                modes.add("Other");
                features.put("Format", modes);
                allCategories.add(new Category("File", features));
            }
            CategoryCenter.getIncstance().setAllCategories(allCategories, true);
            br.close();
            fileReader.close();
        } catch (IOException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized void updateAllCategories(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allCategories.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized void setAllProductsFormDataBase() {
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

    public synchronized void updateAllProducts(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allProducts.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public synchronized void replaceProductId(String productId) {
        try {
            FileWriter fileWriter = new FileWriter("lastProductId.txt");
            fileWriter.write(productId);
            fileWriter.close();
        } catch (IOException e) {
        }
    }


    public synchronized void replaceAuctionId(String auctionId) {
        try {
            FileWriter fileWriter = new FileWriter("lastAuctionId.txt");
            fileWriter.write(auctionId);
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public synchronized void replaceLogId(String logId) {
        try {
            FileWriter fileWriter = new FileWriter("lastLogId.txt");
            fileWriter.write(logId);
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public synchronized void setAllUsersListFromDateBase() {
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
        try {
            fileReader = new FileReader("allSupporter.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        br = new BufferedReader(fileReader);
        try {
            String json;
            while ((json = br.readLine()) != null) {
                Gson gson = new Gson();
                Type userListType = new TypeToken<ArrayList<Supporter>>() {
                }.getType();
                ArrayList<Supporter> allUsers = gson.fromJson(json, userListType);
                UserCenter.getIncstance().setAllSupporter(allUsers);
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

    public synchronized void updateAllRequests(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allRequests.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public synchronized void updateAllDiscountCode(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allDiscountCodes.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public synchronized void setAllRequestsListFromDateBase() {
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

    public synchronized void setAllDiscountCodesListFromDateBase() {
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

    public synchronized void replaceRequestId(String lastRequestId) {
        try {
            FileWriter fileWriter = new FileWriter("lastRequestId.txt");
            fileWriter.write(lastRequestId);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public synchronized void replaceDiscountCodeId(String lastDiscountCodeId) {
        try {
            FileWriter fileWriter = new FileWriter("lastDiscountCodeId.txt");
            fileWriter.write(lastDiscountCodeId);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public synchronized void setLastRequestId() {
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

    public synchronized void setLastLogId() {
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

    public synchronized void setLastDiscountCodeId() {
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

    public void getAllUsersListFromDateBase(DataOutputStream dataOutputStream) {
        System.out.println("yes");
        FileReader fileReader = null;
        BufferedReader br;
        String allJson = "";
        try {
            fileReader = new FileReader("allCustomers.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        br = new BufferedReader(fileReader);
        try {
            String json;
            int i=0;
            while ((json = br.readLine()) != null) {
                i++;
                System.out.println("11111");
                if (!json.isEmpty()) {
                    allJson += json + "&";
                }else {
                    allJson += "[]" + "&";
                }
            }
            if(i==0) {
                allJson += "[]" + "&";
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
            int i=0;
            while ((json = br.readLine()) != null) {
                i++;
                System.out.println("22222");

                if (!json.isEmpty()) {
                    allJson += json + "&";
                }else {
                    allJson += "[]" + "&";
                }            }
            if(i==0) {
                allJson += "[]" + "&";
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
            int i=0;
            while ((json = br.readLine()) != null) {
                i++;
                System.out.println("33333");
                if (!json.isEmpty()) {
                    allJson += json;
                }else {
                    allJson += "[]";
                }
            }
            if(i==0) {
                allJson += "[]";
            }
            ServerController.getInstance().sendMessageToClient("@allUsers@" + allJson, dataOutputStream);
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

    public void getAllOffersFromDataBase(DataOutputStream dataOutputStream) {
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
                ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("getAllOffers", json), dataOutputStream);
            } else {
                ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("Error", "There is no Product"), dataOutputStream);
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

    public void getAllProductsFromDataBase(DataOutputStream dataOutputStream) {
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
                ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("getAllProductsForManager", json), dataOutputStream);
            } else {
                ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("Error", "There is no Product"), dataOutputStream);
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

    public synchronized void setLastOfferIdFromDataBase() {
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

    public synchronized void replaceOfferId(String lastOfferId) {
        try {
            FileWriter fileWriter = new FileWriter("lastOfferId.txt");
            fileWriter.write(lastOfferId);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public synchronized void updateAllOffers(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allOffers.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void setAllOffersFromDatabase() {
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
