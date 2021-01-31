package restpackage;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class helper {

    public static int restGetStatus(String url, int paramenter){
        Response response = RestAssured.get(url + paramenter);
        int statusCode = response.getStatusCode();
        return statusCode;
    }
}
