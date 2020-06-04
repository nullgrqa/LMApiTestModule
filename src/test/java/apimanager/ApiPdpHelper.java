package apimanager;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

public class ApiPdpHelper {

    String APIKEY = "NLdu-FEUbU-CCrd-otTWYJGhDfZZKYHAxVd-QksZEMMtCUkUKk";
    String CONTENTTYPE = "application/json";
    String BASEURI = "https://api.leroymerlin.ru/mobile";
    String DATE = getLocalDateTime()+"+03:00";

    public String getLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        //System.out.println(localDateTime.toString());
        String localDateTimeFirstPart = localDateTime.toString().split("\\.", 2)[0];
        //System.out.println("localDateTimeFirstPart : " + localDateTimeFirstPart);
        return localDateTimeFirstPart;
    }


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
