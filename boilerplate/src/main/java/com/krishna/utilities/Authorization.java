package com.krishna.utilities;

public class Authorization {

	private String auth;

	public Authorization(String auth) {
		this.auth = auth;
	}

	public String getHeader() {
		if ((this.auth).equalsIgnoreCase("Basic")) {
			return "Authorization";
		}
		if ((this.auth).equalsIgnoreCase("oAuth")) {
			return "Access-Token";
		}
		if ((this.auth).equalsIgnoreCase("SSO")) {
			return "sso-access-token";
		}

		else {
			System.out.println("No valid header match found");
			return null;
		}
	}

	public String getAuth() {
		if (this.auth.equalsIgnoreCase("Basic")) {
			return UtilityMethods.getBasicAuth(ConfigReader.getConfig("api_user_name"),
					ConfigReader.getConfig("api_password"));
		}
		if (this.auth.equalsIgnoreCase("oAuth")) {
			return UtilityMethods.getoAuth();
		}
		if (this.auth.equalsIgnoreCase("SSO")) {
			return UtilityMethods.authTokenIpWhiteList();
		} 
		else {
			System.out.println("No valid auth token found");
			return null;
		}
	}

}