package vn.edu.tdc.barbershop.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vn.edu.tdc.barbershop.entity.Service;

public class ServiceModel {
    private DatabaseReference database;
    public final static String NAME_TABLE = "services";

    public ServiceModel() {
        this.database = FirebaseDatabase.getInstance().getReference(NAME_TABLE);
    }

    public void addNewSevice(String name,String image, Double price,String description) {
        String id = database.push().getKey();
        Service service = new Service(id, name, image, 100000, description);
        database.child(id).setValue(service);
    }
}
