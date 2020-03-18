package edu.smart.util;

import edu.smart.model.UserDetailsModel;
import edu.smart.pojo.UserDetails;

public class UserRegistration {
	
	public UserDetailsModel setUserType(UserDetailsModel userDetailsModel, String userType) {
		userDetailsModel.getUserDetails().setUserType(userType);
		return userDetailsModel;
	}

	public UserDetailsModel setUserModel(UserDetails userDetails, UserDetailsModel userDetailsModel) {
		userDetailsModel.setUserDetails(userDetails);
		return userDetailsModel;
	}
}