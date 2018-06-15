package com.wadimkazak.weather;

import android.net.Uri;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class WeatherParser {

    private static final String URL_FOR_REQUST = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String API_KEY = "b1ad6bf3fde3785d09d89d99d86ab543";


    public static JSONObject getJSONObj(String city) {
        String url = Uri.parse(String.format(URL_FOR_REQUST, city)).buildUpon().
                appendQueryParameter("appid", API_KEY).
                build().toString();
        String result = new String();
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            result = response.body().string();
            return new JSONObject(result);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static City fillCities(JSONObject object) {
        City city = new City();
        try {

            JSONObject properties = object.getJSONArray("weather").getJSONObject(0);
            JSONObject global = object.getJSONObject("main");

            city.setName(object.getString("name")+ " - " + object.getJSONObject("sys").getString("country"));
            Log.i("1dd", city.getName());
            city.setDescription(properties.getString("description"));
            Log.i("1dd", city.getDescription());
            city.setHumidity("Humidity - " + global.getString("humidity") + "%");
            Log.i("1dd", city.getHumidity());
            city.setDegree(String.format("%.1f", global.getDouble("temp"))+ " ℃");
            return city;

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("1dd", e.toString());
        }
        return null;

    }

    public static ArrayList<City> getCities(ArrayList<String> citiesNames) {
        ArrayList<City> cities = new ArrayList<>();
        for (int i = 0; i < citiesNames.size(); i++) {
            cities.add(fillCities(getJSONObj(citiesNames.get(i))));
        }
        Log.i("1dd", cities.get(0).getName());
        return cities;
    }
}
