package apimanager;

public class ApiManager {
    private ApiRegHelper apiRegHelper;
    private ApiLoginHelper apiLoginHelper;
    private ApiPdpHelper apiPdpHelper;
    private ApiBasketHelper apiBasketHelper;

    public void dealWithApi() {
        apiRegHelper = new ApiRegHelper();
        apiLoginHelper = new ApiLoginHelper();
        apiPdpHelper = new ApiPdpHelper();
        apiBasketHelper = new ApiBasketHelper();
    }

    public ApiRegHelper getApiRegHelper() {
        return apiRegHelper;
    }
    public ApiLoginHelper getApiLoginHelper() {
        return apiLoginHelper;
    }
    public ApiPdpHelper getApiPdpHelper() {
        return apiPdpHelper;
    }
    public ApiBasketHelper getApiBasketHelper() { return apiBasketHelper; }

}
