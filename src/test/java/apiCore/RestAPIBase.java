package apiCore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.HttpStatus;


import static io.restassured.RestAssured.given;

public class RestAPIBase {

    public static Response getCall(String query, String endPoint) {
        return given().header("Authorization", "Bearer ").contentType(ContentType.JSON).when()
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

    public static Response postCall(String payLoad) {
        Map<String, String> headers = setCommonHeaders(RestTestBase.tokenResponse.getTicket());
        return given().headers(headers).body(payLoad).when()
                .post("dnis/").then()
                .assertThat().statusCode(HttpStatus.SC_OK).extract().response();
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
        T pojo = gson.fromJson(json, typeOfT);
        return pojo;
    }

    public static <T> T extractAsResp(ExtractableResponse<Response> response, Type typeOfT){
       return response.as(typeOfT);
    }
    private static Map<String, String> setCommonHeaders(String token){
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("content-type", "application/json");
        headers.put("origin", "https://cc-pc-gt-b.qa.tillster.com");
        headers.put("referer", "https://cc-pc-gt-b.qa.tillster.com/");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("ticket", token);
       // headers.put("Cookie", "__cf_bm=b7xSO8bsm.K4VmUi0n4h05ebp0pRmxYCpLndVsyqAHA-1730901615-1.0.1.1-tMTD1FfA67CtaKaYfH1MsWjk0Obh.8MKyvg.e5Aqr55HJvJx.t7zkXHunH88JLtfTlOW0nYGsiIxOCQpHxjaVA");
        return headers;
    }

}
