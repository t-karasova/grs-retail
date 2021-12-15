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
 *
 * [START add_remove_fulfillment_places]
 */

package product;

import static product.setup.SetupCleanup.createProduct;
import static product.setup.SetupCleanup.getProduct;

import com.google.cloud.retail.v2.AddFulfillmentPlacesRequest;
import com.google.cloud.retail.v2.ProductServiceClient;
import com.google.cloud.retail.v2.ProductServiceSettings;
import com.google.protobuf.Timestamp;
import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class AddFulfillmentPlaces {

  public static final String PROJECT_NUMBER = System.getenv("PROJECT_NUMBER");
  public static final String ENDPOINT = "retail.googleapis.com:443";
  public static final String PRODUCT_ID = "add_fulfillment_test_product_id";
  public static final String PRODUCT_NAME = String.format(
      "projects/%s/locations/global/catalogs/default_catalog/branches/default_branch/products/%s",
      PROJECT_NUMBER, PRODUCT_ID);

  // The request timestamp
  private static final Timestamp requestTime = Timestamp.newBuilder()
      .setSeconds(Instant.now().getEpochSecond())
      .setNanos(Instant.now().getNano()).build();

  // get product service client
  private static ProductServiceClient getProductServiceClient() throws IOException {
    ProductServiceSettings productServiceSettings = ProductServiceSettings.newBuilder()
        .setEndpoint(ENDPOINT)
        .build();
    return ProductServiceClient.create(productServiceSettings);
  }

  // add fulfillment request
  public static AddFulfillmentPlacesRequest getAddFulfillmentRequest(String productName) {
    AddFulfillmentPlacesRequest addfulfillmentPlacesRequest = AddFulfillmentPlacesRequest.newBuilder()
        .setProduct(productName)
        .setType("pickup-in-store")
        .addPlaceIds("store1, store2, store3")
        .setAddTime(requestTime)
        .setAllowMissing(true)
        .build();

    System.out.println("Add fulfillment request " + addfulfillmentPlacesRequest);

    return addfulfillmentPlacesRequest;
  }

  // add fulfillment places to product
  public static void addFulfillmentPlaces(String productName)
      throws IOException, InterruptedException {
    AddFulfillmentPlacesRequest addFulfillmentRequest = getAddFulfillmentRequest(productName);

    getProductServiceClient().addFulfillmentPlacesAsync(addFulfillmentRequest);

    // This is a long running operation and its result is not immediately present with get operations,
    // thus we simulate wait with sleep method.
    System.out.println("Add fulfillment places, wait 60 seconds: ");

    getProductServiceClient().awaitTermination(60, TimeUnit.SECONDS);
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    createProduct(PRODUCT_ID);

    getProductServiceClient().awaitTermination(30, TimeUnit.SECONDS);

    addFulfillmentPlaces(PRODUCT_NAME);

    getProduct(PRODUCT_NAME);
  }

}

// [END add_remove_fulfillment_places]