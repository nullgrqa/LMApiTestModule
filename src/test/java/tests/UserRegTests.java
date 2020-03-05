package tests;

import model.ErrorResponse;
import model.UserReg;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.restassured.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UserRegTests extends TestBase {
    String email;

    public String getRandomEmail() {
        String randomString = am.getApiRegHelper().getRandomeString();
        email = randomString + "@gmail.com";
        System.out.println(email);
        return email;
    }

    @DataProvider (name = "validUserData")
        Object[] getUser() {
        String user[][] = {{"Slava", "Test", "62", "+79036788778", getRandomEmail(), "qwertyU1"}};
        return user;
        }


    @Test(dataProvider = "validUserData") // Registration check. Adding uniq user. Successful registration.
    public void checkUserReg_1(String firstName, String lastName, String refStoreId, String phone,
                               String email, String password)
    {
        UserReg user2 = new UserReg()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withRefStoreId(Integer.parseInt(refStoreId))
                .withPhone(phone)
                .withEmail(email)
                .withPassword(password);

        Response response = am.getApiRegHelper().getBaseResponseForReg(user2);

        response.then().log().all().assertThat().statusCode(200)
        .body("msg", equalTo("success"));
    }

    @Test(dataProvider = "validUserData")
    public void checkTimeOfResponseSuccessReg(String firstName, String lastName, String refStoreId, String phone,
                                              String email, String password)
    {
        UserReg user2 = new UserReg()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withRefStoreId(Integer.parseInt(refStoreId))
                .withPhone(phone)
                .withEmail(email)
                .withPassword(password);

        long timeResponseForReg = am.getApiRegHelper().getTimeResponseForReg(user2);

        System.out.println("timeResponseForReg : " + timeResponseForReg);

        assertTrue(timeResponseForReg < 3000);
    }

    @Test //Registration check. User already exist in DB.
    public void checkUserReg_2() throws IOException, URISyntaxException {
        System.out.println("//Registration check. User already exist in DB.");

        UserReg user1 = new UserReg()
                .withFirstName("Slava")
                .withLastName("Test")
                .withRefStoreId(62)
                .withPhone("+79036788778")
                .withEmail("slava17puh56@gmail.com")
                .withPassword("qwertyU1");

        ErrorResponse errorResponseExpected = new ErrorResponse()
                .withStatus(false)
                .withMessage("Sorry, this login already exists")
                .withStringCode("LOGIN_ERROR");

        int statusCodeFromApi = am.getApiRegHelper().getRegStatusCodeFromApi(user1);
        System.out.println("statusCodeFromApi " + statusCodeFromApi);

        ErrorResponse errorResponseFromApi = am.getApiRegHelper().getRegResponseFromApi(user1);
        System.out.println("regResponseExpected " + errorResponseExpected);

        assertEquals(statusCodeFromApi, (400));
        assertEquals(errorResponseFromApi, errorResponseExpected);
    }

    @Test // Registration check. FirstName has 101 symbols
    public void checkUserReg_3() throws IOException, URISyntaxException {
        System.out.println("// Registration check. FirstName has 101 symbols");

        String name = "slavaslavaslavaslavaslavaslavaslavaslavaslavaslavaslavaslavaslavaslavaslavaslavaslavaslavaslavaslavaw";

        UserReg user3 = new UserReg()
                .withFirstName(name)
                .withLastName("Test")
                .withRefStoreId(62)
                .withPhone("+79036788778")
                .withEmail(email)
                .withPassword("qwertyU1");

        ErrorResponse errorResponseExpected = new ErrorResponse()
                .withStatus(false)
                .withMessage("Move failed (registration)")
                .withStringCode("MOVE_ERROR");

        int statusCodeFromApi = am.getApiRegHelper().getRegStatusCodeFromApi(user3);
        System.out.println("statusCodeFromApi " + statusCodeFromApi);

        ErrorResponse errorResponseFromApi = am.getApiRegHelper().getRegResponseFromApi(user3);
        System.out.println("regResponseExpected " + errorResponseExpected);

        assertEquals(statusCodeFromApi, (409));
        assertEquals(errorResponseFromApi, errorResponseExpected);
    }


}


