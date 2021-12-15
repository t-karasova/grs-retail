/*
 * Copyright 2021 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package product.setup;

import static com.google.cloud.storage.StorageClass.STANDARD;

import com.google.api.gax.paging.Page;
import com.google.api.gax.rpc.NotFoundException;
import com.google.cloud.retail.v2.CreateProductRequest;
import com.google.cloud.retail.v2.DeleteProductRequest;
import com.google.cloud.retail.v2.FulfillmentInfo;
import com.google.cloud.retail.v2.GetProductRequest;
import com.google.cloud.retail.v2.PriceInfo;
import com.google.cloud.retail.v2.Product;
import com.google.cloud.retail.v2.Product.Availability;
import com.google.cloud.retail.v2.Product.Type;
import com.google.cloud.retail.v2.ProductServiceClient;
import com.google.cloud.retail.v2.ProductServiceSettings;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class SetupCleanup {

  public static final String PROJECT_NUMBER = System.getenv("PROJECT_NUMBER");
  public static final String ENDPOINT = "retail.googleapis.com:443";
  public static final String DEFAULT_BRANCH_NAME = String.format(
      "projects/%s/locations/global/catalogs/default_catalog/branches/default_branch",
      PROJECT_NUMBER);

  public static final Storage storage = StorageOptions.newBuilder().setProjectId(PROJECT_NUMBER)
      .build().getService();

  private static ProductServiceClient getProductServiceClient() throws IOException {
    ProductServiceSettings productServiceSettings = ProductServiceSettings.newBuilder()
        .setEndpoint(ENDPOINT)
        .build();
    return ProductServiceClient.create(productServiceSettings);
  }

  public static Product generateProduct() {
    PriceInfo priceInfo = PriceInfo.newBuilder()
        .setPrice(30.0f)
        .setOriginalPrice(35.5f)
        .setCurrencyCode("USD")
        .build();

    FulfillmentInfo fulfillmentInfo = FulfillmentInfo.newBuilder()
        .setType("pickup-in-store")
        .addAllPlaceIds(Arrays.asList("store0", "store1"))
        .build();

    return Product.newBuilder()
        .setTitle("Nest Mini")
        .setType(Type.PRIMARY)
        .addCategories("Speakers and displays")
        .addBrands("Google")
        .setPriceInfo(priceInfo)
        .addFulfillmentInfo(fulfillmentInfo)
        .setAvailability(Availability.IN_STOCK)
        .build();
  }

  public static Product createProduct(String productId) throws IOException {
    CreateProductRequest createProductRequest = CreateProductRequest.newBuilder()
        .setProduct(generateProduct())
        .setProductId(productId)
        .setParent(DEFAULT_BRANCH_NAME)
        .build();

    Product product = getProductServiceClient().createProduct(createProductRequest);

    System.out.println("Product is created: " + product);

    return product;
  }

  public static void deleteProduct(String productName) throws IOException {
    DeleteProductRequest deleteProductRequest = DeleteProductRequest.newBuilder()
        .setName(productName)
        .build();

    getProductServiceClient().deleteProduct(deleteProductRequest);

    System.out.printf("Product %s was deleted.%n", productName);
  }

  public static Product getProduct(String productName) throws IOException {
    Product product = Product.newBuilder()
        .build();

    GetProductRequest getProductRequest = GetProductRequest.newBuilder()
        .setName(productName)
        .build();

    try {
      product = getProductServiceClient().getProduct(getProductRequest);

      System.out.println("Get product response: " + product);

      return product;
    } catch (NotFoundException e) {
      System.out.printf("Product %s not found", productName);
      return product;
    }
  }

  public static String getProjectId() {
    return storage.getOptions().getProjectId();
  }

  public static Bucket createBucket(String bucketName) {
    // Create a new bucket in Cloud Storage
    Bucket bucket = null;

    System.out.println("Bucket name: " + bucketName);

    String bucketsInYourProject = listBuckets().toString();

    if (bucketsInYourProject.contains(bucketName)) {
      System.out.printf("Bucket %s already exists%n", bucketsInYourProject);
    } else {
      bucket = storage.create(
          BucketInfo.newBuilder(bucketName)
              .setStorageClass(STANDARD)
              .setLocation("US")
              .build());

      System.out.printf("Created bucket %s in %s with storage class %s%n", bucket.getName(),
          bucket.getLocation(), bucket.getStorageClass());
    }

    return bucket;
  }

  public static Page<Bucket> listBuckets() {
    Page<Bucket> bucketList = storage.list();

    for (Bucket bucket : bucketList.iterateAll()) {
      System.out.println(bucket.getName());
    }

    return bucketList;
  }

  public static void uploadObject(String bucketName, String objectName, String filePath)
      throws IOException {
    Storage storage = StorageOptions.newBuilder().setProjectId(PROJECT_NUMBER).build().getService();

    BlobId blobId = BlobId.of(bucketName, objectName);

    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

    storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));

    System.out.println(
        "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
  }

  public static void createBqDataset(String datasetName) {
    // Create a BigQuery dataset

    // TODO: 12/14/21 Finish the method.
  }

  // TODO: 12/14/21 Add other methods.
}
