package vn.edu.tdc.barbershop.entity;

import java.io.Serializable;
import java.util.Objects;

public class Service implements Serializable {
    private String ID;
    private String name;
    private String image;
    private double price;
    private String description;
    private String create_at;
    private int time;

    public Service() {

    }

    public Service(String ID, String name, String image, double price, String description) {
        this.ID = ID;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
    }

    public Service(String ID, String name, String image, double price, String description, String create_at, int time) {
        this.ID = ID;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.create_at = create_at;
        this.time = time;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(ID, service.ID);
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
