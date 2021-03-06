package apimanager;

public class ApiManager {
    private ApiRegHelper apiRegHelper;
    private ApiLoginHelper apiLoginHelper;
    private ApiPdpHelper apiPdpHelper;
    private ApiBasketHelper apiBasketHelper;
    private ApiGroupHelper apiGroupHelper;

    public void dealWithApi() {
        apiRegHelper = new ApiRegHelper();
        apiLoginHelper = new ApiLoginHelper();
        apiPdpHelper = new ApiPdpHelper();
        apiBasketHelper = new ApiBasketHelper();
        apiGroupHelper = new ApiGroupHelper();
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
    public ApiGroupHelper getApiGroupHelper() {
        return apiGroupHelper;
    }

}
