package RunnerClass;

import java.util.LinkedHashSet;
import java.util.Set;
import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class FailureListener implements ITestListener, IExecutionListener {

	private static Set<String> failedOrSkippedTests = new LinkedHashSet<>();

	private static String url;

	@Override
	public void onTestFailure(ITestResult result) {
		ITestContext context = result.getTestContext();
		url = context.getCurrentXmlTest().getParameter("url");
		System.out.println("URL Parameter: " + url);
		failedOrSkippedTests.add(result.getMethod().getMethodName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ITestContext context = result.getTestContext();
		url = context.getCurrentXmlTest().getParameter("url");
		System.out.println("URL Parameter: " + url);
		failedOrSkippedTests.add(result.getMethod().getMethodName());
	}

	@Override
	public void onExecutionFinish() {

		System.out.println("===== Failed Test Methods =====");

		if (!failedOrSkippedTests.isEmpty()) {
			String failedMethod = "";
			for (String method : failedOrSkippedTests) {
				failedMethod = method;
				System.out.println(failedMethod);
				if (failedMethod.equalsIgnoreCase("verify_IncommingAndOutgoingCallsWithTwilioVoiceApp")) {
					TC_IncommingAndOutgoingCallsWithTwilioVoiceApp callApp = new TC_IncommingAndOutgoingCallsWithTwilioVoiceApp();
					callApp.verify_IncommingAndOutgoingFunctionalities(url);
				} else if (failedMethod.equalsIgnoreCase("verify_ColdTransferFunctionality")) {
					TC_ColdCallTransfer callApp1 = new TC_ColdCallTransfer();
					callApp1.verify_ColdTransferFunctionality(url);
				} else if (failedMethod.equalsIgnoreCase("verify_WarmCallTransferFunctionality")) {
					TC_WarmCallTransfer callApp2 = new TC_WarmCallTransfer();
					callApp2.verify_WarmCallTransferFunctionalities(url);
				} else if (failedMethod.equalsIgnoreCase("verify_OutgoingAndIncommingMessages")) {
					TC_OutgoingAndIncommingMessages callApp3 = new TC_OutgoingAndIncommingMessages();
					callApp3.verify_OutgoingAndIncommingMessages(url);
				} else if (failedMethod.equalsIgnoreCase("verify_OutgoingAndIncommingMMS")) {
					TC_OutgoingAndIncommingMMS callApp4 = new TC_OutgoingAndIncommingMMS();
					callApp4.verify_OutgoingAndIncommingMMS(url);
				}
			}
		}
	}
}
