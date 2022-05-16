package vn.edu.tdc.barbershop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vn.edu.tdc.barbershop.adapter.OderAdapter;
import vn.edu.tdc.barbershop.adapter.UserAdapter;
import vn.edu.tdc.barbershop.models.Oder;
import vn.edu.tdc.barbershop.models.User;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ManagerAdminServiceActivity extends AppCompatActivity {

        private RecyclerView rcvFragment;
        private OderAdapter oderAdapter;
//    private List<User> mListUsers;

    //private EditText edtId, edtName;
    //private Button btnAddUser;
    //private  Button btnDelete;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_oder);

        rcvFragment = findViewById(R.id.fragment_rcv);
        oderAdapter = new OderAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvFragment.setLayoutManager(linearLayoutManager);

        oderAdapter.setData(getListOder());
        rcvFragment.setAdapter(oderAdapter);


//        Dữ liệu
//        mUserAdapter.setData(getListUser());
//        rcvUser.setAdapter(mUserAdapter);


        //initUi();
//        btnAddUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int id = Integer.parseInt(edtId.getText().toString().trim());
//                String name = edtName.getText().toString().trim();
//                User user = new User(id, name);
//
//                onClickAddUser(user);
//            }
//        });

//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        //getListUsersFromRealtimeDatabase();
    }

    private List<Oder> getListOder() {
        List<Oder> list = new ArrayList<>();
        list.add(new Oder("hi"));
        return list;
    }

//    private void initUi(){
////        edtId = findViewById(R.id.edt_id);
////        edtName = findViewById(R.id.edt_name);
////        btnAddUser = findViewById(R.id.btn_add_user);
//
//        rcvUser = findViewById(R.id.fragment_rcv);
//        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        rcvUser.setLayoutManager(linearLayoutManager);
//
//        mListUsers = new ArrayList<>();
//        mUserAdapter = new UserAdapter(mListUsers, new UserAdapter.IClickListener() {
//            @Override
//            public void onClickUpdateItem(User user) {
//                openDialogUpdateItem(user);
//            }
//
//            @Override
//            public void onClickDeleteItem(User user) {
//                onClickDeleteData(user);
//            }
//        });
//        rcvUser.setAdapter(mUserAdapter);
//    }

    //Them user
//    private void onClickAddUser(User user){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("list_users");
//
//        String pathObject = String.valueOf(user.getResourceId());
//        myRef.child(pathObject).setValue(user, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                Toast.makeText(AdminActivity.this, "Add thanh cong", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void onClickAddAllUser(){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("list_users");
//
//        List<User> list = new ArrayList<>();
//        list.add(new User(1, "user 1"));
//        list.add(new User(2, "user 2"));
//        list.add(new User(3, "user 3"));
//
//        myRef.setValue(list, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                Toast.makeText(AdminActivity.this, "Add all thanh cong", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    private void getListUsersFromRealtimeDatabase(){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("list_users");
//
//        //cach 1
////        myRef.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                if (mListUsers != null){
////                    mListUsers.clear();
////                }
////
////                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
////                    User user = dataSnapshot.getValue(User.class);
////                    mListUsers.add(user);
////                }
////                mUserAdapter.notifyDataSetChanged();
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////                Toast.makeText(AdminActivity.this, "get list faild", Toast.LENGTH_SHORT).show();
////            }
////        });
//
//        //cach2
//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                User user = snapshot.getValue(User.class);
//                if (user != null){
//                    mListUsers.add(user);
//                    mUserAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                User user = snapshot.getValue(User.class);
//                if (user != null || mListUsers == null || mListUsers.isEmpty()){
//                    return;
//                }
//                for (int i = 0; i < mListUsers.size() ; i++){
//                    if (user.getId() == mListUsers.get(i).getId())
//                    mListUsers.remove(mListUsers.get(i));
//                    break;
//                }
//                mUserAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//                if (user != null || mListUsers == null || mListUsers.isEmpty()){
//                    return;
//                }
//                for (int i = 0; i < mListUsers.size() ; i++){
//                    if (user.getId() == mListUsers.get(i).getId())
//                        mListUsers.set(i, user);
//                }
//                mUserAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item){
//        switch (item.getItemId()){
//            case android.R.id.home:
//                Intent intent = new Intent(ManagerAdminServiceActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    public void onBackPressed(){
//        Intent intent = new Intent(ManagerAdminServiceActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();
//    }
//
//    // Dữ liệu giả
//    private List<User> getListUser(){
//        List<User> list = new ArrayList<>();
//
////        list.add(new User(R.drawable.anh1, "Tên khách hàng 1"));
////        list.add(new User(R.drawable.anh2, "Tên khách hàng 2"));
//
//        return list;
//    }
//
////    Thong bao
//    private void openDialogUpdateItem(User user){
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.layout_dialog_update);
//        Window window = dialog.getWindow();
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(false);
//
//        EditText edtUpdateName = dialog.findViewById(R.id.edt_update_name);
//        Button btnUpdate = dialog.findViewById(R.id.btn_update);
//        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
//
//        edtUpdateName.setText(user.getName());
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference("list_users");
//
//                String newName = edtUpdateName.getText().toString().trim();
//                user.setName(newName);
//
//                myRef.child(String.valueOf(user.getId())).updateChildren(user.toMap(), new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                        Toast.makeText(ManagerAdminServiceActivity.this, "update thanh cong", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                    }
//                });
//            }
//        });
//
//
//        dialog.show();
//    }
//
//    private void onClickDeleteData(User user){
//        new AlertDialog.Builder(this)
//                .setTitle(getString(R.string.app_name))
//                .setMessage("Chắc chắn đã hoàn thành chưa")
//                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        FirebaseDatabase database = FirebaseDatabase.getInstance();
//                        DatabaseReference myRef = database.getReference("list_users");
//
//                        myRef.removeValue(new DatabaseReference.CompletionListener() {
//                            @Override
//                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                                Toast.makeText(ManagerAdminServiceActivity.this, "Đã hoàn thành", Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//                    }
//                })
//                .setNegativeButton("Quay lại", null)
//                .show();
//    }

//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.activity_admin, container, false);
//    }

}