package tests;

import io.restassured.response.Response;
import model.*;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

//        List<Characteristics> shortCharacteristicsFromResp = am.getApiPdpHelper()
//                .getShortCharacteristics("src/test/mockFiles/getPdp_10073940_Mock.json");

//        Set<AdditionalItemsAtPdp> additionalItemsFromResp = am.getApiPdpHelper()
//                .getAdditionalItemsR("src/test/mockFiles/getPdp_10073940_Mock.json");
//        System.out.println("additionalItemsFromResp.size() : " + additionalItemsFromResp.size());

//        String  qntFromDeliveryStock = am.getApiPdpHelper()
//                .getQntOfDeliveryStock("src/test/mockFiles/getPdp_10073940_Mock.json");

//        String  qntFromDeliveryStock = am.getApiPdpHelper()
//                .getStoresAtPickupBlock("src/test/mockFiles/getPdp_10073940_Mock.json");
//        System.out.println(qntFromDeliveryStock);

//        String antComments = am.getApiPdpHelper()
//                .getQntComments("src/test/mockFiles/comments_first_page_art_10073940.json");

//        UserReply userReply = am.getApiPdpHelper()
//                .getUserRely("src/test/mockFiles/comments_first_page_art_10073940.json");

        Set<StoresAtPdp> storesFromResp = am.getApiPdpHelper()
                .getStores("src/test/mockFiles/getPdp_10073940_Mock.json",
                        "src/test/mockFiles/Stores.json");
    }

    @Test
    public void testLists() {
        List<Sample_1> list_1 = new ArrayList<>();
        Sample_1 obj_1_1 = new Sample_1().withId(1).withName("name_1");
        Sample_1 obj_1_2 = new Sample_1().withId(2).withName("name_2");
        Sample_1 obj_1_3 = new Sample_1().withId(4).withName("name_3");
        list_1.add(obj_1_1);
        list_1.add(obj_1_2);
        list_1.add(obj_1_3);

        System.out.println(list_1);

        List<Sample_2> list_2 = new ArrayList<>();
        Sample_2 obj_2_1 = new Sample_2().withId(1).withDescribe("describe_1");
        Sample_2 obj_2_2 = new Sample_2().withId(2).withDescribe("describe_2");
        Sample_2 obj_2_3 = new Sample_2().withId(3).withDescribe("describe_3");
        list_2.add(obj_2_1);
        list_2.add(obj_2_2);
        list_2.add(obj_2_3);

        System.out.println(list_2);

        List<Sample_3> list_3 = new ArrayList<>();


        int z=0;
        do {
            for (int i = 0; i < list_2.size(); i++) {
                if (list_1.get(z).getId() == list_2.get(i).getId()) {

                    Sample_3 obj_3 = new Sample_3()
                            .withId(list_2.get(i).getId())
                            .withName(list_1.get(z).getName())
                            .withDescribe(list_2.get(i).getDescribe());
                    System.out.println("obj_3: " + obj_3);

                    list_3.add(obj_3);
                }
            }
            z++;
        } while (z<list_1.size());

        System.out.println("list_3 : " + list_3);
    }


}
