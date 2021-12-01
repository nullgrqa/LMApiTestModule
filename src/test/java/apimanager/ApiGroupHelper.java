package apimanager;

import com.sun.org.apache.xpath.internal.objects.XNumber;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Locale;

public class ApiGroupHelper {
    String APIKEY = "NLdu-FEUbU-CCrd-otTWYJGhDfZZKYHAxVd-QksZEMMtCUkUKk";
    String CONTENTTYPE = "application/json";
    String BASEURI = "https://api.leroymerlin.ru/mobile";


    public String getNameOfItem(String pathToFile) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(new FileReader(pathToFile));

        JSONObject ecom = (JSONObject) object.get("ecom");

        JSONArray deliveryGroupsArray = (JSONArray) ecom.get("deliveryGroups");
//        System.out.println("deliveryGroupsArray : " + deliveryGroupsArray);

        JSONObject firstGroupObject = (JSONObject) deliveryGroupsArray.get(0);
//        System.out.println("firstGroupObject : " + firstGroupObject);

        JSONArray products = (JSONArray) firstGroupObject.get("products");
//        System.out.println("products " + products);

        JSONObject firstProduct = (JSONObject) products.get(0);
//        System.out.println("firstProduct " + firstProduct);

        String nameOfProduct = (String) firstProduct.get("displayedName");
        System.out.println("nameOfProduct from Response: " + nameOfProduct);

        return nameOfProduct;
    }

    public String getPriceOfItem(String pathToFile) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(new FileReader(pathToFile));

        JSONObject ecom = (JSONObject) object.get("ecom");

        JSONArray deliveryGroupsArray = (JSONArray) ecom.get("deliveryGroups");
//        System.out.println("deliveryGroupsArray : " + deliveryGroupsArray);

        JSONObject firstGroupObject = (JSONObject) deliveryGroupsArray.get(0);
//        System.out.println("firstGroupObject : " + firstGroupObject);

        JSONArray products = (JSONArray) firstGroupObject.get("products");
//        System.out.println("products " + products);

        JSONObject firstProduct = (JSONObject) products.get(0);
//        System.out.println("firstProduct " + firstProduct);

        JSONArray prices = (JSONArray)  firstProduct.get("prices");
        JSONObject price = (JSONObject) prices.get(0);

//        String priceOfProduct = (String) price.get("price");
//        System.out.println("priceOfProduct from Response: " + priceOfProduct);

        float priceMain = Float.parseFloat(price.get("price").toString());

        System.out.println("priceMain : " + priceMain );
        //float priceMainFloat = (float) priceMain;
        String priceMainString = String.format(Locale.FRANCE,"%.02f", priceMain);
        String[] priceMainStringSub = priceMainString.split(",",2);
        String priceF = priceMainStringSub[1].trim();
        if (priceF.equals("00")) {
            priceMainString = priceMainString.replaceAll(",00", "");
        }
        System.out.println("priceF : " + priceF);

        System.out.println("price of Item from resp: " + priceMainString);

//        String priceCurrency = price.get("currency").toString();
////        System.out.println("priceCurrency : " + priceCurrency);
//
//        String mainUom =  price.get("uom").toString();
//
////        System.out.println("mainUom : " + mainUom);
//
//        String priceMainRespTogether = priceMainString + " " + priceCurrency + "/" + mainUom;
//
//        String priceMainRespReplaced = priceMainRespTogether.replace("₽","");
//
//        System.out.println("price of Item from response " + priceMainRespReplaced);

        return priceMainString;
    }

    public String getTotalWeight(String pathToFile) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(new FileReader(pathToFile));

        JSONObject ecom = (JSONObject) object.get("ecom");

        JSONArray deliveryGroupsArray = (JSONArray) ecom.get("deliveryGroups");
//        System.out.println("deliveryGroupsArray : " + deliveryGroupsArray);

        JSONObject firstGroupObject = (JSONObject) deliveryGroupsArray.get(0);
//        System.out.println("firstGroupObject : " + firstGroupObject);

        float totalWeight = Float.parseFloat(firstGroupObject.get("totalWeight").toString());
        String totalWeightString = String.format(Locale.FRANCE,"%.01f", totalWeight);
        System.out.println("totalWeightString : " + totalWeightString);

        return totalWeightString;
    }

    public String getTotalPrice(String pathToFile) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(new FileReader(pathToFile));

        JSONObject ecom = (JSONObject) object.get("ecom");

        JSONArray deliveryGroupsArray = (JSONArray) ecom.get("deliveryGroups");
//        System.out.println("deliveryGroupsArray : " + deliveryGroupsArray);

        JSONObject firstGroupObject = (JSONObject) deliveryGroupsArray.get(0);
//        System.out.println("firstGroupObject : " + firstGroupObject);

        float totalWeight = Float.parseFloat(firstGroupObject.get("totalPrice").toString());
        String totalWeightString = String.format(Locale.FRANCE,"%.02f", totalWeight);
        System.out.println("totalWeightString : " + totalWeightString);

        return totalWeightString;
    }

    public String getTogetherPriceOfItem(String pathToFile, String type) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(new FileReader(pathToFile));

        JSONObject ecom = (JSONObject) object.get("ecom");

        JSONArray deliveryGroupsArray = (JSONArray) ecom.get(type);
//        System.out.println("deliveryGroupsArray : " + deliveryGroupsArray);

        JSONObject firstGroupObject = (JSONObject) deliveryGroupsArray.get(0);
//        System.out.println("firstGroupObject : " + firstGroupObject);

        JSONArray products = (JSONArray) firstGroupObject.get("products");
//        System.out.println("products " + products);

        JSONObject firstProduct = (JSONObject) products.get(0);
//        System.out.println("firstProduct " + firstProduct);

        String totalProductPrice =  firstProduct.get("totalProductPrice").toString();

        float priceMainFloat = Float.parseFloat(totalProductPrice);
        String togetherPrice = String.format(Locale.FRANCE,"%.02f", priceMainFloat);
        System.out.println("price of Item from resp at: " + type + " " + togetherPrice);

        return togetherPrice;
    }
}
