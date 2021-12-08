# Google Retail Search
## Java code samples
The code here demonstartes how to consume Google Retail Search API in Java

### Authorization
In order to authenticate and authorize the client place a JSON file with authorization token to your cloudshell project and refer environment variable `GOOGLE_APPLICATION_CREDENTIALS` to it (use full path to file):

```
export GOOGLE_APPLICATION_CREDENTIALS="/home/use_name/cloudshell_open/grs-retail/token.json"
```

### Define a system property
PROJECT_NUMBER is a system property. Change the number if you are using a different project.
### Running code samples

Use maven command to run specific code sample:
```
mvn compile exec:java -Dexec.mainClass="packagePath.CodeSampleClassName" -DPROJECT_NUMBER=945579214386
```
Use maven command to run all code samples:
```
mvn test -DPROJECT_NUMBER=945579214386
```

### Running unit tests

Use maven command to run specific unit test class:
```
mvn test -Dtest=TestClassName -DPROJECT_NUMBER=945579214386
```
Use maven command to run all unit tests:
```
mvn test -DPROJECT_NUMBER=945579214386
```
