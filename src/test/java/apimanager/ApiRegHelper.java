package apimanager;


import model.UserReg;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public class ApiRegHelper {
    public String getRandomeString() {
        String generatedstring= RandomStringUtils.randomAlphabetic(8);
        return(generatedstring);
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
}
