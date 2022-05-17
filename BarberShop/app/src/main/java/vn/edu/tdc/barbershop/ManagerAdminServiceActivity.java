package vn.edu.tdc.barbershop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;


import vn.edu.tdc.barbershop.adapter.ScheduleAdapter;
import vn.edu.tdc.barbershop.models.Schedule;
import vn.edu.tdc.barbershop.models.User;

import java.util.ArrayList;
import java.util.List;

public class ManagerAdminServiceActivity extends AppCompatActivity {


        private RecyclerView rcvFragment;
        private ScheduleAdapter scheduleAdapter;

//    private List<User> mListUsers;
    //private TextView txtName;
    //private TextView txtPhone;
    //private ImageView imgService;
    //private Date txtTimeOder;
    //private Date txtTimeFinish;

    //private Button btnXong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_manament_schedule);

        rcvFragment = findViewById(R.id.fragment_rcv);
        scheduleAdapter = new ScheduleAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvFragment.setLayoutManager(linearLayoutManager);

        scheduleAdapter.setData(getListOder());
        rcvFragment.setAdapter(scheduleAdapter);

        //Xử lý sự kiện cho nút xong
//        btnXong.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        //getListOdersFromRealtimeDatabase();
    }

    private List<Schedule> getListOder() {
        List<Schedule> list = new ArrayList<>();
        list.add(new Schedule("hi"));
        return list;
    }

//    private void initUi(){
//
//        rcvFragment = findViewById(R.id.fragment_rcv);
//        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        rcvFragment.setLayoutManager(linearLayoutManager);
//
//        ListOders = new ArrayList<>();
//        scheduleAdapter = new OderAdapter(mListUsers, new OderAdapter.IClickListener() {
//
//
//            @Override
//            public void onClickXongItem(Oder oder) {
//                onClickDeleteData(oder);
//            }
//        });
//        rcvFragment.setAdapter(scheduleAdapter);
//    }

    //hàm lấy danh sách lịch hẹn trên database
//    private void getListOdersFromRealtimeDatabase(){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("");

//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                Oder oder = snapshot.getValue(Oder.class);
//                if (oder != null || ListOders == null || ListOders.isEmpty()){
//                    return;
//                }
//                for (int i = 0; i < ListOders.size() ; i++){
//                    if (oder.getId() == ListOders.get(i).getId())
//                    ListOders.remove(ListOders.get(i));
//                    break;
//                }
//                scheduleAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                Oder oder = snapshot.getValue(Oder.class);
//                if (oder != null || ListOders == null || ListOders.isEmpty()){
//                    return;
//                }
//                for (int i = 0; i < ListOders.size() ; i++){
//                    if (user.getId() == ListOders.get(i).getId())
//                        ListOders.set(i, user);
//                }
//                scheduleAdapter.notifyDataSetChanged();
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

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(ManagerAdminServiceActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(ManagerAdminServiceActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // Dữ liệu giả
    private List<User> getListUser(){
        List<User> list = new ArrayList<>();

//        list.add(new User(R.drawable.anh1, "Tên khách hàng 1"));

        return list;
    }



//thong bao da hoan thanh hay chua

//    private void onClickXongData(Oder oder){
//        new AlertDialog.Builder(this)
//                .setTitle(getString(R.string.app_name))
//                .setMessage("Chắc chắn đã hoàn thành chưa")
//                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        FirebaseDatabase database = FirebaseDatabase.getInstance();
//                        DatabaseReference myRef = database.getReference("");
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