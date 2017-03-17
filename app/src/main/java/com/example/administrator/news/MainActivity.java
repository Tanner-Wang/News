package com.example.administrator.news;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    private static final String USGS_REQUEST_URL =
            "https://content.guardianapis.com/search?q=debate&tag=environment/plasticbags&from-date=2011-01-01&api-key=test";


    private static final int EARTHQUAKE_LOADER_ID = 0;

    /** Adapter for the list of earthquakes */
    private NewsAdapter mAdapter;

    private TextView mEmptyStateView;

    static ViewHolder holder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        holder.newsesListView = (ListView) findViewById(R.id.list);

        mEmptyStateView = (TextView) findViewById(R.id.empty_view);
        holder.newsesListView.setEmptyView( mEmptyStateView);


        // Create a new adapter that takes an empty list of books as input
        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        holder.newsesListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected book.
        holder.newsesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current book that was clicked on
                News currentNews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getmUrl());

                // Create a new intent to view the book URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        }
        else{
            View mLoading = findViewById(R.id.in_loading);
            mLoading.setVisibility(View.GONE);
            mEmptyStateView.setText(R.string.no_internet_connection);
        }

            holder.refresh = (TextView) findViewById(R.id.refresh_button);
        holder.refresh.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    refreshLoader();
                }
            });


    }

    static class ViewHolder{
        ListView newsesListView;
        TextView mEmptyStateView;
        TextView refresh;
        TextView webPublicationDate;
        TextView webTitle;
        TextView sectionName;
    }

    private void refreshLoader(){
        getLoaderManager().restartLoader(EARTHQUAKE_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new NewsLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newses) {

        View mLoading = findViewById(R.id.in_loading);
        mLoading.setVisibility(View.GONE);
        // Clear the adapter of previous book data
        mAdapter.clear();
        mEmptyStateView.setText(R.string.empty_view_text);
        // If there is a valid list of {@link book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newses != null && !newses.isEmpty()) {
            mAdapter.addAll(newses);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
