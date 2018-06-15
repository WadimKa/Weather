package com.wadimkazak.weather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<City> parsedCities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new CitiesParse().execute();
        setUpAdapter();
    }

    private void setUpAdapter(){
        recyclerView.setAdapter(new CityAdapter(parsedCities));
    }


    private class CityViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameOfCity, tvDegree, tvPressure, tvHumidity, tvDescription;

        public CityViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.item_of_list, viewGroup, false));
            tvNameOfCity = itemView.findViewById(R.id.tvNameOfCity);
            tvDegree = itemView.findViewById(R.id.tvDegree);
            tvPressure = itemView.findViewById(R.id.tvPressure);
            tvHumidity = itemView.findViewById(R.id.tvHumidity);
            tvDescription = itemView.findViewById(R.id.tvDescription);

        }

        public void onBind(City city) {
            tvNameOfCity.setText(city.getName());
            tvDescription.setText(city.getDescription());
            tvHumidity.setText(city.getHumidity());
            tvDegree.setText(city.getDegree());
        }
    }

    private class CityAdapter extends RecyclerView.Adapter<CityViewHolder> {

        ArrayList<City> cities;


        public CityAdapter(ArrayList<City> cities) {
            this.cities = cities;
        }

        @Override
        public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            return new CityViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CityViewHolder holder, int position) {
            holder.onBind(cities.get(position));

        }

        @Override
        public int getItemCount() {
            return cities.size();
        }
    }

    private class CitiesParse extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<String> strings = new ArrayList<>();
            strings.add("minsk");
            parsedCities = WeatherParser.getCities(strings);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setUpAdapter();
        }
    }
}
