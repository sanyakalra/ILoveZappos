package com.example.sanya.challenge2;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanya.challenge2.POJO.Model;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sanya on 8/2/17.
 */

public class SearchActivity extends AppCompatActivity{
    String url ="https://api.zappos.com";
    private int i, j , commonIndex;
    private SearchView searchView;

    String pName="", pPrice="", pBname="";
    private TextView b1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        SearchManager searchManager =(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =(SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setPadding(0,10,10,10);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query){
                searchView.clearFocus();
                runRestAPI(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return true;
    }

    public void runRestAPI(final String temp)
    {


        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(url).build();
        RestInterface restInterface = adapter.create(RestInterface.class);
        restInterface.getProductDetails(new Callback<Model>() {
            @Override
            public void success(Model model, Response response)
            {
                List<String> words = new ArrayList<String>();
                words = getSplitWords(temp);
                List<Integer> brandIndex = new ArrayList<Integer>();
                List<Integer> productnameIndex = new ArrayList<Integer>();
                int len = model.getResults().size();
                int flag = 0;
                for (j = 0; j < words.size(); j++) {
                    for (i = 0; i < len; i++) {
                        if (model.getResults().get(i).getBrandName().toLowerCase().contains(words.get(j).toLowerCase())) {
                            if (!brandIndex.contains(i)) {
                                brandIndex.add(i);
                            }
                        }
                        if (model.getResults().get(i).getProductName().toLowerCase().contains(words.get(j).toLowerCase())) {
                            if (!productnameIndex.contains(i)) {
                                productnameIndex.add(i);
                            }
                        }

                    }

                }

                if(brandIndex.size()!=0 && productnameIndex.size()!=0){
                    for (j = 0; j< brandIndex.size();j++) {
                        for (i = 0; i < productnameIndex.size(); i++) {
                            if (brandIndex.get(j) == productnameIndex.get(i)) {
                                commonIndex = j;
                                flag = 1;
                                break;
                            }
                        }
                        if(flag ==1)
                            break;
                    }
                }
                else if(brandIndex.size()!=0 && productnameIndex.size() == 0)
                {
                    commonIndex = brandIndex.get(0);
                    flag = 1;
                }
                else if(brandIndex.size()==0 && productnameIndex.size() != 0)
                {
                    commonIndex = productnameIndex.get(0);
                    flag = 1;
                }
                else
                    flag = 0;


                if (flag == 1)
                {
                    Intent intent = new Intent(SearchActivity.this, ProductPage.class);
                    Bundle moveBundle = new Bundle();
                    moveBundle.putString("BrandName",model.getResults().get(commonIndex).getBrandName());
                    moveBundle.putString("ProductName",model.getResults().get(commonIndex).getProductName());
                    moveBundle.putString("OriginalPrice",model.getResults().get(commonIndex).getOriginalPrice());
                    intent.putExtras(moveBundle);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(SearchActivity.this, "No results found!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void failure(RetrofitError error) {

                String merror = error.getMessage();
            }
            });


    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        // Handle action buttons
        if(item.getItemId()==R.id.search)
        {
        }
        return super.onOptionsItemSelected(item);

    }

    public static List<String> getSplitWords(String text) {
        List<String> words = new ArrayList<String>();
        BreakIterator breakIterator = BreakIterator.getWordInstance();
        breakIterator.setText(text);
        int lastIndex = breakIterator.first();
        while (BreakIterator.DONE != lastIndex) {
            int firstIndex = lastIndex;
            lastIndex = breakIterator.next();
            if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(text.charAt(firstIndex))) {
                words.add(text.substring(firstIndex, lastIndex));
            }
        }

        return words;
    }
}
