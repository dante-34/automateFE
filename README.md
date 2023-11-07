# automateFE
automation tasks fe then be

FrontEnd is first
Code of this executable is to be written in Java, 
IDE in use is IntelliJ Community
Maven for dependency management
Selenium webdriver for interacting with pages and components

Upon using the provided credentials, there is a verification step via email which cannot be completed.
When using any credentials too frequently for LinkedIn's taste, the login process is taking user through validation.
Validation is to be complete manually and a 10-second wait is in place for that purpose.

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
.
✅ Build JSON containing details of all contacts
[ ] Save JSON to file

Further subtasks:
- Extend to include ALL contacts, covering infinite scroll
- Extract own (name, workplace, city) to be placed at top of JSON
- clear away more actions from Main into helper method for modularity
- There are at least 2 variations with "Experience" item strucutre:
  + if person holds single role in the company, the bold header contains "TITLE"
  + if person has/had 2+ roles in same company, the leading bold header will now contain "COMPANY-NAME"
  + This inconsistency along with flexibility given to users to add or omit whatever they like poses an additional challenge
Following clicks path, NOT navigating directly to  https://www.linkedin.com/mynetwork/invite-connect/connections/


if present - click on "show more results" for complete list of contacts
(until 'show more results' does not re-appear)
Add protection from duplicate entries, based on similar linkedin's "/in" URL


