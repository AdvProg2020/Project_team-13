package Models;

public class Receipt{
    private String description;
    private ReceiptType receiptType;
    private double money;
    private String sourceId;
    private String destinationId;
    private String receiptId;
    private String paid;

    public Receipt(String description, ReceiptType receiptType, double money, String sourceId, String receiptId, String destinationId, String paid) {
        this.destinationId = destinationId;
        this.description = description;
        this.receiptType = receiptType;
        this.money = money;
        this.sourceId = sourceId;
        this.receiptId = receiptId;
        this.paid = paid;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getToken() {
        return description;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public ReceiptType getReceiptType() {
        return receiptType;
    }

    public double getMoney() {
        return money;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getReceiptId() {
        return receiptId;
    }
}

