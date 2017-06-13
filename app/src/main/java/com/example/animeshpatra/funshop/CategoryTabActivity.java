package com.example.animeshpatra.funshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryTabActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentTransaction fragmentTransaction;
    public static final String TAG = "CategoryTabActivity";
    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    NavigationView navigationView;
    String getName, getEmail;
    public static final String DEFAULT = "No data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_tab);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setUpViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        navigationView = (NavigationView) findViewById(R.id.navigationView);
        View view = navigationView.inflateHeaderView(R.layout.navigation_drawer_header);
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String Loginname = sharedPreferences.getString("Name", DEFAULT);
        String Loginemail = sharedPreferences.getString("Email", DEFAULT);
        TextView name = (TextView) view.findViewById(R.id.tv_drawerName);
        TextView email = (TextView) view.findViewById(R.id.tv_drawerEmail);
        name.setText(Loginname);
        email.setText(Loginemail);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_logout:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_content, new LogoutFragment());
                        fragmentTransaction.commit();
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_profile:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_content, new ProfileFragment());
                        fragmentTransaction.commit();
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_cart:
                        startActivity(new Intent(CategoryTabActivity.this, CartItems.class));
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_contact:
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:+919434202606"));
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_contactDeveloper:
                        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                        emailIntent.setType("plain/text");
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"animeshpatra001@gemail.com"});
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "[Fun App] Review/Report");
                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Enter your message here please....");
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_aboutUs:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(CategoryTabActivity.this);
                        dialog.setMessage("This is a shopping app")
                                .setNegativeButton("Okay", null)
                                .create()
                                .show();
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_rateUs:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_content, new RatingFragment());
                        fragmentTransaction.commit();
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_exit:
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(CategoryTabActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(CategoryTabActivity.this);
                        }
                        builder.setTitle("Exit From The App?")
                                .setMessage("Are you sure you want to exit from this app?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        finishAffinity();
                                        System.exit(0);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.shoppingCart){
            startActivity(new Intent(this, CartItems.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpViewPager (ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentMan(), "Man");
        adapter.addFragment(new FragmentWoman(), "Woman");
        adapter.addFragment(new FragmentKids(), "Kids");
        viewPager.setAdapter(adapter);
    }
}
