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
import android.widget.ListView;

public class TweetsListFragment extends Fragment {
	
	private TweetsAdapter adapter;
	private ArrayList<Tweet> tweets;
	private ListView lvTweets;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragments_tweets_list, container, false);
	}
	
	public void initTweets(){
		TwitterClientApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray jsonTweets) {
				// TODO Auto-generated method stub
				tweets = Tweet.fromJson(jsonTweets);
				adapter = new TweetsAdapter(getActivity(), tweets);
				lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);
				lvTweets.setAdapter(adapter);
			}
		});
	}
	
	public void moreTweets() {
		TwitterClientApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray jsonTweets) {
				// TODO Auto-generated method stub
				if (tweets.size() >= 25) {
					return;
				}
				tweets.addAll(Tweet.fromJson(jsonTweets));
				adapter = new TweetsAdapter(getActivity(), tweets);
				lvTweets.setAdapter(adapter);
			}
		});
	}
	
	public TweetsAdapter getAdpater() {
		return adapter;
	}

}
