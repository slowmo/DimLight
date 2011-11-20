package se.netlight.dimlight.model;

import se.netlight.dimlight.objects.User;

public class UserWrapper {
	private User user;

	public UserWrapper(User user) {
		this.user = user;
	}
	
	public boolean isLoggedin() {
		return user != null;
	}	
}
