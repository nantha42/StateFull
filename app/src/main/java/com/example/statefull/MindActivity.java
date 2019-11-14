package com.example.statefull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

public class MindActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, itemClickListener {
    DrawerLayout drawer;
    NavigationView navigationView;
    TextView username;
    MoodFragment fragment;
    int alreadySelected = R.id.nav_today;
    int currentmood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mind);
        currentmood = 5;
        int id = 0;
        if (getIntent() != null) {
            Log.d("Start dialog", "" + getIntent().getStringExtra("EXTRA_START_DIALOG"));
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        username = headerView.findViewById(R.id.username);
        username.setText(DatabaseManager.databaseManager.getUserName());
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TodayFragment(-1)).commit();
            navigationView.setCheckedItem(R.id.nav_today);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (id == R.id.action_signout) {
            DatabaseManager.databaseManager.logout();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.d("Called", "ONNavigationItemSelected" + menuItem.getItemId());
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        if (alreadySelected != menuItem.getItemId()) {
            switch (menuItem.getItemId()) {
                case R.id.nav_today:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TodayFragment(-1)).commit();
                    navigationView.setCheckedItem(R.id.nav_today);
                    break;

                case R.id.nav_diary:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewDiaryFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_diary);
                    break;
                case R.id.nav_events:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewEventsFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_events);
                    break;
                case R.id.nav_goals:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewGoalsFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_goals);
                    break;
                case R.id.nav_reminders:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReminderFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_reminders);
                    break;

                case R.id.nav_about:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_about);
                    break;
                case R.id.nav_insight:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InsightFragment()).commit();
                    break;
            }
            drawer.closeDrawer(GravityCompat.START);
            alreadySelected = menuItem.getItemId();
        }


        return true;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onNoteClick(int id) {
        Log.d("Clickedfine", id + " ");
    }
}
