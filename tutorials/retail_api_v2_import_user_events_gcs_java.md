<walkthrough-metadata>
  <meta name="title" content="Import user events from Cloud Storage tutorial" />
  <meta name="description" content="This type of import is useful when you need to import a large number of user events to your catalog in a single step." />
  <meta name="component_id" content="593554" />
  <meta name="keywords" content="retail, import user events" />
</walkthrough-metadata>

# Import user events from Cloud Storage tutorial

## Introduction

The Retail API lets you import your user event data from Cloud Storage. All you need to do is provide a name of
the JSON file in the Cloud Storage bucket.

This type of import is useful when you need to import a large number of user events to your catalog in a single step.

For more information about different import types, their restrictions, and use cases, see the [Retail API documentation](https://cloud.google.com/retail/docs/import-user-events).

<walkthrough-tutorial-duration duration="5"></walkthrough-tutorial-duration>

## Get started

The Retail API offers you an easy way to import your catalog data from Cloud Storage. All you need is to provide the name of
the JSON file in the Cloud Storage bucket.

This type of import is useful when you need to import a large number of items to your catalog in a single step.

You can find more information about different import types, their restrictions, and use cases in the [Retail API documentation](https://cloud.google.com/retail/docs/upload-catalog#considerations).

<walkthrough-tutorial-duration duration="5"></walkthrough-tutorial-duration>

## Get started with Google Cloud Retail

This step is required if this is the first Retail API tutorial you run.
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

### Create service account

To access the Retail API you must create a service account. 

1. To create service account follow this [instruction](https://cloud.google.com/retail/docs/setting-up#service-account)

1. Find your service account on the [IAM page](https://console.cloud.google.com/iam-admin/iam),
   click `Edit` icon and add the roles "Storage Admin" and "BigQuery Admin". It may take a while for the changes to take effect.

1. Copy the service account email in the field Principal.

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
    gcloud auth activate-service-account --key-file ~/key.json
    ```

1. Set key as the GOOGLE_APPLICATION_CREDENTIALS environment variable to be used for requesting the Retail API.

    ```bash
    export GOOGLE_APPLICATION_CREDENTIALS=~/key.json
    ```

**Note**: Click the copy button on the side of the code box to paste the command in the Cloud Shell terminal and run it.

### Set the PROJECT_NUMBER environment variable

Because you are going to run the code samples in your own Google Cloud project, you should specify the **project_number** as an environment variable. It will be used in every request to the Retail API.

1. You can find the ```project_number``` in the Project Info card displayed on **Home/Dashboard**.

1. Set the environment variable with the following command:
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

## Upload catalog data to Cloud Storage

1. Before you start, build the Maven project and go to the code samples directory - our starting point to runcode samples:
   ```bash
   cd ~/java-retail | mvn clean install -DskipTests
   cd ~/java-retail/samples/snippets  
   ```

There is a <walkthrough-editor-select-regex filePath="cloudshell_open/java-retail/samples/snippets/src/main/resources/user_events.json" regex="id">resources/user_events.json</walkthrough-editor-select-regex> file with valid user events prepared in the `resources` directory.

The other file,
<walkthrough-editor-select-regex filePath="cloudshell_open/java-retail/samples/snippets/src/main/resources/user_events_some_invalid.json" regex="id">resources/user_events_some_invalid.json</walkthrough-editor-select-regex>,
contains both valid and invalid user events. You will use it to check the error
handling.

In your own project you should create a Cloud Storage bucket and put the JSON file there.
The bucket name must be unique. For convenience, you can name it `<YOUR_PROJECT_ID>_<TIMESTAMP>`.

1. To create the bucket and upload the JSON file, run the following command in the Terminal:
    ```bash
    mvn compile exec:java -Dexec.mainClass=events.setup.EventsCreateGcsBucket
    ```
1. Now you can see the bucket is created in the [Cloud Storage](https://console.cloud.google.com/storage/browser), and the file is uploaded. The name of the created Cloud Storage bucket is printed in the Terminal.

1. Copy the name and set it as the environment variable `EVENTS_BUCKET_NAME`:
    ```bash
    export EVENTS_BUCKET_NAME=<YOUR_BUCKET_NAME>
    ```

## Import user events to the Retail catalog from the Cloud Storage source

1. To check the example of an import user events request, open <walkthrough-editor-select-regex filePath="cloudshell_open/java-retail/samples/snippets/src/main/java/events/ImportUserEventsGcs.java" regex="# call the Retail API to import products">events/ImportUserEventsGcs.java</walkthrough-editor-select-regex>.

    The `parent` field in the `ImportUserEventsRequest` method contains a catalog name along with a branch number you are going to import your user events to.

    If you are using user events prepared for these tutorials from the <walkthrough-editor-select-regex filePath="cloudshell_open/java-retail/samples/snippets/src/main/resources/user_events.json" regex="id">resources/user_events.json</walkthrough-editor-select-regex> file, you can use the default branch to import user events to. However, if you are using custom user events, change the default_branch, which is `0`, to another branch ID, for example `1`.

    The `input_config` field defines the `GcsSource` as an import source.

1.  To perform the user events import, open Terminal and run the command:
    ```bash
    mvn compile exec:java -Dexec.mainClass=events.ImportUserEventsGcs
    ```

## Response analysis

After you call the import user events method from the Retail API, the import operation starts.

Importing can take a lot of time depending on the size of the user events set in your Cloud Storage.

The operation is completed when the field `operation.done()` is set to true.

1. Check the result. One of the following fields should be present:
    - `error`, if the operation failed.
    - `result`, if the operation was successful.

    You have imported valid user event objects into the catalog.

1. Check the `gcs_operation.metadata.success_count` field to get the total number of successfully imported user events. The number of failures during the user events import is returned in the `gcs_operation.metadata.failure_count` field.

1. Check the results printed out in the Terminal:
    ```terminal
    errors_config {
      gcs_prefix: "gs://import_user_events/error"
    }
    import_summary {
      joined_events_count: 4
    }
    ```

## Errors appeared during the importing

Try to import some invalid user events objects and check the error message in the operation response.

**Note**: The operation in this example generates an error message, but finishes successfully.

The `type` field is required and should have one of the [defined values](https://cloud.google.com/retail/docs/user-events#types), so if you set some invalid value, you get the invalid user event objects.

There is a <walkthrough-editor-select-regex filePath="cloudshell_open/java-retail/samples/snippets/src/main/resources/user_events_some_invalid.json" regex="id">resources/user_events_some_invalid.json</walkthrough-editor-select-regex> file in the Cloud Storage bucket that contains some invalid user events.

1. Open the <walkthrough-editor-select-regex filePath="cloudshell_open/java-retail/samples/snippets/src/main/java/events/ImportUserEventsGcs.java" regex="# TO CHECK ERROR HANDLING USE THE JSON WITH INVALID USER EVENT">code sample</walkthrough-editor-select-regex> and assign the `gcs_events_object` value to the filename:
    ```
    GCS_EVENTS_OBJECT = "user_events_some_invalid.json"
    ```

1. Run the code sample and wait until the operation is completed:
    ```bash
    mvn compile exec:java -Dexec.mainClass=events.ImportUserEventsGcs
    ```

Next, check the operation printed out in the Terminal.

## Errors appeared during importing: output analysis

If the operation is completed successfully, the `result` field will be displayed. Otherwise, an `error` field is displayed instead.

Check the operation printed out in the Terminal. The operation is considered **successful** and the `gcs_operation.metadata.success_count` field contains the number of successfully imported user events, which is `3`.

There are two invalid user events in the input JSON file, and the number of failures during the user events import in the `gcs_operation.metadata.failure_count` field is `1`.

The `operation.result` field points to the errors bucket, where you can find a JSON file with all of the importing errors.

The error is the following:
```terminal
errors_config {
  gcs_prefix: "gs://import_user_events/error"
}
import_summary {
  joined_events_count: 3
}
```

## Errors appeared due to an invalid request

Next, send an invalid import request to check the error message.

1. Open the  <walkthrough-editor-select-regex filePath="cloudshell_open/java-retail/samples/snippets/src/main/java/events/ImportUserEventsGcs.java" regex="# TO CHECK ERROR HANDLING PASTE THE INVALID CATALOG NAME HERE">`get_import_events_gcs_request()`</walkthrough-editor-select-regex> method, and uncomment the line with the `default_catalog` local variable with an invalid catalog name.

1. Run the code again with the following command:
    ```bash
    mvn compile exec:java -Dexec.mainClass=events.ImportUserEventsGcs
    ```
1. Check the error message in the Terminal:
    ```terminal
    google.api_core.exceptions.InvalidArgument: 400 Request contains an invalid argument.
    ```

## Congratulations

<walkthrough-conclusion-trophy></walkthrough-conclusion-trophy>

You have completed the tutorial! We encourage you to test the importing user events from Cloud Storage by yourself and try different combinations of various filter expressions.

<walkthrough-inline-feedback></walkthrough-inline-feedback>
