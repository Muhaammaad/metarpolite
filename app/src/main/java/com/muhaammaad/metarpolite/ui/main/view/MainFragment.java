package com.muhaammaad.metarpolite.ui.main.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.muhaammaad.metarpolite.R;
import com.muhaammaad.metarpolite.databinding.MainFragmentBinding;
import com.muhaammaad.metarpolite.persistence.entity.Metar;
import com.muhaammaad.metarpolite.ui.main.adapter.MetarDataAdapter;
import com.muhaammaad.metarpolite.ui.main.viewmodel.MainViewModel;

import java.util.ArrayList;

/**
 * Fragment responsible to show list of metars
 */
public class MainFragment extends Fragment {

    /**
     * return a new object of the fragment.
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //region Configure binder and viewModel
        MainFragmentBinding binder = MainFragmentBinding.bind(inflater.inflate(R.layout.main_fragment, container, false));
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        MetarDataAdapter metarDataAdapter = new MetarDataAdapter();
        binder.setViewModel(mainViewModel);
        //endregion
        //region Bind RecyclerView
        RecyclerView recyclerView = binder.rvMetars;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(metarDataAdapter);
        //endregion
        return binder.getRoot();
    }

    /**
     * pass data into adapter, when the view observes data
     *
     * @param view   which calls this adapter
     * @param metars metars list which were observed by the {@param view}
     */
    @BindingAdapter("items")
    public static void setItems(@NonNull RecyclerView view, ArrayList<Metar> metars) {
        MetarDataAdapter adapter = (MetarDataAdapter) view.getAdapter();
        if (adapter == null)
            return;
        adapter.setMetars(metars);
        adapter.notifyDataSetChanged();
    }

}
