package polskiedev.country_list_from_api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import java.text.DecimalFormat;


public class CountriesListAdapter extends ArrayAdapter<Country> implements Filterable {

    private final Context mContext;
    private List<Country> countryList = new ArrayList<>();
    private List<Country> filterList = new ArrayList<>();
    CustomFilter filter;

    public CountriesListAdapter(@NonNull Context context, @NonNull ArrayList<Country> list) {
        super(context, 0, list);
        this.mContext = context;
        this.countryList = list;
        this.filterList = list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listItem = convertView;

        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.country_list_item, parent, false);

        Country currentCountry = this.countryList.get(position);

        TextView tvName = (TextView) listItem.findViewById(R.id.tvName);
        TextView tvAbbrev = (TextView) listItem.findViewById(R.id.tvAbbrev);
        TextView tvCapital = (TextView) listItem.findViewById(R.id.tvCapital);
        TextView tvRegion = (TextView) listItem.findViewById(R.id.tvRegion);
        TextView tvSubRegion = (TextView) listItem.findViewById(R.id.tvSubRegion);
        TextView tvPopulation = (TextView) listItem.findViewById(R.id.tvPopulation);
        //TextView tvLblFlagUrl = (TextView) listItem.findViewById(R.id.tvLblFlagUrl);


        DecimalFormat formatter = new DecimalFormat("#,###,###");

        String sName = currentCountry.getName();
        String sAbbrev = currentCountry.getAbbrev();
        String sFlagUrl = currentCountry.getFlag();
        String sCapital = currentCountry.getCapital();
        String sRegion = currentCountry.getRegion();
        String sSubRegion = currentCountry.getSubRegion();
        String sPopulation = formatter.format(Integer.parseInt(currentCountry.getPopulation()));

        tvName.setText(sName);
        tvAbbrev.setText(sAbbrev);
        tvCapital.setText(sCapital);
        tvRegion.setText(sRegion);
        tvSubRegion.setText(sSubRegion);
        tvPopulation.setText(sPopulation);
//        tvLblFlagUrl.setText(sFlagUrl);

        final ImageView mImageView;
        mImageView = (ImageView) listItem.findViewById(R.id.imgViewFlag);

        //"https://www.countryflags.io/PH/shiny/64.png"
        //"http://flagpedia.net/data/flags/normal/ph.png"
        Glide.with(this.mContext)
                //.load(sFlagUrl) //Svg not supported yet??? // "https://restcountries.eu/data/afg.svg"
                .load("https://www.countryflags.io/"+sAbbrev+"/shiny/64.png")
                //.load("http://flagpedia.net/data/flags/normal/"+sAbbrev.toLowerCase()+".png")
                .into(mImageView);


        return listItem;
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public Country getItem(int pos) {
        return countryList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return countryList.indexOf(getItem(pos));
    }

    @NonNull
    @Override
    public Filter getFilter() {

        if(filter == null)
        {
            filter = new CustomFilter();
        }
//        return super.getFilter();
        return filter;
    }

    class CustomFilter extends Filter{

        FilterResults results = new FilterResults();

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            ////////////////////////////////////////////
            if(constraint != null && constraint.length() > 0)
            {
                constraint = constraint.toString().toUpperCase();

                ArrayList<Country> filters = new ArrayList<Country>();

                //get specific items
                for(int i=0;i < filterList.size();i++)
                {
                    if(filterList.get(i).getName().toUpperCase().contains(constraint) || filterList.get(i).getAbbrev().toUpperCase().contains(constraint))
                    {
                        Country item = filterList.get(i);

                        filters.add(item);
                    }
                }

                results.count = filters.size();
                results.values = filters;

            } else {
                results.count = filterList.size();
                results.values = filterList;
            }

            return results;
            /////////////////////////////////////////
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            countryList = (ArrayList<Country>) results.values;
            notifyDataSetChanged();
        }
    }
}