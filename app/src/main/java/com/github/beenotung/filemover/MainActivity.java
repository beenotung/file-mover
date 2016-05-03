package com.github.beenotung.filemover;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import com.github.beenotung.utils.Lang;
import com.github.beenotung.utils.Lang.Consumer;

import java.io.IOException;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;

import static java.net.NetworkInterface.getNetworkInterfaces;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {
  private TextView debug_tv;
  private static final int MODE_INCOMING = 0;
  private static final int MODE_OUTGOING = 1;
  private int direction_mode = 1;
  private static final String[] MODE_STRINGS = {"<----", "---->"};
  private TextView mobileaddress_tv;
  private TextView pcaddress_tv;

  private void debug(final String msg) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        debug_tv.setText("debug: " + msg);
      }
    });
  }

  private void info(final String msg) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        debug_tv.setText("info: " + msg);
      }
    });
  }

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
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    /* init var */
    debug_tv = (TextView) findViewById(R.id.debug_tv);
    Button setpath_mobile_btn = (Button) findViewById(R.id.setpath_mobile_btn);
    Button setpath_pc_btn = (Button) findViewById(R.id.setpath_pc_btn);
    final Button direction_btn = (Button) findViewById(R.id.direction_btn);
    mobileaddress_tv = (TextView) findViewById(R.id.mobileaddress_tv);
    pcaddress_tv = (TextView) findViewById(R.id.pcaddress_tv);

    /* init listener*/
    setpath_mobile_btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });
    setpath_pc_btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });
    direction_btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        direction_mode = 1 - direction_mode;
        direction_btn.setText(MODE_STRINGS[direction_mode]);
      }
    });

    /* init value */
    //debug_tv.setText("");
    direction_btn.setText(MODE_STRINGS[direction_mode]);
    startServer();
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

  private void startServer() {
    debug("start server");
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          debug("start server socket");
          ServerSocket server = new ServerSocket(0);
          debug("port=" + server.getLocalPort());
          final String address = getMobileAddress(server.getLocalPort());
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              mobileaddress_tv.setText(address);
            }
          });
          while (true) {
            Socket socket = server.accept();
            debug("socket client connected" + socket.getRemoteSocketAddress());
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  private String getMobileAddress(int port) {
    try {
      for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
        if (!networkInterface.isLoopback()) {
          for (InetAddress inetAddress : Collections.list(networkInterface.getInetAddresses())) {
            String s = inetAddress.getCanonicalHostName();
            if (!s.contains(":"))
              return s + ":" + port;
          }
        }
      }
    } catch (SocketException e) {
      e.printStackTrace();
    }
    return "";
  }
}
