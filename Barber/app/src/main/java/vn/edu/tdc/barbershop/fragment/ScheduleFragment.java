package vn.edu.tdc.barbershop.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.adapter.ScheduleAdapter;
import vn.edu.tdc.barbershop.models.Schedule;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rcvSchedule;
    private ScheduleAdapter scheduleAdapter;
    private ArrayList<Schedule> scheduleArrayList;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        rcvSchedule = view.findViewById(R.id.fragment_rcv);
        rcvSchedule.setLayoutManager(new LinearLayoutManager(getContext()));

        scheduleArrayList = new ArrayList<>();
        scheduleArrayList.add(new Schedule(R.drawable.anh1, "303 Nguyễn Trãi, Q.Thanh Xuân, Hà Nội", "20:30 | 15/05/2022 | 160k"));
        scheduleArrayList.add(new Schedule(R.drawable.anh2, "303 Nguyễn Trãi, Q.Thanh Xuân, Hà Nội", "20:30 | 15/05/2022 | 160k"));
        scheduleArrayList.add(new Schedule(R.drawable.anh1, "303 Nguyễn Trãi, Q.Thanh Xuân, Hà Nội", "20:30 | 15/05/2022 | 160k"));
        scheduleArrayList.add(new Schedule(R.drawable.anh2, "303 Nguyễn Trãi, Q.Thanh Xuân, Hà Nội", "20:30 | 15/05/2022 | 160k"));
        scheduleArrayList.add(new Schedule(R.drawable.anh1, "303 Nguyễn Trãi, Q.Thanh Xuân, Hà Nội", "20:30 | 15/05/2022 | 160k"));
        scheduleArrayList.add(new Schedule(R.drawable.anh1, "303 Nguyễn Trãi, Q.Thanh Xuân, Hà Nội", "20:30 | 15/05/2022 | 160k"));
        scheduleArrayList.add(new Schedule(R.drawable.anh2, "303 Nguyễn Trãi, Q.Thanh Xuân, Hà Nội", "20:30 | 15/05/2022 | 160k"));
        scheduleArrayList.add(new Schedule(R.drawable.anh1, "303 Nguyễn Trãi, Q.Thanh Xuân, Hà Nội", "20:30 | 15/05/2022 | 160k"));
        scheduleArrayList.add(new Schedule(R.drawable.anh2, "303 Nguyễn Trãi, Q.Thanh Xuân, Hà Nội", "20:30 | 15/05/2022 | 160k"));
        scheduleArrayList.add(new Schedule(R.drawable.anh1, "303 Nguyễn Trãi, Q.Thanh Xuân, Hà Nội", "20:30 | 15/05/2022 | 160k"));
        scheduleArrayList.add(new Schedule(R.drawable.anh1, "303 Nguyễn Trãi, Q.Thanh Xuân, Hà Nội", "20:30 | 15/05/2022 | 160k"));
        scheduleArrayList.add(new Schedule(R.drawable.anh2, "303 Nguyễn Trãi, Q.Thanh Xuân, Hà Nội", "20:30 | 15/05/2022 | 160k"));
        scheduleArrayList.add(new Schedule(R.drawable.anh1, "303 Nguyễn Trãi, Q.Thanh Xuân, Hà Nội", "20:30 | 15/05/2022 | 160k"));
        scheduleArrayList.add(new Schedule(R.drawable.anh2, "303 Nguyễn Trãi, Q.Thanh Xuân, Hà Nội", "20:30 | 15/05/2022 | 160k"));
        scheduleArrayList.add(new Schedule(R.drawable.anh1, "303 Nguyễn Trãi, Q.Thanh Xuân, Hà Nội", "20:30 | 15/05/2022 | 160k"));

        scheduleAdapter = new ScheduleAdapter(scheduleArrayList);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvSchedule.addItemDecoration(itemDecoration);

        rcvSchedule.setAdapter(scheduleAdapter);
        return view;
    }
}