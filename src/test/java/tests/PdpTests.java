package tests;

import io.restassured.response.Response;
import model.AdditionalItemsAtPdp;
import model.Characteristics;
import model.NameCategoryAtPdp;
import org.apache.commons.math3.analysis.function.Add;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static junit.framework.Assert.assertEquals;

public class PdpTests extends TestBase {


    @Test
    public void parsePdpResponse() {

        Response response = am.getApiPdpHelper().getPdpResponse();
        String displayedName = response.jsonPath().get("displayedName");
        System.out.println("displayedName: " + displayedName);

        int articul = response.jsonPath().get("articul");
        System.out.println("articul: " + articul);

        ArrayList priceArray = response.jsonPath().get("prices.price");
        int priceMain = (int) priceArray.get(0);
        float priceSecond = (float) priceArray.get(1);
        System.out.println("priceMain: " + priceMain);
        System.out.println("priceSecond: " + priceSecond);
        float priceMainFloat = (float) priceMain;
        System.out.println("priceMainFloat: " + priceMainFloat);
        String priceMainString = String.format(Locale.FRANCE, "%.02f", priceMainFloat);
        System.out.println("priceMainString: " + priceMainString);

        ArrayList priceCurrency = response.jsonPath().get("prices.currency");
        String mainCurrency = (String) priceCurrency.get(0);
        String secondCurrency = (String) priceCurrency.get(1);

        System.out.println("mainCurrency : " + mainCurrency);
        System.out.println("secondCurrency : " + secondCurrency);

        ArrayList priceUom = response.jsonPath().get("prices.uom");
        String mainUom = (String) priceUom.get(0);
        String secondUom = (String) priceUom.get(1);

        System.out.println("mainUom : " + mainUom);
        System.out.println("secondUom : " + secondUom);

        String priceMainTogether = priceMainString + " " + mainCurrency  + "/" + mainUom ;
        // + "/" + mainUom

        assertEquals("442,00 ₽/шт.", priceMainTogether);
    }

    @Test
    public void checkDeliveryBlock() throws ParseException {

        String timeNow = am.getApiPdpHelper().getLocalDateTime();
        String dateNowWithoutT = timeNow.split("T", 2)[0];
        System.out.println("dateNowWithoutT: " + dateNowWithoutT);
        Date dateNowValue = new SimpleDateFormat("yyyy-MM-dd").parse(dateNowWithoutT);

        Response response = am.getApiPdpHelper().getPdpResponse();
        String deliveryNearestDateFromResp = response.jsonPath().get("eligibility.deliveryNearestDate");
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

    }

    @Test
    public void checkPickupBlock() throws ParseException {

        String timeNow = am.getApiPdpHelper().getLocalDateTime();
        String dateNowWithoutT = timeNow.split("T", 2)[0];
        System.out.println("dateNowWithoutT: " + dateNowWithoutT);
        Date dateNowValue = new SimpleDateFormat("yyyy-MM-dd").parse(dateNowWithoutT);

        Response response = am.getApiPdpHelper().getPdpResponse();
        String pickupNearestDateFromResp = response.jsonPath().get("eligibility.pickupNearestDate");
        System.out.println("pickupNearestDateFromResp : " + pickupNearestDateFromResp);

        String dateWithoutT = pickupNearestDateFromResp.split("T", 2)[0];
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
            together = "Самовывоз • " + "Сегодня";
        } else {
            if((dateValue.getTime()-dateNowValue.getTime())/100000 == 864) {
                together = "Самовывоз • " + "Завтра" + ", " + dateRus1Part + " " + dateRus2Part;
            }
            else {
                together = "Самовывоз • " + dateOfWeekUpper + ", " + dateRus1Part + " " + dateRus2Part;
            }
        }

        long t1 = dateValue.getTime();
        long t2 = dateNowValue.getTime();
        long t3 = t1-t2;
        System.out.println("t3 " + t3);
        System.out.println(together);

    }

    @Test
    public void checkCharacteristics() {
        Response response = am.getApiPdpHelper().getPdpResponse();
        ArrayList descriptionArray = response.jsonPath().get("characteristics.description");
        ArrayList valueArray = response.jsonPath().get("characteristics.value");

        List<Characteristics> characteristicsListFromResp = new ArrayList<>();

   for(int i=0; i< 5; i++) {
       Characteristics characteristics = new Characteristics()
               .withDescription(descriptionArray.get(i).toString())
               .withValue(valueArray.get(i).toString().replaceAll("[\\[\\]]",""));

       characteristicsListFromResp.add(characteristics);
   }

        System.out.println("characteristicsListFromResp :" +characteristicsListFromResp);

    }

    @Test
    public void checkNameCategoryAtPdp() {
        Response response = am.getApiPdpHelper().getPdpResponse();
        ArrayList categoriesArray = response.jsonPath().get("categories.familyNames");
        System.out.println("categoriesArray " + categoriesArray);

        Set<NameCategoryAtPdp> nameCategoryAtPdpSet = new HashSet<>();

        for(int i=0; i<categoriesArray.size();i++) {
            NameCategoryAtPdp nameCategory = new NameCategoryAtPdp()
                    .withNameCategory(categoriesArray.get(i).toString());

            nameCategoryAtPdpSet.add(nameCategory);
        }

        System.out.println("nameCategoryAtPdpSet " + nameCategoryAtPdpSet);
    }

    @Test
    public void checkAdditionalItemsAtPdp() {
        Response response = am.getApiPdpHelper().getPdpResponse();
        ArrayList itemsNameArray = response.jsonPath().get("complements.displayedName");
//        System.out.println("itemsArray :" +itemsNameArray);

        ArrayList priceArray = response.jsonPath().get("complements.prices.price");
        ArrayList uomArray = response.jsonPath().get("complements.prices.uom");
        ArrayList currencyArray = response.jsonPath().get("complements.prices.currency");

//        System.out.println("uomArray: " + uomArray);


        Set<AdditionalItemsAtPdp> itemsSetFromRes = new HashSet<>();

        for (int i=0; i<itemsNameArray.size();i++) {
            List<Integer> prices = (List<Integer>) priceArray.get(i);
            List<String> uoms = (List<String >) uomArray.get(i);
            List<String> currencies = (List<String >) currencyArray.get(i);
//            System.out.println("prices: " + prices);
            float priceMain =  (float) prices.get(0);
            String uomMain = uoms.get(0);
            String currencyMain = currencies.get(0);
//            System.out.println("priceMain: " + priceMain);
//            System.out.println("uomMain: " + uomMain);
//            System.out.println("currencyMain: " + currencyMain);

            String priceMainString = String.format(Locale.FRANCE, "%.02f", priceMain);
//            System.out.println("priceMainString: " + priceMainString);

            String priceMainTogether = priceMainString + "" + currencyMain  + "/" + uomMain;
//            System.out.println("priceMainTogether: " + priceMainTogether);

            AdditionalItemsAtPdp currentItem = new AdditionalItemsAtPdp()
                    .withName(itemsNameArray.get(i).toString())
                    .withMainPrice(priceMainTogether);

//            System.out.println("===currentItem === " + currentItem);

            itemsSetFromRes.add(currentItem);
        }
        System.out.println("itemsSetFromRes :" + itemsSetFromRes);

    }

    @Test
    public void ttt() throws IOException, org.json.simple.parser.ParseException, ParseException {
//
//        String mainPriceFromResponse = am.getApiPdpHelper()
//                .getMainPrice("src/test/mockFiles/getPdp_10073940_Mock.json");
//
//        String secondPriceFromResponse = am.getApiPdpHelper()
//                .getSecondPrice("src/test/mockFiles/getPdp_10073940_Mock.json");
//
//        String nearestDeliveryDateFromResp = am.getApiPdpHelper()
//                .getNearestDeliveryDate("src/test/mockFiles/getPdp_10073940_Mock.json");

        List<Characteristics> shortCharacteristicsFromResp = am.getApiPdpHelper()
                .getShortCharacteristics("src/test/mockFiles/getPdp_10073940_Mock.json");
    }


}
