package Controller.Server;

import Models.UserAccount.UserAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataBase {
    private static DataBase dataBase;
    private DataBase(){

    }
    public static DataBase getIncstance() {
        if (dataBase == null){
            dataBase = new DataBase();
        }
        return dataBase;
    }
    public void updateAllUsers(String json){
        try{
            FileWriter fileWriter=new FileWriter("allUsers.txt");
            fileWriter.write(json);
            fileWriter.close();
        }catch(Exception e){System.out.println(e);}
    }
    public void setAllUsersListFromDateBase() throws IOException {
        FileReader fileReader=new FileReader("allUsers.txt");
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String json;
            while ((json = br.readLine()) != null) {
                Gson gson=new Gson();
                ArrayList<UserAccount> allUsers=gson.fromJson(json,new TypeToken<ArrayList<UserAccount>>(){}.getType());
                UserCenter.getIncstance().setAllUserAccount(allUsers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
    }
}
