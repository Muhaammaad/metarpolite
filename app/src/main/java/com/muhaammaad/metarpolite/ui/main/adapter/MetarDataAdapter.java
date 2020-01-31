package com.muhaammaad.metarpolite.ui.main.adapter;

//region Imports

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.muhaammaad.metarpolite.R;
import com.muhaammaad.metarpolite.databinding.MetarListItemBinding;
import com.muhaammaad.metarpolite.model.METAR;
import com.muhaammaad.metarpolite.model.Station;

import java.util.ArrayList;
//endregion

/**
 * Adaptor responsible for metars list binding
 */
public class MetarDataAdapter
        extends RecyclerView.Adapter<MetarDataAdapter.MetarViewHolder> {

    //region Global members
    private ArrayList<METAR> metars;
    //endregion

    @NonNull
    @Override
    public MetarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MetarListItemBinding MetarListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.metar_list_item, viewGroup, false);
        return new MetarViewHolder(MetarListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MetarViewHolder metarViewHolder, int i) {
        METAR currentStudent = metars.get(i);
        metarViewHolder.metarListItemBinding.setMetar(currentStudent);
    }

    @Override
    public int getItemCount() {
        return metars != null ? metars.size() : 0;
    }

    public void setMetars(ArrayList<METAR> metars) {
        this.metars = metars;
        notifyDataSetChanged();
    }

    /**
     * View holder responsible for metar item binding
     */
    class MetarViewHolder extends RecyclerView.ViewHolder {

        private MetarListItemBinding metarListItemBinding;

        MetarViewHolder(@NonNull MetarListItemBinding metartListItemBinding) {
            super(metartListItemBinding.getRoot());
            this.metarListItemBinding = metartListItemBinding;
        }
    }

    /**
     * pass manipulate and set data to view, when the view observes data
     *
     * @param view which calls this adapter
     * @param s    station which were observed by the {@param view}
     */

    @BindingAdapter("station_name")
    public static void getStationNameByID(@NonNull TextView view, Station s) {
        if (s == null)
            return;
        String str = (s.country != null ? s.country : "").concat(" ").concat(s.state != null ? s.state : "").concat(" ").concat(s.site != null ? s.site : "");
        view.setText(str);
    }
}
