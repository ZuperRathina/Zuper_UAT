package PageObjectRep;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import BaseTest.Baseclass;
import UtilityPackages.Non_WebDriver_Util;

public class CallGroupPageFactory extends Baseclass {

	private WebDriver driver;

	public CallGroupPageFactory() {
		this.driver = Baseclass.getDriver();
	}

	// TODO: built from description, not inspected HTML - verify against the real DOM.
	private By tab_CallGroups = By.xpath("//span[normalize-space()='Call Groups' or normalize-space()='Call Group']");
	private By button_NewGroup = By.xpath("//span[contains(normalize-space(),'New Group')]");
	private By dialogTitle_CreateNewCallGroup = By
			.xpath("//*[normalize-space()='Create new call group' or normalize-space()='Create New Call Group']");
	private By input_GroupName = By.cssSelector("input#groupName");
	private By field_GroupMembers = By.xpath("//label[normalize-space()='Group Members']/following::div[1]");
	private By buttons_AddMemberInList = By.xpath("//span[normalize-space()='Add']");
	private By button_Create = By.xpath("//button[normalize-space()='Create']");
	private By element_CallGroupCreatedMessage = By
			.xpath("//*[contains(normalize-space(),'Call Group Created Successfully')]");
	private By button_Done = By.xpath("//button[normalize-space()='Done']");
	private By dialogTitle_EditCallGroup = By
			.xpath("//*[normalize-space()='Edit Call Group' or normalize-space()='Edit call group']");
	private By buttons_RemoveMemberInList = By
			.xpath("//div[normalize-space()='Search any users']/parent::div/div[2]/div//button/em");
	private By button_UpdateGroup = By.xpath("//button[normalize-space()='Update']");
	private By element_GroupUpdatedMessage = By
			.xpath("//*[contains(normalize-space(),'Group Updated Successfully')]");
	private By button_DeleteCallGroupConfirm = By.xpath("//button[normalize-space()='Delete Call Group']");
	private By element_GroupDeletedMessage = By
			.xpath("//*[contains(normalize-space(),'Group Deleted Successfully')]");

	public static String callGroupName;

	public void navigateToCallGroupsTab() {
		Non_WebDriver_Util.waitForBeClickable(driver, tab_CallGroups, 20);
		Non_WebDriver_Util.jsScrollAndActionClick(driver, tab_CallGroups);
		Non_WebDriver_Util.waitThread(1);
	}

	public void clickNewGroupButton() {
		Non_WebDriver_Util.waitForBeClickable(driver, button_NewGroup, 10);
		driver.findElement(button_NewGroup).click();
		Non_WebDriver_Util.waitForVisible(driver, dialogTitle_CreateNewCallGroup, 10);
	}

	public void enterGroupName() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM");
		String currentDay = LocalDate.now().format(formatter);
		callGroupName = "ZuperGrp@ " + currentDay;
		Non_WebDriver_Util.waitForBeClickable(driver, input_GroupName, 10);
		driver.findElement(input_GroupName).clear();
		driver.findElement(input_GroupName).sendKeys(callGroupName);
	}

	public void addFirstThreeGroupMembers() {
		Non_WebDriver_Util.waitForBeClickable(driver, field_GroupMembers, 10);
		driver.findElement(field_GroupMembers).click();
		Non_WebDriver_Util.waitThread(1);
		List<WebElement> addButtons = driver.findElements(buttons_AddMemberInList);
		int membersToAdd = Math.min(2, addButtons.size());
		for (int i = 0; i < membersToAdd; i++) {
			addButtons.get(i).click();
			Non_WebDriver_Util.waitThread(1);
		}
		logger.info(membersToAdd + " member(s) added to the Call Group");
	}

	public void closeMemberDropdownAndCreate() {
		// Click the dialog title to close the member-selector dropdown before hitting Create.
		driver.findElement(dialogTitle_CreateNewCallGroup).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, button_Create, 10);
		driver.findElement(button_Create).click();
	}

	public void verifyCallGroupCreatedAndClickDone() {
		Non_WebDriver_Util.waitForVisible(driver, element_CallGroupCreatedMessage, 15);
		Non_WebDriver_Util.getScreenshot(driver, "CallGroupCreated");
		logger.info("Call Group '" + callGroupName + "' Created Successfully");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Call Group '" + callGroupName + "' Created Successfully");
		Non_WebDriver_Util.waitForBeClickable(driver, button_Done, 10);
		driver.findElement(button_Done).click();
	}

	public void createNewCallGroup() {
		navigateToCallGroupsTab();
		clickNewGroupButton();
		enterGroupName();
		addFirstThreeGroupMembers();
		closeMemberDropdownAndCreate();
		verifyCallGroupCreatedAndClickDone();
	}

	public void clickEditIconForGroup(String groupName) {
		By icon_EditGroup = By.xpath(String.format(
				"//*[normalize-space()='%s']/parent::hlm-td/parent::cdk-row/hlm-td[4]/span[1]",
				groupName));
		Non_WebDriver_Util.waitForBeClickable(driver, icon_EditGroup, 15);
		driver.findElement(icon_EditGroup).click();
		Non_WebDriver_Util.waitForVisible(driver, dialogTitle_EditCallGroup, 10);
		
	}

	public void updateGroupName() {
		Non_WebDriver_Util.waitForBeClickable(driver, input_GroupName, 10);
		driver.findElement(input_GroupName).clear();
		callGroupName = callGroupName + " New";
		driver.findElement(input_GroupName).sendKeys(callGroupName);
	}

	public void removeOneSelectedMember() {
		Non_WebDriver_Util.waitThread(3);
		Non_WebDriver_Util.waitForBeClickable(driver, buttons_RemoveMemberInList, 10);
		List<WebElement> removeButtons = driver.findElements(buttons_RemoveMemberInList);
		if (!removeButtons.isEmpty()) {
			removeButtons.get(0).click();
			Non_WebDriver_Util.waitThread(1);
			logger.info("One existing member removed from the Call Group");
		}
	}

	public void addOneNewMember() {
		Non_WebDriver_Util.waitForBeClickable(driver, field_GroupMembers, 10);
		driver.findElement(field_GroupMembers).click();
		Non_WebDriver_Util.waitThread(1);
		List<WebElement> addButtons = driver.findElements(buttons_AddMemberInList);
		if (!addButtons.isEmpty()) {
			addButtons.get(3).click();
			Non_WebDriver_Util.waitThread(1);
			logger.info("One new member added to the Call Group");
		}
	}

	public void clickUpdateButton() {
		// Click the dialog title to close the member-selector dropdown before hitting Update.
		driver.findElement(dialogTitle_EditCallGroup).click();
		Non_WebDriver_Util.waitThread(1);
		Non_WebDriver_Util.waitForBeClickable(driver, button_UpdateGroup, 10);
		driver.findElement(button_UpdateGroup).click();
		Non_WebDriver_Util.getScreenshot(driver, "CallGroup Updated");
	}

	public void verifyGroupUpdatedSuccessfully() {
		Non_WebDriver_Util.waitForVisible(driver, element_GroupUpdatedMessage, 15);
		logger.info("Call Group '" + callGroupName + "' Updated Successfully");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Call Group '" + callGroupName + "' Updated Successfully");
	}

	public void editExistingCallGroup() {
		clickEditIconForGroup(callGroupName);
		updateGroupName();
		removeOneSelectedMember();
		addOneNewMember();
		clickUpdateButton();
		verifyGroupUpdatedSuccessfully();
	}

	public void clickDeleteIconForGroup(String groupName) {
		By icon_DeleteGroup = By.xpath(String.format(
				"//*[normalize-space()='%s']/parent::hlm-td/parent::cdk-row/hlm-td[4]/span[2]",
				groupName));
		Non_WebDriver_Util.waitForBeClickable(driver, icon_DeleteGroup, 15);
		driver.findElement(icon_DeleteGroup).click();
		Non_WebDriver_Util.waitForVisible(driver, button_DeleteCallGroupConfirm, 10);
	}

	public void confirmDeleteCallGroup() {
		Non_WebDriver_Util.waitForBeClickable(driver, button_DeleteCallGroupConfirm, 10);
		driver.findElement(button_DeleteCallGroupConfirm).click();
	}

	public void verifyGroupDeletedSuccessfully() {
		Non_WebDriver_Util.waitForVisible(driver, element_GroupDeletedMessage, 15);
		Non_WebDriver_Util.waitThread(5);
		Non_WebDriver_Util.getScreenshot(driver, "CallGroupDeleted");
		logger.info("Call Group '" + callGroupName + "' Deleted Successfully");
		Non_WebDriver_Util.testCase.log(Status.PASS, "Call Group '" + callGroupName + "' Deleted Successfully");
	}

	public void deleteExistingCallGroup() {
		clickDeleteIconForGroup(callGroupName);
		confirmDeleteCallGroup();
		verifyGroupDeletedSuccessfully();
	}

}
