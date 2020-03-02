package tests;

import model.User;
import model.UserLogin;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.Base64;

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
    public void checkLogin(String firstName, String lastName, String email,
                           String password, String phone, String customerNumber) {

        AuthData = email + ":"+ password;
        authorization = Base64.getEncoder().encodeToString(AuthData.getBytes());

        User userExpected = new User()
                .withCustomerNumber(customerNumber)
                .withEmail(email)
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
}
