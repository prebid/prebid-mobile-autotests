package OMSDK;

/**
 * The container for description of the check result.
 * Can provide the boolean flag as value of particular validation
 * and error message if validation has been failed.
 */
public class OMSDKCheckResult {

    private boolean checkPassed = false;
    private String errorMessage = null;

    public OMSDKCheckResult(boolean checkPassed, String errorMessage) {
        this.checkPassed = checkPassed;
        this.errorMessage = checkPassed ? "" : errorMessage;
    }

    /**
     * Returns if check is passed
     * @return if check is passed
     */
    public boolean getIsPassed() {
        return this.checkPassed;
    }

    /**
     * Returns the error message if check did not pass
     * @return the description of fail
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * Composes the result in current object with the result in given one.
     * Creates a new OMSDKCheckResult with consolidated params:
     * - checkPassed is a conjunction of checkPassed flags.
     * - errorMessage is a concatenation of error messages.
     *
     * @param anotherResult another object with result of check
     * @return a new object with composition of results
     */
    public OMSDKCheckResult and(OMSDKCheckResult anotherResult) {
        final boolean newValue = this.checkPassed && anotherResult.getIsPassed();

        final String newErrorMessage = anotherResult.getIsPassed() ?
                this.errorMessage :
                this.errorMessage.concat("\n").concat(anotherResult.getErrorMessage());

        return new OMSDKCheckResult(newValue, newErrorMessage);
    }
}
