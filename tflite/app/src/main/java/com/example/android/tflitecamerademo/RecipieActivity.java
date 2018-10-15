package com.example.android.tflitecamerademo;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//import android.example.com.tflitecamerademo.R;

public class RecipieActivity extends Activity {

    TextView mTextView;
    ListView lv;

    JSONObject dataset;
    JSONArray global_api_ing;
    String all_ingredients[];
    class card {
        String title, grofers_name, grofers_price, nature_name, nature_price;
    }

    ArrayList<card> data;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie);
        all_ingredients = new String[30];
        try {
            dataset = new JSONObject("{\"grofers\":[{\"NAME\":\" Best Value Refined Sugar\",\"PRICE\":\" 42\"},{\"NAME\":\" Amul Creami Cheese Spread\",\"PRICE\":\" 78\"},{\"NAME\":\" Amul Butter\",\"PRICE\":\" 46\"},{\"NAME\":\" Kissan Berry Blast Jam\",\"PRICE\":\" 93\"},{\"NAME\":\" Aashirvaad Select Superior Sharbati Whole Wheat Atta\",\"PRICE\":\" 234\"},{\"NAME\":\" Best Value White Eggs\",\"PRICE\":\" 180\"},{\"NAME\":\" Raw Sugar  -  Organic - Conscious Food - 500 g\",\"PRICE\":\" 96\"},{\"NAME\":\" Unsweetened Almond Butter - The Butternut Co. - 220 g\",\"PRICE\":\" 450\"},{\"NAME\":\" Blueberry  -  Exotic - 125 g\",\"PRICE\":\" 375\"},{\"NAME\":\" Organic Whole Wheat Flour - Healthy Alternatives - 5 kg\",\"PRICE\":\" 305\"},{\"NAME\":\" Free Range Country Eggs Pk Of 6 - Healthy Alternatives - 1 U\",\"PRICE\":\" 105\"},{\"NAME\":\" Amul Butter\",\"PRICE\":\" 211\"},{\"NAME\":\" Del Monte Gourmet Penne Rigate Pasta\",\"PRICE\":\" 141\"},{\"NAME\":\" Chicken & Liver Chunks\",\"PRICE\":\" 422\"},{\"NAME\":\" Best Value White Eggs\",\"PRICE\":\" 180\"},{\"NAME\":\" Best Value Rice Flour\",\"PRICE\":\" 17\"},{\"NAME\":\" Amul Butter\",\"PRICE\":\" 46\"},{\"NAME\":\" Tops Baking Powder\",\"PRICE\":\" 27\"},{\"NAME\":\" Borges Spaghetti\",\"PRICE\":\" 183\"},{\"NAME\":\" Figaro Olive Oil (Tin)\",\"PRICE\":\" 2880\"},{\"NAME\":\" Sumeru Streaky Bacon - Pork\",\"PRICE\":\" 298\"},{\"NAME\":\" Onion\",\"PRICE\":\" 115\"},{\"NAME\":\" Catch Black Pepper Powder (Carton)\",\"PRICE\":\" 120\"},{\"NAME\":\" Amul Pizza Mozzarella Cheese Block\",\"PRICE\":\" 92\"},{\"NAME\":\" Best Value White Eggs\",\"PRICE\":\" 180\"}],\"nature\":[{\"NAME\":\" Raw Sugar  -  Organic - Conscious Food - 500 g\",\"PRICE\":\" 96\"},{\"NAME\":\" Unsweetened Almond Butter - The Butternut Co. - 220 g\",\"PRICE\":\" 450\"},{\"NAME\":\" Blueberry  -  Exotic - 125 g\",\"PRICE\":\" 375\"},{\"NAME\":\" Organic Whole Wheat Flour - Healthy Alternatives - 5 kg\",\"PRICE\":\" 305\"},{\"NAME\":\" Free Range Country Eggs Pk Of 6 - Healthy Alternatives - 1 U\",\"PRICE\":\" 105\"},{\"NAME\":\" Raw Sugar  -  Organic - Conscious Food - 500 g\",\"PRICE\":\" 96\"},{\"NAME\":\" Unsweetened Almond Butter - The Butternut Co. - 220 g\",\"PRICE\":\" 450\"},{\"NAME\":\" Blueberry  -  Exotic - 125 g\",\"PRICE\":\" 375\"},{\"NAME\":\" Organic Whole Wheat Flour - Healthy Alternatives - 5 kg\",\"PRICE\":\" 305\"},{\"NAME\":\" Parsley Curled - 50 g\",\"PRICE\":\" 60\"},{\"NAME\":\" Whole Pre Cut Chicken - Real Good - 1 kg\",\"PRICE\":\" 225\"},{\"NAME\":\" Free Range Country Eggs Pk Of 6 - Healthy Alternatives - 1 U\",\"PRICE\":\" 105\"},{\"NAME\":\" Organic Whole Wheat Flour - Healthy Alternatives - 5 kg\",\"PRICE\":\" 305\"},{\"NAME\":\" Unsweetened Almond Butter - The Butternut Co. - 220 g\",\"PRICE\":\" 450\"},{\"NAME\":\" Baking Powder - Blue Bird - 50 g\",\"PRICE\":\" 25\"},{\"NAME\":\" Streaky Bacon Pork - Prasuma - 150 g\",\"PRICE\":\" 290\"},{\"NAME\":\" Garlic - 100 g\",\"PRICE\":\" 14\"},{\"NAME\":\" Pepper Coarse - Snapin - 40 g\",\"PRICE\":\" 95\"},{\"NAME\":\" Free Range Country Eggs Pk Of 6 - Healthy Alternatives - 1 U\",\"PRICE\":\" 105\"},{\"NAME\":\" Parsley Curled - 50 g\",\"PRICE\":\" 60\"},{\"NAME\":\" Whole Pre Cut Chicken - Real Good - 1 kg\",\"PRICE\":\" 225\"},{\"NAME\":\" Free Range Country Eggs Pk Of 6 - Healthy Alternatives - 1 U\",\"PRICE\":\" 105\"},{\"NAME\":\" Organic Whole Wheat Flour - Healthy Alternatives - 5 kg\",\"PRICE\":\" 305\"},{\"NAME\":\" Unsweetened Almond Butter - The Butternut Co. - 220 g\",\"PRICE\":\" 450\"},{\"NAME\":\" Baking Powder - Blue Bird - 50 g\",\"PRICE\":\" 25\"}]}");
        }
        catch(Exception e){
            Log.d("abc",e.toString());
        }

        mTextView = (TextView) findViewById(R.id.text);
        lv = (ListView) findViewById(R.id.listVIew);
        data = new ArrayList<card>();
        counter = 0;
        Intent intent = getIntent();
        String value = intent.getStringExtra("key");


        // Debugging
        // Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
        //new FetchWeatherData().execute();
        set_global_ingredients();
        CustomAdapter lvAdapter = new CustomAdapter();
        lv.setAdapter(lvAdapter);
    }

    /*private class FetchWeatherData extends AsyncTask<Void, Void, String> {

        public void parseJSON(String s) {
            String data = s;
            try{
                JSONObject obj = new JSONObject(data);
                JSONObject recipe = obj.getJSONObject("recipe");
                JSONArray recipe_list = recipe.getJSONArray("ingredients");
                String recipe_txt[] = new String[recipe_list.length()];

                for(int i = 0; i < recipe_list.length(); i++) {
                    recipe_txt[i] = recipe_list.getString(i);
                }

                // mTextView.setVisibility(View.VISIBLE);
                mTextView.setText("");
                String res = "";
                for(int i = 0; i < recipe_txt.length; i++) {
                    res += recipe_txt[i];
                }
                mTextView.setText(res);
                global_api_ing = recipe_list;
            }catch (Exception e) {
                Log.d("abc", "parseJSON: exception raised");
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            JSONObject abc;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url = new URL("https://inout-api-food.herokuapp.com/api/send/apple%20pie");

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
                return forecastJsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } catch (Exception e) {
                Log.d("abc", "doInBackground: fds");
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mTextView.setText(s);
            parseJSON(s);
            set_global_ingredients();
            CustomAdapter lvAdapter = new CustomAdapter();

            lv.setAdapter(lvAdapter);
            Log.i("json", s);

        }
    }*/

    void set_global_ingredients() {
        String data_str = "{\"cheesecake\":[{\"title\":\"CrackerCrumbs\",\"NAME\":[\"NA\",\"NA\"],\"PRICE\":[\"NA\",\"NA\"]},{\"title\":\"CocoaPowder\",\"NAME\":[\"HersheysCocoaPowder\",\"HersheysCocoaPowder\"],\"PRICE\":[\"250\",\"212\"]},{\"title\":\"Sugar\",\"NAME\":[\"BestRefinedSugar\",\"RawSugarOrganic-500g\"],\"PRICE\":[\"42\",\"96\"]},{\"title\":\"UnsaltedButter\",\"NAME\":[\"AmulButter\",\"AmulButter\"],\"PRICE\":[\"208\",\"211\"]},{\"title\":\"DarkChocolate\",\"NAME\":[\"Amul55%CocoaDark\",\"DarkChocolate88%\"],\"PRICE\":[\"89\",\"275\"]},{\"title\":\"HeavyCream\",\"NAME\":[\"AmulFreshCream\",\"NestleWhippingCream-170g\"],\"PRICE\":[\"180\",\"NA\"]},{\"title\":\"CreamCheese\",\"NAME\":[\"AmulCreamiCheeseSpread\",\"CreamyCheeseLactima-120g\"],\"PRICE\":[\"78\",\"NA\"]},{\"title\":\"Eggs\",\"NAME\":[\"EggsPack-6\",\"NutriEggs-6PC\"],\"PRICE\":[\"33\",\"54\"]},{\"title\":\"VanillaExtract\",\"NAME\":[\"NA\",\"VanillaPowder-Orchid-10g\"],\"PRICE\":[\"NA\",\"NA\"]}]}";
        try {
            JSONObject obj = new JSONObject(data_str);
            JSONArray cheesecake = obj.getJSONArray("cheesecake");

            for(int i = 0; i < cheesecake.length(); i++) {
                String title1, grofers_name1, grofers_price1, nature_name1, nature_price1;
                JSONObject item = cheesecake.getJSONObject(i);
                title1 = item.getString("title");

                JSONArray name_arr = item.getJSONArray("NAME");
                grofers_name1 = name_arr.getString(0);
                nature_name1 = name_arr.getString(1);

                JSONArray price_arr = item.getJSONArray("PRICE");
                grofers_price1 = price_arr.getString(0);
                nature_price1 = price_arr.getString(1);

                card temp = new card();
                temp.title = title1;
                temp.grofers_name = grofers_name1;
                temp.grofers_price = grofers_price1;
                temp.nature_name = nature_name1;
                temp.nature_price = nature_price1;
                data.add(temp);
            }
        } catch (Exception e) {

        }
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.card, null);

            TextView title, grofers_price, grofers_name, nature_name, nature_price;
            title = (TextView) convertView.findViewById(R.id.title);
            grofers_name = (TextView) convertView.findViewById(R.id.grofers);
            grofers_price = (TextView) convertView.findViewById(R.id.grofers_price);
            nature_name = (TextView) convertView.findViewById(R.id.bigbasket);
            nature_price = (TextView) convertView.findViewById(R.id.bigbasket_price);


            title.setText(data.get(position).title);
            grofers_name.setText(data.get(position).grofers_name);
            grofers_price.setText(data.get(position).grofers_name);
            nature_name.setText(data.get(position).nature_name);
            nature_price.setText(data.get(position).nature_price);
            return null;
        }
    }
}
