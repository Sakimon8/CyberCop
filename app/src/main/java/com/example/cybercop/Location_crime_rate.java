package com.example.cybercop;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



import android.content.Context;
//import android.support.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Location_crime_rate extends AppCompatActivity {

    public class Location {
        private int id;
        private String name;
        private String statusDesc;
        private String premisDesc;
        private String weaponDesc;
        private String crimeDesc;
        private String victSex;
        private String area;
        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }



        public String getWeaponDesc() {
            return weaponDesc;
        }

        public void setWeaponDesc(String weaponDesc) {
            this.weaponDesc = weaponDesc;
        }

        public String getCrimeDesc() {
            return crimeDesc;
        }

        public void setCrimeDesc(String crimeDesc) {
            this.crimeDesc = crimeDesc;
        }

        public String getVictSex() {
            return victSex;
        }

        public void setVictSex(String victSex) {
            this.victSex = victSex;
        }



        public String getPremisDesc() {
            return premisDesc;
        }

        public void setPremisDesc(String premisDesc) {
            this.premisDesc = premisDesc;
        }


        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getStatusDesc() {
            return statusDesc;
        }
        public void setStatusDesc(String statusDesc) {
            this.statusDesc = statusDesc;
        }
        @Override
        public String toString() {
            return name;
        }
    }
    class FilterHelper extends Filter {
        ArrayList<Location> currentList;
        ListViewAdapter adapter;
        Context c;
        public FilterHelper(ArrayList<Location> currentList, ListViewAdapter adapter, Context c) {
            this.currentList = currentList;
            this.adapter = adapter;
            this.c=c;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults=new FilterResults();

            if(constraint != null && constraint.length()>0)
            {
                //CHANGE TO UPPER
                constraint=constraint.toString().toUpperCase();

                //HOLD FILTERS WE FIND
                ArrayList<Location> foundFilters=new ArrayList<>();

                Location location =null;

                //ITERATE CURRENT LIST
                for (int i=0;i<currentList.size();i++)
                {
                    location = currentList.get(i);

                    //SEARCH
                    if(location.getName().toUpperCase().contains(constraint) )
                    {
                        //ADD IF FOUND
                        foundFilters.add(location);
                    }
                }

                //SET RESULTS TO FILTER LIST
                filterResults.count=foundFilters.size();
                filterResults.values=foundFilters;
            }else
            {
                //NO ITEM FOUND.LIST REMAINS INTACT
                filterResults.count=currentList.size();
                filterResults.values=currentList;
            }

            //RETURN RESULTS
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            adapter.setLocations((ArrayList<Location>) filterResults.values);
            adapter.refresh();
        }
    }

    public class ListViewAdapter extends BaseAdapter implements Filterable {

        Context c;
        ArrayList<Location> locations;
        public ArrayList<Location> currentList;
        FilterHelper filterHelper;

        public ListViewAdapter(Context c, ArrayList<Location> locations) {
            this.c = c;
            this.locations = locations;
            this.currentList= locations;
        }
        @Override
        public int getCount() {
            return locations.size();
        }
        @Override
        public Object getItem(int i) {
            return locations.get(i);
        }
        @Override
        public long getItemId(int i) {
            return i;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view==null)
            {
                view= LayoutInflater.from(c).inflate(R.layout.model,viewGroup,false);
            }

            TextView txtName = view.findViewById(R.id.nameTextView);
            TextView status_desc = view.findViewById(R.id.TextView);
            final Location s= (Location) this.getItem(i);
            txtName.setText(s.getArea());
            status_desc.setText(s.getName());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Dialog_box dialog_hint = new Dialog_box();
                    Bundle bundle = new Bundle();
                    bundle.putString("TEXT", s.getStatusDesc());
                    bundle.putString("HEADING", s.getName());
                    bundle.putString("TEXT2",s.getPremisDesc());
                    bundle.putString("weapon", s.getWeaponDesc());
                    bundle.putString("crime",s.getCrimeDesc());
                    bundle.putString("area",s.getArea());
                    bundle.putString("gender",s.getVictSex());
                    dialog_hint.setArguments(bundle);
                    dialog_hint.show(getSupportFragmentManager(), "Dialog_box");
                }
            });

            return view;
        }
        public void setLocations(ArrayList<Location> filteredLocations)
        {
            this.locations = filteredLocations;

        }
        @Override
        public Filter getFilter() {
            if(filterHelper==null)
            {
                filterHelper=new FilterHelper(currentList,this,c);
            }

            return filterHelper;
        }
        public void refresh(){
            notifyDataSetChanged();
        }
    }

    /*
    Our HTTP Client
     */
    public class JSONDownloader {

        //SAVE/RETRIEVE URLS
        private static final String JSON_DATA_URL="https://api.npoint.io/ab1abcbd612aaefde7b5";
        //INSTANCE FIELDS
        private final Context c;

        public JSONDownloader(Context c) {
            this.c = c;
        }
        /*
        Fetch JSON Data
         */
        public ArrayList<Location> retrieve(final ListView mListView, final ProgressBar myProgressBar)
        {
            final ArrayList<Location> downloadedData=new ArrayList<>();
            myProgressBar.setIndeterminate(true);
            myProgressBar.setVisibility(View.VISIBLE);

            AndroidNetworking.get(JSON_DATA_URL)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            JSONObject jo;
                            Location s;
                            try
                            {
                                for(int i=0;i<response.length();i++)
                                {
                                    jo=response.getJSONObject(i);
                                    String name=jo.getString("location");
                                    String statusDesc=jo.getString("statusDesc");
                                    String premisDesc=jo.getString("premisDesc");
                                    String crimeDesc=jo.getString("crimeDesc");
                                    String weaponDesc=jo.getString("weaponDesc");
                                    String victSex=jo.getString("victSex");
                                    String area=jo.getString("areaName");
                                    s=new Location();
                                    s.setName(name);
                                    s.setStatusDesc(statusDesc);
                                    s.setPremisDesc(premisDesc);
                                    s.setArea(area);
                                    s.setCrimeDesc(crimeDesc);
                                    s.setWeaponDesc(weaponDesc);
                                    s.setVictSex(victSex);
                                    downloadedData.add(s);
                                }
                                myProgressBar.setVisibility(View.GONE);

                            }catch (JSONException e)
                            {
                                myProgressBar.setVisibility(View.GONE);
                                Toast.makeText(c, "GOOD RESPONSE BUT JAVA CAN'T PARSE JSON IT RECEIEVED. "+e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        //ERROR
                        @Override
                        public void onError(ANError anError) {
                            anError.printStackTrace();
                            myProgressBar.setVisibility(View.GONE);
                            Toast.makeText(c, "UNSUCCESSFUL :  ERROR IS : "+anError.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
            return downloadedData;
        }
    }
    ArrayList<Location> locations = new ArrayList<>();
    ListView myListView;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_crime_rate);

        myListView= findViewById(R.id.myListView);
        final ProgressBar myProgressBar= findViewById(R.id.myProgressBar);
        SearchView mySearchView=findViewById(R.id.mySearchView);

        mySearchView.setIconified(true);
        mySearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });
        locations =new JSONDownloader(Location_crime_rate.this).retrieve(myListView,myProgressBar);
        adapter=new ListViewAdapter(this, locations);
        myListView.setAdapter(adapter);

    }
}
