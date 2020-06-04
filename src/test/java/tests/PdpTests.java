package tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

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
    public void CheckDeliveryBlock() throws ParseException {
        Response response = am.getApiPdpHelper().getPdpResponse();
        String deliveryNearestDateFromResponse = response.jsonPath().get("eligibility.deliveryNearestDate");
        System.out.println("deliveryNearestDateFromResponse : " + deliveryNearestDateFromResponse);
    }

}
