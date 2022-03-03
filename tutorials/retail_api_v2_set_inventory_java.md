<walkthrough-metadata>
  <meta name="title" content="Set inventory tutorial" />
  <meta name="description" content="You can push inventory updates using the `SetInventory()` method instead of updating the entire product." />
  <meta name="component_id" content="593554" />
</walkthrough-metadata>

# Set inventory tutorial

## Introduction

Changes to a product's inventory information might occur more frequently than changes to the catalog information.

You can push inventory updates using the `setInventoryAsync()` method instead of updating the entire product.

For more information about managing catalog information, see the [Retail API documentation](https://cloud.google.com/retail/docs/inventory-updates#non-incremental-updates).

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

1.  Login with your user credentials.

    ```bash
    gcloud auth login
    ```

1.  Type `Y` and press **Enter**. Click the link in Terminal. A browser window
    should appear asking you to log in using your Gmail account.

1.  Provide the Google Auth Library with access to your credentials and paste
    the code from the browser to the Terminal.

1.  Upload your service account key JSON file and use it to activate the service
    account:

    ```bash
    gcloud iam service-accounts keys create ~/key.json --iam-account <YOUR_SERVICE_ACCOUNT_EMAIL>
    ```

    ```bash
    gcloud auth activate-service-account --key-file ~/key.json
    ```

1.  Set key as the GOOGLE_APPLICATION_CREDENTIALS environment variable to be
    used for requesting the Retail API.

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
   
## Set inventory

1. Set the following fields to send the `SetInventoryRequest` call:
    - `inventory`—the inventory information to update. The fields allowed for updating are:
        - `Product.priceInfo`
        - `Product.availability`
        - `Product.availableQuantity`
        - `Product.fulfillmentInfo`
    - `setMask`—indicates what product inventory fields to update
    - `setTime`—the time when the request is sent. It is used to prevent out-of-order updates on inventory fields that contain the last update time recorded value.
    - `allowMissing`—if set to true and the product is not found, the fulfillment information will be retained for up to 24 hours and processed after the product is created.

1. Open the <walkthrough-editor-select-regex filePath="cloudshell_open/java-retail/samples/snippets/src/main/java/product/SetInventory.java" regex="getSetInventoryRequest">SetInventory.java</walkthrough-editor-select-regex> file and check the `SetInventoryRequest` request.

2. Before you start, build the Maven project and go to the code samples directory - our starting point to run code samples:
   ```bash
   cd ~/java-retail | mvn clean install -DskipTests
   cd ~/java-retail/samples/snippets  
   ```

3. Run the code sample in the Terminal to create a product in a catalog and remove it using a prepared request:
    ```bash
    mvn compile exec:java -Dexec.mainClass=product.SetInventory
    ```

1. Check the response in the Terminal. The product is initially created with `priceInfo` and `availability` information.

Next, to ensure that the product inventory information was updated successfully, check the `getProduct()` response.

## Send an out-of-order SetInventory request

The `setInventoryAsync()` method allows you to specify an update time when the request is sent.
The Retail API compares the update time you've specified with the latest time recorded for the relevant inventory fields. An update happens only if the specified update time value is greater than the latest update time value.

1. Modify the <walkthrough-editor-select-regex filePath="cloudshell_open/java-retail/samples/snippets/src/main/java/product/SetInventory.java" regex="float price">`price`</walkthrough-editor-select-regex> field value:
    ```
    final float price = 20.0f;
    ```

1. Change the <walkthrough-editor-select-regex filePath="cloudshell_open/java-retail/samples/snippets/src/main/java/product/SetInventory.java" regex="The request timestamp">`SetInventory.java`</walkthrough-editor-select-regex> request and set the `requestTime` value to yesterday:
    ```
    Timestamp requestTime = Timestamp.newBuilder()
        .setSeconds(Instant.now().minus(1, ChronoUnit.DAYS).getEpochSecond())
        .setNanos(Instant.now().getNano()).build();
    ```

1. Run the code sample in the Terminal:
    ```bash
    mvn compile exec:java -Dexec.mainClass=product.SetInventory
    ```

1. Check the product printed out in the Terminal. The inventory information isn't updated.

## Congratulations

<walkthrough-conclusion-trophy></walkthrough-conclusion-trophy>

You have completed the tutorial! We encourage you to test the product inventory information by yourself.

<walkthrough-inline-feedback></walkthrough-inline-feedback>

### Do more with the Retail API

<walkthrough-tutorial-card id="retail_api_v2_add_fulfillment_places_java" icon="LOGO_JAVA" title="Add fulfillment tutorial" keepPrevious=true>Try to get a product via the Retail API</walkthrough-tutorial-card>

<walkthrough-tutorial-card id="retail_api_v2_remove_fulfillment_places_java" icon="LOGO_JAVA" title="Remove fulfillment tutorial" keepPrevious=true>Try to update a product via the Retail API</walkthrough-tutorial-card>