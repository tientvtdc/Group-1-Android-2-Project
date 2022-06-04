package vn.edu.tdc.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import vn.edu.tdc.barbershop.fragment.HomeFragment;
import vn.edu.tdc.barbershop.fragment.OrderFragment;

public class CusomerScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_SCHEDULE = 1;
    private static final int FRAGMENT_ADDRESS = 2;

    private int mCurrentFragment = 0;

    private DrawerLayout mDrawerLayout;
    private ImageView imageViewBGToolbar;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    //logout and login
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        imageViewBGToolbar = findViewById(R.id.img_backgound_collapsing_toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        toolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getColor(R.color.white));

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        collapsingToolbarLayout.setTitle(getString(R.string.app_name));
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home: {
                if (mCurrentFragment != FRAGMENT_HOME) {
                    imageViewBGToolbar.setImageResource(R.drawable.bg_header);
                    collapsingToolbarLayout.setTitle(getString(R.string.app_name));
                    replaceFragment(new HomeFragment());
                    mCurrentFragment = FRAGMENT_HOME;
                }
                break;
            }
            case R.id.nav_address:
                break;
            case R.id.nav_schedule: {
                if (mCurrentFragment != FRAGMENT_SCHEDULE) {
                    collapsingToolbarLayout.setTitle(getString(R.string.nav_schedule));
                    replaceFragment(new OrderFragment());
                    mCurrentFragment = FRAGMENT_SCHEDULE;
                    imageViewBGToolbar.setImageResource(R.drawable.schedule_bg);
                }
                break;
            }
            case R.id.nav_log_out: {
                break;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}