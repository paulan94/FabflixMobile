package edu.uci.ics.fabflixmobile;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainPageActivity extends ActionBarActivity {

    private TextView mWelcomeTextView;
    private EditText mEditText;
    private ImageButton mButton;
//    private SearchView.SearchAutoComplete mSearchTextView;
    private ListView mListView;
    private Context mContext;
    private Singleton mSingleton;

    String inc_url = "";

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



        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    inc_url = "";
                    mListView.clearChoices();
                    connectToTomcatSearch();
                } catch (UnsupportedEncodingException e) {

                    e.printStackTrace();
                }

            }
        });

        //need to move this so it updates when user searches. SHOULD NOT BE IN ONCREATE
        ArrayAdapter<CharSequence> aa = ArrayAdapter.createFromResource(this, R.array.movie_titles, android.R.layout.simple_list_item_1);
        mListView.setAdapter(aa);

        //show welcome
        Bundle bundle = getIntent().getExtras();
        Toast.makeText(this, "Welcome " + bundle.get("welcome") + ".", Toast.LENGTH_LONG).show();

    }

    public void connectToTomcatSearch() throws UnsupportedEncodingException {
        final String search_text = mEditText.getText().toString();
        final String url_string = java.net.URLEncoder.encode(search_text, "utf-8");

        String url = "http://52.25.32.130:8080/Fabflix/servlet/Pagination?query=SELECT+*+FROM+movies+as+m+WHERE+m.title+LIKE+%27%25" +
                url_string + "%25%27&pageIndex=0&limitValue=1000&NextPrev=Next";

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int index = 0;
                        Log.d("responsetomcat", response);
                        if (response.contains("title submitLink")) {
                            // TODO: grab data first before incrementing index
                            inc_url += response;
                            //while response.contains titlesubmit link, increment i by 1, new function that gets new url and returns
                            //increment index by 1, do another getrequest for next page.
//                            incrementConnect(index, url_string, new VolleyCallback(){
//                                @Override
//                                public void onSuccess(String result) {
//                                    //check result
//                                    Log.d("resultsuccess", inc_url);
//
//                                }
//                            });

                            //TODO: combine all arrays and use this to show in ListView
                            List<String> movieTitles = getMovieTitles(inc_url);
                            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(MainPageActivity.this,
                                    android.R.layout.simple_list_item_1, movieTitles);
                            mListView.setAdapter(itemsAdapter);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("abd", "Error: " + error
                                + "\n>>" + error.networkResponse.statusCode
                                + "\n>>" + error.networkResponse.data
                                + "\n>>" + error.getCause()
                                + "\n>>" + error.getMessage());
                    }
                });
// add it to the RequestQueue
        Log.d("get request: ", getRequest.toString());
        mSingleton.getInstance(this).addToRequestQueue(getRequest);
        mListView = (ListView) findViewById(R.id.movie_listview);
        return;


    }
//    public void incrementConnect(int index, String url_string, final VolleyCallback volleyCallback){
//
//        String url = "http://52.25.32.130:8080/Fabflix/servlet/Pagination?query=SELECT+*+FROM+movies+as+m+WHERE+m.title+LIKE+%27%25" +
//                url_string + "%25%27&pageIndex="+ index + "&limitValue=100&NextPrev=Next";
//
//        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("responseinctomcat", response);
//                        inc_url += response;
////                        result = response;
//                        volleyCallback.onSuccess(inc_url);
//                        //add to arraylist
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("abd", "Error: " + error
//                                + "\n>>" + error.networkResponse.statusCode
//                                + "\n>>" + error.networkResponse.data
//                                + "\n>>" + error.getCause()
//                                + "\n>>" + error.getMessage());
//                        // Handle error
//                    }
//                });
//
//// add it to the RequestQueue
//        Log.d("get request: ", getRequest.toString());
//
//        mSingleton.getInstance(this).addToRequestQueue(getRequest);
//
//        return;
//    }

    //TODO: add these into strings.xml file or pull data to show onto listview.
    public List<String> getMovieTitles(String http_response) {
        List<String> titleArray = new ArrayList<String>();

        //TODO: get only title, and stop at
        BufferedReader bufReader = new BufferedReader(new StringReader(http_response));
        String line = null;
        try {
            while ((line = bufReader.readLine()) != null){
                if (line.contains("title submitLink")){
                    String pattern1 = "value=\"";
                    String pattern2 = "\" name=\"";
                    //regex to grab text
                    String regexString = Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2);
                    Pattern pattern = Pattern.compile(regexString);
                    // text contains the full text that you want to extract data
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        String textInBetween = matcher.group(1); // Since (.*?) is capturing group 1
                        // You can insert match into a List/Collection here
                        titleArray.add(textInBetween);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return titleArray;
    }

    public interface VolleyCallback{
        void onSuccess(String result);
    }
}
