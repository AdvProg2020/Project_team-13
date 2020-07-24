package Controller.Client;

import Models.DiscountCode;
import Models.Product.Product;
import Models.UserAccount.Customer;
import Models.UserAccount.Manager;
import Models.UserAccount.Seller;
import Models.UserAccount.UserAccount;
import View.MainMenu;
import View.Menu;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.sun.xml.internal.messaging.saaj.util.Base64;
import io.fusionauth.jwt.JWTExpiredException;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

public class ClientController {
    private static ClientController clientController;
    private UserAccount currentUser;
    private DiscountCode currentDiscountCode;
    private Product currentProduct;
    private ArrayList<View.Menu> menus = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private String message;
    private Socket socket, customerSocket;
    private ServerSocket serverSocket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Date expirationDate;
    private static Algorithm algorithm;
    private int qq = 0;
    private long time;

    static {
        try {
            algorithm = Algorithm.HMAC256(new String(Files.readAllBytes(Paths.get("secret.txt"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectToServer() {
        try {
//            socket = new Socket("0.tcp.ngrok.io", 16150);
            socket = new Socket("2.tcp.ngrok.io", 10718);
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getCustomerSocket() {
        return customerSocket;
    }

    public void setCustomerSocket(Socket customerSocket) {
        this.customerSocket = customerSocket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public DiscountCode getCurrentDiscountCode() {
        return currentDiscountCode;
    }

    public void addNewMenu(View.Menu menu) {
        menus.add(menu);
    }

    public void resetMenuArray() {
        Menu menu = menus.get(0);
        menus.clear();
        new MainMenu(menu.getStage()).execute();
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public void back() {
        if (menus.size() > 1) {
            menus.remove(menus.size() - 1);
            menus.get(menus.size() - 1).setMenuBarGridPane();
            menus.get(menus.size() - 1).execute();
        }
    }

    public View.Menu getMainMenu() {
        return new MainMenu(menus.get(0).getStage());
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
    }


    public void setCurrentDiscountCode(DiscountCode currentDiscountCode) {
        this.currentDiscountCode = currentDiscountCode;
    }

    private ClientController() {
    }

    public void setSellerSocket() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("2.tcp.ngrok.io", 10718);
                    DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                    DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    System.out.println("in thread");
                    String sellerMessage = "@setSellerSocket@" + currentUser.getUsername();
                    String message1 = getTheEncodedMessage("0@getTime@");
                    dataOutputStream.writeUTF(message1);
                    dataOutputStream.flush();
                    String string = "";
                    try {
                        string = dataInputStream.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    getMessageFromServer(string);
                    sellerMessage = getTheEncodedMessage(time + sellerMessage);
                    dataOutputStream.writeUTF(sellerMessage);
                    dataOutputStream.flush();
                    System.out.println("AFTER SEND");
                    while (true) {
                        //  if(dataInputStream.available()>0) {
                        String string2 = dataInputStream.readUTF();
                        getMessageFromServer(string2);
                        //   }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static ClientController getInstance() {
        if (clientController == null) {
            clientController = new ClientController();
        }
        return clientController;
    }

    public void updateCurrentUserFromServer() {
        if (currentUser != null) {
            sendMessageToServer("@getUser@" + currentUser.getUsername());
        }
    }

    public UserAccount getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserAccount currentUser) {
        this.currentUser = currentUser;
    }


    public Menu getCurrentMenu() {
        return menus.get(menus.size() - 1);
    }

    public void sendMessageToServer(String message) {
        this.message = message;
        String message1 = getTheEncodedMessage("0@getTime@");
        try {
            System.out.println(message1);
            dataOutputStream.writeUTF(message1);
            dataOutputStream.flush();
            String string = "";
            try {
                string = dataInputStream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            getMessageFromServer(string);
            message = getTheEncodedMessage(time + message);
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
            String string1 = "";
            do {
                try {
                    string1 = dataInputStream.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getMessageFromServer(string1);
            } while (dataInputStream.available() > 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getMessageFromServer(String message) {
        MessageController.getInstance().processMessage(message);
    }

    public Seller getSeller() {
        if (currentUser.getType().equals("@Seller")) {
            return (Seller) currentUser;
        }
        return null;
    }

    public String getTransactionMessage() {
        return message;
    }

    public String getTheEncodedMessage(String message) {
        if (getCurrentUser() != null) {
            return JWT.create().withIssuer(getCurrentUser().getUsername()).withSubject(message).withExpiresAt(expirationDate).sign(algorithm);
        }
        return JWT.create().withIssuer("Client").withSubject(message).sign(algorithm);
    }


    public boolean isMessageValid(String token) {
        boolean flag = true;
        try {
            JWTVerifier jwt = JWT.require(algorithm).withIssuer("Server").build();
            jwt.verify(token);
        } catch (JWTVerificationException | JWTExpiredException e) {
            flag = false;
        }
        return flag;
    }

    public String getTheDecodedMessage(String message) {
        DecodedJWT decodedJWT = JWT.decode(message);
        expirationDate = decodedJWT.getExpiresAt();
        String message2 = new String(new Base64().decode(decodedJWT.getPayload().getBytes()));
        String finalMessage = message2.substring(8, message2.lastIndexOf(",\"iss\"") - 1);
        if (finalMessage.contains("requestId")) {
            for (int i = 0; i < 2; i++) {
                finalMessage = finalMessage.replace("\\\"", "\"");
            }
        } else {
            while ((finalMessage.contains("\\\""))) {
                finalMessage = finalMessage.replace("\\\"", "\"");
            }
        }
        return finalMessage;
    }

    public void getAllOrdersFromServer() {
        sendMessageToServer("@getAllOrders@");
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setCurrentUser(String message) {
        if (currentUser instanceof Seller) {
            System.out.println(message);
            currentUser = new Gson().fromJson(message, Seller.class);
        } else if (currentUser instanceof Customer) {
            currentUser = new Gson().fromJson(message, Customer.class);
        } else if (currentUser instanceof Manager) {
            currentUser = new Gson().fromJson(message, Manager.class);
        }
    }
}
