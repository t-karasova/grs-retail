/*
 * Copyright 2021 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * [START retail_update_attribute_config]
 * Update product in a catalog using Retail API to change the product attribute searchability and indexability.
 */

package search;

import com.google.cloud.retail.v2.CustomAttribute;
import com.google.cloud.retail.v2.GetProductRequest;
import com.google.cloud.retail.v2.Product;
import com.google.cloud.retail.v2.ProductServiceClient;
import com.google.cloud.retail.v2.ProductServiceSettings;
import com.google.protobuf.FieldMask;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SearchUpdateAttributeConfig {

  private static final long YOUR_PROJECT_NUMBER = Long.parseLong(System.getenv("PROJECT_NUMBER"));
  private static final String ENDPOINT = "retail.googleapis.com:443";
  private static final String DEFAULT_CATALOG_NAME =
      String.format(
          "projects/%d/locations/global/catalogs/default_catalog/branches/default_branch/products/",
          YOUR_PROJECT_NUMBER);

  // get product service client
  private static ProductServiceClient getProductServiceClient() throws IOException {
    ProductServiceSettings productServiceSettings = ProductServiceSettings.newBuilder()
        .setEndpoint(ENDPOINT)
        .build();
    return ProductServiceClient.create(productServiceSettings);
  }

  public static Product getProduct(String productId) throws IOException {
    return getProductServiceClient().getProduct(DEFAULT_CATALOG_NAME + productId);
  }

  public static Product getUpdateProductRequest(Product productToUpdate) throws IOException {
    GetProductRequest request = GetProductRequest
        .newBuilder()
        .setName(DEFAULT_CATALOG_NAME + productToUpdate.getId())
        .build();
    return getProductServiceClient().getProduct(request);
  }

  // update the product attribute
  public static Product updateProduct(String productToUpdateId)
      throws IOException, InterruptedException {

    // Get a product from catalog
    Product productToUpdate = getProduct(productToUpdateId);

    // Prepare the product attribute to be updated
    final CustomAttribute customAttribute = CustomAttribute.newBuilder()
        .setIndexable(true)
        .setSearchable(false)
        .addText(
            "\"recycled fabrics\", \"recycled packaging\", \"plastic-free packaging\", \"ethically made\"")
        .build();

    // Set the attribute to the original product
    productToUpdate.getAttributesOrDefault("ecofriendly", customAttribute);

    // Update product
    Product updatedProduct = getProductServiceClient().updateProduct(
        getUpdateProductRequest(productToUpdate), FieldMask.getDefaultInstance());

    System.out.println("Updated product: " + updatedProduct);

    System.out.println("Wait 2 minutes to be sure the catalog has been indexed after the changes:");

    getProductServiceClient().shutdownNow();
    getProductServiceClient().awaitTermination(2, TimeUnit.MINUTES);

    System.out.println("You can proceed with the search requests");

    return updatedProduct;
  }
}

// [END retail_update_attribute_config]