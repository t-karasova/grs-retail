<walkthrough-metadata>
  <meta name="title" content="Write user events tutorial" />
  <meta name="description" content="Use this method if you want to add one user event to the catalog." />
  <meta name="component_id" content="593554" />
</walkthrough-metadata>

# Write user events tutorial

## Introduction

The Retail API exposes methods for managing user events.
If you want to add one user event to the catalog, you can use the `writeUserEvent` method.

<walkthrough-tutorial-duration duration="5"></walkthrough-tutorial-duration>

## Get started with Google Cloud Retail

This step is required if this is the first Retail API Tutorial you run.
Otherwise, you can skip it.

### Select your project and enable the Retail API

Google Cloud organizes resources into projects. This lets you
collect all the related resources for a single application in one place.

If you don't have a Google Cloud project yet or you're not the owner of an existing one, you can
[create a new project](https://console.cloud.google.com/projectcreate).

After the project is created, set your PROJECT_ID to a ```project``` variable.
1. Run the following command in Terminal:
    ```bash
    gcloud config set project <YOUR_PROJECT_ID>
    ```

1. Check that the Retail API is enabled for your project in the [Admin Console](https://console.cloud.google.com/ai/retail/).

### Set up authentication

To run a code sample from the Cloud Shell, you need to be authenticated using the service account credentials.

1. Login with your user credentials.
    ```bash
    gcloud auth login
    ```

1. Type `Y` and press **Enter**. Click the link in Terminal. A browser window should appear asking you to log in using your Gmail account.

1. Provide the Google Auth Library with access to your credentials and paste the code from the browser to the Terminal.

1. Upload your service account key JSON file and use it to activate the service account:

    ```bash
    gcloud iam service-accounts keys create ~/key.json --iam-account <YOUR_SERVICE_ACCOUNT_EMAIL>
    ```

    ```bash
    gcloud auth activate-service-account --key-file  ~/key.json
    ```

1. Set key as the GOOGLE_APPLICATION_CREDENTIALS environment variable to be used for requesting the Retail API:
    ```bash
    export GOOGLE_APPLICATION_CREDENTIALS=~/key.json
    ```

**Note**: Click the copy button on the side of the code box to paste the command in the Cloud Shell terminal and run it.

### Set the PROJECT_NUMBER and PROJECT_ID environment variables

Because you are going to run the code samples in your own Google Cloud project, you should specify the **project_number** as an environment variable. It will be used in every request to the Retail API.

1. You can find the ```project_number``` in the Project Info card displayed on **Home/Dashboard**.

1. Set **project_number** with the following command:
    ```bash
    export PROJECT_NUMBER=<YOUR_PROJECT_NUMBER>
    ```

### Google Cloud Retail libraries

You can find the Java Google Retail library is described [here](https://googleapis.dev/java/google-cloud-retail/latest/index.html)

## Clone the Retail code samples

This step is required if this is the first Retail API tutorial you run.
Otherwise, you can skip it.

Clone the Git repository with all the code samples to learn the Retail features and check them in action.

<!-- TODO(ianan): change the repository link -->
1. Run the following command in the Terminal:
    ```bash
    git clone https://github.com/t-karasova/grs-retail.git
    ```

    The code samples for each of the Retail services are stored in different directories.

1. Go to the code samples directory - our starting point to run more commands.
    ```bash
    cd java-retail/samples/snippets
    ```

## Write user events

1. Before you start, build the Maven project and go to the code samples directory - our starting point to runcode samples:
   ```bash
   cd ~/java-retail | mvn clean install -DskipTests
   cd ~/java-retail/samples/snippets  
   ```
   
The `WriteUserEventRequest` request consists of two fields:
- `parent`—required field. The parent catalog name, such as `projects/<YOUR_PROJECT_NUMBER>/locations/global/catalogs/default_catalog`.
- `user_event`—required field. The user event you are going to write.

Learn more about the user events in [the Retail documentation](https://cloud.google.com/retail/docs/reference/rpc/google.cloud.retail.v2#userevent)

1. Check the `WriteUserEventRequest` request example in the <walkthrough-editor-select-regex filePath="cloudshell_open/grs-retail/src/main/java/events/WriteUserEvent.java" regex="# get write user event request">`events/WriteUserEvent.java`</walkthrough-editor-select-regex> file.

1. Run the code sample in the Terminal with the following command:
    ```bash
    mvn compile exec:java -Dexec.mainClass=events.WriteUserEvent
    ```

The Retail API returns the created user event. Check the output in the Terminal.


## Error handling

Next, check the error handling by sending a request with an invalid parent.

1. Find the <walkthrough-editor-select-regex filePath="cloudshell_open/grs-retail/src/main/java/events/WriteUserEvent.java" regex="# TO CHECK THE ERROR HANDLING TRY TO PASS INVALID CATALOG:">comment</walkthrough-editor-select-regex> and uncomment the next line.

1. Run the code sample in the Terminal with the following command:
    ```bash
    mvn compile exec:java -Dexec.mainClass=events.WriteUserEvent
    ```

1. Check the error message:
    ```terminal
    Caused by: io.grpc.StatusRuntimeException: NOT_FOUND: catalog_id 'invalid_catalog' not found for project. In most cases, this should be set to 'default_catalog'. If you just created this resource (for example, by activating your project), it may take up 5 minutes for the resource(s) to be activated.
    ```

## Congratulations

<walkthrough-conclusion-trophy></walkthrough-conclusion-trophy>

You have completed the tutorial! We encourage you to test writing user events by yourself.

<walkthrough-inline-feedback></walkthrough-inline-feedback>

### Do more with the Retail API

<walkthrough-tutorial-card id="retail_api_v2_purge_user_events_java" icon="LOGO_JAVA" title="Purge user events tutorial" keepPrevious=true>Try to purge user events via the Retail API</walkthrough-tutorial-card>

<walkthrough-tutorial-card id="retail_api_v2_rejoin_user_events_java" icon="LOGO_JAVA" title="Rejoin user events tutorial" keepPrevious=true>Try to rejoin user events via the Retail API</walkthrough-tutorial-card>
