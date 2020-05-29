package apimanager;

public class ApiManager {
    private ApiRegHelper apiRegHelper;
    private ApiLoginHelper apiLoginHelper;
    private ApiPdpHelper apiPdpHelper;

    public void dealWithApi() {
        apiRegHelper = new ApiRegHelper();
        apiLoginHelper = new ApiLoginHelper();
        apiPdpHelper = new ApiPdpHelper();
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

}
