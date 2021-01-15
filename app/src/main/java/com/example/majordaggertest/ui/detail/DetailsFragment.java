package com.example.majordaggertest.ui.detail;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.majordaggertest.R;
import com.example.majordaggertest.base.BaseFragment;
import com.example.majordaggertest.data.model.Repo;
import com.example.majordaggertest.util.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;

public class DetailsFragment extends BaseFragment {

    @BindView(R.id.tv_repo_name) TextView repoNameTextView;
    @BindView(R.id.tv_repo_description) TextView repoDescriptionTextView;
    @BindView(R.id.tv_forks) TextView forksTextView;
    @BindView(R.id.tv_stars) TextView starsTextView;

    @Inject
    ViewModelFactory viewModelFactory;
    private DetailsViewModel detailsViewModel;

    @Override
    protected int layoutRes() {
        return R.layout.screen_details;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        detailsViewModel = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(DetailsViewModel.class);
        detailsViewModel.restoreFromBundle(savedInstanceState);
        displayRepo();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        detailsViewModel.saveToBundle(outState);
    }

    private void displayRepo() {
        detailsViewModel.getSelectedRepo().observe(getViewLifecycleOwner(), new Observer<Repo>() {
            @Override
            public void onChanged(Repo repo) {
                if (repo != null) {
                    repoNameTextView.setText(repo.name);
                    repoDescriptionTextView.setText(repo.description);
                    forksTextView.setText(String.valueOf(repo.forks));
                    starsTextView.setText(String.valueOf(repo.stars));
                }
            }
        });
    }
}
