package edu.smart.model;

import java.util.ArrayList;

import edu.smart.pojo.UserDetails;

public class UserDetailsModel {
	
	
	private UserDetails userDetails;
	private ArrayList<UserDetails> userList;

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public ArrayList<UserDetails> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<UserDetails> userList) {
		this.userList = userList;
	}

	public UserDetailsModel() {
		userDetails = new UserDetails();
		userList = new ArrayList<UserDetails>();
	}
}
