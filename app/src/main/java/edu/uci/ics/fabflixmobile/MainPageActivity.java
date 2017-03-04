package edu.uci.ics.fabflixmobile;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainPageActivity extends ActionBarActivity {

    private TextView mWelcomeTextView;
    private EditText mEditText;
    private ImageButton mButton;
//    private SearchView.SearchAutoComplete mSearchTextView;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mWelcomeTextView = (TextView) findViewById(R.id.welcome_tv);
        mListView = (ListView)findViewById(R.id.movie_listview);
        mEditText = (EditText)findViewById(R.id.search_view);
        mButton = (ImageButton)findViewById(R.id.search_button);

        //need to move this so it updates when user searches. SHOULD NOT BE IN ONCREATE
        ArrayAdapter<CharSequence> aa = ArrayAdapter.createFromResource(this, R.array.movie_titles, android.R.layout.simple_list_item_1);
        mListView.setAdapter(aa);

        //show welcome
        Bundle bundle = getIntent().getExtras();
        Toast.makeText(this, "Welcome " + bundle.get("welcome") + ".", Toast.LENGTH_LONG).show();

    }

    //change this to match GET request, and to fit SearchResult.java from tomcat servlet file
//    public void connectToTomcatSearch() {
//
//        final Map<String, String> params = new HashMap<String, String>();
//
//        // no user is logged in, so we must connect to the server
//        RequestQueue queue = Volley.newRequestQueue(this);
//
////        final Context context = this;
//        String url = "http://52.25.32.130:8080/TomcatForm/servlet/SearchResult";
//
//        //use to login
//        final String searchRequest = mSearchTextView.getText().toString();
//
//        params.put("searchRequest", searchRequest);
//
//
//        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        Log.d("response", response);
//
////                        if (response.contains("You have entered an invalid email or password")) {
////
////                            Context context = getApplicationContext();
////                            CharSequence text = "Invalid Email or Password!";
////                            int duration = Toast.LENGTH_SHORT;
////
////                            Toast toast = Toast.makeText(context, text, duration);
////                            toast.show();
////
////                        }
////                        else {
////                            goToMainPage(login_uname);
//////                            continue with going to new activity.
////                            //create new activity and go there.
////                        }
//                        //if this response contains You have entered an invalid email dont go to new activity
////                        ((TextView)findViewById(R.id.http_response)).setText(response);
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        Log.d("security.error", error.toString());
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("searchRequest", searchRequest);
//
//
//                return params;
//            }
//        };
//
//
//        // Add the request to the RequestQueue.
//        queue.add(postRequest);
//
//        return;
//    }



}
