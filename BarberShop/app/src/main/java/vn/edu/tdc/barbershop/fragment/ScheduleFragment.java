package vn.edu.tdc.barbershop.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.adapter.ScheduleAdapter;
import vn.edu.tdc.barbershop.entity.Schedule;

public class ScheduleFragment extends Fragment {


    // TODO: Rename and change types of parameters
    private RecyclerView rcvSchedule;
    private ScheduleAdapter scheduleAdapter;
    private List<Schedule> scheduleArrayList = new ArrayList<Schedule>();

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        rcvSchedule = view.findViewById(R.id.fragment_rcv);
        rcvSchedule.setLayoutManager(new LinearLayoutManager(getContext()));

        getScheduleArrayList();

        scheduleAdapter = new ScheduleAdapter(scheduleArrayList, getContext());

        rcvSchedule.setAdapter(scheduleAdapter);
        return view;
    }

    private void getScheduleArrayList() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("orders");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Schedule schedule = snapshot.getValue(Schedule.class);
                if (schedule != null) {
                    scheduleArrayList.add(schedule);
                    scheduleAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Schedule schedule = snapshot.getValue(Schedule.class);
                if (schedule != null) {
                    if (scheduleArrayList == null || scheduleArrayList.isEmpty()) return;

                    for (int i = 0; i < scheduleArrayList.size(); i++) {
                        if (scheduleArrayList.get(i).getId().equals(schedule.getId())) {
                            scheduleArrayList.set(i, schedule);
                        }
                    }
                    scheduleAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (scheduleAdapter != null) {
            scheduleAdapter.release();
        }
    }
}