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

import com.google.cloud.retail.v2.CustomAttribute;
import com.google.cloud.retail.v2.GetProductRequest;
import com.google.cloud.retail.v2.Product;
import com.google.cloud.retail.v2.ProductServiceClient;
import com.google.cloud.retail.v2.ProductServiceSettings;
import java.io.IOException;
import java.util.UUID;

public class SearchUpdateAttributeConfigurationTest {

  private static final long YOUR_PROJECT_NUMBER = Long.parseLong(System.getenv("PROJECT_NUMBER"));
  private static final String ENDPOINT = "retail.googleapis.com:443";
  private static final String DEFAULT_CATALOG_NAME =
      String.format("projects/%d/locations/global/catalogs/default_catalog", YOUR_PROJECT_NUMBER);
  private static final String DEFAULT_SEARCH_PLACEMENT_NAME =
      DEFAULT_CATALOG_NAME + "/placements/default_search";
  private static final String VISITOR_ID = UUID.randomUUID().toString();

  // get product service client
  private static ProductServiceClient getProductServiceClient() throws IOException {
    ProductServiceSettings productServiceSettings = ProductServiceSettings.newBuilder()
        .setEndpoint(ENDPOINT)
        .build();
    return ProductServiceClient.create(productServiceSettings);
  }

  public static Product getProduct(String productId) throws IOException {
    return getProductServiceClient().getProduct(productId);
  }

  public static Product getProductWithRequest(String productName) throws IOException {
    GetProductRequest request = GetProductRequest
        .newBuilder()
        .setName(productName)
        .build();
    return getProductServiceClient().getProduct(request);
  }

  // update the product attribute
  public static void updateProduct(String productToUpdateId) throws IOException {

    // Get a product from catalog
    Product productToUpdate = getProduct(productToUpdateId);

    // Prepare the product attribute to be updated
    final CustomAttribute customAttribute = CustomAttribute.newBuilder()
        .setIndexable(true)
        .setSearchable(true)
        // setText - "recycled fabrics", "recycled packaging", "plastic-free packaging", "ethically made"
        .setText(1, "")
        .build();

    // Set the attribute to the original product
    // TODO: 11/24/21 https://github.com/t-karasova/grs-samples-python/blob/master/search/update_attribute_configuration.py

    // Update product
//    updated_product = get_product_service_client().update_product(
//        get_update_product_request(product_to_update))
//
//    print('---updated product---:')
//    print(updated_product)
//
//    print('---Wait 5 minutes to be sure the catalog has been indexed after the changes---:')
//    time.sleep(300)
//    print('---You can proceed with the search requests---')
//
//
//    update_product(product_id)
  }
}

// [END retail_update_attribute_config]