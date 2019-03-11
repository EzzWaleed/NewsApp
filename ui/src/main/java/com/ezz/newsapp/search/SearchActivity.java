package com.ezz.newsapp.search;

import android.os.Bundle;

import com.ezz.domain.resource.DataStatus;
import com.ezz.newsapp.App;
import com.ezz.newsapp.R;
import com.ezz.newsapp.databinding.ActivitySearchBinding;
import com.ezz.newsapp.news.adapter.NewsAdapter;
import com.ezz.newsapp.news.details.DetailsActivity;
import com.ezz.newsapp.search.di.DaggerSearchScreenComponent;
import com.ezz.presentation.viewmodel.search.SearchViewModel;
import com.ezz.presentation.viewmodel.viewmodel_factory.ViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.paginate.Paginate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

	public static final String SEARCH_QUERY_KEY = "searchQueryKey";

	SearchView searchView;

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@BindView(R.id.news_recycler_view)
	RecyclerView recyclerView;

	@Inject
	ViewModelFactory viewModelFactory;

	@Inject
	NewsAdapter newsAdapter;

	private SearchViewModel searchViewModel;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DataBindingUtil.setContentView(this, R.layout.activity_search);

		ButterKnife.bind(this);

		setSupportActionBar(toolbar);

		DaggerSearchScreenComponent.builder()
		.presentationComponent(App.getPresentationComponent(this))
		.build().inject(this);

		searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);

		if (searchViewModel.getSearchQueryState() == null){
			searchViewModel.searchFor(getIntent().getStringExtra(SEARCH_QUERY_KEY));
		}

		searchViewModel.getNewsLiveData().observe(this, newsUIPagedList -> newsAdapter.submitList(newsUIPagedList));

		recyclerView.setAdapter(newsAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		newsAdapter.setClickListener((newsUI, imageView) ->
		DetailsActivity.startDetailsActivity(this, newsUI, imageView));
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		MenuItem menuItem = menu.findItem(R.id.action_search);
		setupSearchView(menuItem);
		return true;
	}

	private void setupSearchView(MenuItem menuItem) {
		menuItem.expandActionView();
		searchView = (SearchView) menuItem.getActionView();
		searchView.setOnQueryTextListener(this);
		searchView.setQuery(searchViewModel.getSearchQueryState(), false);
		searchView.setIconified(false);
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		searchViewModel.searchFor(query);
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}
}
