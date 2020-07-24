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
import java.util.*;
import java.util.Date;

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
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://ProjectDatabase.accdb");
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE allIds SET wage = ?");
            preparedStatement.setString(1, String.valueOf(CartCenter.getInstance().getWage()));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public synchronized void setAtLeastCredit() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://ProjectDatabase.accdb");
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE allIds SET atLeastCredit = ?");
            preparedStatement.setString(1, String.valueOf(CartCenter.getInstance().getAtLeastAmount()));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
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

    public synchronized void updateAllSupporter(String json) {
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

    public synchronized void setLastAuctionIdFromDataBase() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://ProjectDatabase.accdb");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT lastAuctionId FROM allIds");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AuctionCenter.getInstance().setLastAuctionId(resultSet.getString("lastAuctionId"));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void getWagePercent() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://ProjectDatabase.accdb");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT wage FROM allIds");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CartCenter.getInstance().setWage(Double.parseDouble(resultSet.getString("wage")));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    public synchronized void setLastProductIdFromDataBase() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://ProjectDatabase.accdb");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT lastProductId FROM allIds");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProductCenter.getInstance().setLastProductId(resultSet.getString("lastProductId"));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void setAllCategoriesFormDataBase() {
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
                if(!data[2].equals("[]")){
                    Type allProductType = new TypeToken<ArrayList<Product>>() {
                    }.getType();
                    category1.setAllProducts(new Gson().fromJson(data[2], allProductType));
                }
                allCategories.add(category1);
            }
            statement.close();
            connection.close();
            CategoryCenter.getIncstance().setAllCategories(allCategories, true);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public synchronized void updateAllCategories(String json) {
        Type categoryType = new TypeToken<ArrayList<Category>>(){}.getType();
        ArrayList<Category> allCategories = new Gson().fromJson(json, categoryType);
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM allCategories");
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO allCategories (name, features, allProducts) VALUES (?, ?, ?)");
            for (Category category : allCategories) {
                preparedStatement1.setString(1, category.getName());
                preparedStatement1.setString(2, new Gson().toJson(category.getFeatures()));
                preparedStatement1.setString(3, new Gson().toJson(category.getAllProducts()));
                preparedStatement1.executeUpdate();
            }
            preparedStatement.close();
            preparedStatement1.close();
            connection.close();
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public void setAllProductsFormDataBase() {
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
                System.out.println(data[4]);
                Product product1 = new Product(data[3], data[0], data[2], data[4], Double.parseDouble(data[6]), data[8], data[9],
                        Integer.parseInt(data[11]), new Gson().fromJson(data[12], features));
                if(!data[1].equals("null")){
                  product1.setProductStatus(ProductStatus.valueOf(data[1]));
                }
                if(!data[5].equals("[]")){
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
                if(!data[10].equals("[]")){
                    Type commentType = new TypeToken<ArrayList<Comment>>(){
                    }.getType();
                  product1.setCommentList(new Gson().fromJson(data[10], commentType));
                }
                if(!data[13].equals("[]")){
                    Type customer = new TypeToken<ArrayList<Customer>>(){
                    }.getType();
                    product1.setAllBuyers(new Gson().fromJson(data[13], customer));
                }
                if(!data[14].equals("[]")){
                   Type offerType = new TypeToken<ArrayList<Offer>>(){
                   }.getType();
                   product1.setOffers(new Gson().fromJson(data[14], offerType));
                }
                if(!data[15].equals("null")){
                   product1.setImagePath(data[15]);
                }
                if(!data[16].equals("null")){
                   product1.setVideoPath(data[16]);
                }
                if(!data[17].equals("null")){
                  product1.setFilePath(data[17]);
                }
                if(!data[18].equals("null")){
                  product1.setExistInOfferRegistered(data[18].equals("true"));
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
            PreparedStatement preparedStatementForInsertCustomer = connection.prepareStatement("INSERT INTO allProducts (productId, productStatus, productName, productCompany, seller, allScores, productCost, costAfterOff, productsCategory, description, commentList, numberOfAvailableProducts, featuresOfCategroy, allBuyers, offers, imagePath, videoPath,filePath, existInOfferRegistered)" +
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
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://ProjectDatabase.accdb");
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE allIds SET lastProductId = ?");
            preparedStatement.setString(1, productId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    public synchronized void replaceAuctionId(String auctionId) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://ProjectDatabase.accdb");
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE allIds SET lastAuctionId = ?");
            preparedStatement.setString(1, auctionId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public synchronized void replaceLogId(String logId) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://ProjectDatabase.accdb");
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE allIds SET lastLogId = ?");
            preparedStatement.setString(1, logId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
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
                if(!data[8].equals("[]")){
                    Type discountType = new TypeToken<ArrayList<DiscountCode>>(){
                    }.getType();
                    customer1.setAllDiscountCodes(new Gson().fromJson(data[8], discountType));
                }
                if(!data[9].equals("[]")){
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
                seller += resultSet1.getString("allOffers") + "&&";
                seller += resultSet1.getString("allRequests") + "&&";
                seller += resultSet1.getString("commercializedProduct") + "&&";
                seller += resultSet1.getString("auction");
                String[] data1 = seller.split("&&");
                Seller seller1 = new Seller(data1[0], data1[1], data1[2], data1[3], data1[4], data1[5], data1[7].equals("null") ? 0 : Double.parseDouble(data1[7]), data1[10], data1[11].equals("true"));
                if(!data1[12].equals("[]")){
                  Type allProducts = new TypeToken<ArrayList<Product>>(){
                  }.getType();
                  seller1.setAllProducts(new Gson().fromJson(data1[12], allProducts));
                }
                if(!data1[13].equals("[]")){
                    Type allOffers = new TypeToken<ArrayList<Offer>>(){
                    }.getType();
                    seller1.setAllOffer(new Gson().fromJson(data1[13], allOffers));
                }
                if(!data1[14].equals("[]")){
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
                if(!data2[8].equals("[]")){
                    Type discountType = new TypeToken<ArrayList<DiscountCode>>(){
                    }.getType();
                    manager1.setAllDiscountCodes(new Gson().fromJson(data2[8], discountType));
                }
                if(!data2[9].equals("[]")){
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
                if(!data4[8].equals("[]")){
                    Type discountType = new TypeToken<ArrayList<DiscountCode>>(){
                    }.getType();
                    supporter1.setAllDiscountCodes(new Gson().fromJson(data4[8], discountType));
                }
                if(!data4[9].equals("[]")){
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
       Type requestType = new TypeToken<ArrayList<Request>>(){}.getType();
       ArrayList<Request> allRequests = new Gson().fromJson(json, requestType);
       try{
           Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
           final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
           Connection connection = DriverManager.getConnection(url);
           PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM allRequests");
           preparedStatement.executeUpdate();
           PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO allRequests (requestId, TypeType, status, details) VALUES (?, ?, ?, ?)");
           for (Request request : allRequests) {
               preparedStatement1.setString(1, request.getRequestId());
               preparedStatement1.setString(2, String.valueOf(request.getType()));
               preparedStatement1.setString(3, String.valueOf(request.getStatus()));
               preparedStatement1.setString(4, request.getDetails());
               preparedStatement1.executeUpdate();
           }
           preparedStatement.close();
           preparedStatement1.close();
           connection.close();
       }catch (SQLException | ClassNotFoundException e){
           e.printStackTrace();
       }
    }

    public void setAllRequestsListFromDateBase() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM allRequests");
            ArrayList<Request> allRequests = new ArrayList<>();
            while (resultSet.next()) {
                String request = "";
                request += resultSet.getString("requestId") + "&&";
                request += resultSet.getString("TypeType") + "&&";
                request += resultSet.getString("status") + "&&";
                request += resultSet.getString("details");
                String[] data = request.split("&&");
                Request request1 = new Request(RequestType.valueOf(data[1]), RequestStatus.valueOf(data[2]), data[0], data[3]);
                allRequests.add(request1);
            }
            statement.close();
            connection.close();
            RequestCenter.getIncstance().setAllRequests(allRequests);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public synchronized void updateAllDiscountCode(String json) {
        Type type = new TypeToken<ArrayList<DiscountCode>>(){}.getType();
        ArrayList<DiscountCode> allDiscountCodes = new Gson().fromJson(json, type);
        try{
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE * FROM allDiscountCodes");
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO allDiscountCodes (discountCodeId, startTime, endTime, allUserAccountsThatHaveDiscount, discountPercent, maxDiscountAmount, maxUsingTime, remainingTimesForEachCustomer) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            for (DiscountCode discountCode : allDiscountCodes) {
                preparedStatement1.setString(1, discountCode.getDiscountCodeID());
                preparedStatement1.setString(2, new Gson().toJson(discountCode.getStartTime()));
                preparedStatement1.setString(3, new Gson().toJson(discountCode.getEndTime()));
                preparedStatement1.setString(4, new Gson().toJson(discountCode.getAllUserAccountsThatHaveDiscount()));
                preparedStatement1.setString(5, String.valueOf(discountCode.getDiscountPercent()));
                preparedStatement1.setString(6, String.valueOf(discountCode.getMaxDiscountAmount()));
                preparedStatement1.setString(7, new Gson().toJson(discountCode.getMaxUsingTime()));
                preparedStatement1.setString(8, new Gson().toJson(discountCode.getRemainingTimesForEachCustomer()));
                preparedStatement1.executeUpdate();
            }
            preparedStatement.close();
            preparedStatement1.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    public void setAllDiscountCodesListFromDateBase() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM allDiscountCodes");
            ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();
            while (resultSet.next()) {
                String discountCode = "";
                discountCode += resultSet.getString("discountCodeId") + "&&";
                discountCode += resultSet.getString("startTime") + "&&";
                discountCode += resultSet.getString("endTime") + "&&";
                discountCode += resultSet.getString("allUserAccountsThatHaveDiscount") + "&&";
                discountCode += resultSet.getString("discountPercent") + "&&";
                discountCode += resultSet.getString("maxDiscountAmount") + "&&";
                discountCode += resultSet.getString("maxUsingTime") + "&&";
                discountCode += resultSet.getString("remainingTimesForEachCustomer");
                String[] data = discountCode.split("&&");
                Type type = new TypeToken<ArrayList<String>>(){}.getType();
                Type type1 = new TypeToken<HashMap<String, Integer>>(){}.getType();
                Type type2 = new TypeToken<HashMap<String, Integer>>(){}.getType();
                DiscountCode discountCode1 = new DiscountCode(new Gson().fromJson(data[1], Date.class), new Gson().fromJson(data[2], Date.class),
                        new Gson().fromJson(data[3], type), Integer.parseInt(data[4]), Double.parseDouble(data[5]), new Gson().fromJson(data[6], type1), new Gson().fromJson(data[7], type2));
                if(!data[0].equals("null")){
                    discountCode1.setDiscountCodeID(data[0]);
                }
                allDiscountCodes.add(discountCode1);
            }
            statement.close();
            connection.close();
            DiscountCodeCenter.getIncstance().setAllDiscountCodes(allDiscountCodes);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public synchronized void replaceRequestId(String lastRequestId) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://ProjectDatabase.accdb");
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE allIds SET lastRequestId = ?");
            preparedStatement.setString(1, lastRequestId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public synchronized void replaceDiscountCodeId(String lastDiscountCodeId) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://ProjectDatabase.accdb");
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE allIds SET lastDiscountCodeId = ?");
            preparedStatement.setString(1, lastDiscountCodeId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public synchronized void setLastRequestId() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://ProjectDatabase.accdb");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT lastRequestId FROM allIds");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                RequestCenter.getIncstance().setLastRequestID(resultSet.getString("lastRequestId"));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public synchronized void setLastLogId() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://ProjectDatabase.accdb");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT lastLogId FROM allIds");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CartCenter.getInstance().setLastLogId(resultSet.getString("lastLogId"));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public synchronized void setLastDiscountCodeId() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://ProjectDatabase.accdb");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT lastDiscountCodeId FROM allIds");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DiscountCodeCenter.getIncstance().setLastDiscountCodeID(resultSet.getString("lastDiscountCodeId"));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
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
                if(!data[8].equals("[]")){
                    Type discountType = new TypeToken<ArrayList<DiscountCode>>(){
                    }.getType();
                    customer1.setAllDiscountCodes(new Gson().fromJson(data[8], discountType));
                }
                if(!data[9].equals("[]")){
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
                seller += resultSet1.getString("allOffers") + "&&";
                seller += resultSet1.getString("allRequests") + "&&";
                seller += resultSet1.getString("commercializedProduct") + "&&";
                seller += resultSet1.getString("auction");
                String[] data1 = seller.split("&&");
                Seller seller1 = new Seller(data1[0], data1[1], data1[2], data1[3], data1[4], data1[5], data1[7].equals("null") ? 0 : Double.parseDouble(data1[7]), data1[10], data1[11].equals("true"));
                if(!data1[12].equals("[]")){
                    Type allProducts = new TypeToken<ArrayList<Product>>(){
                    }.getType();
                    seller1.setAllProducts(new Gson().fromJson(data1[12], allProducts));
                }
                if(!data1[13].equals("[]")){
                    Type allOffers = new TypeToken<ArrayList<Offer>>(){
                    }.getType();
                    seller1.setAllOffer(new Gson().fromJson(data1[13], allOffers));
                }
                if(!data1[14].equals("[]")){
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
                if(!data2[8].equals("[]")){
                    Type discountType = new TypeToken<ArrayList<DiscountCode>>(){
                    }.getType();
                    manager1.setAllDiscountCodes(new Gson().fromJson(data2[8], discountType));
                }
                if(!data2[9].equals("[]")){
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
                if(!data4[8].equals("[]")){
                    Type discountType = new TypeToken<ArrayList<DiscountCode>>(){
                    }.getType();
                    supporter1.setAllDiscountCodes(new Gson().fromJson(data4[8], discountType));
                }
                if(!data4[9].equals("[]")){
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
        ArrayList<Offer> allOffers = new ArrayList<>();
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM allOffers");
            while (resultSet.next()) {
                String offer = "";
                offer += resultSet.getString("offerId") + "&&";
                offer += resultSet.getString("amount") + "&&";
                offer += resultSet.getString("seller") + "&&";
                offer += resultSet.getString("productsId") + "&&";
                offer += resultSet.getString("startTime") + "&&";
                offer += resultSet.getString("endTime") + "&&";
                offer += resultSet.getString("offerStatus");
                String[] data = offer.split("&&");
                Type arrayList = new TypeToken<ArrayList<String>>(){}.getType();
                Offer offer1 = new Offer(Double.parseDouble(data[1]), data[2], new Gson().fromJson(data[3], arrayList), new Gson().fromJson(data[4], Date.class), new Gson().fromJson(data[5], Date.class));
                if(!data[0].equals("null")){
                    offer1.setOfferId(data[0]);
                }
                if(!data[6].equals("null")){
                    offer1.setOfferStatus(OfferStatus.valueOf(data[6]));
                }
                allOffers.add(offer1);
            }
            statement.close();
            connection.close();
            OffCenter.getInstance().setAllOffers(allOffers);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        if(!allOffers.isEmpty()){
            ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("getAllOffers", new Gson().toJson(allOffers)), dataOutputStream);
        }else{
            ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("Error", "There is no Product"), dataOutputStream);
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
                Product product1 = new Product(data[3], data[0], data[2], data[4], Double.parseDouble(data[6]), data[8], data[9],
                        Integer.parseInt(data[11]), new Gson().fromJson(data[12], features));
                if(!data[1].equals("null")){
                    product1.setProductStatus(ProductStatus.valueOf(data[1]));
                }
                if(!data[5].equals("[]")){
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
                if(!data[10].equals("[]")){
                    Type commentType = new TypeToken<ArrayList<Comment>>(){
                    }.getType();
                    product1.setCommentList(new Gson().fromJson(data[10], commentType));
                }
                if(!data[13].equals("[]")){
                    Type customer = new TypeToken<ArrayList<Customer>>(){
                    }.getType();
                    product1.setAllBuyers(new Gson().fromJson(data[13], customer));
                }
                if(!data[14].equals("[]")){
                    Type offerType = new TypeToken<ArrayList<Offer>>(){
                    }.getType();
                    product1.setOffers(new Gson().fromJson(data[14], offerType));
                }
                if(!data[15].equals("null")){
                    product1.setImagePath(data[15]);
                }
                if(!data[16].equals("null")){
                    product1.setVideoPath(data[16]);
                }
                if(!data[17].equals("null")){
                    product1.setFilePath(data[17]);
                }
                if(!data[18].equals("null")){
                    product1.setExistInOfferRegistered(data[18].equals("true"));
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
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://ProjectDatabase.accdb");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT lastOfferId FROM allIds");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OffCenter.getInstance().setLastOffId(resultSet.getString("lastOfferId"));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public synchronized void replaceOfferId(String lastOfferId) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://ProjectDatabase.accdb");
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE allIds SET lastOfferId = ?");
            preparedStatement.setString(1, lastOfferId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public synchronized void updateAllOffers(String json) {
        Type type = new TypeToken<ArrayList<Offer>>(){}.getType();
        ArrayList<Offer> allOffers = new Gson().fromJson(json, type);
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM allOffers");
            preparedStatement.execute();
            PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO allOffers (offerId, amount, seller, productsId, startTime, endTime, offerStatus) VALUES (?, ?, ?, ?, ?, ?, ?)");
            for (Offer offer : allOffers) {
              preparedStatement1.setString(1, offer.getOfferId());
              preparedStatement1.setString(2, String.valueOf(offer.getAmount()));
              preparedStatement1.setString(3, offer.getSeller());
              preparedStatement1.setString(4, new Gson().toJson(offer.getProducts()));
              preparedStatement1.setString(5, new Gson().toJson(offer.getStartTime()));
              preparedStatement1.setString(6, new Gson().toJson(offer.getEndTime()));
              preparedStatement1.setString(7, String.valueOf(offer.getOfferStatus()));
              preparedStatement1.executeUpdate();
            }
            preparedStatement.close();
            preparedStatement1.close();
            connection.close();
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public void setAllOffersFromDatabase() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM allOffers");
            ArrayList<Offer> allOffers = new ArrayList<>();
            while (resultSet.next()) {
                String offer = "";
                offer += resultSet.getString("offerId") + "&&";
                offer += resultSet.getString("amount") + "&&";
                offer += resultSet.getString("seller") + "&&";
                offer += resultSet.getString("productsId") + "&&";
                offer += resultSet.getString("startTime") + "&&";
                offer += resultSet.getString("endTime") + "&&";
                offer += resultSet.getString("offerStatus");
                String[] data = offer.split("&&");
                Type arrayList = new TypeToken<ArrayList<String>>(){}.getType();
                Offer offer1 = new Offer(Double.parseDouble(data[1]), data[2], new Gson().fromJson(data[3], arrayList), new Gson().fromJson(data[4], Date.class), new Gson().fromJson(data[5], Date.class));
                if(!data[0].equals("null")){
                    offer1.setOfferId(data[0]);
                }
                if(!data[6].equals("null")){
                    offer1.setOfferStatus(OfferStatus.valueOf(data[6]));
                }
                allOffers.add(offer1);
            }
            statement.close();
            connection.close();
            OffCenter.getInstance().setAllOffers(allOffers);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
}
