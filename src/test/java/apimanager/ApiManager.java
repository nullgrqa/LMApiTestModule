package apimanager;

public class ApiManager {
    private ApiRegHelper apiRegHelper;

    public void dealWithApi() {
        apiRegHelper = new ApiRegHelper();
    }

    public ApiRegHelper getApiRegHelper() {
        return apiRegHelper;
    }
}
