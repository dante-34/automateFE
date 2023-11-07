package org.example;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {

        String targetJsonPath = "outputLinkedinContacts.txt";
        String credentialsLinkedinUser = "YOUR-USER-NAME";
        String credentialsLinkedinPassword = "YOUR-PASSWORD";

        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/");
        driver.manage().window().maximize();
        driver.get("https://www.linkedin.com/");

        WebElement username=driver.findElement(By.xpath("//*[@id=\"session_key\"]"));
        username.sendKeys(credentialsLinkedinUser);

        WebElement password=driver.findElement(By.xpath("//*[@id=\"session_password\"]"));
        password.sendKeys(credentialsLinkedinPassword);


        WebElement login=driver.findElement(By.xpath("//*[@id=\"main-content\"]/section[1]/div/div/form/div[2]/button"));
        //WebElement login=driver.findElement(By.tagName("data-id")..name("Sign in"));
        //data-id="sign-in-form__submit-btn"
        // #main-content > section.section.min-h-\[560px\].flex-nowrap.pt-\[40px\].babybear\:flex-col.babybear\:min-h-\[0\].babybear\:px-mobile-container-padding.babybear\:pt-\[24px\] > div > div > form > div.flex.justify-between.sign-in-form__footer--full-width > button


        login.click();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
// >>> >>> >>>
// collect here now for self data items
        String[] selfDataItems;
        selfDataItems = scraperUtils.harvestSelfDetails(driver);
        System.out.println(" ~~DEBUG~~ \"SELF\" items found. 0=" + selfDataItems[0] + " 1=" + selfDataItems[1] + " 2=" + selfDataItems[2]);

        // Locate "Me" and click it to open sub-menu
        WebElement meSubMenu=driver.findElement(By.xpath("//*[@id=\"ember15\"]"));
        meSubMenu.click();

        // WebElement viewProfileButton=driver.findElement(By.xpath("//*[@id=\"ember924\"]"));
        // id selector is dynamic so specifying nor "ember924" or "ember495" could be suitable for use here
        // WebElement viewProfileButton=driver.findElement(By.cssSelector("ember-view artdeco-button artdeco-button--secondary artdeco-button--1 mt2 full-width"));

        // Locate sub-menu, and click the "View Profile" button in it
        //WebElement submenuContainer=driver.findElement(By.className("artdeco-dropdown__content-inner")); // wrong???
       // WebElement submenuContainer=driver.findElement(By.cssSelector("artdeco-dropdown__content-inner"));
        // WebElement submenuContainer=driver.findElement(By.cssSelector(".p2")); // wrong???

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p2")));

        //driver.findElement(By.xpath("//input[@id='text3']")).sendKeys("Text box is visible now");
        WebElement submenuContainer=driver.findElement(By.cssSelector(".p2"));
        System.out.println(" ~~DEBUG~~ found webelement p2: " + submenuContainer.toString());




        WebElement viewProfileButton=submenuContainer.findElement(By.linkText("View Profile"));
        viewProfileButton.click();

        // Locate sub-header where the "Connections" link is placed, and click that link.
       // WebElement profileContainer=driver.findElement(By.className("ph5")); // parent container?
        //WebElement profileContainer=driver.findElement(By.className("artdeco-card ember-view pv-top-card")); // parent container??
        WebElement profileContainer=driver.findElement(By.cssSelector(".artdeco-card.ember-view.pv-top-card")); // parent container!
        System.out.println(" ~~DEBUG~~ found container XXartdecoXX: " + profileContainer.toString());

        // **** WAIT *****
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(8));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ph5")));

        WebElement targetConnectionsLink=profileContainer.findElement(By.className("link-without-visited-state")); //not by text
        System.out.println(" ~~DEBUG~~ found connections webelement : " + targetConnectionsLink.toString());
        System.out.println(" ~~DEBUG~~ search if we have >1");

        List<WebElement> multiConnections=profileContainer.findElements(By.className("link-without-visited-state"));
        for (int i = 0; i < multiConnections.size(); i++) {
            System.out.println(" ~~DEBUG~~ item#" + i + " OF " +multiConnections.size() + multiConnections.toArray()[i].toString());
        }

        multiConnections.get(1).click();
        //System.out.println(multiConnections.size() + " CONNECTIONS found ~~" + multiConnections.toArray()[0].toString());

//        WebElement targetConnectionsLink=profileContainer.findElement(By.xpath("//*[@id=\"ember303\"]")); //not necessarily by xpath
        // List<WebElement> multiConnections=profileContainer.findElements(By.className("link-without-visited-state"));
        // <a href> selector = #ember76
        // <a href> JS path = document.querySelector("#ember76")
        // <a href> xpath = *[@id="ember919"]
        // <a href> full xpath =  /html/body/div[5]/div[3]/div/div/div[2]/div/div/main/section[1]/div[2]/div[2]/div[2]/span[2]/a

        // span selector = #ember76 > span
        //document.querySelector("#ember76 > span")


        /*       if (multiConnections.isEmpty())
            System.out.println(" ~~ it is empty ~~");
        else {
            System.out.println(multiConnections.size() + " CONNECTIONS found ~~" + multiConnections.toArray()[0].toString());
        }

        multiConnections.get(0).click();
*/
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
            //targetConnectionsLink.click();
// NEXT

        List<String> collectedContactsData = scraperUtils.collectProfileURLs(driver);
        if (collectedContactsData.isEmpty())
            System.out.println(" !! NOTHING FOUND !! ");
        else
            System.out.println(" ~~DEBUG~~ This URL " + collectedContactsData.get(0) + " is 1st out of " + collectedContactsData.size());



        // TEST : single contact
        // String[] contactDataItems = new String[3];
        // contactDataItems = scraperUtils.harvestLatestWorkplace(driver,collectedContactsData.get(17));
        // 17=dudu 32=Rafi  36=anton 38=Hazak

        // DEBUG
        // System.out.println(" ~~DEBUG~~ FullName=" + contactDataItems[0] + " Title=" + contactDataItems[1] +" Period=" + contactDataItems[2]);
        // Alternative- navigate immediately after login into https://www.linkedin.com/mynetwork/invite-connect/connections/

        List<Integer> selectedIndices = Arrays.asList(17, 38, 36); // TEST-TEST-TEST : when wishing to visit a handful of contacts and not all of them
        // Iterate on all available URLs, collect results into a newly defined List<String[]>
        List<String[]> collectedNamesAndTitles = new ArrayList<>();

        // go through ALL URLs
        /*
        for (String url : collectedContactsData) {
            String[] contactDataItems = scraperUtils.harvestLatestWorkplace(driver, url);
            collectedNamesAndTitles.add(contactDataItems);
        }
        */
        // TEST-TEST-TEST go through only the index numbers specified in "selectedIndices"
        for (Integer index : selectedIndices) {
            if (index >= 0 && index < collectedContactsData.size()) {
                String url = collectedContactsData.get(index);
                String[] contactDataItems = scraperUtils.harvestLatestWorkplace(driver, url);
                collectedNamesAndTitles.add(contactDataItems);
            }
        }



        String thisIsMyJSON = scraperUtils.convertDataToJson(collectedNamesAndTitles,selfDataItems);
        System.out.println(" ~~DEBUG~~ JSON-JSON-JSON = " + thisIsMyJSON);

        scraperUtils.writeJsonToFile(thisIsMyJSON,targetJsonPath);

        // Identify elements (connections) in list based on class for profile image.
        // "ember-view mn-connection-card__picture"
        // Find list of webelments of all results
        // from every webelement extract (1)aa (2)bb (3)cc
        // //*[@id="ember770"]/div[2]/div[1]/ul/li[1]
        // //*[@id="ember770"]/div[2]/div[1]/ul/li[2]
        //*[@id="ember770"]/div[2]/div[1]/ul/li[40]
      //  driver.quit();
/*        try {
            driver.wait(5000);
            driver.close();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/

    }
}