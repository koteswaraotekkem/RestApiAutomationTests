import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.zaproxy.clientapi.core.Alert;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

public class SecurityScanTests {

private static final String ZAP_API_URL = "http://localhost:8080";
    private static final String ZAP_API_KEY="d8jfoo6nuobnkj12p6qlt2li1m";
    private static final String TARGET_URL = "https://demo.testfire.net/login.jsp";

    public static void main(String[] args) throws ClientApiException {
        WebDriver driver = new ChromeDriver();
        ClientApi zapclient = new ClientApi("localhost", 8080, ZAP_API_KEY);
        driver.get(TARGET_URL);
        System.out.println("starting spider for: " + TARGET_URL);

        ApiResponse response = zapclient.spider.scan(TARGET_URL, null, null, null, null);
        String scanID = response.toString();
        while (true) {
            ApiResponse status = null;
            try {
                status = zapclient.spider.status(scanID);
            } catch (ClientApiException ex) {
                throw new RuntimeException(ex);
            }
            if (Integer.parseInt(status.toString()) >= 100) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        System.out.println("starting active scan for: " + TARGET_URL);
        response = zapclient.ascan.scan(TARGET_URL, null, null, null, null, null);
        scanID = response.toString();
        while (true) {
            ApiResponse status = zapclient.ascan.status(scanID);
            if (Integer.parseInt(status.toString()) >= 100) {
                break;
            }
        }
        List<Alert> alerts = null;
        try {
            alerts = zapclient.getAlerts(TARGET_URL, 0, 0);
        } catch (ClientApiException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("Alerts: " + alerts.toString());

        //Object[] list=alerts.toArray();
        //System.out.println(list);
    }
}
