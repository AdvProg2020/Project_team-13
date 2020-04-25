package Controller.Server;

import java.io.FileWriter;

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
}
