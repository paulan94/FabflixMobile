package edu.uci.ics.fabflixmobile;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

public class MainPageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Bundle bundle = getIntent().getExtras();
        Toast.makeText(this, "Welcome " + bundle.get("welcome") + ".", Toast.LENGTH_LONG).show();

    }

}
