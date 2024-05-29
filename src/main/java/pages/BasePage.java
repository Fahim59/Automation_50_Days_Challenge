package pages;

import base.BaseClass;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class BasePage extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    /**
     * Day 1
    */
    private final By basicAuthOption = By.linkText("Basic Auth");
    private final By confirmMsg = By.xpath("//p[contains(text(),'Congratulations! You must have the proper credential')]");

    public void clickBasicAuth(){
        click_Element(basicAuthOption);
    }

    public String verifyMessage(){
        return get_Text(confirmMsg);
    }

    /**
     * Day 2
    */
    private final By passField = By.id("pass");
    private final By passNewField = By.id("passnew");

    public void enterPassword(String pass){
        write_JS_Executor(passField, pass);
    }

    public void enterPasswordRemovingAttribute(String pass){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("document.getElementById('passnew').removeAttribute('disabled')");

        write_Send_Keys(passNewField, pass);
    }

    /**
     * Day 3
    */
    private final By ratingField = By.cssSelector(".star-rating");

    private final By ratingTextField = By.xpath("//input[@id='txt_rating']");

    private final By checkRatingBtn = By.xpath("//button[@id='check_rating']");

    private final By validateRatingMsg = By.xpath("//span[@id='validate_rating']");

    public String getContentOfRatingField(){
        WebElement element = wait_for_presence(ratingField);

        String script = "return window.getComputedStyle(arguments[0], '::after').getPropertyValue('content');";
        String pseudoContent = (String) js.executeScript(script, element);

        if (pseudoContent != null && pseudoContent.length() > 1) {
            pseudoContent = pseudoContent.substring(1, pseudoContent.length() - 1);
        }

        return pseudoContent;
    }

    public void enterRating(String rating){
        write_Send_Keys(ratingTextField, rating);
    }

    public void clickRatingButton(){
        click_Element(checkRatingBtn);
    }

    public String getValidationMsg(){
        return get_Text(validateRatingMsg);
    }

    /**
     * Day 4
    */
    private final By clickBtn = By.xpath("//button[@id='growbutton']");

    private final By getTriggerMsg = By.xpath("//p[@id='growbuttonstatus']");

    public void clickOnButton(){
        WebElement button = wait_for_visibility(clickBtn);
        wait.until(ExpectedConditions.attributeContains(button, "class", "grown"));

        if (button.getAttribute("class").contains("grown")) {
            click_Element(clickBtn);
        }
    }

    public String getTriggerMsg(){
        return get_Text(getTriggerMsg);
    }

    /**
     * Day 5
    */
    private final By inputField = By.xpath("(//input[@type='number'])");

    private final By successMsg = By.xpath("//small[@class='info success']");

    public int getInputFieldSize() {
        List<WebElement> elements = wait_for_presence_list(inputField);
        return elements.size();
    }

    public void enterCode(){
        Actions actions = new Actions(driver);

        Runnable pressKeyUpNineTimesAndTab = () -> {
            for (int i = 0; i < 9; i++) {
                actions.sendKeys(Keys.ARROW_UP).perform();
            }
            actions.sendKeys(Keys.TAB).perform();
        };

        pressKeyUpNineTimesAndTab.run();
    }

    public String getSuccessMsg(){
        return get_Text(successMsg);
    }

    /**
     * Day 6
    */
    private final By startBtn = By.xpath("//button[@id='startButton']");

    private final By stopBtn = By.xpath("//button[@id='stopButton']");

    public void clickStartBtn(){
        click_Element(startBtn);
    }

    public void clickStopBtn(int value){
        String script = "return document.querySelector('.progress-bar').style.width;";

        while (true) {
            String progressValue = (String) js.executeScript(script);

            int progress = Integer.parseInt(progressValue.replace("%", ""));

            if (progress == value) {
                click_Element(stopBtn);
                break;
            }
        }
    }

    /**
     * Day 7
    */
    private final By textField = By.xpath("//p[@id='msg']");
    private final By shareBtn = By.xpath("//span[normalize-space()='Share']");

    private final By twitterBtn = By.xpath("//span[normalize-space()='Twitter']");
    private final By twitterMsg = By.xpath("//*[text()='Menu item Twitter clicked']");

    private final By instagramBtn = By.xpath("//span[normalize-space()='Instagram']");
    private final By instagramMsg = By.xpath("//*[text()='Menu item Instagram clicked']");

    private final By dribbleBtn = By.xpath("//span[normalize-space()='Dribble']");
    private final By dribbleMsg = By.xpath("//*[text()='Menu item Dribble clicked']");

    private final By telegramBtn = By.xpath("//span[normalize-space()='Telegram']");
    private final By telegramMsg = By.xpath("//*[text()='Menu item Telegram clicked']");

    public BasePage clickTextField(){
        WebElement element = wait_for_visibility(textField);
        actions.contextClick(element).perform();

        return this;
    }
    public void clickShareBtn(){
        WebElement element = wait_for_visibility(shareBtn);
        actions.moveToElement(element).perform();

    }

    public void clickTwitterBtn(){
        click_Element(twitterBtn);
    }
    public String getTwitterMsg(){
        return get_Text(twitterMsg);
    }

    public void clickInstagramBtn(){
        click_Element(instagramBtn);
    }
    public String getInstagramMsg(){
        return get_Text(instagramMsg);
    }

    public void clickDribbleBtn(){
        click_Element(dribbleBtn);
    }
    public String getDribbleMsg(){
        return get_Text(dribbleMsg);
    }

    public void clickTelegramBtn(){
        click_Element(telegramBtn);
    }
    public String getTelegramMsg(){
        return get_Text(telegramMsg);
    }

    public void openMenu(String option, String actualMsg){
        clickTextField().clickShareBtn();

        switch (option) {
            case "twitter":
                clickTwitterBtn();
                Assert.assertEquals(actualMsg, getTwitterMsg());
                break;

            case "instagram":
                clickInstagramBtn();
                Assert.assertEquals(actualMsg, getInstagramMsg());
                break;

            case "dribble":
                clickDribbleBtn();
                Assert.assertEquals(actualMsg, getDribbleMsg());
                break;

            case "telegram":
                clickTelegramBtn();
                Assert.assertEquals(actualMsg, getTelegramMsg());
                break;

            default: throw new IllegalStateException("INVALID SOCIAL MEDIA OPTION: " +option);
        }
    }

    /**
     * Day 8
    */
    private final By checkOrderBtn = By.xpath("(//button[normalize-space()='Check Order'])");

    public void dragAndDrop(String [] expectedOrder){
        for (int i = 0; i < expectedOrder.length; i++) {
            WebElement sourceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='person-name' and contains(text(),'"+expectedOrder[i]+"')]")));
            WebElement targetElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='number' and contains(text(),'"+(i+1)+"')]")));

            actions.dragAndDrop(sourceElement, targetElement).perform();
        }
    }

    public void verifyOrder(String [] expectedOrder){
        for (int i = 0; i < expectedOrder.length; i++) {
            WebElement item = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='draggable-list']/li[" +(i+1)+ "]//p[@class='person-name']")));
            String actualName = item.getText();

            Assert.assertTrue(actualName.contains(expectedOrder[i]), "Error: Person at position " +(i+1)+ " is " +actualName+ " instead of " +expectedOrder[i]);
        }
    }

    public void clickCheckOrderBtn(){
        click_Element(checkOrderBtn);
    }

    /**
     * Day 9
    */
    public SearchContext shadowRoot(){
        WebElement shadowHost = driver.findElement(By.tagName("guid-generator"));
        return (SearchContext) js.executeScript("return arguments[0].shadowRoot", shadowHost);
    }

    public void clickGuidGenerator(){
        WebElement guidGenerator = shadowRoot().findElement(By.id("buttonGenerate"));
        guidGenerator.click();

        System.out.println("GUID Clicked");
    }

    public void clickCopyBtn(){
        WebElement copyBtn = shadowRoot().findElement(By.id("buttonCopy"));
        copyBtn.click();

        System.out.println("Copy Clicked");
    }

    public String getInputFieldValue(){
        WebElement inputField = shadowRoot().findElement(By.id("editField"));
        return (String) js.executeScript("return arguments[0].value;", inputField);
    }

    public String getClipBoardValue() throws IOException, UnsupportedFlavorException {
        return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
    }
}
