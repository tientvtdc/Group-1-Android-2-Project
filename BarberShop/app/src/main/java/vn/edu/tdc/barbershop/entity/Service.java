package vn.edu.tdc.barbershop.entity;

public class Service {
    private String ID;
    private String name;
    private String image;
    private double price;
    private String description;

    public Service(String ID, String name, String image, double price, String description) {
        this.ID = ID;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
    }
    public Service(){

    }
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
