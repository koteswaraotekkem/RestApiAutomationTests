package apiCore;

import dnis.LoginRequest;
import dnis.TilsterTokenResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.java.Log;
import org.testng.annotations.BeforeClass;
import static io.restassured.RestAssured.given;

@Log
public class RestTestBase {
    public static TilsterTokenResponse tokenResponse;

    @BeforeClass(alwaysRun = true)
    public void preReqSetUp() {
        RestAssured.baseURI = "https://api-pc-gt-b.qa.tillster.com/platform-call-center-web-service";
        Response response = getAccessToken();
        tokenResponse = RestAPIBase.getListOfObjectFromJsonString(response.getBody().asString(), TilsterTokenResponse.class);
        System.out.println(tokenResponse.getTicket() + " ======================TICKET================================/n");
    }

    public Response getAccessToken() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("bqamanager@tillster.com");
        loginRequest.setPassword("managerqab");
        loginRequest.setTenant("pc-gt");

        Response response = given()
                .header("accept", "*/*")
                .header("accept-language", "en-US,en;q=0.9")
                .header("content-type", "application/json")
                .header("origin", "https://cc-pc-gt-b.qa.tillster.com")
                .header("referer", "https://cc-pc-gt-b.qa.tillster.com/")
                .header("ticket", "null")
                //.header("Cookie", "__cf_bm=pA6xn_lSVWyW_nvCpzmY368soONHAZsNJsIiX7crVNI-1730882331-1.0.1.1-RiFXGAH2vldrwQnSZUPck4tymPk_ooYXdlqlC_g_4zPHjRDYfEbSo8KA4VY3_alViJcDYKODQB4HPJ4QdgLtqQ")
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .post("/agents/login");
        response.then().statusCode(200);
        return response;
    }

    public void STEP_INFO(int stepNumber, String testStep, String expectedResult) {
        log.info("\n" + stepNumber + ": \n Description: " + testStep + "\n Expected Result: " + expectedResult + " \n");
    }
}
