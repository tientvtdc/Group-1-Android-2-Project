package vn.edu.tdc.barbershop.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vn.edu.tdc.barbershop.entity.Schedule;

public class ScheduleModel {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final static String NAME_TABLE = "orders";

    public void addSchedule(Schedule schedule, IScheduleListennerModel listennerModel) {
        DatabaseReference databaseReference = database.getReference(NAME_TABLE);
        String id = databaseReference.push().getKey();
        schedule.setId(id);

        databaseReference.child(schedule.getId()).setValue(schedule, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                listennerModel.onCompleteRegisterUser(error);
            }
        });
    }


    //thong bao
    public interface IScheduleListennerModel{
        void onCompleteRegisterUser(DatabaseError error);
    };
}
