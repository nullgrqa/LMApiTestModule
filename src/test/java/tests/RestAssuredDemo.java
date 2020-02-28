package tests;

import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Header;
import io.restassured.http.Headers;
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
import utils.XLUtility;

import java.io.IOException;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class RestAssuredDemo extends TestBase {

    @Test
    public void testRestAssuredDemo_GET() {
        //Specify base URI
        RestAssured.baseURI="https://api.leroymerlin.ru/mobile";
        //Request object
        RequestSpecification httpRequest = given();

        httpRequest.header("Content-Type", "application/json");
        httpRequest.header("apikey", "NLdu-FEUbU-CCrd-otTWYJGhDfZZKYHAxVd-QksZEMMtCUkUKk");
        //Response object
        Response response = httpRequest.request(Method.GET, "/regions");
        //Given responseBody
        String responseBody = response.getBody().asString();
        //Given responseStatusCode
        int responseStatusCode = response.getStatusCode();

        System.out.println("response of regions : " + responseBody);
        System.out.println("statusCode of regions : " + responseStatusCode);

       // Assert.assertTrue(responseBody.contains("Москва"));
    }

    @Test
    public void testRestAssuredDemo_Post() {
        //Specify base URI
        RestAssured.baseURI = "https://api.leroymerlin.ru/mobile/user";
        //Request object
        RequestSpecification httpRequest = given();
        httpRequest.header("Content-Type", "application/json");
        httpRequest.header("apikey", "NLdu-FEUbU-CCrd-otTWYJGhDfZZKYHAxVd-QksZEMMtCUkUKk");
        //Request payload sending with POST request
        JSONObject requestParams = new JSONObject();
        requestParams.put("lastName", "Test");
        requestParams.put("firstName", "Slava");
        requestParams.put("refStoreId", 62);
        requestParams.put("email", "test1111@gmail.com");
        requestParams.put("phone", "+79036788778");
        requestParams.put("password", "qwertyU1");
        //Attach data to the request
        httpRequest.body(requestParams.toJSONString());

        //Response object
        Response response = httpRequest.request(Method.POST, "/register");

        //Given responseBody
        String responseBody = response.getBody().asString();
        //Given responseStatusCode
        int responseStatusCode = response.getStatusCode();

        System.out.println("response of registration : " + responseBody);
        System.out.println("statusCode of registration : " + responseStatusCode);

        Boolean status = response.jsonPath().get("status");
        ArrayList arrayListErrors = response.jsonPath().get("errors");
        ArrayList arrayListMessage = response.jsonPath().get("errors.message");
        ArrayList arrayListStringCode = response.jsonPath().get("errors.stringCode");

        System.out.println(" status : " + status);
        System.out.println("errors.message : " + arrayListMessage);
        System.out.println("errors.stringCode : " + arrayListStringCode);

        assertFalse(status);
        assertEquals(arrayListMessage.toString(), "[Sorry, this login already exists]");

        //Validiting headers
        String contentType = response.header("Content-Type");
        System.out.println("value of Content-Type from response in headers: " + contentType);

        //receive all headers
        Headers allheaders = response.headers();
        for (Header header : allheaders) {
            System.out.println(header.getName() + "   " + header.getValue());
        }
    }

     @Test
     public void GetWeatherDetails() {
        RestAssured.baseURI="http://restapi.demoqa.com/utilities/weather/city";

        RequestSpecification httpRequest = given();

        Response response = httpRequest.request(Method.GET, "/Delhi");

        System.out.println(response.getBody().asString());

         JsonPath jsonPath = response.jsonPath();

         String s = jsonPath.get("City");

         System.out.println(s);

      }

      @Test
    public void AutherizationTest() {
        RestAssured.baseURI="http://restapi.demoqa.com/authentication/CheckForAuthentication";

        //Basic auth
          PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
          authScheme.setUserName("ToolsQA");
          authScheme.setPassword("TestPassword");

          RestAssured.authentication = authScheme;

          RequestSpecification httpRequest = given();

          Response response = httpRequest.request(Method.GET, "/");

          String responseBody = response.getBody().asString();

          System.out.println(responseBody);

      }

      @DataProvider(name="empdataprovider")
      Object[] getEmpData() {
        String empData[][] = {{"sss","1000","18"},{"dddd","3000","25"}};
        return empData;
      }

    @DataProvider(name = "customerData")
    public Object[][] retrieveCustomerDataFromExcel() throws IOException {

        String xlFilePath = System.getProperty("user.dir") + "/src/test/resources/empdata.xls";
        System.out.println("xlFilePath = " + xlFilePath);
        int rowCount = XLUtility.getRowCount(xlFilePath, "Sheet1");
        System.out.println(rowCount);
        int columnCount = XLUtility.getCellCount(xlFilePath, "Sheet1", 1);
        System.out.println(columnCount);

        String customerData[][] = new String [rowCount][columnCount];
        for (int currentRow = 1; currentRow <= rowCount; currentRow++){
            for(int currentColumn = 0; currentColumn < columnCount; currentColumn++){
                customerData[currentRow-1][currentColumn] = XLUtility.getCellData(xlFilePath, "Sheet1", currentRow, currentColumn);
            }
        }
        return (customerData);
    }

      @Test (dataProvider = "customerData")
    public void postNewEmployees(String ename, String esal, String eage) {
        RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
          RequestSpecification httpRequest = given();
          JSONObject requestParams = new JSONObject();
          requestParams.put("name", ename);
          requestParams.put("salary", esal);
          requestParams.put("age", eage);

          httpRequest.header("Content-Type","application/json");
          httpRequest.body(requestParams.toJSONString());
          Response response = httpRequest.request(Method.POST, "/create");

          String responseBody = response.getBody().asString();

      }

    @Test
    public void checkLogin() {
        String authorization = "dGVzdGVyMTkwMEB0ZXN0ZXIucnU6cXdlcnRZMTI=";

        User userExpected = new User()
                .withCustomerNumber("11160670")
                .withEmail("tester1900@tester.ru")
                .withPhone("+79056788708")
                .withFirstName("I'lmatd")
                .withLastName("Baisna1");

        System.out.println("userExpected : " + userExpected);


        RestAssured.baseURI = "https://api.leroymerlin.ru/mobile";
        RequestSpecification httpRequest = given();

        httpRequest.header("Content-Type","application/json");
        httpRequest.header("apikey","NLdu-FEUbU-CCrd-otTWYJGhDfZZKYHAxVd-QksZEMMtCUkUKk");

        JSONObject requestParams = new JSONObject();
        requestParams.put("Authorization", authorization);

        httpRequest.body(requestParams.toJSONString());

        Response response = httpRequest.request(Method.POST,"/user/auth");

        UserLogin userLoginFromApi = response.then().extract().body().as(UserLogin.class);
        System.out.println("userLoginFromApi :" + userLoginFromApi);

        User userFromApi = new User()
                .withCustomerNumber(userLoginFromApi.getUser().getCustomerNumber())
                .withEmail(userLoginFromApi.getUser().getEmail())
                .withPhone(userLoginFromApi.getUser().getPhone())
                .withFirstName(userLoginFromApi.getUser().getFirstName())
                .withLastName(userLoginFromApi.getUser().getLastName());

        System.out.println("userFromApi : " + userFromApi);

        String responseBody = response.getBody().asString();
        JsonPath jsonPath = response.jsonPath();
        String accessToken = jsonPath.get("access-token");
        String refreshToken = jsonPath.get("refresh-token");
        String expiresIn = jsonPath.get("expires-in");

        System.out.println("accessToken : " + accessToken);
        System.out.println("refreshToken : " + refreshToken);
        System.out.println("expiresIn : " + expiresIn);

        String accessToken1=null;
        String refreshToken1=null;
        String expiresIn1=null;

        SoftAssert s = new SoftAssert();

        s.assertNotNull(accessToken1, "Error! accessToken is null");
        s.assertNotNull(refreshToken, "Error! refreshToken is null");
        s.assertNotNull(expiresIn1, "Error! expiresIn is null");

        s.assertEquals(userExpected, userFromApi);
        s.assertAll();

    }

    }

