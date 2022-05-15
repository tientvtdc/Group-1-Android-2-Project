package vn.edu.tdc.barbershop.models;

import java.util.HashMap;
import java.util.Map;

public class User {

//Thêm những trường còn lại vào đây, alt enter 2
    private int id;
    private String name;
    private String phone;
    private String password;
    private String image;
    private int role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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



    public User(int id, String name, String phone, String password, String image, int role) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.image = image;
        this.role = role;
    }

    public User(){

    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);

        return result;
    }


}
