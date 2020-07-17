package Controller.Server;

import Controller.Client.AuctionController;
import Controller.Client.MessageController;
import Controller.Client.UserController;
import Models.Auction;
import Models.DiscountCode;
import Models.Offer;
import Models.Product.Cart;
import Models.Product.Category;
import Models.Product.Product;
import Models.UserAccount.Customer;
import Models.UserAccount.Manager;
import Models.UserAccount.Seller;
import Models.UserAccount.UserAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerMessageController {
    private static ServerMessageController serverMessageController;
    private String message;


    private ServerMessageController() {

    }

    public static ServerMessageController getInstance() {
        if (serverMessageController == null) {
            synchronized (ServerMessageController.class) {
                if (serverMessageController == null) {
                    serverMessageController = new ServerMessageController();
                }
            }
        }
        return serverMessageController;
    }

    public String makeMessage(String type, String command) {
        return ("@" + type + "@" + command);
    }

    public void processMessage(String message, DataOutputStream dataOutputStream) {
        if(TokenGenerator.getInstance().isTokenVerified(message, dataOutputStream)) {
            message = TokenGenerator.getInstance().getTheDecodedMessage(message);
            ServerController.getInstance().passTime(dataOutputStream);
            if (message.startsWith("@Register@")) {
                this.message = message.substring(10);
                UserCenter.getIncstance().createNewUserAccount(this.message, dataOutputStream);
            } else if (message.startsWith("@Login@")) {
                message = message.substring(7);
                String[] split = message.split("/");
                UserCenter.getIncstance().login(split[0], split[1], dataOutputStream);
            } else if (message.startsWith("@logout@")) {
                message = message.substring(8);
                ServerController.getInstance().sendMessageToClient("@successfulChat@", dataOutputStream);
                ServerController.getInstance().getAllClients().remove(dataOutputStream, message);
                TokenGenerator.getInstance().getAllExpirationDates().remove(message, TokenGenerator.getInstance().getAllExpirationDates().get(message));
            } else if (message.startsWith("@sendChatMessage@")) {
                //    ServerController.getInstance().sendMessageToClient("@successfulChat@",dataOutputStream);
                message = message.substring(17);
                //  UserCenter.getIncstance().sendChat(message,dataOutputStream);
            } else if (message.startsWith("@getAllRequests@")) {
                ServerController.getInstance().sendMessageToClient("@AllRequests@" + new Gson().toJson(RequestCenter.getIncstance().getAllRequests()), dataOutputStream);
            } else if (message.startsWith("@getOnlineSupporter@")) {
                ServerController.getInstance().sendMessageToClient("@OnlineUsers@" + new Gson().toJson(ServerController.getInstance().getOnlineSupporters()), dataOutputStream);
            } else if (message.startsWith("@getOnlineUsers@")) {
                ArrayList<String> users = new ArrayList<>();
                for (String value : ServerController.getInstance().getAllClients().values()) {
                    users.add(value);
                }
                ServerController.getInstance().sendMessageToClient("@setOnlineUsers@" + new Gson().toJson(users), dataOutputStream);
            }else if (message.startsWith("@AddAuction@")) {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa45489776jhg");
                message = message.substring(12);
                try {
                    dataOutputStream.writeUTF("123");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                AuctionCenter.getInstance().createNewAuctionRequest(message, dataOutputStream);
            }else if (message.startsWith("@acceptRequest@")) {
                message = message.substring(15);
                RequestCenter.getIncstance().acceptRequest(message, dataOutputStream);
            } else if (message.startsWith("@AddProduct@")) {
                message = message.substring(12);
                Gson gson = new Gson();
                Product product = gson.fromJson(message, Product.class);
                ProductCenter.getInstance().createProductRequest(product, dataOutputStream);
            } else if (message.startsWith("@getAllUsers@")) {
                DataBase.getInstance().getAllUsersListFromDateBase(dataOutputStream);
            } else if (message.startsWith("@deleteCustomer@")) {
                UserCenter.getIncstance().removeCustomer(message.substring(16
                ), dataOutputStream);
            } else if (message.startsWith("@deleteSeller@")) {
                UserCenter.getIncstance().removeSeller(message.substring(14), dataOutputStream);
            } else if (message.startsWith("@deleteManager@")) {
                UserCenter.getIncstance().removeManager(message.substring(15), dataOutputStream);
            } else if (message.startsWith("@createManagerProfile@")) {
                UserCenter.getIncstance().createManagerProfile(message.substring(22), dataOutputStream);
            } else if (message.startsWith("@createSupporterProfile@")) {
                UserCenter.getIncstance().createSupporterProfile(message.substring(24), dataOutputStream);
            } else if (message.startsWith("@getAllProductsForManager@")) {
                DataBase.getInstance().getAllProductsFromDataBase(dataOutputStream);
            } else if (message.startsWith("@getAllOffers@")) {
                DataBase.getInstance().getAllOffersFromDataBase(dataOutputStream);
            } else if (message.startsWith("@removeProductForManager@")) {
                message = message.substring(25);
                ProductCenter.getInstance().deleteProduct(message, dataOutputStream);
            } else if (message.startsWith("@setSupporterPort@")) {
                message = message.substring(18);
                String[] split = message.split("&");
                ServerController.getInstance().sendMessageToClient("@successfulchat@", dataOutputStream);
                ServerController.getInstance().getOnlineSupporters().put(split[1], Integer.parseInt(split[0]));
            } else if (message.startsWith("@setCustomerPort@")) {
                message = message.substring(17);
                System.out.println("THIS IS SERVER");
                String[] split = message.split("&");
                ServerController.getInstance().sendMessageToClient("@successfulchat@", dataOutputStream);
                Type userListType = new TypeToken<HashMap<String, ArrayList<String>>>() {
                }.getType();
                HashMap<String, ArrayList<String>> allSeller = new Gson().fromJson(split[1], userListType);
                for (String seller : allSeller.keySet()) {
                    for (String filePath : allSeller.get(seller)) {
                        System.out.println(seller + " " + filePath);
                        ServerController.getInstance().sendMessageToClient("@setCustomerPort@" + split[0] + "&" + filePath, ServerController.getInstance().getSellerSockets().get(seller));
                    }
                }
            } else if (message.startsWith("@getAllCategories@")) {
                CategoryCenter.getIncstance().updateAllCategories();
                ArrayList<Category> allCategories = CategoryCenter.getIncstance().getAllCategories();
                Gson gson = new Gson();
                ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("setAllCategories", gson.toJson(allCategories)), dataOutputStream);
            } else if (message.startsWith("@updateAllCategories@")) {
                message = message.substring(21);
                DataBase.getInstance().updateAllCategories(message);
                ServerController.getInstance().sendMessageToClient(MessageController.getInstance().makeMessage("category added", "category added"), dataOutputStream);
            } else if (message.startsWith("@removeCategory@")) {
                message = message.substring(16);
                CategoryCenter.getIncstance().removeCategory(message, dataOutputStream);
            } else if (message.startsWith("@createDiscountCode@")) {
                message = message.substring(20);
                DiscountCodeCenter.getIncstance().createDiscountCode(message, dataOutputStream);
            } else if (message.startsWith("@getAllDiscountCodes@")) {
                ServerController.getInstance().sendMessageToClient("@AllDiscountCodes@" + new Gson().toJson(DiscountCodeCenter.getIncstance().getAllDiscountCodes()), dataOutputStream);
            } else if (message.startsWith("@editDiscountCode@")) {
                message = message.substring(18);
                DiscountCodeCenter.getIncstance().editDiscountCode(new Gson().fromJson(message, DiscountCode.class), dataOutputStream);
            } else if (message.startsWith("@AddOffer@")) {
                message = message.substring(10);
                OffCenter.getInstance().createOfferRequest(message, dataOutputStream);
            } else if (message.startsWith("@removeDiscountCode@")) {
                message = message.substring(20);
                DiscountCodeCenter.getIncstance().removeDiscountCode(message, dataOutputStream);
            } else if (message.startsWith("@editManager@")) {
                message = message.substring(13);
                UserCenter.getIncstance().editManager(new Gson().fromJson(message, Manager.class), dataOutputStream);
            } else if (message.startsWith("@editCustomer@")) {
                message = message.substring(14);
                UserCenter.getIncstance().editCustomer(new Gson().fromJson(message, Customer.class), dataOutputStream);
            } else if (message.startsWith("@editSeller@")) {
                message = message.substring(12);
                UserCenter.getIncstance().editSeller(new Gson().fromJson(message, Seller.class), dataOutputStream);
            } else if (message.startsWith("@editAuction@")) {
                message = message.substring(13);
                UserCenter.getIncstance().editAuction(new Gson().fromJson(message, Auction.class), dataOutputStream);
            } else if (message.startsWith("@editCategory@")) {
                message = message.substring(14);
                if (message.startsWith("add")) {
                    message = message.substring(3);
                    CategoryCenter.getIncstance().editCategory(new Gson().fromJson(message, Category.class), dataOutputStream);
                } else if (message.startsWith("del")) {
                    message = message.substring(3);
                    CategoryCenter.getIncstance().deleteCategoryFeature(new Gson().fromJson(message, Category.class));
                } else if (message.startsWith("adM")) {
                    message = message.substring(3);
                    CategoryCenter.getIncstance().replaceCategory(new Gson().fromJson(message, Category.class), dataOutputStream);
                }
            } else if (message.startsWith("@editOffer@")) {
                message = message.substring(11);
                OffCenter.getInstance().createEditOfferRequest(new Gson().fromJson(message, Offer.class), dataOutputStream);
            } else if (message.startsWith("@editProduct@")) {
                message = message.substring(13);
                ProductCenter.getInstance().createEditProductRequest(new Gson().fromJson(message, Product.class), dataOutputStream);
            } else if (message.startsWith("@deleteProduct@")) {
                message = message.substring(15);
                ProductCenter.getInstance().createDeleteProductRequest(new Gson().fromJson(message, Product.class), dataOutputStream);
            } else if (message.startsWith("@pay@")) {
                message = message.substring(5);
                CartCenter.getInstance().pay(new Gson().fromJson(message, Cart.class), dataOutputStream);
            } else if (message.startsWith("@declineRequest@")) {
                message = message.substring(16);
                RequestCenter.getIncstance().declineRequest(message, dataOutputStream);
            } else if (message.startsWith("@gSPOA@")) {
                message = message.substring(7);
                ServerController.getInstance().sendMessageToClient("@gSPOA"
                        + AuctionCenter.getInstance().getSocketPort(new Gson().fromJson(message,Auction.class)),dataOutputStream);
            }else if (message.startsWith("@rate@")) {
                message = message.substring(6);
                ProductCenter.getInstance().rating(message);
            } else if (message.startsWith("@cmc@")) {
                message = message.substring(5);
                ProductCenter.getInstance().addCommercialRequest(message, dataOutputStream);
            } else if (message.startsWith("@addComment@")) {
                message = message.substring(12);
                ProductCenter.getInstance().commenting(message);
                ServerController.getInstance().sendMessageToClient("@SuccessfulNotBack@your comment sent to manager for accept", dataOutputStream);
            } else if (message.startsWith("@setSellerSocket@")) {
                System.out.println("IN CVONTROLLER");
                message = message.substring(17);
                if (!ServerController.getInstance().getSellerSockets().containsKey(message)) {
                    ServerController.getInstance().getSellerSockets().put(message, dataOutputStream);
                }
            } else if (message.startsWith("@setWage@")) {
                message = message.substring(9);
                System.out.println(message);
                CartCenter.getInstance().setWage(Double.parseDouble(message));
                ServerController.getInstance().sendMessageToClient("@Successful@wage successfully changed",dataOutputStream);
            }
        }else{
            ServerController.getInstance().sendMessageToClient("@Error@your token is invalid", dataOutputStream);
        }
    }


    public String getMessage() {
        return message;
    }
}
