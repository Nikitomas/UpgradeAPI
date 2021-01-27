package model;

import java.util.UUID;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class LoginService {

	public final String LOGIN_URL = "https://credapi.credify.tech/api/brportorch/v2/login";
	final String uuid = UUID.randomUUID().toString();

	Response res;
	
	public Response login(String body) {
		try {
			res = RestAssured.given()
					.header("x-cf-source-id", "coding-challenge")
					.header("x-cf-corr-id", uuid)
					.header("Content-Type", "application/json")
					.body(body).post(LOGIN_URL);

		} catch (Exception e) {
			System.out.println(e);

			throw e;
		}
	//	System.out.println(uuid);

		return res;
	}
}
