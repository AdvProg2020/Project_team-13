import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RSASecretGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private BigInteger p;
    private BigInteger q;
    private final BigInteger n;
    private final BigInteger phi;
    private final BigInteger d;
    private BigInteger rand;
    private BigInteger rand2;
    private BigInteger rnd3;
    private final BigInteger e;
    private static RSASecretGenerator rsaSecretGenerator;
    private final Key<BigInteger, BigInteger> privateKey;
    private final Key<BigInteger, BigInteger> publicKey;

    private RSASecretGenerator() {
        BigInteger q1;
        BigInteger p1;
        do {
            rnd3 = BigInteger.probablePrime(7, secureRandom);
            rand2 = BigInteger.probablePrime(7, secureRandom);
        } while (rand2.compareTo(rnd3) == 0);
        p = rnd3;
        q = rand2;
        n = p.multiply(q);
        phi = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));
        do {
            rand = BigInteger.valueOf(ThreadLocalRandom.current().nextLong(9, phi.longValue()));
        } while (!isPrimeRelatedToPhi());
        e = rand;
        BigInteger k = BigInteger.valueOf(1);
        d = e.modInverse(phi);
        p1 = null;
        p = p1;
        q1 = null;
        q = q1;
        privateKey = new Key<>(d, n);
        publicKey = new Key<>(e, n);
    }

    private boolean isPrimeRelatedToPhi() {
        long x = phi.longValue();
        long y = rand.longValue();
        long r;
        while (y != 0) {
            r = x % y;
            x = y;
            y = r;
        }
        return x == 1;
    }

    public static RSASecretGenerator getInstance() {
        if (rsaSecretGenerator == null) {
            rsaSecretGenerator = new RSASecretGenerator();
        }
        return rsaSecretGenerator;
    }

    public Key<BigInteger, BigInteger> getPublicKey() {
        return publicKey;
    }


    public String getTheEncodedWithRSA(String message, Key<BigInteger, BigInteger> publicKey) {
        String encrypted = "";
        BigInteger e = publicKey.getKeyNum();
        BigInteger n = publicKey.getNumber();
        int j = 0;
        for(int i = 0; i < message.length(); i++){
            char m = message.charAt(i);
            BigInteger bi1 = BigInteger.valueOf(m);
            BigInteger bi2 = bi1.modPow(e, n);
            j = bi2.intValue();
            m = (char) j;
            encrypted += m;
        }
        return encrypted += " /// " + getTheSign(message) + " /// " + this.publicKey.toString();
    }

    public String getTheDecodedMessageViaRSA(String message) {
        String decrypted = "";
        int j = 0;
        for(int i = 0; i < message.length(); i++){
            char c = message.charAt(i);
            BigInteger bi1 = BigInteger.valueOf(c);
            BigInteger bi2 = bi1.modPow(d, n);
            j = bi2.intValue();
            c = (char) j;
            decrypted += c;
        }
        return decrypted;
    }

    private String getTheSign(String message) {
        String sign = "";
        String hashMessage = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(message.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            hashMessage = String.format("%064x", new BigInteger(1, digest));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        int j = 0;
        for(int i = 0; i < hashMessage.length(); i++){
            char m = hashMessage.charAt(i);
            BigInteger bi1 = BigInteger.valueOf(m);
            BigInteger bi2 = bi1.modPow(d, n);
            j = bi2.intValue();
            m = (char) j;
            sign += m;
        }
        return sign;
    }

    public boolean isVerified(String message, String signature, Key<BigInteger, BigInteger> publicKey) {
        String hashMessage = "";
        String expectedHashed = "";
        BigInteger e = publicKey.getKeyNum();
        BigInteger n = publicKey.getNumber();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(message.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            hashMessage = String.format("%064x", new BigInteger(1, digest));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        int j = 0;
        for(int i = 0; i < signature.length(); i++){
            char m = signature.charAt(i);
            BigInteger bi1 = BigInteger.valueOf(m);
            BigInteger bi2 = bi1.modPow(e, n);
            j = bi2.intValue();
            m = (char) j;
            expectedHashed += m;
        }
        return hashMessage.equals(expectedHashed);
    }


    public static void main(String[] args) {
        String encodedMessage = RSASecretGenerator.getInstance().getTheEncodedWithRSA("My message", RSASecretGenerator.getInstance().publicKey);
        System.out.println(encodedMessage);
        String decodedMessage = RSASecretGenerator.getInstance().getTheDecodedMessageViaRSA(encodedMessage.split(" /// ")[0]);
        System.out.println(decodedMessage);
        boolean result = RSASecretGenerator.getInstance().isVerified(decodedMessage, encodedMessage.split(" /// ")[1], RSASecretGenerator.getInstance().publicKey);
        System.out.println(result);
    }


}


class Key<E, T> {
    private E keyNum;
    private T number;

    public Key(E keyNum, T number) {
        this.keyNum = keyNum;
        this.number = number;
    }

    public E getKeyNum() {
        return keyNum;
    }


    public T getNumber() {
        return number;
    }


    @Override
    public String toString() {
        return "Key{" +
                "keyNum=" + keyNum.toString() +
                ", number=" + number.toString() +
                '}';
    }
}
