package com.example.studentsmoney.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.studentsmoney.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * The main activity for working with app.
 * There is <b>only one</b>  activity in this app,
 * user can switch between fragments using
 * BottomNavBar or using buttons.
 *
 * @author MarkYav
 * @version 1.0
 * @since 2021-01-25
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Use this method is used to:
     * <ul>
     *     <li>set up {@link android.widget.Toolbar}</li>
     *     <li>set up {@link BottomNavigationView}</li>
     *     <li>set up navigation between fragments, using {@link BottomNavigationView}</li>
     * </ul>
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO setup ToolBur
        //Toolbar toolbar = new Toolbar();
        //ActionBar toolbar = getSupportActionBar();
        //toolbar.setLogo();

        //код от гугла (скопированый) - не трогать
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_menu);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_statistic, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}