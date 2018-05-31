package com.gmail.timothy10.silas.hymnbook.view;

import java.util.ArrayList;

import com.gmail.timothy10.silas.hymnbook.model.MXLparser;
import com.gmail.timothy10.silas.hymnbook.model.ZipInputStreamImpl;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ScrollView;
import uk.co.dolphin_com.sscore.SScore;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * the current magnification.
     * <p>
     * Preserved to avoid reload on rotate (which causes complete destruction and recreation of this Activity)
     */
    private float magnification;

    /**
     * the View which displays the score
     */
    private SeeScoreView ssview;

    /**
     * to parse the mxl files into xml files to be read by sscore
     */
    private MXLparser mxLparser;

    /**
     * ZipInputStreamUtil for unzipping mxl files into xml files to be read by seescore
     */
    private ZipInputStreamImpl zipInputStream = new ZipInputStreamImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Creating the cursor view
        final CursorView cursorView = new CursorView(this, new CursorView.OffsetCalculator() {
            public float getScrollY() {
                final ScrollView sv = findViewById(R.id.mainScrollView);
                return sv.getScrollY();
            }
        });

        ssview = new SeeScoreView(this, cursorView, getAssets(), new SeeScoreView.ZoomNotification(){

            public void zoom(float scale) {
                magnification = scale;
            }

        }, new SeeScoreView.TapNotification() {
            public void tap(int systemIndex, int partIndex, int barIndex, Component[] components) {
                System.out.println("tap system:" + systemIndex + " bar:" + barIndex);

                for (Component comp : components)
                    System.out.println(comp);
            }

            public void longTap(int systemIndex, int partIndex, int barIndex, Component[] components) {

                invalidateOptionsMenu();
            }
        });

        final ScrollView scrollView = findViewById(R.id.mainScrollView);
        scrollView.addView(ssview);

        mxLparser = new MXLparser(this);
        SScore sScore = mxLparser.generateSScore(4);
        ArrayList parts = new ArrayList<Boolean>();

        for (int i = 0; i < sScore.numParts(); ++i)
            parts.add(true);

        ssview.setScore(sScore, parts, 0.5f);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * load the SeeScoreLib.so library
     */
    static {
        System.loadLibrary("stlport_shared");
        System.loadLibrary("SeeScoreLib");
    }
}
