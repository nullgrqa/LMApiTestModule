package tests;

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
}


