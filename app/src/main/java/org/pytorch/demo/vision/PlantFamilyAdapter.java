package org.pytorch.demo.vision;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.pytorch.demo.R;

import java.util.ArrayList;
import java.util.List;

public class PlantFamilyAdapter extends ArrayAdapter<PlantFamily> implements Filterable {
    Context context;
    int layoutResourceId;
    List<PlantFamily> data;
    List<PlantFamily> dataFiltered;

    public PlantFamilyAdapter(@NonNull Context context, int resource, List data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
        this.dataFiltered=data;
        this.layoutResourceId = resource;
    }

    @Override
    public int getCount() {
        return dataFiltered.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        PlantFamilyHolder holder = null;
        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new PlantFamilyHolder();
            holder.iv_background = row.findViewById(R.id.img_listview_plant_family_background);
            holder.txt_name = row.findViewById(R.id.txt_listview_plant_family_name);
            holder.txt_description = row.findViewById(R.id.txt_listview_plant_family_description);
            holder.txt_num_genus = row.findViewById(R.id.txt_listview_plant_family_num_genus);
            row.setTag(holder);
        } else {
            holder = (PlantFamilyHolder) row.getTag();
        }

        PlantFamily plantFamily = dataFiltered.get(position);
//        PlantFamily plantFamily = data.get(position);
        holder.txt_name.setText(plantFamily.name);
        holder.txt_description.setText(plantFamily.description);
        holder.txt_num_genus.setText(plantFamily.numGenus);
        holder.iv_background.setImageResource(plantFamily.background);

        return row;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dataFiltered = data;
                } else {
                    List<PlantFamily> filteredList = new ArrayList<>();
                    for (PlantFamily row : data) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    dataFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataFiltered = (ArrayList<PlantFamily>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class PlantFamilyHolder
    {
        ImageView iv_background;
        TextView txt_name;
        TextView txt_description;
        TextView txt_num_genus;
    }
}
