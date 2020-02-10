package com.muhaammaad.metarpolite.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.muhaammaad.metarpolite.R;
import com.muhaammaad.metarpolite.databinding.ActivityMetarDetailBinding;
import com.muhaammaad.metarpolite.model.SkyCondition;
import com.muhaammaad.metarpolite.persistence.entity.Metar;
import com.muhaammaad.metarpolite.ui.main.viewmodel.MetarDetailViewModel;

public class MetarDetailActivity extends AppCompatActivity {

    /**
     * return a new object of the activity.
     */
    public static void openActivity(Context context, Metar metar) {
        Intent intent = new Intent(context, MetarDetailActivity.class);
        intent.putExtra(Metar.class.getName(), metar);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMetarDetailBinding binding =  DataBindingUtil.setContentView(this,R.layout.activity_metar_detail);
        MetarDetailViewModel viewModel = new ViewModelProvider(this).get(MetarDetailViewModel.class);
        binding.setViewModel(viewModel);
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Metar.class.getName())) {
            Metar m = (Metar) getIntent().getExtras().getSerializable(Metar.class.getName());
            viewModel.setMetar(m);
        }

    }
}
