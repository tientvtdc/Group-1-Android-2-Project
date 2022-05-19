package vn.edu.tdc.barbershop.entity;

import java.io.Serializable;

public class User implements Serializable {

    private String id;
    private String name;
    private String phone;
    private String image;
    private int role;

    public User() {

    }

    public User(String id, String name, String phone, String image, int role) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
