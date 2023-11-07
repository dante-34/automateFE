package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class scraperUtils {

    public static String[] harvestLatestWorkplace(WebDriver driver, String profileUrl) {
        String[] data = new String[3];

        // Navigate to the profile URL
        driver.get(profileUrl);

        // **** WAIT ***** until bottom of page was loaded
        WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(10));
        //wait4.until(ExpectedConditions.visibilityOfElementLocated(By.id("expanded-footer")));
        wait4.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".global-footer.global-footer--static")));


        // Find the "Experience" section ===>>> --->>> skip this, to catch 1st "li" element instead
        //WebElement experienceSection = driver.findElement(By.id("experience"));

         //**** WAIT *****
        WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(8));
        wait3.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("li")));


        // Find all the list items in the "Experience" section
        //WebElement firstItem = experienceSection.findElement(By.tagName("li"));
        //WebElement firstItem = experienceSection.findElement(By.cssSelector("ul.pvs-list > li"));
        //WebElement firstItem = driver.findElement(By.cssSelector("ul.pvs-list > li")); // proposed
        //WebElement firstItem = driver.findElement(By.cssSelector(".artdeco-list__item.pvs-list__item--line-separated.pvs-list__item--one-column")); //try to use selector
        //artdeco-list__item pvs-list__item--line-separated pvs-list__item--one-column
        WebElement firstItem = driver.findElement(By.cssSelector("li.artdeco-list__item.pvs-list__item--line-separated.pvs-list__item--one-column")); // suggested#2
        // assumed order= 0=name 1=title 2=period

        // Extract name
        WebElement nameElement = driver.findElement(By.cssSelector("h1.text-heading-xlarge"));
        String name = nameElement.getText();
        //System.out.println(" ~~DEBUG~~ name name name ??? " + name);

        data[0] = name;
        // Extract the title
//        data[1] = firstItem.findElement(By.xpath(".//div[contains(@class, 't-bold')]")).getText(); //Title with duplicity
        data[1] = firstItem.findElement(By.xpath(".//div[contains(@class, 't-bold')and not(contains(@class, 'visually-hidden'))]/span[@aria-hidden='true']")).getText(); //duplicity handled
        //System.out.println(" ~~DEBUG~~ DATA0 found??? " + data[0]);
        // Extract the company
        //data[1] = firstItem.findElement(By.xpath(".//span[contains(@class, 't-14')][1]")).getText();
        //System.out.println(" ~~DEBUG~~ DATA1 found??? " + data[1]);
        // Extract the date
        //data[2] = firstItem.findElement(By.xpath(".//span[contains(@class, 't-14')][2]")).getText(); //produces duplicates
        //data[2] = firstItem.findElement(By.xpath(".//span[contains(@class, 't-14') and not(contains(@class, 'visually-hidden'))][2]")).getText(); // fixed duplicate visible+invisible
        String timePeriod = firstItem.findElement(By.xpath(".//span[contains(@class, 't-14') and not(contains(@class, 'visually-hidden'))][2]/span[@aria-hidden='true']")).getText(); //another attempt at fixing the duplicate
        // this will grab third row. If contact person did not detail their information properly formatted, unexpected data is grabbed instead.
        data[2] = trimInfoItem(timePeriod);
        System.out.println(" ~~DEBUG~~ DATA2 found : : : " + data[2]);
        return data;
    }
    public static String[] harvestSelfDetails(WebDriver driver) {
        String[] data = new String[3];

        data[0] = "My Name";
        data[1] = "My Work Place";
        data[2] = "My City"; // this will NOT necessarily show up

        String selfName = driver.findElement(By.xpath("//div[@class='t-16 t-black t-bold']")).getText();

        data[0] = selfName;
        System.out.println(" ~~DEBUG~~ DATA2 found : : : " + data[2]);
        return data;
    }
    public static List<String> collectProfileURLs(WebDriver driver) {
        List<String> profileURLs = new ArrayList<>();
        List<WebElement> connectionElements = driver.findElements(By.cssSelector("li[class^='mn-connection-card artdeco-list']"));

        // Find and iterate through the "a" elements containing profile information
        for (WebElement connection : connectionElements) {
            WebElement profileLink = connection.findElement(By.cssSelector("a[class*='mn-connection-card__link']"));
            String href = profileLink.getAttribute("href");
            //System.out.println(" ~~DEBUG~~ href found??? " + href);
            if (href != null ) {
                //&& href.startsWith("/in/")
                // This is a valid LinkedIn profile URL
                profileURLs.add(href);
            }
        }

        return profileURLs;
    }

    // Assuming anything after dot or newline should not be there
    private static String trimInfoItem(String infoItem){
        String[] parts = infoItem.split("\n| · ");
        String trimmedString = parts[0];
        return trimmedString.trim();
    }

    public static String convertDataToJson(List<String[]> collectedData, String[] selfData) {
        // Create a JSON object with the desired structure
        JSONObject json = new JSONObject();

        // place "SELF" data at root level
        json.put("myName", selfData[0]);
        json.put("myWorkplace", selfData[1]);
        json.put("city", selfData[2]);
        // Create a JSON array for connections
        JSONArray connectionsArray = new JSONArray();

        // Add data for each connection
        for (String[] contactData : collectedData) {
            JSONObject connection = new JSONObject();
            connection.put("name", contactData[0]);
            connection.put("title", contactData[1]);
            connection.put("date", contactData[2]);
            connectionsArray.add(connection);
        }

        // Add the connections array to the JSON object
        json.put("connections", connectionsArray);

        // Convert the JSON object to a string
        return json.toString();
    }
    public static void writeJsonToFile(String json, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
