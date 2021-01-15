package com.example.majordaggertest.ui.list;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.majordaggertest.R;
import com.example.majordaggertest.base.BaseFragment;
import com.example.majordaggertest.data.model.Repo;
import com.example.majordaggertest.ui.detail.DetailsFragment;
import com.example.majordaggertest.ui.detail.DetailsViewModel;
import com.example.majordaggertest.util.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.security.AccessController.getContext;

public class ListFragment extends BaseFragment implements RepoSelectedListener {

    @BindView(R.id.recyclerView)
    RecyclerView listView;
    @BindView(R.id.tv_error)
    TextView errorTextView;
    @BindView(R.id.loading_view)
    View loadingView;
    @BindView(R.id.llError)
    LinearLayout llError;
    @BindView(R.id.bRetry)
    Button bRetry;

    @Inject
    ViewModelFactory viewModelFactory;
    private ListViewModel viewModel;

    @Override
    protected int layoutRes() {
        return R.layout.screen_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.class);
        listView.addItemDecoration(new DividerItemDecoration(getBaseActivity(), DividerItemDecoration.VERTICAL));
        listView.setAdapter(new RepoListAdapter(viewModel, this, this));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));

        observableViewModel();
    }

    @Override
    public void onRepoSelected(Repo repo) {
        DetailsViewModel detailsViewModel = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(DetailsViewModel.class);
        detailsViewModel.setSelectedRepo(repo);
        getBaseActivity().getSupportFragmentManager().beginTransaction().replace(R.id.screenContainer, new DetailsFragment())
                .addToBackStack(null).commit();
    }

    private void observableViewModel() {
        viewModel.getRepos().observe(getViewLifecycleOwner(), new Observer<List<Repo>>() {
            @Override
            public void onChanged(List<Repo> repos) {
                if (repos != null) listView.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError != null) if (isError) {
                    llError.setVisibility(View.VISIBLE);
                    errorTextView.setVisibility(View.VISIBLE);
                    errorTextView.setText("An Error Occurred While Loading Data!");
                    listView.setVisibility(View.GONE);
                    bRetry.setOnClickListener(v -> viewModel.fetchRepos());
                } else {
                    errorTextView.setVisibility(View.GONE);
                    llError.setVisibility(View.GONE);
                    errorTextView.setText(null);
                }
            }
        });

        viewModel.getLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading != null) {
                    loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                    if (isLoading) {
                        errorTextView.setVisibility(View.GONE);
                        listView.setVisibility(View.GONE);
                        llError.setVisibility(View.GONE);

                    }
                }
            }
        });
    }
}
