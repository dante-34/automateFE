# automateFE
automation tasks fe then be

FrontEnd is first
Code of this executable is to be written in Java, 
IDE in use is IntelliJ Community
Maven for dependency management
Selenium webdriver for interacting with pages and components

Provided credentials are not usable as 
Upon using the provided credentials, there is a verification step via email which cannot be completed.

Flow:
✅ Open browser
✅ Navigate to login page
✅ Log in to specified acount
✅ click "Me" in ribbon menu >>>
✅ click "View Profile" button >>>
✅ click "Connections" link >>> Navigate to contacts page.

✅ collect URL of that user's profile page
✅ Iterate on all URLs. For each, Locate element of "Experiene" section
  ✅ Locate user's full name (FN1), keep it.
  ✅ Locate user's title (Title1), keep it.
  ✅ Locate user's period info (TimeFrame1), keep it.
✅ Collect list of details [FN1,Title1,TimeFrame1]
[ ] Build JSON containing details of all contacts
[ ] Save JSON to file

Navigate to https://www.linkedin.com/mynetwork/invite-connect/connections/

iterate on each contact- 
* Browse to profile page
* Keep FullName = FN1
* Navigate to element= "Experience"
  * go to top row, selector= #ember1178 > div.pvs-list__outer-container > ul > li:nth-child(1) > div
  * title xpath = //*[@id="ember1178"]/div[3]/ul/li[1]/div/div[2]/div[1]/div[1]/div
    Keep title = Title1
  * time xpath = //*[@id="ember1178"]/div[3]/ul/li[1]/div/div[2]/div[1]/div[1]/span[2]
    Keep period = TimeFrame1
    
if present - click on "show more results" for complete list of contacts
(until 'show more results' does not re-appear)
Add protection from duplicate entries, based on similar linkedin's "/in" URL

export all collected [FN1, Title1, TimeFrame1] into the target JSON
