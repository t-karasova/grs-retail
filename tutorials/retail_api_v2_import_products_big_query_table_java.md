<walkthrough-metadata>
  <meta name="title" content="Retail tutorial. Import products from the BigQuery table tutorial. Java" />
  <meta name="description" content="Learn how to import products from BigQuery table using Retail API Java client library" />
  <meta name="component_id" content="593554" />
  <meta name="keywords" content="retail, import products" />
</walkthrough-metadata>

#  Importing products from the BigQuery table tutorial

## Get started

The Retail API offers you a convenient way to import your catalog data from a previously loaded BigQuery table.

Using a BigQuery table allows you to import massive catalog data with no limits.

When you use a BigQuery table, you can specify the Retail schema, which allows more product attributes, such as key/value custom attributes, than other catalog import options allow.

You can choose one of the reconciliation modes, `INCREMENTAL` or `FULL`, using the BigQuery table as an import source:
- Incremental import creates products that didn't exist in a catalog before importing and updates all current products.
- Full import deletes current products if they aren't present in the BigQuery source, adds new products and updates 
  current products that existed in the catalog before importing and in the BigQuery table.

To find more information about different import types, their restrictions, and use cases, check the [Retail API documentation](https://cloud.google.com/retail/docs/upload-catalog#considerations).

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
**Note**: Click the copy button on the side of the code box to paste the command in the Cloud Shell terminal and run it.

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

### Set the PROJECT_NUMBER and PROJECT_ID environment variables

Because you are going to run the code samples in your own Google Cloud project, you should specify the **project_number** and **project_id** as environment variables. It will be used in every request to the Retail API.

1. Find the project number and project ID in the Project Info card displayed on **Home/Dashboard**.

1. Set **project_number** with the following command:
    ```bash
    export PROJECT_NUMBER=<YOUR_PROJECT_NUMBER>
    ```
1. Set **project_id** with the following command:
    ```bash
    export PROJECT_ID=<YOUR_PROJECT_ID>
    ```

### Google Cloud Retail libraries

You can find the Java Google Retail library is described [here](https://googleapis.dev/java/google-cloud-retail/latest/index.html)

## Clone the Retail code samples

This step is required if this is the first Retail API Tutorial you run.
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

## Create the BigQuery table and upload products

1. Before you start, build the Maven project and go to the code samples directory - our starting point to run code samples:
   ```bash
   cd ~/java-retail | mvn clean install -DskipTests
   cd ~/java-retail/samples/snippets  
   ```

Before you import products to your catalog, you need to upload the data to the BigQuery table first. There are two ways to do it:
- [Create an empty BigQuery table](https://cloud.google.com/bigquery/docs/tables#creating_an_empty_table_with_a_schema_definition) using the Retail schema and products in this table using SQL.
- Create a BigQuery table using the prepared JSON file with products. You can define the [Retail schema](https://cloud.google.com/retail/docs/catalog#expandable-1) or use an autodetect option.

To upload the data to the BigQuery table, first create a dataset, then create the table with the Retail data schema.
Next, upload data to the table from a prepared JSON file. The data in the file should correspond the Retail schema as well.

There is a <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/src/main/resources/products.json" regex="id">`resources/products.json`</walkthrough-editor-select-regex> file with valid products. It should be uploaded to the `products` dataset and `products` table.

Also, there is a <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/src/main/resources/products_some_invalid.json" regex="id">`resources/products_some_invalid.json`</walkthrough-editor-select-regex> file containing some invalid products along with valid ones. It should be uploaded to the `products` dataset and `products_some_invalid` table. This table will be used to demonstrate the error handling.

1. Run the following code in the Terminal to create tables and import data:
    ```bash
    mvn compile exec:java -Dexec.mainClass=product.setup.ProductsCreateBigqueryTable
    ```
1. The dataset `products` with both tables are created, check them in [Cloud Console](https://console.cloud.google.com/bigquery).

**Note**: If you don't have permissions to run the ```bq``` command and get a `Permission denied` error, you can create the table and upload your data from UI admin console.


## Create the BigQuery table and upload products from UI admin console

If you do not have permissions to run the ```bq``` command, you can create the table and upload your data from the UI admin console.

### Upload catalog data to Cloud Storage

There is a <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/src/main/resources/products.json" regex="id">resources/products.json</walkthrough-editor-select-regex> file with valid products prepared in the `product` directory.

The other file, <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/src/main/resources/products_some_invalid.json" regex="id">resources/products_some_invalid.json</walkthrough-editor-select-regex>, contains both valid and invalid products. You will use it to check the error handling.

In your own project create a Cloud Storage bucket and put the JSON file there.
The bucket name must be unique. For convenience, you can name it `<YOUR_PROJECT_ID>_<TIMESTAMP>`.

1. To create the bucket and upload the JSON file, run the following command in the Terminal:
    ```bash
    mvn compile exec:java -Dexec.mainClass=product.setup.ProductsCreateGcsBucket
    ```
    The bucket is created in the [Cloud Storage](https://console.cloud.google.com/storage/browser), and the file is uploaded.

1. Save the name of the created Cloud Storage bucket printed in the Terminal for the next steps.

### Create the BigQuery table and upload products

1. Go to [BigQuery in Cloud Console](https://console.cloud.google.com/bigquery).

1. In the Explorer panel, find the list of your projects.

1. Click the View actions button next to the current project name and chose **Create Dataset** option.

1. Enter the Dataset ID and click **Create Dataset**.

1. Expand the node of your current project.

1. Click the **View actions** button next to your new dataset and choose **Create table**.

1. To set the source, in the field **Create table from**, choose **Google Cloud Storage** option.

1. Click **Browse** in the **Select file from GCS bucket** and choose the bucket you created on the previous step.

1. Choose **`products.json`** and click **Select**.

1. Set the **Destination** field **Table** with the value ```products```.

1. To provide a table schema, enable the toggle **Edit as text** and in the **Schema** field, enter the schema from **`interactive-tutorials/src/main/resources/product_schema.json`**.

1. Click **Create table**.

The BigQuery table is created. You can proceed to importing products to the catalog.


## Import products from the BigQuery table

1. Add the service account `cloud-retail-customer-data-access@system.gserviceaccount.com` as a BigQuery Data Viewer for your project.

1. Open the file <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/src/main/java/product/ImportProductsBigQueryTable.java" regex="# get import products from big query request">`product/ImportProductsBigQueryTable.java`</walkthrough-editor-select-regex> and look at the example of the import product request.

    The field `parent` contains a catalog name along with a branch number where you are going to import your
    products to. You can use the default branch to import products to. However, if you are using custom products, change `default_branch`, which is **0**, to another branch ID, for example **1**. In the search tutorials you request `SearchService` to search for products in the default branch.

    The field `inputConfig` defines the `BigQuerySource` as an import source.

1. To import products, open the Terminal and run the following command:
    ```bash
    mvn compile exec:java -Dexec.mainClass=product.ImportProductsBigQueryTable
    ```

## Response analysis

Once you have called the import products method, the import operation has started.

Importing may take some time depending on the size of your BigQuery table.

The operation is completed when the field `operation.done()` is set to true.

1. Check the result. One of the following fields should be present:
    - `error`, if the operation failed.
    - `result`, if the operation was successful.

1. Check the `operation.getMetadata().unpack(ImportMetadata.class).getSuccessCount()` to get the total number of the successfully imported products. Their expected number is 1896.

    **Note** If you run the same code twice, the importing product catalog is exactly the same as the previous one.
    Retail calculates the diff and only performs create/update/delete on the difference. There won't be any update necessary and the `successCount` would be 0.

    The number of failures during the product import is returned in `operation.getMetadata().unpack(ImportMetadata.class).getFailureCount()`. The expected number is 0.

    The operation is successful, and the operation object contains a `result` field.

1. Check that it printed out in the Terminal:
    ```
    errors_config {
    gcs_prefix: "gs://945579214386_us_import_product/errors10929346905535193452"
    }
    ```

## Errors appeared during product importing

Try to import a couple of invalid product objects and check the error message in the operation response.

The title field is required, so if you remove it, you get an invalid product object.
Another example of an invalid product is a product with an incorrect value for the `product.availability` field.
There is a `products_for_import_invalid` table in the BigQuery dataset that contains such invalid products.

Use it for importing to get an error message.

1. Go to the <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/src/main/java/product/ImportProductsBigQueryTable.java" regex="# TO CHECK ERROR HANDLING USE THE TABLE WITH INVALID PRODUCTS:">`product/ImportProductsBigQueryTable.java`</walkthrough-editor-select-regex> file and assign the `TABLE_ID` value to the table with invalid products:
    ```
    private static final String TABLE_ID = "products_some_invalid";
    ```
1. Run the code sample and wait till the operation is completed:
    ```bash
    mvn compile exec:java -Dexec.mainClass=product.ImportProductsBigQueryTable
    ```

Next, check the operation printed out to the Terminal.

## Errors appeared during the importing: output analysis

If the operation is completed successfully, there is a `result` field. Otherwise, an `error` field appears instead.

The example operation is considered successful, and the `operation.getMetadata().unpack(ImportMetadata.class).getSuccessCount()` contains the number of successfully imported products, which is `6`.

There are two invalid products in the BigQuery table, and the number of failures during the product import in the `operation.getMetadata().unpack(ImportMetadata.class).getFailureCount()` is `3`.

The `operation.result` field points to the errors bucket where you can find a JSON file with all the importing errors.

The error is the following:
```json
{"code":3,"message":"Invalid value at 'availability' (type.googleapis.com/google.cloud.retail.v2main.Product.Availability): \"INVALID_VALUE\"","details":[{"@type":"type.googleapis.com/google.protobuf.Struct","value":{"line_number":1}}]}
```

## Errors appearing due to invalid request

Send an invalid import request to make the operation fail.

1. In the <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/src/main/java/product/ImportProductsBigQueryTable.java" regex="# TO CHECK ERROR HANDLING USE THE TABLE WITH INVALID PRODUCTS:">`product/ImportProductsBigQueryTable.java`</walkthrough-editor-select-regex> file, open the <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/src/main/java/product/ImportProductsBigQueryTable.java" regex="getImportProductsBigQueryRequest">`getImportProductsBigQueryRequest()`</walkthrough-editor-select-regex> method, and add a local variable `DEFAULT_CATALOG` with an invalid catalog name.

1. Run the code again and check the error message

    ```terminal
    io.grpc.StatusRuntimeException: INVALID_ARGUMENT: Request contains an invalid argument.
    ```

## Congratulations

<walkthrough-conclusion-trophy></walkthrough-conclusion-trophy>

You have completed the tutorial! We encourage you to prepare data and to test the importing products from BigQuery table by yourself.

<walkthrough-inline-feedback></walkthrough-inline-feedback>
