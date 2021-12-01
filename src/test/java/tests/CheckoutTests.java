package tests;

import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.IOException;

public class CheckoutTests extends TestBase {

    @Test
    public void checkNameOfItemForDeliveryTab() throws IOException, ParseException {
//        String nameFromResp = am.getApiGroupHelper()
//                .getNameOfItem("src/test/mockFiles/groupOneItemMore1000r.json");

        String priceOfItemFromResp = am.getApiGroupHelper()
                .getPriceOfItem("src/test/mockFiles/groupOneItemMore1000r.json");

//        String totalWeightFromResp = am.getApiGroupHelper()
//                .getTotalWeight("src/test/mockFiles/groupOneItemMore1000r.json");


//        String totalWeightFromResp = am.getApiGroupHelper()
//                .getTotalPrice("src/test/mockFiles/groupOneItemMore1000r.json");


//        String totalWeightFromResp = am.getApiGroupHelper()
//                .getTogetherPriceOfItem("src/test/mockFiles/groupOneItemWithTwoQntMore1000r.json",
//                        "deliveryGroups");


    }
}
