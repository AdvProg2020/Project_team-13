package Controller.Client;

import Models.Product.Cart;
import Models.Product.Product;
import Models.UserAccount.Customer;
import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CartController {
    private Cart currentCart;
    private static CartController cartController;
    private double atLeastCredit;
    private CartController() {
    }

    public Cart getCurrentCart() {
        return currentCart;
    }

    public double getAtLeastCredit() {
        return atLeastCredit;
    }

    public void setAtLeastCredit(double atLeastCredit) {
        this.atLeastCredit = atLeastCredit;
    }

    public void payed(String json) {
        ClientController.getInstance().setCurrentUser(new Gson().fromJson(json, Customer.class));
        setCurrentCart(new Cart());
    }

    public void setCurrentCart(Cart currentCart) {
        this.currentCart = currentCart;
    }

    public void getAtLeastCreditFromServer() {
        ClientController.getInstance().sendMessageToServer("@getAtLeastCredit@");
    }

    public static CartController getInstance() {
        if (cartController == null) {
            cartController = new CartController();
            cartController.currentCart = new Cart();
        }
        return cartController;
    }

    public void changeCountOfProduct(Product product, boolean amount) {
        if (amount) {
            if (product.getNumberOfAvailableProducts() >= currentCart.getCountOfEachProduct().get(product.getProductId()) + 1)
                currentCart.getCountOfEachProduct().replace(product.getProductId(), currentCart.getCountOfEachProduct().get(product.getProductId()) + 1);
        } else {
            if (currentCart.getCountOfEachProduct().get(product.getProductId()) - 1 > 0) {
                currentCart.getCountOfEachProduct().replace(product.getProductId(), currentCart.getCountOfEachProduct().get(product.getProductId()) - 1);
            } else {
                for (Product product1 : currentCart.getAllproduct()) {
                    if (product.getProductId().equals(product1.getProductId())) {
                        currentCart.getAllproduct().remove(product1);
                        currentCart.getCountOfEachProduct().remove(product.getProductId());
                        break;
                    }
                }
            }
        }
    }

    public double getTotalPriceOfProduct(Product product) {
        for (String s : cartController.getCurrentCart().getCountOfEachProduct().keySet()) {
            if (s.equals(product.getProductId())) {
                return cartController.getCurrentCart().getCountOfEachProduct().get(s) * product.getCostAfterOff();
            }
        }
        return 0;
    }

    public void pay() {
        ClientController.getInstance().sendMessageToServer("@pay@" + new Gson().toJson(currentCart));
    }

    public void payWithBankAccount(String accountId) {
        ClientController.getInstance().sendMessageToServer("@payWithBankAccount@" + accountId + "//" + new Gson().toJson(currentCart));
    }

    public void waitForDownload() {
        System.out.println("step1");
        new waitForSellerFile().start();
    }

    class waitForSellerFile extends Thread {
        @Override
        public void run() {
            System.out.println("step2");
            try {
                ServerSocket serverSocket = new ServerSocket(0);
                for (String s : currentCart.getAllSeller().keySet()) {
                    System.out.println(s);
                }
                ClientController.getInstance().sendMessageToServer("@setCustomerPort@" + serverSocket.getLocalPort() + "&" + new Gson().toJson(currentCart.getAllSeller()));
                int input = 0;
                while (true) {
                    Socket socket = serverSocket.accept();
                    DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    DataOutputStream fileOutputStream = null;
                    do {
                        if (dataInputStream != null) {
                            input = dataInputStream.available();
                        }
                    } while (input == 0);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    String fileName = "";
                    String fileData = "";
                    while (true) {
                        try {
                            String s = dataInputStream.readUTF();
                            fileName = s.split("&")[0];
                            fileData = s.split("&")[1];
                        } catch (IOException e) {
                            break;
                        }
                    }
                    dataInputStream.close();
                    System.out.println("file was creating: " + fileName);
                    fileOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
                    String[] split = fileData.split(", ");
                    byte[] bytes = new byte[split.length];
                    for (int i = 0; i < split.length; i++) {
                        bytes[i] = Byte.parseByte(split[i]);
                    }
                    fileOutputStream.write(bytes);
                    fileOutputStream.close();
                    System.out.println("end of file");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendFileToCustomer(int port, String filePath) {
        new Thread(new Runnable() {
            public void run() {
                System.out.println("step4" + filePath);
                Socket socket = null;
                try {
                    socket = new Socket("127.0.0.1", port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DataOutputStream transactionStream = null;
                try {
                    transactionStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DataInputStream fileInputStream = null;
                ByteArrayOutputStream byteArrayOutputStream = null;
                try {
                    fileInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    while (true) {
                        try {

                            byteArrayOutputStream.write(fileInputStream.readByte());
                            byteArrayOutputStream.flush();

                        } catch (IOException e) {
                            break;
                        }
                    }
                    try {
                        String fileName = filePath.substring(filePath.lastIndexOf('/') + 1);
                        String data = fileName + "&";
                        String s = data;
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        for (int i = 0; i < bytes.length; i++) {
                            s += String.valueOf(bytes[i]) + ", ";
                        }
                        transactionStream.writeUTF(s);
                        transactionStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        transactionStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();

    }
}
