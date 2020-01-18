package tests;

import model.RegResponse;
import model.UserReg;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.testng.Assert.assertEquals;

public class UserRegTests extends TestBase {
    String email;

    @BeforeMethod
    public String getRandomEmail() {
        String randomString = am.getApiRegHelper().getRandomeString();
        email = randomString + "@gmail.com";
        System.out.println(email);
        return email;
    }

    @Test // Registration check. Adding uniq user. Successful registration.
    public void checkUserReg_1() throws IOException, URISyntaxException {
        System.out.println("// Registration check. Adding uniq user. Successful registration.");

        UserReg user2 = new UserReg()
                .withFirstName("Slava")
                .withLastName("Test")
                .withRefStoreId(62)
                .withPhone("+79036788778")
                .withEmail(email)
                .withPassword("qwertyU1");

        int statusCodeFromApi = am.getApiRegHelper().getRegStatusCodeFromApi(user2);
        System.out.println("statusCodeFromApi " + statusCodeFromApi);

        assertEquals(statusCodeFromApi, (200));
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

        RegResponse regResponseExpected = new RegResponse()
                .withStatus(false)
                .withMessage("Sorry, this login already exists")
                .withStringCode("LOGIN_ERROR");

        int statusCodeFromApi = am.getApiRegHelper().getRegStatusCodeFromApi(user1);
        System.out.println("statusCodeFromApi " + statusCodeFromApi);

        RegResponse regResponseFromApi = am.getApiRegHelper().getRegResponseFromApi(user1);
        System.out.println("regResponseExpected " + regResponseExpected);

        assertEquals(statusCodeFromApi, (400));
        assertEquals(regResponseFromApi, regResponseExpected);
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

        RegResponse regResponseExpected = new RegResponse()
                .withStatus(false)
                .withMessage("Move failed (registration)")
                .withStringCode("MOVE_ERROR");

        int statusCodeFromApi = am.getApiRegHelper().getRegStatusCodeFromApi(user3);
        System.out.println("statusCodeFromApi " + statusCodeFromApi);

        RegResponse regResponseFromApi = am.getApiRegHelper().getRegResponseFromApi(user3);
        System.out.println("regResponseExpected " + regResponseExpected);

        assertEquals(statusCodeFromApi, (409));
        assertEquals(regResponseFromApi, regResponseExpected);
    }
}


