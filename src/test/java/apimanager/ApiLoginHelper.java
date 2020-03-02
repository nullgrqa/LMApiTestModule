package apimanager;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.User;
import model.UserLogin;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class ApiLoginHelper {
    String APIKEY = "NLdu-FEUbU-CCrd-otTWYJGhDfZZKYHAxVd-QksZEMMtCUkUKk";
    String CONTENTTYPE = "application/json";
    String BASEURI = "https://api.leroymerlin.ru/mobile";

    public Response getBaseResponseForAuth(String authorization) {
        RestAssured.baseURI = BASEURI;
        RequestSpecification httpRequest = given();

        httpRequest.header("Content-Type",CONTENTTYPE);
        httpRequest.header("apikey",APIKEY);

        JSONObject requestParams = new JSONObject();
        requestParams.put("Authorization", authorization);

        httpRequest.body(requestParams.toJSONString());

        Response response = httpRequest.request(Method.POST,"/user/auth");

        return response;
    }

    public UserLogin getUserLoginFromApi(String authorization) {

        UserLogin userLoginFromApi = getBaseResponseForAuth(authorization).then().extract().body().as(UserLogin.class);
        //System.out.println("userLoginFromApi :" + userLoginFromApi);

//        User userFromApi = new User()
//                .withCustomerNumber(userLoginFromApi.getUser().getCustomerNumber())
//                .withEmail(userLoginFromApi.getUser().getEmail())
//                .withPhone(userLoginFromApi.getUser().getPhone())
//                .withFirstName(userLoginFromApi.getUser().getFirstName())
//                .withLastName(userLoginFromApi.getUser().getLastName());

        //System.out.println("userFromApi : " + userFromApi);

//        String responseBody = response.getBody().asString();
//        JsonPath jsonPath = response.jsonPath();
//        String accessToken = jsonPath.get("access-token");
//        String refreshToken = jsonPath.get("refresh-token");
//        String expiresIn = jsonPath.get("expires-in");

//        System.out.println("accessToken : " + accessToken);
//        System.out.println("refreshToken : " + refreshToken);
//        System.out.println("expiresIn : " + expiresIn);

//        String accessToken1=null;
//        String refreshToken1=null;
//        String expiresIn1=null;

        return userLoginFromApi;
    }

    public int getStatusCodeForAuth(String authorization) {

        return getBaseResponseForAuth(authorization).getStatusCode();
    }

    public long getTimeResponseForAuth (String authorization) {

        return getBaseResponseForAuth(authorization).getTime();
    }




}
