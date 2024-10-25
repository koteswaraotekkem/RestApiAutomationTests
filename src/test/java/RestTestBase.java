import lombok.extern.java.Log;

@Log
public class RestTestBase {
    public void STEP_INFO(int stepNumber, String testStep, String expectedResult){
        log.info("\n" + stepNumber+ ": \n Description: "+ testStep + "\n Expected Result: "+ expectedResult + " \n");
    }
}
