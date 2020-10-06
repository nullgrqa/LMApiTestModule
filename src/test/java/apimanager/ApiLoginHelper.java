package apimanager;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.ErrorResponse;
import model.User;
import model.UserLogin;
import model.UserReg;
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

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);

        return response;
    }

    public UserLogin getUserLogin(String authorization) {

        UserLogin userLoginResponse = getBaseResponseForAuth(authorization).then().extract().body().as(UserLogin.class);

//        String responseBody = response.getBody().asString();
//        JsonPath jsonPath = response.jsonPath();
//        String accessToken = jsonPath.get("access-token");
//        String refreshToken = jsonPath.get("refresh-token");
//        String expiresIn = jsonPath.get("expires-in");

        return userLoginResponse;
    }

    public User getUser(String authorization) {
        UserLogin userLoginFromApi = getBaseResponseForAuth(authorization).then().extract().body().as(UserLogin.class);

                User userFromApi = new User()
                .withCustomerNumber(userLoginFromApi.getUser().getCustomerNumber())
                .withEmail(userLoginFromApi.getUser().getEmail())
                .withPhone(userLoginFromApi.getUser().getPhone())
                .withFirstName(userLoginFromApi.getUser().getFirstName())
                .withLastName(userLoginFromApi.getUser().getLastName());

        return userFromApi;
    }

    public int getStatusCodeForAuth(String authorization) {

        return getBaseResponseForAuth(authorization).getStatusCode();
    }

    public long getTimeResponseForAuth (String authorization) {

        return getBaseResponseForAuth(authorization).getTime();
    }


    public ErrorResponse getErrorResponse(String authorization) {
        Response response = getBaseResponseForAuth(authorization);
        String responseBody = response.getBody().asString();

        JsonPath jsonPath = response.jsonPath();
        Boolean status = jsonPath.get("status");
        String message = jsonPath.get("errors[0].message");
        String stringCode = jsonPath.get("errors[0].stringCode");

        ErrorResponse errorResponse = new ErrorResponse()
                .withStatus(status)
                .withMessage(message)
                .withStringCode(stringCode);

        return errorResponse;
    }

    public String getAccessToken(String authorization) {
        Response response = getBaseResponseForAuth(authorization);
        JsonPath jsonPath = response.jsonPath();
        String accessToken = jsonPath.get("access-token");
        System.out.println(accessToken);
        return accessToken;
    }

}
