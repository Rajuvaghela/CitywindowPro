package com.citywindowpro.Adapter;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.citywindowpro.Model.SearchModel;
import com.citywindowpro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raju on 05-Oct-16.
 */
public class SearchAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<SearchModel> originalList;
    private ArrayList<Object> suggestions = new ArrayList<>();
    private ArrayList<Object> surnamesuggestions = new ArrayList<>();
    Filter filter = new CustomFilter();
    ArrayList<String> surname;

    private int size = 0;

    /**
     * @param context      Context
     * @param originalList Original list used to compare in constraints.
     */
    public SearchAdapter(Context context, List<SearchModel> originalList) {
        this.context = context;
        this.originalList = originalList;
        notifyDataSetChanged();


    }

    @Override
    public void notifyDataSetChanged() {
        size = suggestions.size();
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return size; // Return the size of the suggestions list.
    }

    @Override
    public Object getItem(int position) {
        return suggestions.get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * This is where you inflate the layout and also where you set what you want to display.
     * Here we also implement a View Holder in order to recycle the views.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.rowsearch_category,
                    parent,
                    false);
            holder = new ViewHolder();
            holder.autoText = (TextView) convertView.findViewById(R.id.tv_cate_img_list);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.autoText.setText(suggestions.get(position).toString());


        return convertView;
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    private static class ViewHolder {
        TextView autoText;
    }

    /**
     * Our Custom Filter Class.
     */
    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            suggestions.clear();
            try {
                if (originalList != null) { // Check if the Original List and Constraint aren't null.
                    for (int i = 0; i < originalList.size(); i++) {
                        if (originalList.get(i).getSearch().toLowerCase().contains(constraint)) {
                            // Compare item in original list if it contains constraints.
                            suggestions.add(originalList.get(i).getSearch());

                            // If TRUE add item in Suggestions.
                        }

                    }
                } else {
                    notifyDataSetChanged();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            FilterResults results = new FilterResults(); // Create new Filter Results and return this to publishResults;
            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0 && results != null) {
                notifyDataSetChanged();

            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}