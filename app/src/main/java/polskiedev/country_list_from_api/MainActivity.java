package polskiedev.country_list_from_api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    private ListView listView;
    private CountriesListAdapter clAdapter;

    private TextView mTextViewResult;
    private static String TAG = MainActivity.class.getSimpleName();
    private ArrayList<HashMap<String, String>> countryList;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Required (On build.gradle): implementation 'com.android.volley:volley:1.0.0'
        mQueue = Volley.newRequestQueue(this);
        mTextViewResult = findViewById(R.id.text_view_result);
        searchView = findViewById(R.id.searchView);

        countryList = new ArrayList<>();
        final Context mContext = this;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                jsonParse(mContext, text);

                return false;
            }
        });

        jsonParse(this, null);
//        jsonAssociativeParse();
    }

    private void jsonParse(final Context context, final String searchFilter) {
        //https://medium.com/mindorks/custom-array-adapters-made-easy-b6c4930560dd
        String url = "https://restcountries.eu/rest/v2/all";
        //https://restcountries.eu/rest/v2/name/USA

//        if (!TextUtils.isEmpty(searchFilter)) {
//            url = "https://restcountries.eu/rest/v2/name/"+searchFilter;
//        }

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    if(response.length() > 0) {
                        // Parsing json array response
                        // loop through each json object
                        ArrayList<Country> countryList = new ArrayList<>();

                        for (int i = 0; i < response.length(); i++) {

                            JSONObject countryObject = (JSONObject) response.get(i);

/*                        JSONObject phone = person
                                .getJSONObject("phone");
                        String home = phone.getString("home");
                        String mobile = phone.getString("mobile");*/

                            String sName = countryObject.getString("name");
                            String sAbbrev = countryObject.getString("alpha2Code");
                            String sFlag = countryObject.getString("flag");
                            String sPopulation = countryObject.getString("population");
                            String sCapital = countryObject.getString("capital");
                            String sRegion = countryObject.getString("region");
                            String sSubRegion = countryObject.getString("subregion");

                            Country countryData = new Country();

                            countryData.setName(sName);
                            countryData.setFlag(sFlag);
                            countryData.setAbbrev(sAbbrev);
                            countryData.setPopulation(sPopulation);
                            countryData.setCapital(sCapital);
                            countryData.setRegion(sRegion);
                            countryData.setSubRegion(sSubRegion);

                            countryList.add(countryData);
                        }

                        ListView listView = (ListView) findViewById(R.id.listView);

                        CountriesListAdapter clAdapter = new CountriesListAdapter(context, countryList);
                        clAdapter.getFilter().filter(searchFilter);
                        listView.setAdapter(clAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(),
//                            "Error: " + e.getMessage(),
//                            Toast.LENGTH_LONG).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mQueue.add(request);
    }

    private void jsonAssociativeParse() {
        String url = "http://10.0.2.2/android-web-api/countries";
//        String url = "https://restcountries.eu/rest/v2/all";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("countries");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject countryObject = jsonArray.getJSONObject(i);

                        String countryName = countryObject.getString("name");
                        String countryFlag = countryObject.getString("flag");
                        String countryCode = countryObject.getString("alpha2Code");
                        String countryPopulation = countryObject.getString("population");

                        // tmp hash map for single country
                        HashMap<String, String> country = new HashMap<>();
                        // adding each child node to HashMap key => value
                        country.put("name", countryName);
                        country.put("flag", countryFlag);
                        country.put("code", countryCode);
                        country.put("population", countryPopulation);

                        // adding contact to contact list
                        countryList.add(country);
                        mTextViewResult.append( countryName + "\n");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo!= null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

}
