package com.codepath.apps.simpleTwitterApp.models;

import java.io.Serializable;

import org.json.JSONObject;

public class User implements Serializable{
	private String name;
	private Long id;
	private String screenName;
	private int numTweets;
	private int numFollowers;
	private String tagline;
	private int numFollowing;
	private String profileImgUrl;
	
	public User() {
		super();
	}
	
    public User(String name, Long id) {
		super();
		this.name = name;
		this.id = id;
	}

	public String getName() {
        return this.name;
    }

    public long getId() {
        return this.id;
    }

    public String getScreenName() {
        return this.screenName;
    }
    
    public String getDescription() {
    	return this.tagline;
    }
    
    public String getProfileImageUrl() {
        return this.profileImgUrl;
    }

    public int getNumTweets() {
        return this.numTweets;
    }

    public int getFollowersCount() {
        return this.numFollowers;
    }

    public int getFriendsCount() {
        return this.numFollowing;
    }
    
    public String getIdStr() {
    	return this.id.toString();
    }

    public static User fromJson(JSONObject json) {
        User u = new User();

        try {
            u.name = json.getString("name");
            u.id = json.getLong("id");
            u.numFollowers = json.getInt("followers_count");
            u.numTweets = json.getInt("statuses_count");
            u.screenName = json.getString("screen_name");
            u.tagline = json.getString("description");
            u.numFollowing = json.getInt("friends_count");
            u.profileImgUrl = json.getString("profile_image_url");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return u;
    }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.screenName;
	}



}