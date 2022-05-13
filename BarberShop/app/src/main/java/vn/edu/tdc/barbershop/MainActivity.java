package vn.edu.tdc.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import vn.edu.tdc.barbershop.adapter.UserAdapter;
import vn.edu.tdc.barbershop.fragment.HomeFragment;
import vn.edu.tdc.barbershop.fragment.ScheduleFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_SCHEDULE = 1;
//    repass
    private static final int FRAGMENT_REPASS = 1;

    private int mCurrentFragment = 0;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:{
                if (mCurrentFragment!=FRAGMENT_HOME){
                    replaceFragment(new HomeFragment());
                    mCurrentFragment = FRAGMENT_HOME;
                }
                break;
                }
            case R.id.nav_address:
                break;
            case R.id.nav_schedule:{
                if (mCurrentFragment!=FRAGMENT_SCHEDULE){
                    replaceFragment(new ScheduleFragment());
                    mCurrentFragment = FRAGMENT_SCHEDULE;
                }
                break;
            }
            case R.id.nav_change_password:{
                if (mCurrentFragment!=FRAGMENT_REPASS){
                    replaceFragment(new ScheduleFragment());
                    mCurrentFragment = FRAGMENT_REPASS;
                }
                break;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }
}