package com.codepath.apps.simpleTwitterApp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Tweet implements Serializable{
	private User user;
    private String text;
    private String timestamp;
    
    public Tweet(){
		super();
	}
	
    public Tweet(User user, String text, String timestamp) {
    	super();
		this.user = user;
		this.text = text;
		this.timestamp = timestamp;
	}

    public User getUser() {
        return this.user;
    }

    public String getBody() {
        return this.text;
    }
    
    public String getTimestampShort() {
    	String t = this.timestamp;
    	int i = t.indexOf(':', 0);
    	t = t.substring(0, t.indexOf(':', i+1));
    	return t;
    }

    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
            tweet.text = jsonObject.getString("text");
            tweet.timestamp = jsonObject.getString("created_at");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = Tweet.fromJson(tweetJson);
            if (tweet != null) {
                tweets.add(tweet);
            }
        }

        return tweets;
    }
}