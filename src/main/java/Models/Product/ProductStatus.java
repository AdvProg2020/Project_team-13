package Models.Product;

public enum ProductStatus {
    editing("editing"),
    inCreatingProgress("inCreatingProgress"),
    accepted (" accepted");

    private String name;

    private ProductStatus(String s) {
        name=s;
    }

    public String getName() {
        return name;
    }
}
