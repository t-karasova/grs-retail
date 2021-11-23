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
 * [START retail_search_for_products_with_filter]
 * Call Retail API to search for a products in a catalog, filter the results by different product fields.
 */

import com.google.cloud.retail.v2.SearchRequest;
import com.google.cloud.retail.v2.SearchRequest.FacetSpec;
import com.google.cloud.retail.v2.SearchRequest.FacetSpec.FacetKey;
import com.google.cloud.retail.v2.SearchResponse;
import com.google.cloud.retail.v2.SearchServiceClient;
import com.google.cloud.retail.v2.SearchServiceSettings;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SearchServiceFacetsExcludedFilterKeyTest {

  private static final long YOUR_PROJECT_NUMBER = Long.parseLong(System.getenv("PROJECT_NUMBER"));
  private static final String ENDPOINT = "retail.googleapis.com:443";
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
  private static void searchProductsWithTextualFacet_excludedFilterKeys(String query,
      String key, String filter, List<String> excludedFilterKeys, Integer pageSize)
      throws IOException, InterruptedException {
    SearchServiceClient searchClient = getSearchServiceClient();

    FacetKey facetKey = FacetKey.newBuilder()
        .setKey(key)
        .build();
    FacetSpec facetSpec = FacetSpec.newBuilder()
        .setFacetKey(facetKey)
        .addAllExcludedFilterKeys(excludedFilterKeys)
        .build();
    SearchRequest searchRequest = SearchRequest.newBuilder()
        .setPlacement(
            DEFAULT_SEARCH_PLACEMENT_NAME) // Placement is used to identify the Serving Config name.
        .setQuery(query)
        .setFilter(filter)
        .setPageSize(pageSize)
        .setVisitorId(VISITOR_ID) // A unique identifier to track visitors
        .build();

    System.out.println(
        "Search and return textual facet with excluded filter keys, request: " + searchRequest);

    SearchResponse response = searchClient.search(searchRequest).getPage().getResponse();

    searchClient.shutdownNow();
    searchClient.awaitTermination(2, TimeUnit.SECONDS);

    System.out.println(
        "Search and return textual facet with excluded filter keys, response: " + response);
  }

  // call the Retail Search:
  @Test
  public void search() throws IOException, InterruptedException {
    // TRY DIFFERENT FILTER EXPRESSIONS HERE:
    String filter = "(colorFamily: ANY(\"Black\"))";
    Integer pageSize = 10;

    List<String> excludedFilterKeys = Lists.newArrayList("colorFamily");

    searchProductsWithTextualFacet_excludedFilterKeys("Tee", "colorFamily",
        filter, excludedFilterKeys, pageSize
    );

  }
}

// [END retail_search_for_products_with_filter]