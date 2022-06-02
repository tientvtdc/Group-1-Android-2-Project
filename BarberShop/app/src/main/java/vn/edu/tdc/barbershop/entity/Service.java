package vn.edu.tdc.barbershop.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Service implements Serializable {
    private String ID;
    private String name;
    private String image;
    private double price;
    private String description;
    private int time;
    private String create_at;

    public Service(String ID, String name, String image, double price, String description, int time) {
        this.ID = ID;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.time = time;
        this.create_at = (new Timestamp((new Date()).getTime())).toString();
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }
}
