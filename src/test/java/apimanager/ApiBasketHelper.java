package apimanager;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class ApiBasketHelper {
    String APIKEY = "NLdu-FEUbU-CCrd-otTWYJGhDfZZKYHAxVd-QksZEMMtCUkUKk";
    String CONTENTTYPE = "application/json";
    String BASEURI = "https://api.leroymerlin.ru/mobile";

    public Response getResponseForBasketDelete(String accessToken) {
        RestAssured.baseURI = BASEURI;
        RequestSpecification httpRequest = given();

        httpRequest.header("Content-Type",CONTENTTYPE);
        httpRequest.header("apikey",APIKEY);
        httpRequest.header("access-token", accessToken);

        JSONObject requestParams = new JSONObject();

        httpRequest.body(requestParams.toJSONString());

        Response response = httpRequest.request(Method.DELETE,"/basketDelete");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);

        return response;
    }
}
