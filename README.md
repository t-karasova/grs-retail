# Google Retail Search
## Java code samples
The code here demonstartes how to consume Google Retail Search API in Java

### Authorization
In order to authenticate and authorize the client place a JSON file with authorization token to your cloudshell project and refer environment variable `GOOGLE_APPLICATION_CREDENTIALS` to it (use full path to file):

```
export GOOGLE_APPLICATION_CREDENTIALS="/home/use_name/cloudshell_open/grs-retail/token.json"
```
### Running the code samples

Use maven commant to run one of test classes:
```
mvn test -Dtest=SearchServicePaginationTest
```
Or run All tests in this code samples with a command:
```
mvn test
```
