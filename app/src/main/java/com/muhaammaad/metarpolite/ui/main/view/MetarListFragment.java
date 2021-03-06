package com.muhaammaad.metarpolite.ui.main.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muhaammaad.metarpolite.R;
import com.muhaammaad.metarpolite.databinding.FragmentMetarListBinding;
import com.muhaammaad.metarpolite.di.factory.ViewModelFactory;
import com.muhaammaad.metarpolite.global.IdlingResource.SimpleIdlingResource;
import com.muhaammaad.metarpolite.global.callback.ClickListener;
import com.muhaammaad.metarpolite.persistence.entity.Metar;
import com.muhaammaad.metarpolite.ui.detail.view.MetarDetailActivity;
import com.muhaammaad.metarpolite.ui.main.adapter.MetarDataAdapter;
import com.muhaammaad.metarpolite.ui.main.viewmodel.MainViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


/**
 * Fragment responsible to show list of metars
 */
public class MetarListFragment extends DaggerFragment implements ClickListener<Metar> {

    /**
     * return a new object of the fragment.
     */
    static MetarListFragment newInstance() {
        return new MetarListFragment();
    }

    @Inject
    ViewModelFactory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //region Configure binder and viewModel
        FragmentMetarListBinding binder = FragmentMetarListBinding.bind(inflater.inflate(R.layout.fragment_metar_list, container, false));
        MainViewModel mainViewModel = new ViewModelProvider(this, viewModelFactory).get(MainViewModel.class);
        MetarDataAdapter metarDataAdapter = new MetarDataAdapter(this);
        binder.setViewModel(mainViewModel);
        //endregion
        //region Bind RecyclerView
        RecyclerView recyclerView = binder.rvMetars;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(metarDataAdapter);

        mainViewModel.loading.observe(getViewLifecycleOwner(), aBoolean -> ((SimpleIdlingResource.IdlingDelegate) getActivity()).setIdleState(!aBoolean));
        //endregion
        return binder.getRoot();
    }

    private void showMetarDetailsActivity(Metar metar) {
        MetarDetailActivity.openActivity(getActivity(), metar);
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

    @Override
    public void onclick(Metar metar) {
        showMetarDetailsActivity(metar);
    }
}
