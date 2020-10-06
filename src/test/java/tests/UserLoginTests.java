package tests;

import io.restassured.response.Response;
import model.ErrorResponse;
import model.User;
import model.UserLogin;
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
        String userData[][] = {
                // just exist users
                {"I'lmatd","Baisna","tester1900@tester.ru", "qwertY12", "+79056788708", "11160670"},
                {"Sl","Fio","sl123321123@gmail.com","qwertyU2", "+71111111111", "13480011"},
                // Users with different domain name in email
                {"Slava", "Тестер", "s@gmail.com", "qwertyU1", "+79036788778", "13580239"},
                {"Slava", "Тестер", "sl1@yandex.ru", "qwertyU1", "+71234567890", "13580269"},
                {"Slava", "Тестер", "sl1@yahoo.com", "qwertyU1", "+71234567890", "13580285"},
                {"Slava", "Тестер", "sl1@mail.ru", "qwertyU1", "+71234567890", "13580290"},
                {"Slava", "Тестер", "sl1@nullgr.com", "qwertyU1", "+71234567890", "13580294"},
                // user with max quantity characters in Email 50 symbols
                {"Slava", "Тестер", "slava12345SLAVA67890_TESTER000ORGANIZATI@gmail.com", "qwertyU1", "+71234567890", "13580342"},
                // user with max quantity characters in Email and Password 50 symbols
                {"Slava", "Тестер", "slava12345SLAVA67890_TESTER000ORGANIZAT3@gmail.com", "slava12345SLAVA67890_TESTER000ORGANIZAT2@gmail.com", "+71234567890", "13580382"}
        };
        return userData;
    }


    @Test(dataProvider = "existingUsers", priority = 1)
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

        UserLogin userLoginResponse = am.getApiLoginHelper().getUserLogin(authorization);
        User userFromResponse = am.getApiLoginHelper().getUser(authorization);

        int statusCodeForAuth = am.getApiLoginHelper().getStatusCodeForAuth(authorization);
        long timeResponseForAuth = am.getApiLoginHelper().getTimeResponseForAuth(authorization);

        SoftAssert s = new SoftAssert();

        s.assertEquals(statusCodeForAuth, 200);
        s.assertNotNull(userLoginResponse.getAccessToken(), "Error! accessToken is null");
        s.assertNotNull(userLoginResponse.getRefreshToken(), "Error! refreshToken is null");
        s.assertNotNull(userLoginResponse.getExpiresIn(), "Error! expiresIn is null");
        s.assertEquals(userFromResponse, userExpected);
        s.assertTrue(timeResponseForAuth < 2000);
        s.assertAll();
    }

    @DataProvider (name = "EmptyAuthParam")
    Object[] getAuthParam() {
        String authParam[] = {""};
        return authParam;
    }

    @Test(dataProvider = "EmptyAuthParam", priority = 2)
    public void checkUnsuccessfulLogin(String authorization) {

        int statusCodeForAuth = am.getApiLoginHelper().getStatusCodeForAuth(authorization);

        ErrorResponse responseExpected = new ErrorResponse()
                .withStatus(false)
                .withMessage("Authorization parameter is not defined")
                .withStringCode("REQUEST_ERROR");

        //asserts with use Rest assured
//        Response response = am.getApiLoginHelper().getBaseResponseForAuth(authorization);
//
//        response.then().log().all().assertThat().statusCode(400)
//        .body("status", equalTo(responseExpected.getStatus()))
//        .body("errors[0].message", equalTo(responseExpected.getMessage()))
//        .body("errors[0].stringCode", equalTo(responseExpected.getStringCode()));

        ErrorResponse responseFromApi = am.getApiLoginHelper().getErrorResponse(authorization);
        long timeResponseForAuth = am.getApiLoginHelper().getTimeResponseForAuth(authorization);

        SoftAssert s = new SoftAssert();
        s.assertEquals(statusCodeForAuth, 400);
        s.assertEquals(responseFromApi, responseExpected);
        s.assertTrue(timeResponseForAuth < 2000);
        s.assertAll();

    }

    @DataProvider(name = "notExistEmail")
    Object[] getAuthParam2() {
        String authParam[] = {
                // not exist user in DB
                "czQ0MzM0NEB5YW5kZXgucnU6cXdlcnR5VTY3ODlvb2lnaA==",
                // not correct email sl1:qwertyU2
                "c2wxOnF3ZXJ0eVUy"};
        return authParam;
    }


    @Test(dataProvider = "notExistEmail", priority = 3)
    public void checkLoginNotExistEmail(String authorization) {

        ErrorResponse responseExpected = new ErrorResponse()
                .withStatus(false)
                .withMessage("Wrong login")
                .withStringCode("LOGIN_ERROR");

        int statusCodeForAuth = am.getApiLoginHelper().getStatusCodeForAuth(authorization);
        ErrorResponse responseFromApi = am.getApiLoginHelper().getErrorResponse(authorization);
        long timeResponseForAuth = am.getApiLoginHelper().getTimeResponseForAuth(authorization);

        SoftAssert s = new SoftAssert();
        s.assertEquals(statusCodeForAuth, 400);
        s.assertEquals(responseFromApi, responseExpected);
        s.assertTrue(timeResponseForAuth < 2000);
        s.assertAll();
    }

    @DataProvider(name = "notExistPasswordOrEmptyPassword")
    Object[] getAuthParam3() {
        String authParam[] = {
                // sl123321123@gmail.com:qwertyU0123
                "c2wxMjMzMjExMjNAZ21haWwuY29tOnF3ZXJ0eVUwMTIz",
                // sl123321123@gmail.com:
                "c2wxMjMzMjExMjNAZ21haWwuY29tOg==",
                // sl1@mail.ru:qwertyUu not correct password (without digit)
                "c2wxQG1haWwucnU6cXdlcnR5VXU=",
                // sl1@mail.ru:qwerty1234 not correct password (without Uppercase symbol)
                "c2wxQG1haWwucnU6cXdlcnR5MTIzNA==",
                // sl1@mail.ru:qwerty not correct password (less characters than 8)
                "c2wxQG1haWwucnU6cXdlcnR5"};
        return authParam;
    }


    @Test(dataProvider = "notExistPasswordOrEmptyPassword", priority = 4)
    public void checkLoginNotExistPasswordOrEmptyPassword(String authorization) {

        ErrorResponse responseExpected = new ErrorResponse()
                .withStatus(false)
                .withMessage("Wrong password")
                .withStringCode("PASSWORD_ERROR");

        int statusCodeForAuth = am.getApiLoginHelper().getStatusCodeForAuth(authorization);
        ErrorResponse responseFromApi = am.getApiLoginHelper().getErrorResponse(authorization);
        long timeResponseForAuth = am.getApiLoginHelper().getTimeResponseForAuth(authorization);

        SoftAssert s = new SoftAssert();
        s.assertEquals(statusCodeForAuth, 400);
        s.assertEquals(responseFromApi, responseExpected);
        s.assertTrue(timeResponseForAuth < 2000);
        s.assertAll();
    }

    @DataProvider(name = "EmptyEmail")
    Object[] getAuthParam4() {
        String authParam[] = {
                // :qwertyU0123
                "OnF3ZXJ0eVUwMTIz"
        };
        return authParam;
    }


    @Test(dataProvider = "EmptyEmail", priority = 5)
    public void checkLoginEmptyEmail(String authorization) {

        ErrorResponse responseExpected = new ErrorResponse()
                .withStatus(false)
                .withMessage("Oauth failed (check login)")
                .withStringCode("OAUTH_ERROR");

        int statusCodeForAuth = am.getApiLoginHelper().getStatusCodeForAuth(authorization);
        ErrorResponse responseFromApi = am.getApiLoginHelper().getErrorResponse(authorization);
        long timeResponseForAuth = am.getApiLoginHelper().getTimeResponseForAuth(authorization);

        SoftAssert s = new SoftAssert();
        s.assertEquals(statusCodeForAuth, 409);
        s.assertEquals(responseFromApi, responseExpected);
        s.assertTrue(timeResponseForAuth < 2000);
        s.assertAll();
    }

    @DataProvider(name = "NotExpectedSymbolsInBase64")
    Object[] getAuthParam5() {
        String authParam[] = {"sl1@gmail.com:qwertyU2"};
        return authParam;
    }


    @Test(dataProvider = "NotExpectedSymbolsInBase64", priority = 6)
    public void checkNotExpectedSymbolsInBase64(String authorization) {


        int statusCodeForAuth = am.getApiLoginHelper().getStatusCodeForAuth(authorization);
        long timeResponseForAuth = am.getApiLoginHelper().getTimeResponseForAuth(authorization);

        SoftAssert s = new SoftAssert();
        s.assertEquals(statusCodeForAuth, 400);
        s.assertTrue(timeResponseForAuth < 2000);
        s.assertAll();
    }

    @Test
    public void loginAndDeleteBasketAtBackEnd() {
        String email = "qwe@gmail.com";
        String password = "qwertyU1";
        AuthData = email + ":"+ password;
        authorization = Base64.getEncoder().encodeToString(AuthData.getBytes());
        String accessToken = am.getApiLoginHelper().getAccessToken(authorization);

        am.getApiBasketHelper().getResponseForBasketDelete(accessToken);
    }


}
