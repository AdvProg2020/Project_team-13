package Models;

public class SellLog extends Log {
    private ReceivingStatus receivingStatus;
    private double reduceCostForOffs;

    public SellLog(ReceivingStatus receivingStatus, double reduceCostForOffs) {
        this.receivingStatus = receivingStatus;
        this.reduceCostForOffs = reduceCostForOffs;
    }

    public ReceivingStatus getReceivingStatus() {
        return receivingStatus;
    }

    public double getReduceCostForOffs() {
        return reduceCostForOffs;
    }
}
