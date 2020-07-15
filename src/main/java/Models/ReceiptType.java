package Models;

public enum ReceiptType{
    DEPOSIT("deposit"),
    WITHDRAW("withdraw"),
    MOVE("move");

    ReceiptType(String name) {
        this.name = name;
    }

    private String name;
}
