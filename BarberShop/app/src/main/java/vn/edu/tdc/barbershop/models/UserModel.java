package vn.edu.tdc.barbershop.models;

import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vn.edu.tdc.barbershop.entity.User;

public class UserModel {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final static String NAME_TABLE = "users";

    public void regiterUser(User user, IUserListennerModel iUserListennerModel) {
        DatabaseReference databaseReference = database.getReference(NAME_TABLE);
//        String id = databaseReference.push().getKey();
//        user.setId(id);

        databaseReference.child(user.getId()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                iUserListennerModel.onCompleteRegisterUser(error);
            }
        });
    }

    public interface IUserListennerModel{
        void onCompleteRegisterUser(DatabaseError error);
    };
}
