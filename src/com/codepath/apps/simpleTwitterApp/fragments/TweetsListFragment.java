package com.codepath.apps.simpleTwitterApp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import com.codepath.apps.simpleTwitterApp.*;
import com.codepath.apps.simpleTwitterApp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ListView;

public class TweetsListFragment extends Fragment {
	
	protected TweetsAdapter adapter;
	protected ArrayList<Tweet> tweets;
	protected ListView lvTweets;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragments_tweets_list, container, false);
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		initTweets();
		lvTweets.setOnScrollListener(new EndlessScrollListener(3) {
			public void onLoadMore(int page, int totalItemsCount) {
				moreTweets();
			}
		});
		return v;
	}
	
	public TweetsAdapter getAdpater() {
		return adapter;
	}
	
	// To be overridden by inheriting classes 
	public void initTweets(){}
	public void moreTweets(){}

}
