<walkthrough-metadata>
  <meta name="title" content="Update inventory: remove fulfillment places tutorial" />
  <meta name="description" content="Learn how to update inventory information by removing fulfillment places" />
  <meta name="component_id" content="593554" />
  <meta name="unlisted" content="true" />
  <meta name="keywords" content="retail, update inventory, remove fulfillment places" />
</walkthrough-metadata>

# Update inventory: remove fulfillment places tutorial

## Introduction

Changes to a product's inventory information might occur more frequently than changes to its catalog information.

Instead of using the `UpdateProduct` method to respecify an entire product when the fulfillment availabilities of a few specific places change, you can push incremental updates.

In such cases, `AddFulfillmentPlaces` and `RemoveFulfillmentPlaces` methods can be used to incrementally update product fulfillment. The place IDs are added to or removed from a given fulfillment type based on the fulfillment changes.

These methods are asynchronous because of downstream optimizations that support hundreds of concurrent updates per product without sacrificing performance.

For more information about managing catalog information, see the [Retail API documentation](https://cloud.google.com/retail/docs/inventory-updates#inventory-update-methods).

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

1. Run the following command in the Terminal:
    ```bash
    git clone https://github.com/googleapis/java-retail.git
    ```

   The code samples for each of the Retail services are stored in different directories.

## Remove fulfillment places

1. Before you start, build the Maven project and go to the code samples directory - our starting point to run code samples:
   ```bash
   cd ~/java-retail | mvn clean install -DskipTests
   cd ~/java-retail/samples/snippets  
   ```

1. Open <walkthrough-editor-select-regex filePath="cloudshell_open/java-retail/samples/snippets/src/main/java/product/RemoveFulfillmentPlaces.java" regex="Remove fulfillment request">product/RemoveFulfillmentPlaces.java</walkthrough-editor-select-regex> file and go to the `getRemoveFulfillmentRequest()` method. Check which fields need to be set in order to send the `RemoveFulfillmentPlacesRequest`:
   - `product`—the product name whose inventory information will be updated
   - `type`—the fulfillment type. You can set one of the [supported values](https://cloud.google.com/retail/docs/reference/rpc/google.cloud.retail.v2#removefulfillmentplacesrequest).
   - `placeIds`—the store IDs for each of the fulfillment types
   - `removeTime`—the time when the fulfillment updates are pushed. It is used to prevent out-of-order updates on the fulfillment information. If this isn't provided, the internal system time will be used.
   - `allowMissing`—if set to true and the product is not found, the fulfillment information will be retained for up to 24 hours and processed after the product is created.

1. To remove the fulfillment places, open the Terminal and run the following command:
    ```bash
    mvn compile exec:java -Dexec.mainClass=product.RemoveFulfillmentPlaces
    ```

1. Check the response in the Terminal. The product was initially created with fulfillment places `store0` and `store1`.

1. Check the ```getProduct()``` response. The `store0`  place has been removed from the list of fulfillment places for the `pickup-in-store` fulfillment type.

## Congratulations

<walkthrough-conclusion-trophy></walkthrough-conclusion-trophy>

You have completed the tutorial! We encourage you to test removing product fulfillment places by yourself.

<walkthrough-inline-feedback></walkthrough-inline-feedback>

### Do more with the Retail API

<walkthrough-tutorial-card id="retail_api_v2_set_invenory_java" icon="LOGO_JAVA" title="Set inventory tutorial" keepPrevious=true>Try to set inventory via the Retail API</walkthrough-tutorial-card>

<walkthrough-tutorial-card id="retail_api_v2_add_fulfillment_places_java" icon="LOGO_JAVA" title="Add fulfillment tutorial" keepPrevious=true>Try to add fulfillment places via the Retail API</walkthrough-tutorial-card>

