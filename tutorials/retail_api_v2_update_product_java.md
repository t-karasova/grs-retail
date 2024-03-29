<walkthrough-metadata>
  <meta name="title" content="Retail API. Update product tutorial" />
  <meta name="description" content="Learn how to use Retail API Java library to update product tutorial" />
  <meta name="component_id" content="593554" />
  <meta name="keywords" content="retail, update product" />
</walkthrough-metadata>

# Update product tutorial

## Get started

To fill the catalog or to update a massive number of products, we recommend using the `importProducts()` method. However,
sometimes you might need to make some detached changes in your product catalog.

For such cases, the Retail API provides you with the following methods:
- createProduct()
- getProduct()
- updateProduct()
- deleteProduct()

In this tutorial you will call the `updateProduct()` method.

For information about managing catalog information, see the [Retail API documentation](https://cloud.google.com/retail/docs/manage-catalog).

<walkthrough-tutorial-duration duration="4"></walkthrough-tutorial-duration>

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
    git clone https://github.com/googleapis/java-retail.git
    ```

   The code samples for each of the Retail services are stored in different directories.

## Update product

To update a product you should send an `UpdateProductRequest` call to the Retail API with the following required fields specified:
- `product`—the product object to be updated or created (depending on the  `allowMissing` value, the product can be created if it's missing).
- `allowMissing`—if the value is set to `true`, and the product is not found, a new product is created.

1. Before you start, build the Maven project and go to the code samples directory - our starting point to run code samples:
   ```bash
   cd java-retail/samples/interactive-tutorials/  
   mvn clean install -DskipTests
   ```

The originally created product is the following:
```
{
  "name": "projects/<PROJECT_NUMBER>/locations/global/catalogs/default_catalog/branches/default_branch/products/<PRODUCT_ID>",
  "id": "<PRODUCT_ID>",
  "type": "PRIMARY",
  "categories": "Speakers and displays",
  "title": "Nest Mini",
  "brands": "Google",
  "price_info": {
    currency_code: "USD"
    price: 30.0
    original_price: 35.5
  },
  "availability": "IN_STOCK"
}
```

To update each of its fields, you need to set a new product object to the `product` request field.

The product object with updated fields is already preset in generateProductForUpdate() method:
```
{
  "name": "projects/<PROJECT_NUMBER>/locations/global/catalogs/default_catalog/branches/default_branch/products/<PRODUCT_ID>", #cannot be updated , should point to the existing product
  "id": "<PRODUCT_ID>", #cannot be updated
  "type": "PRIMARY", #cannot be updated
  "categories": "Updated Speakers and displays",
  "title": "Updated Nest Mini",
  "brands": "Updated Google",
  "price_info": {
    currency_code: "EUR"
    price: 12.0
    original_price: 25.5
  },
  "availability": "OUT_OF_STOCK"
}
```

1. Run the following command in the Terminal:
   ```bash
    mvn compile exec:java -Dexec.mainClass=product.UpdateProduct
    ```

2. The Retail API returns the created product, the update request and the updated product. Check the output in the Terminal.

## Congratulations

<walkthrough-conclusion-trophy></walkthrough-conclusion-trophy>

You have completed the tutorial! We encourage you to test updating products by yourself.

<walkthrough-inline-feedback></walkthrough-inline-feedback>

### Do more with the Retail API

<walkthrough-tutorial-card id="retail_api_v2_create_product_java" icon="LOGO_JAVA" title="Create product tutorial" keepPrevious=true>Try to create a product via the Retail API</walkthrough-tutorial-card>

<walkthrough-tutorial-card id="retail_api_v2_get_product_java" icon="LOGO_JAVA" title="Get product tutorial" keepPrevious=true>Try to get a product via the Retail API</walkthrough-tutorial-card>

<walkthrough-tutorial-card id="retail_api_v2_delete_product_java" icon="LOGO_JAVA" title="Delete product tutorial" keepPrevious=true>Try to delete a product via the Retail API</walkthrough-tutorial-card>
