package com.codepath.apps.simpleTwitterApp.fragments;

import org.json.JSONArray;

import android.os.Bundle;

import com.codepath.apps.simpleTwitterApp.TweetsAdapter;
import com.codepath.apps.simpleTwitterApp.TwitterClientApp;
import com.codepath.apps.simpleTwitterApp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	
	private long uid;
	
	private static final String UID_KEY = "uid";
	
	public static UserTimelineFragment newInstance(long uid) {
		UserTimelineFragment tlFragment = new UserTimelineFragment();
		Bundle args = new Bundle();
		args.putLong(UID_KEY, uid);
		tlFragment.setArguments(args);
		return tlFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		uid = getArguments().getLong(UID_KEY);
	}
	
	public void initTweets(){
		TwitterClientApp.getRestClient().getUserTimelineById(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray jsonTweets) {
				// TODO Auto-generated method stub
				tweets = Tweet.fromJson(jsonTweets);
				adapter = new TweetsAdapter(getActivity(), tweets);
				lvTweets.setAdapter(adapter);
			}
		}, uid);
	}
	
	public void moreTweets() {
		TwitterClientApp.getRestClient().getUserTimelineById(new JsonHttpResponseHandler() {

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
		}, uid);
	}
}
