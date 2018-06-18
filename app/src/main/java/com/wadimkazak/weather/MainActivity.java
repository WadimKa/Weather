package com.wadimkazak.weather;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<City> parsedCities = new ArrayList<>();
    private final boolean CHECK_CITY = true;
    private final boolean FAILED_CHECK_CITY = false;
    private String checkableName = new String();
    private final String KEY = "key";
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getPreferences(MODE_PRIVATE);
        if(sp.getBoolean(KEY, true)){
            NamesOfCites namesOfCity = new NamesOfCites("moscow");
            namesOfCity.save();
            NamesOfCites namesOfSecondCity = new NamesOfCites("minsk");
            namesOfSecondCity.save();
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(KEY, false);
            editor.commit();
        }




        recyclerView = findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new CitiesParse().execute();

        setUpAdapter();
    }

    private void setUpAdapter() {
        recyclerView.setAdapter(new CityAdapter(parsedCities));
    }


    private class CityViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameOfCity, tvDegree, tvHumidity, tvDescription;

        public CityViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.item_of_list, viewGroup, false));
            tvNameOfCity = itemView.findViewById(R.id.tvNameOfCity);
            tvDegree = itemView.findViewById(R.id.tvDegree);
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
            List<NamesOfCites> names = NamesOfCites.listAll(NamesOfCites.class);
            ArrayList<String> cities = new ArrayList<>();
            for (int i = 0; i < names.size(); i++) {
                cities.add(names.get(i).name);
            }
            parsedCities = WeatherParser.getCities(cities);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setUpAdapter();
        }
    }

    private class CityCheck extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            if (WeatherParser.getJSONObj(checkableName) == null) {
                return FAILED_CHECK_CITY;
            } else {
                NamesOfCites namesOfCites = new NamesOfCites(checkableName);
                namesOfCites.save();
                return CHECK_CITY;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (CHECK_CITY) {
                new CitiesParse().execute();
                setUpAdapter();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add_new_city);
        final EditText editText = new EditText(this);
        builder.setView(editText);
        builder.setNeutralButton(getString(R.string.cancel), null);
        builder.setPositiveButton(getString(R.string.add), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = editText.getText().toString();
                checkableName = editText.getText().toString();
                new CityCheck().execute();
            }
        });
        builder.show();
        return true;
    }
}
