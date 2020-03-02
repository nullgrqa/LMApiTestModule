package tests;

import model.ErrorResponse;
import model.User;
import model.UserLogin;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.Base64;
import static org.hamcrest.MatcherAssert.assertThat;
import static junit.framework.Assert.assertEquals;

public class UserLoginTests extends TestBase {

    private String AuthData;
    private String authorization;

    @DataProvider (name = "existingUsers")
    Object[] getUserData() {
        String userData[][] = {{"I'lmatd","Baisna","tester1900@tester.ru", "qwertY12", "+79056788708", "11160670"},
                               {"Sl","Fio","sl123321123@gmail.com","qwertyU2", "+71111111111", "13480011"}};
        return userData;
    }
    

    @Test(dataProvider = "existingUsers")
    public void checkSuccessfulLogin(String firstName, String lastName, String email,
                           String password, String phone, String customerNumber) {

        AuthData = email + ":"+ password;
        authorization = Base64.getEncoder().encodeToString(AuthData.getBytes());

        User userExpected = new User()
                .withCustomerNumber(customerNumber)
                .withEmail(email)
                .withPhone(phone)
                .withFirstName(firstName)
                .withLastName(lastName);

        UserLogin userLoginFromApi = am.getApiLoginHelper().getUserLoginFromApi(authorization);
        User userFromApi = am.getApiLoginHelper().getUserFromApi(authorization);

        int statusCodeForAuth = am.getApiLoginHelper().getStatusCodeForAuth(authorization);
        long timeResponseForAuth = am.getApiLoginHelper().getTimeResponseForAuth(authorization);

        SoftAssert s = new SoftAssert();

        s.assertEquals(statusCodeForAuth, 200);
        s.assertNotNull(userLoginFromApi.getAccessToken(), "Error! accessToken is null");
        s.assertNotNull(userLoginFromApi.getRefreshToken(), "Error! refreshToken is null");
        s.assertNotNull(userLoginFromApi.getExpiresIn(), "Error! expiresIn is null");
        s.assertEquals(userFromApi, userExpected);
        s.assertTrue(timeResponseForAuth < 2000);
        s.assertAll();
    }

    @DataProvider (name = "EmptyAuthParam")
    Object[] getAuthParam() {
        String authParam[] = {""};
        return authParam;
    }

    @Test(dataProvider = "EmptyAuthParam")
    public void CheckUnsuccessfulLogin(String authorization) {

        int statusCodeForAuth = am.getApiLoginHelper().getStatusCodeForAuth(authorization);

        ErrorResponse responseExpected = new ErrorResponse()
                .withStatus(false)
                .withMessage("Authorization parameter is not defined")
                .withStringCode("REQUEST_ERROR");

//        Response response = am.getApiLoginHelper().getBaseResponseForAuth(authorization);
//
//        response.then().log().all().assertThat().statusCode(400)
//        .body("status", equalTo(responseExpected.getStatus()))
//        .body("errors[0].message", equalTo(responseExpected.getMessage()))
//        .body("errors[0].stringCode", equalTo(responseExpected.getStringCode()));

        ErrorResponse responseFromApi = am.getApiLoginHelper().getErrorResponse(authorization);

        //System.out.println("statusCodeForAuth : " + statusCodeForAuth);

        SoftAssert s = new SoftAssert();
        s.assertEquals(statusCodeForAuth, 400);
        s.assertEquals(responseExpected, responseFromApi);
        s.assertAll();

    }
}
