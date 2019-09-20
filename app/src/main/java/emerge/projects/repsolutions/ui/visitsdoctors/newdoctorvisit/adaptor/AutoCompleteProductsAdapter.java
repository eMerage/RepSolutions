package emerge.projects.repsolutions.ui.visitsdoctors.newdoctorvisit.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import emerge.projects.repsolutions.R;
import emerge.projects.repsolutions.data.modeldata.ProductList;

public class AutoCompleteProductsAdapter extends ArrayAdapter<ProductList> {

    Context context;
    int resource, textViewResourceId;
    List<ProductList> items, tempItems, suggestions;

    public AutoCompleteProductsAdapter(Context context, int resource, int textViewResourceId, List<ProductList> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<ProductList>(items); // this makes the difference.
        suggestions = new ArrayList<ProductList>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_autocomplete_products, parent, false);
        }
        ProductList people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(people.getProductName());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((ProductList) resultValue).getProductName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (ProductList people : tempItems) {
                    if (people.getProductName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<ProductList> filterList = (ArrayList<ProductList>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (ProductList people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
