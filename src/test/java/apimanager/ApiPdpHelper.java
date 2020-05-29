package apimanager;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class ApiPdpHelper {

    String APIKEY = "NLdu-FEUbU-CCrd-otTWYJGhDfZZKYHAxVd-QksZEMMtCUkUKk";
    String CONTENTTYPE = "application/json";
    String BASEURI = "https://api.leroymerlin.ru/mobile";
    String DATE = "2020-05-29T21:42:15+03:00";


    public Response getPdpResponse() {
        RestAssured.baseURI = BASEURI;
        RequestSpecification httpRequest = given();

        httpRequest.header("Content-Type", CONTENTTYPE);
        httpRequest.header("apikey", APIKEY);
        httpRequest.header("Date", DATE);

        Response response = httpRequest.request(Method.GET,"/v2/products/getProduct?idProduct=10073940&refStoreId=62&regionsId=34");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);

        return response;
    }
}
