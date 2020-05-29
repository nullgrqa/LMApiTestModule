package apimanager;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.ErrorResponse;
import model.UserReg;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiRegHelper {

    String email;
    String APIKEY = "NLdu-FEUbU-CCrd-otTWYJGhDfZZKYHAxVd-QksZEMMtCUkUKk";
    String CONTENTTYPE = "application/json";
    String BASEURI = "https://api.leroymerlin.ru/mobile";

    public String getRandomeString() {
        String generatedstring= RandomStringUtils.randomAlphabetic(8);
        return(generatedstring);
    }

    public String getRandomEmail() {
        String randomString = getRandomeString();
        email = randomString + "@gmail.com";
        System.out.println(email);
        return email;
    }

    public Response getBaseResponseForReg(UserReg user) {
        RestAssured.baseURI = BASEURI;
        RequestSpecification httpRequest = given();

        httpRequest.header("Content-Type",CONTENTTYPE);
        httpRequest.header("apikey",APIKEY);

        JSONObject requestParams = new JSONObject();
        requestParams.put("firstName", user.getFirstName());
        requestParams.put("lastName", user.getLastName());
        requestParams.put("refStoreId", user.getRefStoreId());
        requestParams.put("email", user.getEmail());
        requestParams.put("phone", user.getPhone());
        requestParams.put("password", user.getPassword());

        httpRequest.body(requestParams.toJSONString());

        Response response = httpRequest.request(Method.POST,"/user/register");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);

        return response;
    }

    public int getStatusCodeForReg(UserReg user) {
        return getBaseResponseForReg(user).getStatusCode();
    }

    public long getTimeResponseForReg(UserReg user) {
        return getBaseResponseForReg(user).getTime();
    }




    public int getRegStatusCodeFromApi(UserReg user1) throws IOException, URISyntaxException {
        CloseableHttpClient client = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder("https://api.leroymerlin.ru/mobile/user/register");

        HttpPost postRequest = new HttpPost(uriBuilder.build());
        postRequest.setHeader("Content-Type", "application/json");
        postRequest.setHeader("apikey", "NLdu-FEUbU-CCrd-otTWYJGhDfZZKYHAxVd-QksZEMMtCUkUKk");

        String json = "{ \"lastName\": \""+ user1.getLastName()+ "\"," +
                "\"firstName\": \""+ user1.getFirstName() +"\"," +
                "\"refStoreId\": "+ user1.getRefStoreId()+"," +
                "\"email\": \""+ user1.getEmail()+"\"," +
                "\"phone\": \""+ user1.getPhone()+"\"," +
                "\"password\": \""+ user1.getPassword()+"\"}";

        System.out.println("json " + json);


        HttpEntity entity = new ByteArrayEntity(json.getBytes("UTF-8"));
        postRequest.setEntity(entity);

        CloseableHttpResponse responseHeader = client.execute(postRequest);
        System.out.println("responseHeader" + responseHeader);

        System.out.println("StatusCode " + responseHeader.getStatusLine().getStatusCode());
        int statusCode = responseHeader.getStatusLine().getStatusCode();

        return statusCode;

    }

//    public int getRegStatusCodeFromApiRestAssured(UserReg user1) {
//        //Specify base URI
//        RestAssured.baseURI="https://api.leroymerlin.ru/mobile/user/register";
//        //Request object
//        RequestSpecification httpRequest = RestAssured.given();
//        //Response object
//        Response response = httpRequest.request(Method.POST);
//
//
//        return 0;
//    }


    public ErrorResponse getRegResponseFromApi(UserReg user1) throws IOException, URISyntaxException {
        ErrorResponse errorResponse;
        String message = null;
        String stringCode = null;

        CloseableHttpClient client = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder("https://api.leroymerlin.ru/mobile/user/register");

        HttpPost postRequest = new HttpPost(uriBuilder.build());
        postRequest.setHeader("Content-Type", "application/json");
        postRequest.setHeader("apikey", "NLdu-FEUbU-CCrd-otTWYJGhDfZZKYHAxVd-QksZEMMtCUkUKk");

        String json = "{ \"lastName\": \""+ user1.getLastName()+ "\"," +
                "\"firstName\": \""+ user1.getFirstName() +"\"," +
                "\"refStoreId\": "+ user1.getRefStoreId()+"," +
                "\"email\": \""+ user1.getEmail()+"\"," +
                "\"phone\": \""+ user1.getPhone()+"\"," +
                "\"password\": \""+ user1.getPassword()+"\"}";
        //System.out.println("\""+user1.getEmail()+"\"");
        System.out.println("json " + json);


        HttpEntity entity = new ByteArrayEntity(json.getBytes("UTF-8"));
        postRequest.setEntity(entity);

        CloseableHttpResponse responseHeader = client.execute(postRequest);
        System.out.println("responseHeader" + responseHeader);

        System.out.println("StatusCode " + responseHeader.getStatusLine().getStatusCode());
        responseHeader.getStatusLine().getStatusCode();

        HttpEntity entity2 = responseHeader.getEntity();
        String responseBody = EntityUtils.toString(entity2);
        System.out.println("responseBody" + responseBody);



        ErrorResponse errorResponse1 =
                new Gson().fromJson(responseBody, new TypeToken<ErrorResponse>(){}.getType());

        System.out.println("regResponse1.getStatus(): " + errorResponse1.getStatus());

        JsonParser jsonParser = new JsonParser();
        JsonArray parsed =  jsonParser.parse(responseBody).getAsJsonObject().getAsJsonArray("errors");

        List<ErrorResponse> errorResponse2 =
                new Gson().fromJson(parsed, new TypeToken<List<ErrorResponse>>(){}.getType());

        for (ErrorResponse r: errorResponse2) {
            message = r.getMessage();
            stringCode = r.getStringCode();

            System.out.println("r.getMessage() " + r.getMessage());
            System.out.println("r.getStringCode() " + r.getStringCode());
        }

        errorResponse = new ErrorResponse()
                .withStatus(errorResponse1.getStatus())
                .withMessage(message)
                .withStringCode(stringCode);

        System.out.println("regResponse " + errorResponse);

        return errorResponse;
    }


}
