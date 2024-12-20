import apiCore.RestTestBase;
import apiCore.RestAPIBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.java.Log;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import user.userpojo.UserPojo;

@Log
public class UsersTest extends RestTestBase {
    int stepNumber = 1;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test(testName = "jira id: 1234")
    public void createUser() {
        STEP_INFO(stepNumber++, "Create a user", "User should be created");

        STEP_INFO(stepNumber++, "Validate a user presence", "User should be available in Oracle Db");

    }
}
