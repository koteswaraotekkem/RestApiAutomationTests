package core.API.setup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestAPIBase {
    public static Response getCall(String query, String endPoint) {
        return given().header("Authorization", "Bearer " + "").contentType(ContentType.JSON).when()
                .get("instance_url + baseEndPoint + endPoint + query").then()
                .extract().response();

    }

    public static Response getObjectsEndPoint(String query, String endPoint) {
        return given().header("Authorization", "Bearer " + "accessToken").contentType(ContentType.JSON).when()
                .get("instance_url + sobjectsEndPoint + endPoint + query").then()
                .extract().response();

    }

    public static Response patchCall(String body, String endPoint) {
        return given().header("Authorization", "Bearer " + "accessToken").contentType(ContentType.JSON).body(body).when()
                .patch("instance_url + sobjectsEndPoint + endPoint").then().assertThat().statusCode(204)
                .extract().response();

    }
    public static Response patchCallWithOutAssert(String body, String endPoint) {
        return given().header("Authorization", "Bearer " + "accessToken").contentType(ContentType.JSON).body(body).when()
                .patch("instance_url + sobjectsEndPoint + endPoint").then()
                .extract().response();

    }
    public static Response patchRequest(String body, String endPoint) {
        return given().header("Authorization", "Bearer " + "accessToken").contentType(ContentType.JSON).body(body).when()
                .patch("instance_url +endPoint").then()
                .extract().response();

    }

    public static Response postCall(String endPoint, String payLoad) {
        return given().header("Authorization", "Bearer " + "accessToken").contentType(ContentType.JSON).body(payLoad).when()
                .post("instance_url + sobjectsEndPoint + endPoint").then().assertThat().statusCode(201)
                .extract().response();
    }

    public static Response postCallNoAuth(String endPoint, String payLoad) {
        return given().contentType(ContentType.JSON).body(payLoad).when().post(endPoint).
                then().extract().response();
    }

    public static Response postCallWithoutAssert(String endPoint, String payLoad) {
        return given().header("Authorization", "Bearer " + "accessToken").contentType(ContentType.JSON).body(payLoad).when()
                .post("instance_url + sobjectsEndPoint + endPoint").then()
                .extract().response();
    }

    public static Response deleteCall(String endPoint) {
        return given().header("Authorization", "Bearer " + "accessToken").contentType(ContentType.JSON).when()
                .delete("instance_url + sobjectsEndPoint + endPoint").then().assertThat().statusCode(204)
                .extract().response();
    }
    public static Response deleteCallForMultipleRecords(String Ids) {
        return given().header("Authorization", "Bearer " + "accessToken").contentType(ContentType.JSON).when()
                .delete("instance_url + sobjectsEndPointMultiple + Ids").then().assertThat().statusCode(200)
                .extract().response();
    }
    public static Response deleteCallNoAssert(String endPoint) {
        return given().header("Authorization", "Bearer " + "accessToken").contentType(ContentType.JSON).when()
                .delete("instance_url + sobjectsEndPoint + endPoint").then()
                .extract().response();
    }

    public static String getJsonString(Object object) {
        return getGsonAsObject().toJson(object);

    }

    public static Map<String, Object> getJsonStringAsMap(String json){
        return getGsonAsObject().fromJson(json, Map.class);
    }
    public static LinkedHashMap<String, Object> getJsonStringAsLinkedHashMap(String json){
        return getGsonAsObject().fromJson(json, LinkedHashMap.class);
    }

    public static Gson getGsonAsObject() {
        return new GsonBuilder().create();
    }

    public static <T> T getListOfObjectFromJsonString(String json, Type typeOfT) {
        Gson gson = getGsonAsObject();
        //Just make sure that all required fields was exists in JSON and also populated in pojo.
        T pojo = gson.fromJson(json, typeOfT);
        return pojo;
    }

}
