package DNISTests;

import apiCore.RestTestBase;
import dnis.DnisDetails;
import apiCore.RestAPIBase;
import dnis.DnisDetailsResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class CreateDNSTests extends RestTestBase {
    public String dnisNum = new Random().nextInt() + "";
    private int stepNum = 1;
    private String tenantName = "tao-tilster";
    private String storeId = "PC-Events-Store";

    @Test(testName = "create a dnis")
    public void createDNIS() {
        STEP_INFO(stepNum++, "Prepare a DNIS payload", "Data should be available in string format");
        DnisDetails dnisDetailsPojo = prepareDNISData();

        STEP_INFO(stepNum++, "Convert object to a Json String", "");
        String dnisDataPayload = RestAPIBase.getJsonString(dnisDetailsPojo);

        STEP_INFO(stepNum++, "Make a post call", "verify record is inserted ");
        Response response = RestAPIBase.postCall(dnisDataPayload);
        DnisDetailsResponse dnisDetails = RestAPIBase.getListOfObjectFromJsonString(response.getBody().asString(), DnisDetailsResponse.class);

        Assert.assertEquals(dnisDetails.getTenant(), tenantName, "Inserted record" + tenantName +  "is not appeared in response");
        Assert.assertEquals(dnisDetails.getId().getDnis(), dnisNum, "Inserted record" + dnisNum + "is not appeared in response");
        Assert.assertEquals(dnisDetails.getStoreId(), storeId, "Inserted record" + dnisNum + "is not appeared in response");
    }

    private DnisDetails prepareDNISData() {
        DnisDetails dnisDetailsPojo = new DnisDetails();
        dnisDetailsPojo.getId().setDnis(dnisNum);
        dnisDetailsPojo.getStoreId().setStoreId(storeId);
        dnisDetailsPojo.setTenant(tenantName);
        return dnisDetailsPojo;
    }
}
