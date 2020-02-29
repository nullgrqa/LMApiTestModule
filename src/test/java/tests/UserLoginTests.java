package tests;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.User;
import model.UserLogin;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

public class UserLoginTests extends TestBase {

    @Test
    public void checkLogin() {
        String authorization = "dGVzdGVyMTkwMEB0ZXN0ZXIucnU6cXdlcnRZMTI=";

        User userExpected = new User()
                .withCustomerNumber("11160670")
                .withEmail("tester1900@tester.ru")
                .withPhone("+79056788708")
                .withFirstName("I'lmatd")
                .withLastName("Baisna");

        //System.out.println("userExpected : " + userExpected);

        UserLogin userLoginFromApi = am.getApiLoginHelper().getUserLoginFromApi(authorization);

        User userFromApi = new User()
                .withCustomerNumber(userLoginFromApi.getUser().getCustomerNumber())
                .withEmail(userLoginFromApi.getUser().getEmail())
                .withPhone(userLoginFromApi.getUser().getPhone())
                .withFirstName(userLoginFromApi.getUser().getFirstName())
                .withLastName(userLoginFromApi.getUser().getLastName());

        SoftAssert s = new SoftAssert();

        s.assertNotNull(userLoginFromApi.getAccessToken(), "Error! accessToken is null");
        s.assertNotNull(userLoginFromApi.getRefreshToken(), "Error! refreshToken is null");
        s.assertNotNull(userLoginFromApi.getExpiresIn(), "Error! expiresIn is null");
        s.assertEquals(userFromApi, userExpected);
        s.assertAll();

    }
}
