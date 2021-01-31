import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.*;
import org.junit.Assert;
import org.json.JSONObject;

import static io.restassured.RestAssured.get;
import static org.junit.matchers.JUnitMatchers.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class spotify {

    int param = 2;


    @Test
    public void test () {
        Response response = RestAssured.get("https://reqres.in/api/users?page=" + param);
        int statusCode = response.getStatusCode();
        Assert.assertEquals("status is ok", 212, statusCode);


    }
    @Test
    public void post() throws IOException, ParseException {


        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("C:\\Users\\Piotrek\\IdeaProjects\\rest assured\\src\\test\\java\\gejson.json")) {

            Object obj = jsonParser.parse(reader);

            System.out.println(obj);
            given()
                    .contentType(ContentType.JSON)

                    .with()
                    .body(obj)

                    .when()

                    .post("https://reqres.in/api/users?page=2")

                    .then()
                    .statusCode(201)
                    .assertThat().body("password", containsString("123elo"))
                    .assertThat().body("email", containsString("dupa@gmail.com"));

        }
    }

    @Test
    public void post2() throws IOException, ParseException {


        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("C:\\Users\\Piotrek\\IdeaProjects\\rest assured\\src\\test\\java\\gejson.json")) {

            //Read JSON file
            Object obj = jsonParser.parse(reader);


            String userId =
                    given().
                            contentType("application/json").
                            body(obj).
                            when().
                            post("https://reqres.in/api/users?page=2").
                            then().
                            statusCode(201).
                            extract().
                            path("id");
            System.out.println(userId);

            Response response = RestAssured.get("https://reqres.in/api/users/" + userId);
            System.out.println(response.getBody().asString());

        }
    }
}
