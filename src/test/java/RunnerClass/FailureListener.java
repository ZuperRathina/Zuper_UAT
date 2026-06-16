package RunnerClass;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class FailureListener implements ITestListener, IExecutionListener {

//	private static List<String> failedTests = new ArrayList<>();
//
//	@Override
//	public void onTestFailure(ITestResult result) {
//		failedTests.add(result.getMethod().getMethodName());
//	}
	
	
	private static Set<String> failedOrSkippedTests = new LinkedHashSet<>();

	@Override
	public void onTestFailure(ITestResult result) {
	    failedOrSkippedTests.add(result.getMethod().getMethodName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
	    failedOrSkippedTests.add(result.getMethod().getMethodName());
	}

	@Override
	public void onExecutionFinish() {

		System.out.println("===== Failed Test Methods =====");
		if (!failedOrSkippedTests.isEmpty()) {
			String failedMethod = "";
			for (String method : failedOrSkippedTests) {
				failedMethod = method;
				if (failedMethod.equalsIgnoreCase("verify_IncommingAndOutgoingCallsWithTwilioVoiceApp")) {
					TC_IncommingAndOutgoingCallsWithTwilioVoiceApp callApp = new TC_IncommingAndOutgoingCallsWithTwilioVoiceApp();
					callApp.verify_IncommingAndOutgoingFunctionalities();
				} else if (failedMethod.equalsIgnoreCase("verify_ColdTransferFunctionality")) {
					TC_ColdCallTransfer callApp1 = new TC_ColdCallTransfer();
					callApp1.verify_ColdTransferFunctionality();
				} else if (failedMethod.equalsIgnoreCase("verify_WarmCallTransferFunctionality")) {
					TC_WarmCallTransfer callApp2 = new TC_WarmCallTransfer();
					callApp2.verify_WarmCallTransferFunctionalities();
				} else if (failedMethod.equalsIgnoreCase("verify_OutgoingAndIncommingMessages")) {
					TC_OutgoingAndIncommingMessages callApp3 = new TC_OutgoingAndIncommingMessages();
					callApp3.verify_OutgoingAndIncommingMessages();
				} else if (failedMethod.equalsIgnoreCase("verify_OutgoingAndIncommingMMS")) {
					TC_OutgoingAndIncommingMMS callApp4 = new TC_OutgoingAndIncommingMMS();
					callApp4.verify_OutgoingAndIncommingMMS();
				}
			}
		}
	}
}
