package UtilityPackages;

import java.util.ArrayList;
import java.util.List;
import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class FailureListener implements ITestListener, IExecutionListener {

    public static List<String> failedTests = new ArrayList<>();

    @Override
    public void onTestFailure(ITestResult result) {
        failedTests.add(result.getMethod().getMethodName());
    }

    @Override
    public void onExecutionFinish() {

        System.out.println("===== Failed Test Methods =====");

         String failedMethod="";
        for (String method : failedTests) {
            failedMethod=method;
        }

        // If needed, call your EmailReport class here
        if (!failedTests.isEmpty()) {	
        	if (failedMethod.equalsIgnoreCase("verify_IncommingAndOutgoingCallsWithTwilioVoiceApp")) {
        		System.out.println(failedMethod);
			}
        }
    }
}
