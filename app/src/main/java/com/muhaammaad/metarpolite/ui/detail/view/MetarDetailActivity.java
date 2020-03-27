package com.muhaammaad.metarpolite.ui.detail.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.muhaammaad.metarpolite.R;
import com.muhaammaad.metarpolite.databinding.ActivityMetarDetailBinding;
import com.muhaammaad.metarpolite.di.factory.ViewModelFactory;
import com.muhaammaad.metarpolite.persistence.entity.Metar;
import com.muhaammaad.metarpolite.ui.detail.viewmodel.MetarDetailViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MetarDetailActivity extends DaggerAppCompatActivity {

    /**
     * return a new object of the activity.
     */
    public static void openActivity(Context context, Metar metar) {
        Intent intent = new Intent(context, MetarDetailActivity.class);
        intent.putExtra(Metar.class.getName(), metar);
        context.startActivity(intent);
    }

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMetarDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_metar_detail);
        MetarDetailViewModel viewModel = new ViewModelProvider(this, viewModelFactory).get(MetarDetailViewModel.class);
        binding.setViewModel(viewModel);
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Metar.class.getName())) {
            Metar m = (Metar) getIntent().getExtras().getSerializable(Metar.class.getName());
            viewModel.setMetar(m);
        }

    }
}
