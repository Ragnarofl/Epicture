package com.example.aled;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import net.azzerial.jmgur.api.Jmgur;
import net.azzerial.jmgur.api.JmgurBuilder;
import net.azzerial.jmgur.api.OAuth2;
import com.example.aled.Api;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "53ede3b1bc77a8b";
    private static final String TAG = "JMGUR_APP_TAG";
    public static Api my_api = new Api();
    private FirstFragment main_page;
    private SecondFragment user_page;
    private FavoriteFragment favorite_page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();

        Log.i(TAG, "Opening to OAuth2 login");

        final FrameLayout root = new FrameLayout(this);
        final WebView webView = new WebView(this);
        LinearLayout wrapper = new LinearLayout(this);
        EditText keyboardHack = new EditText(this);

        keyboardHack.setVisibility(View.GONE);
        final String oauthUrl = "https://api.imgur.com/oauth2/authorize?client_id=" + CLIENT_ID + "&response_type=token";
        //root.addView(webView);
        //setContentView(root);
        webView.getSettings().setJavaScriptEnabled(true);
        //WebSettings settings = webView.getSettings();
        //settings.setDomStorageEnabled(true);
        webView.loadUrl(oauthUrl);
        wrapper.setOrientation(LinearLayout.VERTICAL);
        wrapper.addView(webView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        wrapper.addView(keyboardHack, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int choice) {
                    switch (choice) {
                        case DialogInterface.BUTTON_POSITIVE:
                            break;
                    }
                }
            };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(wrapper)
                .setNeutralButton("Close Connexion", dialogClickListener)
                .show();
        webView.setWebViewClient(new WebViewClient() {
           @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                final String url = request.getUrl().toString();

                Log.i(TAG, "Redirected WebView on: " + url);
                boolean tmp = my_api.set_client_id(request);
                if (tmp == true) {
                    my_api.getAccount();
                    main_page = new FirstFragment();
                    user_page = new SecondFragment();
                    favorite_page = new FavoriteFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, main_page).commit();
                } else {
                  //  setContentView(R.layout.activity_main);
                    return false;
                }
                //setContentView(R.layout.activity_main);
                return true;
            }
        });
        Log.i(TAG, "Opened WebView on: " + oauthUrl);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setBackgroundDrawableResource(R.drawable.layout);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = main_page;
                            break;
                        case R.id.nav_account:
                            selectedFragment = user_page;
                            break;
                        case R.id.nav_favorite:
                            selectedFragment = favorite_page;
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                             selectedFragment).commit();

                    return true;
                }
            };
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //final WebView webView = new WebView(this);
        //final Api api = new Api(webView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.option_filter:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}