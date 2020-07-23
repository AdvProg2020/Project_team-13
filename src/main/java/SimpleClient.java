import Controller.Server.RSASecretGeneratorForBank;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) {
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        try {
            socket = new Socket("localhost", 3030);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        try {
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String data = new Gson().toJson(RSASecretGeneratorForSimpleClient.getInstance().getPublicKey());
            dataOutputStream.writeUTF(data);
            dataOutputStream.flush();
            String response = dataInputStream.readUTF();
            System.out.println("bbbbbbbbbbb");
            Type keyType = new TypeToken<Key<BigInteger, BigInteger>>() {
            }.getType();
            RSASecretGeneratorForSimpleClient.getInstance().setAnotherPublicKey(new Gson().fromJson(response, keyType));
            String string = RSASecretGeneratorForSimpleClient.getInstance()
                    .getTheEncodedWithRSA("0@@getTime@", RSASecretGeneratorForSimpleClient.getInstance().getAnotherPublicKey());
            dataOutputStream.writeUTF(string);
            dataOutputStream.flush();
            String time = dataInputStream.readUTF();
            time = RSASecretGeneratorForSimpleClient.getInstance().getTheDecodedMessageViaRSA(time.split(" /// ")[0]);
            System.out.println("ccccccc");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                String command = scanner.nextLine();
                String string = RSASecretGeneratorForSimpleClient.getInstance()
                        .getTheEncodedWithRSA("0@@getTime@", RSASecretGeneratorForSimpleClient.getInstance().getAnotherPublicKey());
                dataOutputStream.writeUTF(string);
                dataOutputStream.flush();
                String time = dataInputStream.readUTF();
                time = RSASecretGeneratorForSimpleClient.getInstance().getTheDecodedMessageViaRSA(time.split(" /// ")[0]);
                String string1 = RSASecretGeneratorForSimpleClient.getInstance()
                        .getTheEncodedWithRSA(time + "@" + command, RSASecretGeneratorForSimpleClient.getInstance().getAnotherPublicKey());
                dataOutputStream.writeUTF(string1);
                dataOutputStream.flush();
                String response = dataInputStream.readUTF();
                response = RSASecretGeneratorForSimpleClient.getInstance().getTheDecodedMessageViaRSA(response.split(" /// ")[0]);
                System.out.println(response);
            } catch (IOException e) {
                System.out.println("Connection is Closed...");
                break;
            }
        }
    }
}
