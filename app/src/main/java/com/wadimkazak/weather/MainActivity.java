package com.wadimkazak.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_main);

        CityAdapter cityAdapter = new CityAdapter(new ArrayList<City>());
        cityAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cityAdapter);
    }


    private class CityViewHolder extends RecyclerView.ViewHolder {

        TextView tvNameOfCity;

        public CityViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.item_of_list, viewGroup, false));
            tvNameOfCity = itemView.findViewById(R.id.tvNameOfCity);

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

        }

        @Override
        public int getItemCount() {
            return cities.size();
        }
    }
}
