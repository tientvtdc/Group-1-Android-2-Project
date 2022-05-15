package vn.edu.tdc.barbershop.models;

import java.util.HashMap;
import java.util.Map;

public class User {

//Thêm những trường còn lại vào đây, alt enter 2
    private int id;

    private int resourceId;
    private String name;



    public User(){

    }

    public User(int resourceId, String name) {
        this.id = id;

        this.resourceId = resourceId;
        this.name = name;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getSdt() {
//        return sdt;
//    }
//
//    public void setSdt(String sdt) {
//        this.sdt = sdt;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);

        return result;
    }


}
