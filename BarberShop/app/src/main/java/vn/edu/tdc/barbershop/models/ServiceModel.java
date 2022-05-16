package vn.edu.tdc.barbershop.models;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import vn.edu.tdc.barbershop.AddNewServiceActivity;
import vn.edu.tdc.barbershop.ServiceListActivity;
import vn.edu.tdc.barbershop.adapter.ServiceAdpapter;
import vn.edu.tdc.barbershop.entity.Service;

public class ServiceModel {
    private DatabaseReference database;
    public final static String NAME_TABLE = "services";


    public ServiceModel() {
        this.database = FirebaseDatabase.getInstance().getReference(NAME_TABLE);
    }

    public void addNewSevice(String name,String image, Double price,String description,IServiceListennerModel iServiceListennerModel) {
        String id = database.push().getKey();
        Service service = new Service(id, name, image, price, description);
        database.child(id).setValue(service, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                iServiceListennerModel.onCompleteAddService(error);
            }
        });
    }

    public void editServiceWithID(String id,String name,String image, Double price,String description,IServiceListennerModel iServiceListennerModel) {
        Service service = new Service(id, name, image, price, description);
        database.child(id).setValue(service, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                iServiceListennerModel.onCompleteAddService(error);
            }
        });
    }

    public interface IServiceListennerModel{
        void onCompleteAddService(DatabaseError error);
    };

    public interface IClickItemListener {

        void onClickItemService(Service service);
    }



}

