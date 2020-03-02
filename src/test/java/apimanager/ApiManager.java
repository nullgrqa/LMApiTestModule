package apimanager;

public class ApiManager {
    private ApiRegHelper apiRegHelper;
    private ApiLoginHelper apiLoginHelper;

    public void dealWithApi() {
        apiRegHelper = new ApiRegHelper();
        apiLoginHelper = new ApiLoginHelper();
    }

    public ApiRegHelper getApiRegHelper() {
        return apiRegHelper;
    }
    public ApiLoginHelper getApiLoginHelper() {
        return apiLoginHelper;
    }


}
