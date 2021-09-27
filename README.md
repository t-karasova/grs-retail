# Google Retail Search
## Java code samples
The code here demonstartes how to consume Google Retail Search API in Java

### Authorization
In order to authenticate and authorize the client please open the Java Process Console and use a command to set the Project_Id:

```
gcloud config set project project_id
```
Then  place a JSON file with authorization token to your cloudshell project and refer environment variable `GOOGLE_APPLICATION_CREDENTIALS` to it (use full path to file):

```
export GOOGLE_APPLICATION_CREDENTIALS="/home/use_name/cloudshell_open/grs-retail/token.json"
```
### Running the code samples
There is a main() method in the class so just Run the class to see the search results
