package com.ffl.nytimesearch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ffl.nytimesearch.Adapters.ArticleArrayAdapter;
import com.ffl.nytimesearch.R;
import com.ffl.nytimesearch.listerners.RecyclerItemClickListerner;
import com.ffl.nytimesearch.models.Article;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity
{


    public static String api_key="c620da4ef2bb44b2bab087176d14671b";
    public static String baseUrl="http://api.nytimes.com/svc/search/v2/articlesearch.json";

    EditText etQuery;
    Button btnSearch;
    RecyclerView rvResults;
    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViews();

    }
    public void setupViews()
    {
        articles=new ArrayList<>();
        rvResults=(RecyclerView)findViewById(R.id.rvResults);
        btnSearch=(Button)findViewById(R.id.btnsearch);
        etQuery=(EditText)findViewById(R.id.editQuery);

        adapter=new ArticleArrayAdapter(articles);
        rvResults.setAdapter(adapter);
        rvResults.addOnItemTouchListener(
                new RecyclerItemClickListerner (this, rvResults,
                        new RecyclerItemClickListerner.OnItemClickListerner() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                                Article article = articles.get(position);
                                i.putExtra("article", article);
                                startActivity(i);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                // ...
                            }
                        }));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem=menu.findItem(R.id.action_search);
        final SearchView searchView=(SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
// workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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
    public void onArticleSearch(View view){
    String query=etQuery.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("q", query);
        params.put("api-key",api_key);
        client.get(baseUrl,params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        JSONArray articleJsonResults = null;
                        try {
                           articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                            adapter.swap(Article.fromJSONArray(articleJsonResults));
                            Log.d("DEBUG",articles.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }



}
