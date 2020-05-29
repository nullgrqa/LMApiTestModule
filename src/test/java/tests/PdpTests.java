package tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;

public class PdpTests extends TestBase {

    @Test
    public void parsePdpResponse(){

        Response response = am.getApiPdpHelper().getPdpResponse();
        String displayedName = response.jsonPath().get("displayedName");
        System.out.println("displayedName: " + displayedName);

        int articul = response.jsonPath().get("articul");
        System.out.println("articul: " + articul);
    }
}
