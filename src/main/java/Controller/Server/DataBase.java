package Controller.Server;

import Models.*;
import Models.Product.Category;
import Models.Product.Product;
import Models.Product.ProductStatus;
import Models.UserAccount.Customer;
import Models.UserAccount.Manager;
import Models.UserAccount.Seller;
import Models.UserAccount.Supporter;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        Type type = new TypeToken<ArrayList<Customer>>(){}.getType();
        ArrayList<Customer> allCustomer = new Gson().fromJson(json, type);
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM allCustomers");
            preparedStatement.execute();
            PreparedStatement preparedStatementLog = connection.prepareStatement("DELETE FROM allLogs");
            preparedStatementLog.execute();
            PreparedStatement preparedStatementForInsertCustomer = connection.prepareStatement("INSERT INTO allCustomers (userName, passWord, firstName, lastName, email, phoneNumber, TYPETYPE, credit, allDiscountCodes, historyOfTransactions, totalBuyAmount)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement prepareForLog = connection.prepareStatement("INSERT INTO allLogs (logId, Datee, price, receiverUserName, otherSideUserName, allProducts, receivingStatus, reduceCostAfterOff)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            for (Customer customer : allCustomer) {
                preparedStatementForInsertCustomer.setString(1, customer.getUsername());
                preparedStatementForInsertCustomer.setString(2, customer.getPassword());
                preparedStatementForInsertCustomer.setString(3, customer.getFirstName());
                preparedStatementForInsertCustomer.setString(4, customer.getLastName());
                preparedStatementForInsertCustomer.setString(5, customer.getEmail());
                preparedStatementForInsertCustomer.setString(6, customer.getPhoneNumber());
                preparedStatementForInsertCustomer.setString(7, customer.getType());
                preparedStatementForInsertCustomer.setString(8, String.valueOf(customer.getCredit()));
                preparedStatementForInsertCustomer.setString(9, new Gson().toJson(customer.getAllDiscountCodes()));
                preparedStatementForInsertCustomer.setString(10, new Gson().toJson(customer.getHistoryOfTransaction()));
                preparedStatementForInsertCustomer.setString(11, String.valueOf(customer.getTotalBuyAmount()));
                preparedStatementForInsertCustomer.executeUpdate();
                for (Log log : customer.getHistoryOfTransaction()) {
                    prepareForLog.setString(1, log.getId());
                    prepareForLog.setString(2, String.valueOf(log.getDate()));
                    prepareForLog.setString(3, String.valueOf(log.getPrice()));
                    prepareForLog.setString(4, log.getReceiverUserName());
                    prepareForLog.setString(5, log.getOtherSideUserName());
                    prepareForLog.setString(6, new Gson().toJson(log.getAllProducts()));
                    prepareForLog.setString(7, String.valueOf(log.getReceivingStatus()));
                    prepareForLog.setString(8, String.valueOf(log.getReduceCostForOffs()));
                    prepareForLog.executeUpdate();
                }
            }
            prepareForLog.close();
            preparedStatement.close();
            preparedStatementForInsertCustomer.close();
            preparedStatementLog.close();
            connection.close();
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public synchronized void setWagePercent() {
        try {
            FileWriter fileWriter = new FileWriter("wage.txt");
            fileWriter.write(String.valueOf(CartCenter.getInstance().getWage()));
            fileWriter.close();
        } catch (Exception e) {
        }
    }
    public synchronized void setAtLeastCredit() {
        try {
            FileWriter fileWriter = new FileWriter("atLeastCredit.txt");
            fileWriter.write(String.valueOf(CartCenter.getInstance().getAtLeastAmount()));
            fileWriter.close();
        } catch (Exception e) {
        }
    }
    //
    public synchronized void updateAllSellers(String json) {
        Type type = new TypeToken<ArrayList<Seller>>(){}.getType();
        ArrayList<Seller> allSeller = new Gson().fromJson(json, type);
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM allSellers");
            preparedStatement.execute();
            PreparedStatement preparedStatementLog = connection.prepareStatement("DELETE FROM allLogs");
            preparedStatementLog.execute();
            PreparedStatement preparedStatementForInsertCustomer = connection.prepareStatement("INSERT INTO allSellers (userName, passWord, firstName, lastName, email, phoneNumber, TYPETYPE, credit, allDiscountCodes, historyOfTransactions, companyName, isAccepted, allProducts, allOffers, allRequests, commercializedProduct, auction)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement prepareForLog = connection.prepareStatement("INSERT INTO allLogs (logId, Datee, price, receiverUserName, otherSideUserName, allProducts, receivingStatus, reduceCostAfterOff)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            for (Seller seller : allSeller) {
                preparedStatementForInsertCustomer.setString(1, seller.getUsername());
                preparedStatementForInsertCustomer.setString(2, seller.getPassword());
                preparedStatementForInsertCustomer.setString(3, seller.getFirstName());
                preparedStatementForInsertCustomer.setString(4, seller.getLastName());
                preparedStatementForInsertCustomer.setString(5, seller.getEmail());
                preparedStatementForInsertCustomer.setString(6, seller.getPhoneNumber());
                preparedStatementForInsertCustomer.setString(7, seller.getType());
                preparedStatementForInsertCustomer.setString(8, String.valueOf(seller.getCredit()));
                preparedStatementForInsertCustomer.setString(9, new Gson().toJson(seller.getAllDiscountCodes()));
                preparedStatementForInsertCustomer.setString(10, new Gson().toJson(seller.getHistoryOfTransaction()));
                preparedStatementForInsertCustomer.setString(11, seller.getCompanyName());
                preparedStatementForInsertCustomer.setString(12, String.valueOf(seller.isAccepted()));
                preparedStatementForInsertCustomer.setString(13, new Gson().toJson(seller.getAllProducts()));
                preparedStatementForInsertCustomer.setString(14, new Gson().toJson(seller.getAllOffer()));
                preparedStatementForInsertCustomer.setString(15, new Gson().toJson(seller.getAllRequests()));
                preparedStatementForInsertCustomer.setString(16, seller.getCommercializedProduct());
                preparedStatementForInsertCustomer.setString(17, new Gson().toJson(seller.getAuction()));
                preparedStatementForInsertCustomer.executeUpdate();
                for (Log log : seller.getHistoryOfTransaction()) {
                    prepareForLog.setString(1, log.getId());
                    prepareForLog.setString(2, String.valueOf(log.getDate()));
                    prepareForLog.setString(3, String.valueOf(log.getPrice()));
                    prepareForLog.setString(4, log.getReceiverUserName());
                    prepareForLog.setString(5, log.getOtherSideUserName());
                    prepareForLog.setString(6, new Gson().toJson(log.getAllProducts()));
                    prepareForLog.setString(7, String.valueOf(log.getReceivingStatus()));
                    prepareForLog.setString(8, String.valueOf(log.getReduceCostForOffs()));
                    prepareForLog.executeUpdate();
                }
            }
            prepareForLog.close();
            preparedStatement.close();
            preparedStatementForInsertCustomer.close();
            preparedStatementLog.close();
            connection.close();
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public synchronized void updateAllManagers(String json) {
        Type type = new TypeToken<ArrayList<Manager>>(){}.getType();
        ArrayList<Manager> allManagers = new Gson().fromJson(json, type);
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM allManagers");
            preparedStatement.execute();
            PreparedStatement preparedStatementForInsertCustomer = connection.prepareStatement("INSERT INTO allManagers (userName, passWord, firstName, lastName, email, phoneNumber, TYPETYPE, credit, allDiscountCodes, historyOfTransactions)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (Manager manager : allManagers) {
                preparedStatementForInsertCustomer.setString(1, manager.getUsername());
                preparedStatementForInsertCustomer.setString(2, manager.getPassword());
                preparedStatementForInsertCustomer.setString(3, manager.getFirstName());
                preparedStatementForInsertCustomer.setString(4, manager.getLastName());
                preparedStatementForInsertCustomer.setString(5, manager.getEmail());
                preparedStatementForInsertCustomer.setString(6, manager.getPhoneNumber());
                preparedStatementForInsertCustomer.setString(7, manager.getType());
                preparedStatementForInsertCustomer.setString(8, String.valueOf(manager.getCredit()));
                preparedStatementForInsertCustomer.setString(9, new Gson().toJson(manager.getAllDiscountCodes()));
                preparedStatementForInsertCustomer.setString(10, new Gson().toJson(manager.getHistoryOfTransaction()));
                preparedStatementForInsertCustomer.executeUpdate();
            }
            preparedStatement.close();
            preparedStatementForInsertCustomer.close();
            connection.close();
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public void updateAllSupporter(String json) {
        Type type = new TypeToken<ArrayList<Supporter>>(){}.getType();
        ArrayList<Supporter> allSupporters = new Gson().fromJson(json, type);
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM allSupporters");
            preparedStatement.execute();
            PreparedStatement preparedStatementForInsertCustomer = connection.prepareStatement("INSERT INTO allSupporters (userName, passWord, firstName, lastName, email, phoneNumber, TYPETYPE, credit, allDiscountCodes, historyOfTransactions, totalBuyAmount)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (Supporter supporter : allSupporters) {
                preparedStatementForInsertCustomer.setString(1, supporter.getUsername());
                preparedStatementForInsertCustomer.setString(2, supporter.getPassword());
                preparedStatementForInsertCustomer.setString(3, supporter.getFirstName());
                preparedStatementForInsertCustomer.setString(4, supporter.getLastName());
                preparedStatementForInsertCustomer.setString(5, supporter.getEmail());
                preparedStatementForInsertCustomer.setString(6, supporter.getPhoneNumber());
                preparedStatementForInsertCustomer.setString(7, supporter.getType());
                preparedStatementForInsertCustomer.setString(8, String.valueOf(supporter.getCredit()));
                preparedStatementForInsertCustomer.setString(9, new Gson().toJson(supporter.getAllDiscountCodes()));
                preparedStatementForInsertCustomer.setString(10, new Gson().toJson(supporter.getHistoryOfTransaction()));
                preparedStatementForInsertCustomer.executeUpdate();
            }
            preparedStatement.close();
            preparedStatementForInsertCustomer.close();
            connection.close();
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
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

    public void getWagePercent() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("wage.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String wage = br.readLine().trim();
            CartCenter.getInstance().setWage(Double.parseDouble(wage));
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

        //////
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM allCategories");
            int column = resultSet.getMetaData().getColumnCount();
            ArrayList<Category> allCategories = new ArrayList<>();
            while (resultSet.next()) {
                String category = "";
                category += resultSet.getString("name") + "&&";
                category += resultSet.getString("features") + "&&";
                category += resultSet.getString("allProducts");
                String[] data = category.split("&&");
                Type features = new TypeToken<HashMap<String, ArrayList<String>>>() {
                }.getType();
                Category category1 = new Category(data[0], new Gson().fromJson(data[1], features));
                if(!data[2].equals("null")){
                    Type allProductType = new TypeToken<HashMap<String, ArrayList<String>>>() {
                    }.getType();
                    category1.setAllProducts(new Gson().fromJson(data[2], allProductType));
                }
            }
            statement.close();
            connection.close();
            CategoryCenter.getIncstance().setAllCategories(allCategories, true);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        //
        //
        //
        //
        //
        //
        //
        //
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
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM allProducts");
            int column = resultSet.getMetaData().getColumnCount();
            ArrayList<Product> allProducts = new ArrayList<>();
            while (resultSet.next()) {
                String product = "";
                product += resultSet.getString("productId") + "&&";
                product += resultSet.getString("productStatus") + "&&";
                product += resultSet.getString("productName") + "&&";
                product += resultSet.getString("productCompany") + "&&";
                product += resultSet.getString("seller") + "&&";
                product += resultSet.getString("allScores") + "&&";
                product += resultSet.getString("productCost") + "&&";
                product += resultSet.getString("costAfterOff") + "&&";
                product += resultSet.getString("productsCategory") + "&&";
                product += resultSet.getString("description") + "&&";
                product += resultSet.getString("commentList") + "&&";
                product += resultSet.getString("numberOfAvailableProducts") + "&&";
                product += resultSet.getString("featuresOfCategroy") + "&&";
                product += resultSet.getString("allBuyers") + "&&";
                product += resultSet.getString("offers") + "&&";
                product += resultSet.getString("imagePath") + "&&";
                product += resultSet.getString("videoPath") + "&&";
                product += resultSet.getString("filePath") + "&&";
                product += resultSet.getString("existInOfferRegistered");
                String[] data = product.split("&&");
                Type features = new TypeToken<HashMap<String, String>>() {
                }.getType();
                Product product1 = new Product(data[3], data[0], data[2], new Gson().fromJson(data[4], Seller.class), Double.parseDouble(data[6]), data[8], data[9],
                        Integer.parseInt(data[11]), new Gson().fromJson(data[12], features));
                if(!data[1].equals("null")){
                  product1.setProductStatus(ProductStatus.valueOf(data[1]));
                }
                if(!data[5].equals("null")){
                  Type scoreType = new TypeToken<ArrayList<Score>>(){
                  }.getType();
                  product1.setAllScores(new Gson().fromJson(data[5], scoreType));
                }
                if(!data[6].equals("null")){
                  product1.setProductCost(Double.parseDouble(data[6]));
                }
                if(!data[7].equals("null")){
                  product1.setCostAfterOff(Double.parseDouble(data[7]));
                }
                if(!data[11].equals("null")){
                    Type commentType = new TypeToken<ArrayList<Comment>>(){
                    }.getType();
                  product1.setCommentList(new Gson().fromJson(data[11], commentType));
                }
                if(!data[14].equals("null")){
                    Type customer = new TypeToken<ArrayList<Customer>>(){
                    }.getType();
                    product1.setAllBuyers(new Gson().fromJson(data[14], customer));
                }
                if(!data[15].equals("null")){
                   Type offerType = new TypeToken<ArrayList<Offer>>(){
                   }.getType();
                   product1.setOffers(new Gson().fromJson(data[15], offerType));
                }
                if(!data[16].equals("null")){
                   product1.setImagePath(data[16]);
                }
                if(!data[17].equals("null")){
                   product1.setVideoPath(data[17]);
                }
                if(!data[18].equals("null")){
                  product1.setFilePath(data[18]);
                }
                if(!data[19].equals("null")){
                  product1.setExistInOfferRegistered(data[19].equals("true"));
                }
                allProducts.add(product1);
            }
            statement.close();
            connection.close();
            ProductCenter.getInstance().setAllProducts(allProducts);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public synchronized void updateAllProducts(String json) {
        Type type = new TypeToken<ArrayList<Product>>(){}.getType();
        ArrayList<Product> allSeller = new Gson().fromJson(json, type);
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM allProducts");
            preparedStatement.execute();
            PreparedStatement preparedStatementForInsertCustomer = connection.prepareStatement("INSERT INTO allProducts (productId, productStatus, productName, productCompany, seller, allScores, productCost, costAfterOff, productsCategory, description, commentList, numberOfAvailableProducts, featuresOfCategroy, allBuyers, offers, imagePath, videoPathو filePathو existInOfferRegistered)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (Product product : allSeller) {
                preparedStatementForInsertCustomer.setString(1, product.getProductId());
                preparedStatementForInsertCustomer.setString(2, String.valueOf(product.getProductStatus()));
                preparedStatementForInsertCustomer.setString(3, product.getProductName());
                preparedStatementForInsertCustomer.setString(4, product.getProductCompany());
                preparedStatementForInsertCustomer.setString(5, product.getSeller());
                preparedStatementForInsertCustomer.setString(6, new Gson().toJson(product.getAllScores()));
                preparedStatementForInsertCustomer.setString(7, String.valueOf(product.getProductCost()));
                preparedStatementForInsertCustomer.setString(8, String.valueOf(product.getCostAfterOff()));
                preparedStatementForInsertCustomer.setString(9, product.getProductsCategory());
                preparedStatementForInsertCustomer.setString(10, product.getDescription());
                preparedStatementForInsertCustomer.setString(11, new Gson().toJson(product.getCommentList()));
                preparedStatementForInsertCustomer.setString(12, String.valueOf(product.getNumberOfAvailableProducts()));
                preparedStatementForInsertCustomer.setString(13, new Gson().toJson(product.getFeaturesOfCategoryThatHas()));
                preparedStatementForInsertCustomer.setString(14, new Gson().toJson(product.getAllBuyers()));
                preparedStatementForInsertCustomer.setString(15, new Gson().toJson(product.getOffers()));
                preparedStatementForInsertCustomer.setString(16, product.getImagePath());
                preparedStatementForInsertCustomer.setString(17, product.getVideoPath());
                preparedStatementForInsertCustomer.setString(18, product.getFilePath());
                preparedStatementForInsertCustomer.setString(19, String.valueOf(product.isExistInOfferRegistered()));
                preparedStatementForInsertCustomer.executeUpdate();
            }
            preparedStatement.close();
            preparedStatementForInsertCustomer.close();
            connection.close();
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
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

    public void setAllUsersListFromDateBase() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM allCustomers");
            int column = resultSet.getMetaData().getColumnCount();
            ArrayList<Customer> allCustomers = new ArrayList<>();
            while (resultSet.next()) {
                String customer = "";
                customer += resultSet.getString("userName") + "&&";
                customer += resultSet.getString("passWord") + "&&";
                customer += resultSet.getString("firstName") + "&&";
                customer += resultSet.getString("lastName") + "&&";
                customer += resultSet.getString("email") + "&&";
                customer += resultSet.getString("phoneNumber") + "&&";
                customer += resultSet.getString("TypeType") + "&&";
                customer += resultSet.getString("credit") + "&&";
                customer += resultSet.getString("allDiscountCodes") + "&&";
                customer += resultSet.getString("historyOfTransactions") + "&&";
                customer += resultSet.getString("totalBuyAmount");
                String[] data = customer.split("&&");
                Customer customer1 = new Customer(data[0], data[1], data[2], data[3], data[4], data[5], data[7].equals("null") ? 0 : Double.parseDouble(data[7]));
                if(!data[8].equals("null")){
                    Type discountType = new TypeToken<ArrayList<DiscountCode>>(){
                    }.getType();
                    customer1.setAllDiscountCodes(new Gson().fromJson(data[8], discountType));
                }
                if(!data[9].equals("null")){
                    Type logType = new TypeToken<ArrayList<Log>>(){
                    }.getType();
                    customer1.setHistoryOfTransaction(new Gson().fromJson(data[9], logType));
                }
                if(!data[10].equals("null")){
                    customer1.setTotalBuyAmount(Double.parseDouble(data[10]));
                }
                allCustomers.add(customer1);
            }
            UserCenter.getIncstance().setAllCustomer(allCustomers);
            //
            //
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM allSellers");
            int column1 = resultSet1.getMetaData().getColumnCount();
            ArrayList<Seller> allSeller = new ArrayList<>();
            while (resultSet1.next()) {
                String seller = "";
                seller += resultSet1.getString("userName") + "&&";
                seller += resultSet1.getString("passWord") + "&&";
                seller += resultSet1.getString("firstName") + "&&";
                seller += resultSet1.getString("lastName") + "&&";
                seller += resultSet1.getString("email") + "&&";
                seller += resultSet1.getString("phoneNumber") + "&&";
                seller += resultSet1.getString("TypeType") + "&&";
                seller += resultSet1.getString("credit") + "&&";
                seller += resultSet1.getString("allDiscountCodes") + "&&";
                seller += resultSet1.getString("historyOfTransactions") + "&&";
                seller += resultSet1.getString("companyName") + "&&";
                seller += resultSet1.getString("isAccepted") + "&&";
                seller += resultSet1.getString("allProducts") + "&&";
                seller += resultSet1.getString("allRequests") + "&&";
                seller += resultSet1.getString("commercializedProduct") + "&&";
                seller += resultSet1.getString("auction");
                String[] data1 = seller.split("&&");
                Seller seller1 = new Seller(data1[0], data1[1], data1[2], data1[3], data1[4], data1[5], data1[7].equals("null") ? 0 : Double.parseDouble(data1[7]), data1[10], data1[11].equals("true"));
                if(!data1[12].equals("null")){
                  Type allProducts = new TypeToken<ArrayList<Product>>(){
                  }.getType();
                  seller1.setAllProducts(new Gson().fromJson(data1[12], allProducts));
                }
                if(!data1[13].equals("null")){
                    Type allOffers = new TypeToken<ArrayList<Offer>>(){
                    }.getType();
                    seller1.setAllOffer(new Gson().fromJson(data1[13], allOffers));
                }
                if(!data1[14].equals("null")){
                    Type allRequest = new TypeToken<ArrayList<Request>>(){
                    }.getType();
                    seller1.setAllRequests(new Gson().fromJson(data1[14], allRequest));
                }
                if(!data1[15].equals("null")){
                    seller1.setCommercializedProduct(data1[15]);
                }
                if(!data1[16].equals("null")){
                    seller1.setAuction(new Gson().fromJson(data1[16], Auction.class));
                }
                allSeller.add(seller1);
            }
            UserCenter.getIncstance().setAllSeller(allSeller);
            //
            //
            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM allManagers");
            int column2 = resultSet2.getMetaData().getColumnCount();
            ArrayList<Manager> allManagers = new ArrayList<>();
            while (resultSet2.next()) {
                String manager = "";
                manager += resultSet2.getString("userName") + "&&";
                manager += resultSet2.getString("passWord") + "&&";
                manager += resultSet2.getString("firstName") + "&&";
                manager += resultSet2.getString("lastName") + "&&";
                manager += resultSet2.getString("email") + "&&";
                manager += resultSet2.getString("phoneNumber") + "&&";
                manager += resultSet2.getString("TypeType") + "&&";
                manager += resultSet2.getString("credit") + "&&";
                manager += resultSet2.getString("allDiscountCodes") + "&&";
                manager += resultSet2.getString("historyOfTransactions");
                String[] data2 = manager.split("&&");
                Manager manager1 = new Manager(data2[0], data2[1], data2[2], data2[3], data2[4], data2[5], data2[7].equals("null") ? 0 : Double.parseDouble(data2[7]));
                if(!data2[8].equals("null")){
                    Type discountType = new TypeToken<ArrayList<DiscountCode>>(){
                    }.getType();
                    manager1.setAllDiscountCodes(new Gson().fromJson(data2[8], discountType));
                }
                if(!data2[9].equals("null")){
                    Type logType = new TypeToken<ArrayList<Log>>(){
                    }.getType();
                    manager1.setHistoryOfTransaction(new Gson().fromJson(data2[9], logType));
                }
                allManagers.add(manager1);
            }
            UserCenter.getIncstance().setAllManager(allManagers);
            //
            //
            Statement statement3 = connection.createStatement();
            ResultSet resultSet3 = statement3.executeQuery("SELECT * FROM allSupporters");
            int column3 = resultSet3.getMetaData().getColumnCount();
            ArrayList<Supporter> allSupporters = new ArrayList<>();
            while (resultSet3.next()) {
                String supporter = "";
                supporter += resultSet3.getString("userName") + "&&";
                supporter += resultSet3.getString("passWord") + "&&";
                supporter += resultSet3.getString("firstName") + "&&";
                supporter += resultSet3.getString("lastName") + "&&";
                supporter += resultSet3.getString("email") + "&&";
                supporter += resultSet3.getString("phoneNumber") + "&&";
                supporter += resultSet3.getString("TypeType") + "&&";
                supporter += resultSet3.getString("credit") + "&&";
                supporter += resultSet3.getString("allDiscountCodes") + "&&";
                supporter += resultSet3.getString("historyOfTransactions");
                String[] data4 = supporter.split("&&");
                Supporter supporter1 = new Supporter(data4[0], data4[1], data4[2], data4[3], data4[4], data4[5], data4[7].equals("null") ? 0 : Double.parseDouble(data4[7]));
                if(!data4[8].equals("null")){
                    Type discountType = new TypeToken<ArrayList<DiscountCode>>(){
                    }.getType();
                    supporter1.setAllDiscountCodes(new Gson().fromJson(data4[8], discountType));
                }
                if(!data4[9].equals("null")){
                    Type logType = new TypeToken<ArrayList<Log>>(){
                    }.getType();
                    supporter1.setHistoryOfTransaction(new Gson().fromJson(data4[9], logType));
                }
                allSupporters.add(supporter1);
            }
            UserCenter.getIncstance().setAllSupporter(allSupporters);
            //
            //
            //
            resultSet.close();
            resultSet1.close();
            resultSet3.close();
            statement.close();
            statement1.close();
            statement2.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
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
        ArrayList<Manager> allManagers = new ArrayList<>();
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ArrayList<Seller> allSeller = new ArrayList<>();
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM allCustomers");
            int column = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                String customer = "";
                customer += resultSet.getString("userName") + "&&";
                customer += resultSet.getString("passWord") + "&&";
                customer += resultSet.getString("firstName") + "&&";
                customer += resultSet.getString("lastName") + "&&";
                customer += resultSet.getString("email") + "&&";
                customer += resultSet.getString("phoneNumber") + "&&";
                customer += resultSet.getString("TypeType") + "&&";
                customer += resultSet.getString("credit") + "&&";
                customer += resultSet.getString("allDiscountCodes") + "&&";
                customer += resultSet.getString("historyOfTransactions") + "&&";
                customer += resultSet.getString("totalBuyAmount");
                String[] data = customer.split("&&");
                Customer customer1 = new Customer(data[0], data[1], data[2], data[3], data[4], data[5], data[7].equals("null") ? 0 : Double.parseDouble(data[7]));
                if(!data[8].equals("null")){
                    Type discountType = new TypeToken<ArrayList<DiscountCode>>(){
                    }.getType();
                    customer1.setAllDiscountCodes(new Gson().fromJson(data[8], discountType));
                }
                if(!data[9].equals("null")){
                    Type logType = new TypeToken<ArrayList<Log>>(){
                    }.getType();
                    customer1.setHistoryOfTransaction(new Gson().fromJson(data[9], logType));
                }
                if(!data[10].equals("null")){
                    customer1.setTotalBuyAmount(Double.parseDouble(data[10]));
                }
                allCustomers.add(customer1);
            }
            UserCenter.getIncstance().setAllCustomer(allCustomers);
            //
            //
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM allSellers");
            int column1 = resultSet1.getMetaData().getColumnCount();
            while (resultSet1.next()) {
                String seller = "";
                seller += resultSet1.getString("userName") + "&&";
                seller += resultSet1.getString("passWord") + "&&";
                seller += resultSet1.getString("firstName") + "&&";
                seller += resultSet1.getString("lastName") + "&&";
                seller += resultSet1.getString("email") + "&&";
                seller += resultSet1.getString("phoneNumber") + "&&";
                seller += resultSet1.getString("TypeType") + "&&";
                seller += resultSet1.getString("credit") + "&&";
                seller += resultSet1.getString("allDiscountCodes") + "&&";
                seller += resultSet1.getString("historyOfTransactions") + "&&";
                seller += resultSet1.getString("companyName") + "&&";
                seller += resultSet1.getString("isAccepted") + "&&";
                seller += resultSet1.getString("allProducts") + "&&";
                seller += resultSet1.getString("allRequests") + "&&";
                seller += resultSet1.getString("commercializedProduct") + "&&";
                seller += resultSet1.getString("auction");
                String[] data1 = seller.split("&&");
                Seller seller1 = new Seller(data1[0], data1[1], data1[2], data1[3], data1[4], data1[5], data1[7].equals("null") ? 0 : Double.parseDouble(data1[7]), data1[10], data1[11].equals("true"));
                if(!data1[12].equals("null")){
                    Type allProducts = new TypeToken<ArrayList<Product>>(){
                    }.getType();
                    seller1.setAllProducts(new Gson().fromJson(data1[12], allProducts));
                }
                if(!data1[13].equals("null")){
                    Type allOffers = new TypeToken<ArrayList<Offer>>(){
                    }.getType();
                    seller1.setAllOffer(new Gson().fromJson(data1[13], allOffers));
                }
                if(!data1[14].equals("null")){
                    Type allRequest = new TypeToken<ArrayList<Request>>(){
                    }.getType();
                    seller1.setAllRequests(new Gson().fromJson(data1[14], allRequest));
                }
                if(!data1[15].equals("null")){
                    seller1.setCommercializedProduct(data1[15]);
                }
                if(!data1[16].equals("null")){
                    seller1.setAuction(new Gson().fromJson(data1[16], Auction.class));
                }
                allSeller.add(seller1);
            }
            UserCenter.getIncstance().setAllSeller(allSeller);
            //
            //
            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM allManagers");
            int column2 = resultSet2.getMetaData().getColumnCount();
            while (resultSet2.next()) {
                String manager = "";
                manager += resultSet2.getString("userName") + "&&";
                manager += resultSet2.getString("passWord") + "&&";
                manager += resultSet2.getString("firstName") + "&&";
                manager += resultSet2.getString("lastName") + "&&";
                manager += resultSet2.getString("email") + "&&";
                manager += resultSet2.getString("phoneNumber") + "&&";
                manager += resultSet2.getString("TypeType") + "&&";
                manager += resultSet2.getString("credit") + "&&";
                manager += resultSet2.getString("allDiscountCodes") + "&&";
                manager += resultSet2.getString("historyOfTransactions");
                String[] data2 = manager.split("&&");
                Manager manager1 = new Manager(data2[0], data2[1], data2[2], data2[3], data2[4], data2[5], data2[7].equals("null") ? 0 : Double.parseDouble(data2[7]));
                if(!data2[8].equals("null")){
                    Type discountType = new TypeToken<ArrayList<DiscountCode>>(){
                    }.getType();
                    manager1.setAllDiscountCodes(new Gson().fromJson(data2[8], discountType));
                }
                if(!data2[9].equals("null")){
                    Type logType = new TypeToken<ArrayList<Log>>(){
                    }.getType();
                    manager1.setHistoryOfTransaction(new Gson().fromJson(data2[9], logType));
                }
                allManagers.add(manager1);
            }
            UserCenter.getIncstance().setAllManager(allManagers);
            //
            //
            Statement statement3 = connection.createStatement();
            ResultSet resultSet3 = statement3.executeQuery("SELECT * FROM allSupporters");
            int column3 = resultSet3.getMetaData().getColumnCount();
            ArrayList<Supporter> allSupporters = new ArrayList<>();
            while (resultSet3.next()) {
                String supporter = "";
                supporter += resultSet3.getString("userName") + "&&";
                supporter += resultSet3.getString("passWord") + "&&";
                supporter += resultSet3.getString("firstName") + "&&";
                supporter += resultSet3.getString("lastName") + "&&";
                supporter += resultSet3.getString("email") + "&&";
                supporter += resultSet3.getString("phoneNumber") + "&&";
                supporter += resultSet3.getString("TypeType") + "&&";
                supporter += resultSet3.getString("credit") + "&&";
                supporter += resultSet3.getString("allDiscountCodes") + "&&";
                supporter += resultSet3.getString("historyOfTransactions");
                String[] data4 = supporter.split("&&");
                Supporter supporter1 = new Supporter(data4[0], data4[1], data4[2], data4[3], data4[4], data4[5], data4[7].equals("null") ? 0 : Double.parseDouble(data4[7]));
                if(!data4[8].equals("null")){
                    Type discountType = new TypeToken<ArrayList<DiscountCode>>(){
                    }.getType();
                    supporter1.setAllDiscountCodes(new Gson().fromJson(data4[8], discountType));
                }
                if(!data4[9].equals("null")){
                    Type logType = new TypeToken<ArrayList<Log>>(){
                    }.getType();
                    supporter1.setHistoryOfTransaction(new Gson().fromJson(data4[9], logType));
                }
                allSupporters.add(supporter1);
            }
            UserCenter.getIncstance().setAllSupporter(allSupporters);
            //
            //
            //
            resultSet3.close();
            resultSet1.close();
            resultSet3.close();
            statement.close();
            statement1.close();
            statement2.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        String customersJson = new Gson().toJson(allCustomers);
        String sellersJson = new Gson().toJson(allSeller);
        String managerJson = new Gson().toJson(allManagers);
        String allJsons = customersJson + "&" + sellersJson + "&" + managerJson;
        ServerController.getInstance().sendMessageToClient("@allUsers@" + allJsons, dataOutputStream);
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
        ArrayList<Product> allProducts = new ArrayList<>();
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM allProducts");
            int column = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                String product = "";
                product += resultSet.getString("productId") + "&&";
                product += resultSet.getString("productStatus") + "&&";
                product += resultSet.getString("productName") + "&&";
                product += resultSet.getString("productCompany") + "&&";
                product += resultSet.getString("seller") + "&&";
                product += resultSet.getString("allScores") + "&&";
                product += resultSet.getString("productCost") + "&&";
                product += resultSet.getString("costAfterOff") + "&&";
                product += resultSet.getString("productsCategory") + "&&";
                product += resultSet.getString("description") + "&&";
                product += resultSet.getString("commentList") + "&&";
                product += resultSet.getString("numberOfAvailableProducts") + "&&";
                product += resultSet.getString("featuresOfCategory") + "&&";
                product += resultSet.getString("allBuyers") + "&&";
                product += resultSet.getString("offers") + "&&";
                product += resultSet.getString("imagePath") + "&&";
                product += resultSet.getString("videoPath") + "&&";
                product += resultSet.getString("filePath") + "&&";
                product += resultSet.getString("existInOfferRegistered");
                String[] data = product.split("&&");
                Type features = new TypeToken<HashMap<String, String>>() {
                }.getType();
                Product product1 = new Product(data[3], data[0], data[2], new Gson().fromJson(data[4], Seller.class), Double.parseDouble(data[6]), data[8], data[9],
                        Integer.parseInt(data[11]), new Gson().fromJson(data[12], features));
                if(!data[1].equals("null")){
                    product1.setProductStatus(ProductStatus.valueOf(data[1]));
                }
                if(!data[5].equals("null")){
                    Type scoreType = new TypeToken<ArrayList<Score>>(){
                    }.getType();
                    product1.setAllScores(new Gson().fromJson(data[5], scoreType));
                }
                if(!data[6].equals("null")){
                    product1.setProductCost(Double.parseDouble(data[6]));
                }
                if(!data[7].equals("null")){
                    product1.setCostAfterOff(Double.parseDouble(data[7]));
                }
                if(!data[11].equals("null")){
                    Type commentType = new TypeToken<ArrayList<Comment>>(){
                    }.getType();
                    product1.setCommentList(new Gson().fromJson(data[11], commentType));
                }
                if(!data[14].equals("null")){
                    Type customer = new TypeToken<ArrayList<Customer>>(){
                    }.getType();
                    product1.setAllBuyers(new Gson().fromJson(data[14], customer));
                }
                if(!data[15].equals("null")){
                    Type offerType = new TypeToken<ArrayList<Offer>>(){
                    }.getType();
                    product1.setOffers(new Gson().fromJson(data[15], offerType));
                }
                if(!data[16].equals("null")){
                    product1.setImagePath(data[16]);
                }
                if(!data[17].equals("null")){
                    product1.setVideoPath(data[17]);
                }
                if(!data[18].equals("null")){
                    product1.setFilePath(data[18]);
                }
                if(!data[19].equals("null")){
                    product1.setExistInOfferRegistered(data[19].equals("true"));
                }
                allProducts.add(product1);
            }
            statement.close();
            connection.close();
            ProductCenter.getInstance().setAllProducts(allProducts);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        if(!allProducts.isEmpty()){
            String allJsons = new Gson().toJson(allProducts);
            ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("getAllProductsForManager", allJsons), dataOutputStream);
        }else {
            ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("Error", "There is no Product"), dataOutputStream);
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
