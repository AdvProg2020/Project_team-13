package Controller.Client;

import Models.ChatMessage;
import Models.Log;
import Models.UserAccount.Customer;
import Models.UserAccount.Manager;
import Models.UserAccount.Seller;
import Models.UserAccount.Supporter;
import View.MessageKind;
import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageController {

    private static MessageController messageController;

    private MessageController() {

    }

    public static MessageController getInstance() {
        if (messageController == null) {
            messageController = new MessageController();
        }
        return messageController;
    }

    public String makeMessage(String messageType, String command) {
        return "@" + messageType + "@" + command;

    }

    public void processMessage(String message) {
        if (ClientController.getInstance().isMessageValid(message)) {
            message = ClientController.getInstance().getTheDecodedMessage(message);
            if (message.startsWith("@Error@")) {
                message = message.substring(7);
                String finalMessage = message;
                ClientController.getInstance().getCurrentMenu().showMessage(finalMessage, MessageKind.ErrorWithoutBack);
            }else if(message.startsWith("@decreaseCredit@")){
                message = message.substring(16);
                String[] commands = message.split("//");
                ClientController.getInstance().getCurrentUser().setCredit(ClientController.getInstance().getCurrentUser().getCredit() - Double.parseDouble(commands[1]));
                ClientController.getInstance().getCurrentMenu().showMessage(commands[0], MessageKind.MessageWithBack);
            }else if (message.startsWith("@Successfulrc@")) {
                message = message.substring(14, message.length());
                String[] split = message.split("&");
                ClientController.getInstance().setCurrentUser(new Gson().fromJson(split[1], Customer.class));
                ClientController.getInstance().getCurrentMenu().showMessage("Register Successful\nyour bank id is:" + split[0], MessageKind.MessageWithBack);
            }else if(message.startsWith("@Successfulcredit@")){
                message = message.substring(18);
                String[] strings = message.split("//");
                ClientController.getInstance().getCurrentUser().setCredit(ClientController.getInstance().getCurrentUser().getCredit() + Double.parseDouble(strings[1]));
                ClientController.getInstance().getCurrentMenu().showMessage(strings[0], MessageKind.MessageWithBack);
            }else if (message.startsWith("@Successful@")) {
                message = message.substring(12, message.length());
                ClientController.getInstance().getCurrentMenu().showMessage(message, MessageKind.MessageWithBack);
            } else if (message.startsWith("@Successfulrs@")) {
                message = message.substring(14, message.length());
                String[] split = message.split("&");
                ClientController.getInstance().getCurrentMenu().showMessage(split[1] + "\nyour bank id is: " + split[0], MessageKind.MessageWithBack);
            } else if (message.startsWith("@SuccessfulNotBack@")) {
                message = message.substring(19);
                ClientController.getInstance().getCurrentMenu().showMessage(message, MessageKind.MessageWithoutBack);
            } else if (message.startsWith("@payed@")) {
                message = message.substring(7, message.length());
                CartController.getInstance().waitForDownload();
                CartController.getInstance().payed(message);
                int size = ((Customer) ClientController.getInstance().getCurrentUser()).getHistoryOfTransaction().size();
                Log buyLog = (ClientController.getInstance().getCurrentUser()).getHistoryOfTransaction().get(size - 1);
                ClientController.getInstance().getCurrentMenu().showMessage("Successfully purchase\ntotal price: " + buyLog.getPrice() + "\n" + buyLog.getDate(), MessageKind.MessageWithBack);
            } else if (message.startsWith("@Login as Customer@")) {
                Gson gson = new Gson();
                message = message.substring(19, message.length());
                Customer customer = gson.fromJson(message, Customer.class);
                ClientController.getInstance().setCurrentUser(customer);
                ClientController.getInstance().getCurrentMenu().showMessage("Login successful", MessageKind.MessageWithBack);
            } else if (message.startsWith("@Login as Manager@")) {
                Gson gson = new Gson();
                message = message.substring(18);
                Manager manager = gson.fromJson(message, Manager.class);
                ClientController.getInstance().setCurrentUser(manager);
                ClientController.getInstance().getCurrentMenu().showMessage("Login successful", MessageKind.MessageWithBack);
            } else if (message.startsWith("@Login as Supporter@")) {
                Gson gson = new Gson();
                message = message.substring(20, message.length());
                Supporter supporter = gson.fromJson(message, Supporter.class);
                try {
                    ClientController.getInstance().setServerSocket(new ServerSocket(0));
                    ClientController.getInstance().sendMessageToServer("@setSupporterPort@" + ClientController.getInstance().getServerSocket().getLocalPort() + "&" + supporter.getUsername());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ClientController.getInstance().setCurrentUser(supporter);
                ClientController.getInstance().getCurrentMenu().showMessage("Login successful", MessageKind.MessageWithBack);
                new Thread(new Runnable() {
                    public void run() {
                        while (true) {
                            try {
                                Socket clientSocket = ClientController.getInstance().getServerSocket().accept();
                                ClientController.getInstance().setCustomerSocket(clientSocket);
                                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                                new ClientHandeler(clientSocket).start();
                                //   dataOutputStream.writeUTF("");
                                //   dataOutputStream.flush();
                            } catch (IOException e) {
                                System.err.println("Error connecting client to server!");
                            }
                        }

                    }
                }).start();
            } else if (message.startsWith("@Login as Seller@")) {
                Gson gson = new Gson();
                message = message.substring(17);
                Seller seller = gson.fromJson(message, Seller.class);
                ClientController.getInstance().setCurrentUser(seller);
                ClientController.getInstance().setSellerSocket();
                ClientController.getInstance().getCurrentMenu().showMessage("Login successful", MessageKind.MessageWithBack);
            } else if (message.startsWith("@getChatMessage@")) {
                UserController.getInstance().getChatMessage(message.substring(16));
            } else if (message.startsWith("@successfulChat@")) {
                //don't delete this if!!!
            } else if (message.startsWith("@productCreating@")) {
                ClientController.getInstance().getCurrentMenu().showMessage(message.substring(17, message.length()), MessageKind.MessageWithBack);
            } else if (message.startsWith("@removedSuccessful@")) {
                ClientController.getInstance().getCurrentMenu().showMessage(message.substring(19, message.length()), MessageKind.MessageWithoutBack);
            } else if (message.startsWith("@AllRequests@")) {
                message = message.substring(13, message.length());
                RequestController.getInstance().printAllRequests(message);
            } else if (message.startsWith("@OnlineUsers@")) {
                message = message.substring(13);
                UserController.getInstance().setOnlineUsers(message);
            } else if (message.startsWith("@setOnlineUsers@")) {
                message = message.substring(16);
                UserController.getInstance().setOnlineClients(message);
            } else if (message.startsWith("@AllDiscountCodes@")) {
                message = message.substring(18, message.length());
                DiscountController.getInstance().printAllDiscountCodes(message);
            } else if (message.startsWith("@allCustomers@")) {
                message = message.substring(14, message.length());
                UserController.getInstance().setAllCustomers(message);
            } else if (message.startsWith("@allSellers@")) {
                message = message.substring(12, message.length());
                UserController.getInstance().setAllSellers(message);
            } else if (message.startsWith("@allManagers@")) {
                message = message.substring(13);
                UserController.getInstance().setAllManagers(message);
            } else if (message.startsWith("@allUsers@")) {
                message = message.substring(10);
                String[] split = message.split("&");
                System.out.println("aaaaa        " + message);
                UserController.getInstance().setAllCustomers(split[0]);
                UserController.getInstance().setAllSellers(split[1]);
                UserController.getInstance().setAllManagers(split[2]);
            } else if (message.startsWith("@getAllProductsForManager@")) {
                message = message.substring(26);
                ProductController.getInstance().updateAllProducts(message);
            } else if (message.startsWith("@setAllCategories@")) {
                message = message.substring(18);
                CategoryController.getInstance().setAllCategories(message);
            } else if (message.startsWith("@category added@")) {
                ClientController.getInstance().getCurrentMenu().showMessage("Category created", MessageKind.MessageWithBack);
            } else if (message.startsWith("@productRemoved@")) {
                ClientController.getInstance().getCurrentMenu().showMessage("Category removed successfully", MessageKind.MessageWithoutBack);
            } else if (message.startsWith("getAllOffers")) {
                OffsController.getInstance().updateAllOffer(message);
            } else if (message.startsWith("@setCustomerPort@")) {
                message = message.substring(17);
                String[] split = message.split("&");
                System.out.println("step3");
                CartController.getInstance().sendFileToCustomer(Integer.parseInt(split[0]), split[1]);
            } else if (message.startsWith("@gSPOA@")) {
                System.out.println("helllllllllllloooooooooooooooooo auccccccccccccccccction");
                AuctionController.getInstance().connectChatInAuctionPage(Integer.parseInt(message.substring(7)));
            }else if (message.startsWith("@getAtLeastCredit@")) {
                message = message.substring(18);
                CartController.getInstance().setAtLeastCredit(Double.parseDouble(message));
            }
        }
    }

    class ClientHandeler extends Thread {
        private Socket clientSocket;
        private DataInputStream dataInputStream;
        private DataOutputStream dataOutputStream;

        public ClientHandeler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                this.dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void waitForClient() {
            String input = "";
            while (true) {
                try {
                    input = dataInputStream.readUTF();
                } catch (IOException e) {
                    break;
                }
                ChatMessage chatMessage = new Gson().fromJson(input, ChatMessage.class);
                if (!UserController.getInstance().getCustomerDataStreams().containsKey(chatMessage.getUsername()))
                    UserController.getInstance().getCustomerDataStreams().put(chatMessage.getUsername(), clientSocket);
                UserController.getInstance().getChatMessage(input);
            }
        }

        @Override
        public void run() {
            waitForClient();
        }
    }

}
