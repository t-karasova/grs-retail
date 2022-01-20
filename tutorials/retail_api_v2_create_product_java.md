<walkthrough-metadata>
  <meta name="title" content="Retail API. Create product tutorial" />
  <meta name="description" content="Learn how to use Retail API Java library to create product tutorial" />
  <meta name="component_id" content="593554" />
  <meta name="unlisted" content="true" />
  <meta name="keywords" content="retail, create product" />
</walkthrough-metadata>

# Create product tutorial

## Get started

To fill the catalog or to update a massive number of products, we recommend using the `import_products` method. However,
sometimes you might need to make some detached changes in your product catalog.

For such cases, the Retail API provides you with the following methods:
- create_product
- get_product
- update_product
- delete_product

In this tutorial you will create a simple product.

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
    git clone https://github.com/t-karasova/grs-retail.git
    ```

    The code samples for each of the Retail services are stored in different directories.

1. Go to the code samples directory - our starting point to run more commands.
    ```bash
    cd java-retail/samples/snippets
    ```

## Product object overview

The required product fields are:

- `name`—a full resource name of the product, which is:
    ```none
    projects/<project_number>/locations/global/catalogs/<catalog_id>/branches/<branch_id>/products/<product_id>
    ```
- `id`—product identifier, which is the final component of the product name.
- `type`—the type of the product. The default value is `PRIMARY`.
- `primary_product_id`—a variant group identifier required for `VARIANT` products.
- `categories[]`—names of categories that the product belongs to. This can represent different category hierarchies.
- `title`—the product title that will be visible to a customer.

## Product types overview

The types of products are the following:

- `PRIMARY`—a central product in a family of related products. From the Retail perspective it is also a primary unit for indexing and search serving and predicting. It can be grouped with multiple `VARIANT` products as a parent product.
- `VARIANT`—a SKU (stock keeping unit); a product item that usually shares common attributes with its parent `PRIMARY` product, but also has variant attributes: for example, different colors, sizes, and prices.
- `COLLECTION`—a product item where `PRIMARY` or `VARIANT` products are sold together, such as a jewelry set with necklaces, or earrings and rings.

For information about the product object and its fields, see the [Retail API documentation](https://cloud.google.com/retail/docs/reference/rpc/google.cloud.retail.v2#google.cloud.retail.v2.Product).

## Get a simple product object

In this tutorial you will create a simple `PRIMARY` product presented in JSON format:

```
{
  "name": "projects/<PROJECT_NUMBER>/locations/global/catalogs/default_catalog/branches/default_branch/products/<PRODUCT_ID>",
  "id": "PRODUCT_ID",
  "type": "PRIMARY",
  "categories": [
    "Speakers and displays"
  ],
  "brands": [
    "Google"
  ],
  "title": "Nest Mini",
  "availability": "IN_STOCK",
  "price_info": {
    "price": 30.0,
    "original_price": 35.5,
    "currency_code": "USD"
  }
}
```

## Create a product in a catalog

1. Before you start, build the Maven project and go to the code samples directory - our starting point to runcode samples:
   ```bash
   cd ~/java-retail | mvn clean install -DskipTests
   cd ~/java-retail/samples/snippets  
   ```

1. Open the <walkthrough-editor-select-regex filePath="cloudshell_open/java-retail/samples/snippets/src/main/java/product/CreateProduct.java" regex="Generate product to create">CreateProduct.java</walkthrough-editor-select-regex> code sample and check this product generation with the method ```generateProduct()```.

1. To create a product, send a `CreateProductRequest` to Retail API with the following required fields specified:
    - `product`—the product object you want to create
    - `productId`—product ID
    - `parent`—a branch name in a catalog where the product will be created.

1. To create a product, run the sample in the Terminal using the command:
    ```bash
    pmvn compile exec:java -Dexec.mainClass=product.CreateProduct
    ```

The Retail API returns the created product as a response.

## Error handling

If you send a request without one of the required fields or if the field format is incorrect, you will get an error message.

1. Remove the product field `categories` and send this request again.

1. Run the sample in the Terminal using the command:
    ```bash
    pmvn compile exec:java -Dexec.mainClass=product.CreateProduct
    ```

1. You should see the following error message:
    ```terminal
    google.api_core.exceptions.InvalidArgument: 400 Field "product.categories" is a required field, but no value is found.
    ```

## Congratulations

<walkthrough-conclusion-trophy></walkthrough-conclusion-trophy>

You have completed the tutorial! We encourage you to try creating products by yourself.

<walkthrough-inline-feedback></walkthrough-inline-feedback>

### Do more with the Retail API

<walkthrough-tutorial-card id="retail_api_v2_get_product_java" icon="LOGO_JAVA" title="Get product tutorial" keepPrevious=true>
Try to get a product via the Retail API</walkthrough-tutorial-card>

<walkthrough-tutorial-card id="retail_api_v2_update_product_java" icon="LOGO_JAVA" title="Update product tutorial" keepPrevious=true>Try to update a product via the Retail API</walkthrough-tutorial-card>

<walkthrough-tutorial-card id="retail_api_v2_delete_product_java" icon="LOGO_JAVA" title="Delete product tutorial" keepPrevious=true>Try to remove a product via the Retail API</walkthrough-tutorial-card>
