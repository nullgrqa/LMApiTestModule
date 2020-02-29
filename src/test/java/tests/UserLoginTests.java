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

        User userFromApi = am.getApiLoginHelper().getUserFromApi(authorization);

        SoftAssert s = new SoftAssert();

//        s.assertNotNull(accessToken, "Error! accessToken is null");
//        s.assertNotNull(refreshToken, "Error! refreshToken is null");
//        s.assertNotNull(expiresIn, "Error! expiresIn is null");
        s.assertEquals(userExpected, userFromApi);
        s.assertAll();

    }
}
