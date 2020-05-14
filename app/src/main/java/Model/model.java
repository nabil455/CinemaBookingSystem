package Model;

public class model {
    private String name;
    private String start;
    private String price;
    private String quantity;

    public model(){}

    public model(String name, String start, String price,String quantity) {
        this.name = name;
        this.start = start;
        this.price = price;
        this.quantity= quantity;
    }
    public String getName() {
        return name;
    }

    public String getStart() {
        return start;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

}
