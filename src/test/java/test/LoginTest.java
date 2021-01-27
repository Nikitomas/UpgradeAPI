/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.jayway.restassured.response.Response;
import model.LoginService;

public class LoginTest {

	int resLength=0;
	LoginService  loginService = new LoginService();
	JSONParser parser = new JSONParser();
	Response response;

	@Test 
	public void test_proper_login_info() throws ParseException {

		LoginService loginService = new LoginService();

		String body = "{\"username\": \"coding.challenge.login@upgrade.com\",\"password\": \"On$3XcgsW#9q\",\"recaptchaToken\": \"coding_challenge\"}";
		response = loginService.login(body);
		String responseStr = response.asString();
		
		String expectedProductType = "PERSONAL_LOAN";
		String expectedFirstName = "Ian";
		String expectedPurpose = "CREDIT_CARD";
		String actualProductType="";
		String actualFirstName="";
		String actualPurpose="";


		Assert.assertEquals(response.getStatusCode(),200,"Asserting that the response is 200");

		JSONParser parser = new JSONParser();

		try {
			JSONObject obj = (JSONObject) parser.parse(responseStr);
			actualFirstName = (obj.get("firstName")).toString();
			JSONArray loansInReview = (JSONArray) obj.get("loansInReview");	
			
			for (Iterator<JSONObject> i = loansInReview.iterator(); i.hasNext(); ) {
				actualProductType = (i.next()).get("productType").toString();
			}
			for (Iterator<JSONObject> i = loansInReview.iterator(); i.hasNext(); ) {
				actualPurpose = (i.next()).get("purpose").toString();
			}

		} catch (ParseException pe) {
		}
		
		Assert.assertEquals(actualProductType,expectedProductType,"Asserting that the product type is in fact Personal Loan");
		Assert.assertEquals(actualFirstName,expectedFirstName,"Asserting that the first name is in fact Ian");
		Assert.assertEquals(actualPurpose,expectedPurpose,"Asserting that the purpose is in fact Credit Card");

	}

	@Test 
	public void test_wrong_username_login_info() {

		LoginService loginService = new LoginService();

		String body = "{\"username\": \"wrong.email.address@upgrade.com\",\"password\": \"On$3XcgsW#9q\",\"recaptchaToken\": \"coding_challenge\"}";
		Response response = loginService.login(body);
		Assert.assertEquals(response.getStatusCode(),401,"Asserting that the response is 401 since the username info is not correct");
	}

	@Test 
	public void test_wrong_password_login_info() {

		LoginService loginService = new LoginService();

		String body = "{\"username\": \"coding.challenge.login@upgrade.com\",\"password\": \"wrongPassword\",\"recaptchaToken\": \"coding_challenge\"}";
		Response response = loginService.login(body);
		Assert.assertEquals(response.getStatusCode(),401,"Asserting that the response is 401 since the password info is not correct");
	}

	@Test 
	public void test_no_password_login_info() {

		LoginService loginService = new LoginService();

		String body = "{\"username\": \"coding.challenge.login@upgrade.com\",\"recaptchaToken\": \"coding_challenge\"}";
		Response response = loginService.login(body);
		Assert.assertEquals(response.getStatusCode(),400,"Asserting that the response is 401 since the password info is unavailable");
	}

	@Test 
	public void test_no_userName_login_info() {

		LoginService loginService = new LoginService();

		String body = "{\"password\": \"On$3XcgsW#9q\",\"recaptchaToken\": \"coding_challenge\"}";
		Response response = loginService.login(body);
		Assert.assertEquals(response.getStatusCode(),400,"Asserting that the response is 401 since the password info is unavailable");
	}
}