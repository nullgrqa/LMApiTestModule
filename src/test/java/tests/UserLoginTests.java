package tests;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.User;
import model.UserLogin;
import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Base64;

import static io.restassured.RestAssured.given;

public class UserLoginTests extends TestBase {

    private String AuthData;
    private String authorization;

    @DataProvider (name = "one")
    Object[] getUserData() {
        String userData[][] = {{"I'lmatd","Baisna","tester1900@tester.ru", "qwertY12", "+79056788708", "11160670"},
                               {"Sl","Fio","sl123321123@gmail.com","qwertyU2", "+71111111111", "13480011"}};
        return userData;
    }

    @Test(dataProvider = "one")
    public void checkLogin(String firstName, String lastName, String emeil,
                           String password, String phone, String customerNumber) {

        AuthData = emeil + ":"+ password;
        authorization = Base64.getEncoder().encodeToString(AuthData.getBytes());
        //String authorization = "dGVzdGVyMTkwMEB0ZXN0ZXIucnU6cXdlcnRZMTI=";

        User userExpected = new User()
                .withCustomerNumber(customerNumber)
                .withEmail(emeil)
                .withPhone(phone)
                .withFirstName(firstName)
                .withLastName(lastName);

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
