package OMSDK;
import org.testng.Assert;

/**
 * The wrapper for org.testng.Assert to support OMSDKCheckResult
 */
public class OMSDKAssert {

    /**
     * Wrapper for Assert.fail for OMSDKCheckResult
     * @param result the object of OMSDKCheckResult with result of checking OM session
     */
    public static void assertTrue(OMSDKCheckResult result) {
        if (!result.getIsPassed()) {
            Assert.fail(result.getErrorMessage());
        }
    }
}
