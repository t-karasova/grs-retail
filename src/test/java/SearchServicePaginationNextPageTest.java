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
 * [START retail_search_for_products_with_next_page]
 * Call Retail API to search for a products in a catalog,
 * limit the number of the products per page and go to the next page using "next_page_token"
 * or jump to chosen page using "offset".
 */

import com.google.cloud.retail.v2.SearchRequest;
import com.google.cloud.retail.v2.SearchResponse;
import com.google.cloud.retail.v2.SearchServiceClient;
import com.google.cloud.retail.v2.SearchServiceSettings;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SearchServicePaginationNextPageTest {

  private static final long YOUR_PROJECT_NUMBER = Long.parseLong(System.getenv("PROJECT_NUMBER"));
  private static final String ENDPOINT = "retail.googleapis.com";
  private static final String DEFAULT_CATALOG_NAME =
      String.format("projects/%d/locations/global/catalogs/default_catalog", YOUR_PROJECT_NUMBER);
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

  // get search service request
  public static SearchResponse searchProducts_withNextPageToken(String query, String pageToken)
      throws IOException, InterruptedException {
    SearchServiceClient searchClient = getSearchServiceClient();

    SearchRequest firstRequest = SearchRequest.newBuilder()
        .setPlacement(DEFAULT_SEARCH_PLACEMENT_NAME)
        .setVisitorId(VISITOR_ID) // A unique identifier to track visitors
        .setQuery(query)
        .setPageToken(pageToken)
        .build();

    SearchResponse firstResponse = searchClient.search(firstRequest).getPage()
        .getResponse();

    SearchRequest secondRequest = SearchRequest.newBuilder()
        .setPlacement(DEFAULT_SEARCH_PLACEMENT_NAME)
        .setVisitorId(VISITOR_ID) // A unique identifier to track visitors
        .setQuery(query)
        .setPageToken(firstResponse.getNextPageToken())
        .build();

    System.out.println("Search with pagination using page token, request: " + secondRequest);

    SearchResponse response = searchClient.search(secondRequest).getPage().getResponse();

    searchClient.shutdownNow();
    searchClient.awaitTermination(2, TimeUnit.SECONDS);

    System.out.println("Search with pagination using page token, response: " + response);
    return response;
  }

  // call the Retail Search
  @Test
  public void search() throws IOException, InterruptedException {
    // TRY DIFFERENT PAGINATION PARAMETERS HERE:
    String pageToken = "";

    searchProducts_withNextPageToken("Hoodie", pageToken);
  }
}

// [END retail_search_for_products_with_next_page]