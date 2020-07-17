package Controller.Server;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.xml.internal.messaging.saaj.util.Base64;
import io.fusionauth.jwt.JWTExpiredException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenGenerator {
    private static TokenGenerator tokenGenerator;
    private Algorithm algorithm;
    private Map<String, Date> allExpirationDates;

    private TokenGenerator(){
        allExpirationDates = new HashMap<>();
        try {
            algorithm = Algorithm.HMAC256(new String(Files.readAllBytes(Paths.get("secret.txt"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TokenGenerator getInstance(){
        if (tokenGenerator == null) {
            synchronized (TokenGenerator.class){
                if (tokenGenerator == null) {
                    tokenGenerator = new TokenGenerator();
                }
            }
        }
        return tokenGenerator;
    }

    public String getTheToken(String userName, String data){
        Date expirationDate = new Date(new Date().getTime() + 360);
        if(allExpirationDates.containsKey(userName)){
            allExpirationDates.remove(userName, expirationDate);
        }
        allExpirationDates.put(userName, expirationDate);
        return JWT.create().withIssuer("Server").withSubject(data).withExpiresAt(expirationDate).sign(algorithm);
    }


    public String getTheCodedMessage(DataOutputStream dataOutputStream, String data){
        return JWT.create().withIssuer("Server").withSubject(data).withExpiresAt(
                allExpirationDates.get(ServerController.getInstance().getAllClients().get(dataOutputStream))).sign(algorithm);
    }



    public boolean isTokenVerified(String token, DataOutputStream dataOutputStream){
        boolean flag = true;
        try {
            JWTVerifier jwt = JWT.require(algorithm).withIssuer(ServerController.getInstance().getAllClients().get(dataOutputStream)).build();
            jwt.verify(token);
        }catch (JWTVerificationException | JWTExpiredException e){
            flag = false;
        }
        return flag;
    }


    public String getTheDecodedMessage(String message){
       DecodedJWT decodedJWT = JWT.decode(message);
       String message2 = new String(new Base64().decode(decodedJWT.getPayload().getBytes()));
       String finalMessage = message2.substring(8, message2.lastIndexOf(",\"iss\"") - 1);
       while ((finalMessage.contains("\\\""))) {
            finalMessage = finalMessage.replace("\\\"", "\"");
       }
       return finalMessage;
    }

}
