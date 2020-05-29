package tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;

public class PdpTests extends TestBase {

    @Test
    public void parsePdpResponse(){

        Response response = am.getApiPdpHelper().getPdpResponse();
    }
}
