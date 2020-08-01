package apimanager;


import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.AdditionalItemsAtPdp;
import model.Characteristics;
import model.User;
import model.UserReply;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.xml.parsers.DocumentBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static io.restassured.RestAssured.given;

public class ApiPdpHelper {

    String APIKEY = "NLdu-FEUbU-CCrd-otTWYJGhDfZZKYHAxVd-QksZEMMtCUkUKk";
    String CONTENTTYPE = "application/json";
    String BASEURI = "https://api.leroymerlin.ru/mobile";
    String DATE = getLocalDateTime()+"+03:00";

    public String getLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("localDateTime : " + localDateTime.toString());
        String localDateTimeFirstPart = localDateTime.toString().split("\\.", 2)[0];
        System.out.println("localDateTimeFirstPart : " + localDateTimeFirstPart);
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

    public String getMainPrice(String pathToFile) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject  object= (JSONObject) parser.
                parse(new FileReader(pathToFile));


        JSONArray pricesArray = (JSONArray) object.get("prices");
//        System.out.println("pricesArray : " + pricesArray);

        JSONObject priceMainObject = (JSONObject) pricesArray.get(0);
//        System.out.println("priceMainObject : " + priceMainObject);

        int priceMain = Integer.parseInt(priceMainObject.get("price").toString());
//        System.out.println("priceMain : " + priceMain );
        float priceMainFloat = (float) priceMain;
        String priceMainString = String.format(Locale.FRANCE,"%.02f", priceMainFloat);
//        System.out.println("priceMainString: " + priceMainString);

        String priceCurrency = priceMainObject.get("currency").toString();
//        System.out.println("priceCurrency : " + priceCurrency);

        String mainUom =  priceMainObject.get("uom").toString();

//        System.out.println("mainUom : " + mainUom);

        String priceMainRespTogether = priceMainString + " " + priceCurrency + "/" + mainUom;

        String priceMainRespReplaced = priceMainRespTogether.replace("₽","");


        System.out.println("priceMainRespTogether : " + priceMainRespTogether);
        System.out.println("priceMainRespReplaced : " + priceMainRespReplaced);

        return priceMainRespReplaced;
    }

    public String getSecondPrice(String pathToFile) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject  object= (JSONObject) parser.
                parse(new FileReader(pathToFile));


        JSONArray pricesArray = (JSONArray) object.get("prices");
//        System.out.println("pricesArray : " + pricesArray);

        JSONObject priceSecondObject = (JSONObject) pricesArray.get(1);
//        System.out.println("priceMainObject : " + priceMainObject);

        float priceSecond = Float.parseFloat(priceSecondObject.get("price").toString());
//        System.out.println("priceMain : " + priceMain );
        //float priceSecondFloat = (float) priceSecond;
        String priceSecString = String.format(Locale.FRANCE,"%.02f", priceSecond);
//        System.out.println("priceSecondString: " + priceSecondString);

        String priceCurrency = priceSecondObject.get("currency").toString();
//        System.out.println("priceCurrency : " + priceCurrency);

        String mainUom =  priceSecondObject.get("uom").toString();

//        System.out.println("secondUom : " + mainUom);

        String priceSecondRespTogether = priceSecString + " " + priceCurrency + "/" + mainUom;

        String priceSecondRespReplaced = priceSecondRespTogether.replace("₽","");


        System.out.println("priceSecondRespTogether : " + priceSecondRespTogether);
        System.out.println("priceSecondRespReplaced : " + priceSecondRespReplaced);

        return priceSecondRespReplaced;
    }

    public String getNearestDeliveryDate(String pathToFile) throws java.text.ParseException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject  object= (JSONObject) parser.
                parse(new FileReader(pathToFile));

        String timeNow = getLocalDateTime();
        String dateNowWithoutT = timeNow.split("T", 2)[0];
        System.out.println("dateNowWithoutT: " + dateNowWithoutT);
        Date dateNowValue = new SimpleDateFormat("yyyy-MM-dd").parse(dateNowWithoutT);


        JSONObject eligibilityObject = (JSONObject) object.get("eligibility");
        String  deliveryNearestDateFromResp = (String) eligibilityObject. get("deliveryNearestDate");
        System.out.println("deliveryNearestDateFromResp : " + deliveryNearestDateFromResp);

        String dateWithoutT = deliveryNearestDateFromResp.split("T", 2)[0];
        System.out.println("dateWithoutT: " + dateWithoutT);

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        Date dateValue = new SimpleDateFormat("yyyy-MM-dd").parse(dateWithoutT);

        String dateOfWeek = new SimpleDateFormat("EEEE", new Locale("ru")).format(dateValue);
        String dateOfWeekUpper = dateOfWeek.substring(0, 1).toUpperCase() + dateOfWeek.substring(1);
        String dateRusAll =
                DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("ru")).format(dateValue);
        System.out.println("dateRusAll : " + dateRusAll);

        String dateRus1Part = dateRusAll.split(" ", 3)[0];
        String dateRus2Part = dateRusAll.split(" ", 3)[1];

        String together ;
//        long s;


        if(dateNowValue.equals(dateValue)) {
            together = "Доставка • " + "Сегодня";
        } else {
            if((dateValue.getTime()-dateNowValue.getTime())/100000 == 864) {
                together = "Доставка • " + "Завтра" + ", " + dateRus1Part + " " + dateRus2Part;
            }
            else {
                together = "Доставка • " + dateOfWeekUpper + ", " + dateRus1Part + " " + dateRus2Part;
            }
        }

//        long t1 = dateValue.getTime();
//        long t2 = dateNowValue.getTime();
//        long t3 = t1-t2;
//        System.out.println("t3 " + t3);
        System.out.println(together);

        return together;
    }

    public List<Characteristics> getShortCharacteristics(String pathToFile) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject  object= (JSONObject) parser.
                parse(new FileReader(pathToFile));

        JSONArray characteristicsArray = (JSONArray) object.get("characteristics");

        List<Characteristics> characteristicsListFromResp = new ArrayList<>();

        for(int i=0; i< 5; i++) {
            JSONObject characteristic = (JSONObject) characteristicsArray.get(i);
//            System.out.println("characteristic : " + characteristic);
            Characteristics characteristics = new Characteristics()
                    .withDescription(characteristic.get("description").toString())
                    .withValue(characteristic.get("value").toString()
                            .replaceAll("[\\[\\]]","")
                            .replaceAll("[\"]","")
                            .replace(", ",","));

            characteristicsListFromResp.add(characteristics);
        }

        System.out.println("characteristicsListFromResp :" +characteristicsListFromResp);

        return characteristicsListFromResp;

    }

    public Set<AdditionalItemsAtPdp> getAdditionalItemsR(String pathToFile) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject  object = (JSONObject) parser.
                parse(new FileReader(pathToFile));

        JSONArray complementsArray = (JSONArray) object.get("complements");
        System.out.println("complementsArray :" + complementsArray);

        Set<AdditionalItemsAtPdp> itemsSetFromRes = new HashSet<>();

        for (int i=0; i<complementsArray.size();i++) {

            JSONObject complement = (JSONObject) complementsArray.get(i);
            String itemName = complement.get("displayedName").toString();

            JSONArray pricesArray = (JSONArray) complement.get("prices");
            JSONObject prices = (JSONObject) pricesArray.get(0);

            float mainPrice = Float.parseFloat(prices.get("price").toString());
            String mainUom = prices.get("uom").toString();
            String maincurrency = prices.get("currency").toString();

            String priceMainString = String.format(Locale.FRANCE, "%.02f", mainPrice);

            String priceMainTogether = priceMainString + "" + maincurrency  + "/" + mainUom;

            AdditionalItemsAtPdp currentItem = new AdditionalItemsAtPdp()
                    .withName(itemName)
                    .withMainPrice(priceMainTogether);

            itemsSetFromRes.add(currentItem);
        }
        System.out.println("itemsSetFromRes :" + itemsSetFromRes);
        return itemsSetFromRes;

    }

    public String getQntOfDeliveryStock(String pathToFile) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject  object = (JSONObject) parser.
                parse(new FileReader(pathToFile));

        String qntFromResponse = object.get("deliveryStock").toString();
        System.out.println("qntFromResponse : " + qntFromResponse);

        return qntFromResponse;
    }

    public String getStoresAtPickupBlock(String pathToFile) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(new FileReader(pathToFile));

        String qntStores = object.get("pickupStoresCount").toString();

        return qntStores;
    }

    public String getQntComments(String pathToFile) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject  object = (JSONObject) parser.
                parse(new FileReader(pathToFile));

        JSONObject metaObj = (JSONObject) object.get("meta");
        String qntComments = metaObj.get("total_count").toString();
        System.out.println("qntComments : " + qntComments);
        return qntComments;
    }

    public UserReply getUserRely(String pathToFile) throws IOException, ParseException, java.text.ParseException {
        JSONParser parser = new JSONParser();
        JSONObject  object = (JSONObject) parser.
                parse(new FileReader(pathToFile));

        JSONArray data = (JSONArray) object.get("data");
        JSONObject data_0 = (JSONObject) data.get(0);
//        System.out.println("data_0 : " + data_0);

        JSONObject attributes = (JSONObject) data_0.get("attributes");

        String rating = attributes.get("rating").toString();
//        System.out.println("rating : " + rating);

        String body = attributes.get("body").toString();
//        System.out.println("body : " + body);

        String pros = attributes.get("pros").toString();
//        System.out.println("pros : " + pros);

        String cons = attributes.get("cons").toString();
//        System.out.println("cons : " + cons);

        String created_at = attributes.get("created_at").toString();
//        System.out.println("created_at : " + created_at);
        String dateWithoutT = created_at.split("T", 2)[0];
//        System.out.println("dateWithoutT: " + dateWithoutT);

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        Date dateValue = new SimpleDateFormat("yyyy-MM-dd").parse(dateWithoutT);

        String dateOfWeek = new SimpleDateFormat("EEEE", new Locale("ru")).format(dateValue);
        String dateOfWeekUpper = dateOfWeek.substring(0, 1).toUpperCase() + dateOfWeek.substring(1);
        String dateRusAll =
                DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("ru")).format(dateValue);
//        System.out.println("dateRusAll : " + dateRusAll);

        String dateRus1Part = dateRusAll.split(" ", 3)[0];
        String dateRus2Part = dateRusAll.split(" ", 3)[1];

        String together_date = dateRus1Part + " " +  dateRus2Part;
//        System.out.println("together_date : " + together_date);

        String author_name = attributes.get("author_name").toString();
//        System.out.println("author_name : " + author_name);

        Boolean recommended = (Boolean) attributes.get("recommended");
//        System.out.println("recommended : " + recommended);

        String location_name = attributes.get("location_name").toString();
//        System.out.println("location_name : " + location_name);

        JSONArray author_details = (JSONArray) attributes.get("author_details");
        JSONObject author_details_0 = (JSONObject) author_details.get(0);

        String label = author_details_0.get("label").toString();
//        System.out.println("label : " + label);

        String value = author_details_0.get("value").toString();
//        System.out.println("value : " + value);

        String usage = label + ": " + value;

        UserReply userReplyFromResp = new UserReply()
                .withAuthor_name(author_name)
                .withCreated_at(together_date)
                .withLocation_name(location_name)
                .withValue(usage)
                .withBody(body)
                .withPros(pros)
                .withCons(cons)
                .withRecommended(recommended);

        System.out.println("userReplyFromResp : " + userReplyFromResp);

        return userReplyFromResp;
    }
}
