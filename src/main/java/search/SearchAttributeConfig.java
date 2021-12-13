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
 * [START retail_search_with_filter_by_attribute]
 * Call Retail API to search for a products in a catalog, filter the results by the "product.attribute" field.
 */

package search;

import com.google.cloud.retail.v2.SearchRequest;
import com.google.cloud.retail.v2.SearchResponse;
import com.google.cloud.retail.v2.SearchServiceClient;
import com.google.cloud.retail.v2.SearchServiceSettings;
import java.io.IOException;
import java.util.UUID;

public class SearchAttributeConfig {

  private static final String YOUR_PROJECT_NUMBER = System.getenv("PROJECT_NUMBER");
  private static final String ENDPOINT = "retail.googleapis.com:443";
  private static final String DEFAULT_CATALOG_NAME =
      String.format("projects/%s/locations/global/catalogs/default_catalog", YOUR_PROJECT_NUMBER);
  private static final String DEFAULT_SEARCH_PLACEMENT_NAME =
      DEFAULT_CATALOG_NAME + "/placements/default_search";
  private static final String VISITOR_ID = UUID.randomUUID().toString();

  // get search service client
  private static SearchServiceClient getSearchServiceClient() throws IOException {
    SearchServiceSettings settings = SearchServiceSettings.newBuilder()
        .setEndpoint(ENDPOINT)
        .build();
    return SearchServiceClient.create(settings);
  }

  public static SearchResponse search() throws IOException, InterruptedException {

    SearchServiceClient searchClient = getSearchServiceClient();

    // get search service request
    SearchRequest searchRequest = SearchRequest.newBuilder()
        .setPlacement(
            DEFAULT_SEARCH_PLACEMENT_NAME) // Placement is used to identify the Serving Config name.
        .setQuery("sweater")
        .setFilter("attributes.ecofriendly: ANY(\"recycled packaging\")")
        .setPageSize(10)
        .setVisitorId(VISITOR_ID) // A unique identifier to track visitors
        .build();

    System.out.println("Search request: " + searchRequest);

    // call the Retail Search:
    SearchResponse searchResponse = searchClient.search(searchRequest).getPage().getResponse();

    System.out.println("Search response: " + searchResponse);
    return searchResponse;
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    search();
  }
}

// [END retail_search_with_filter_by_attribute]