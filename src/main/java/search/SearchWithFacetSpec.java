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
 * [START retail_search_product_with_facet_spec]
 */

package search;

import com.google.cloud.retail.v2.SearchRequest;
import com.google.cloud.retail.v2.SearchRequest.FacetSpec;
import com.google.cloud.retail.v2.SearchRequest.FacetSpec.Builder;
import com.google.cloud.retail.v2.SearchRequest.FacetSpec.FacetKey;
import com.google.cloud.retail.v2.SearchResponse;
import com.google.cloud.retail.v2.SearchServiceClient;
import com.google.cloud.retail.v2.SearchServiceSettings;
import java.io.IOException;
import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SearchWithFacetSpec {

  /**
   * This variable describes project number getting from environment variable.
   */
  private static final String PROJECT_NUMBER = System.getenv(
      "PROJECT_NUMBER");

  /**
   * This variable describes endpoint for send requests.
   */
  private static final String ENDPOINT = "retail.googleapis.com:443";

  /**
   * This variable describes a unique identifier to track visitors.
   */
  private static final String VISITOR_ID = UUID.randomUUID().toString();

  /**
   * Get search service client.
   *
   * @return SearchServiceClient.
   * @throws IOException if endpoint is incorrect.
   */
  private static SearchServiceClient getSearchServiceClient()
      throws IOException {
    SearchServiceSettings settings = SearchServiceSettings.newBuilder()
        .setEndpoint(ENDPOINT)
        .build();
    return SearchServiceClient.create(settings);
  }

  /**
   * Get search service request.
   *
   * @param query         Search keyword.
   * @param facetKeyParam The key to set.
   * @return SearchRequest.
   */
  public static SearchRequest getSearchRequest(final String query,
      final String facetKeyParam) {

    int pageSize = 10;

    String defaultSearchPlacement = String.format(
        "projects/%s/locations/global/catalogs/default_catalog/placements/default_search",
        PROJECT_NUMBER);

    FacetKey facetKey = FacetKey.newBuilder()
        .setKey(facetKeyParam)
        .build();

    Builder faceSpec = FacetSpec.newBuilder()
        .setFacetKey(facetKey);

    SearchRequest searchRequest = SearchRequest.newBuilder()
        .setPlacement(defaultSearchPlacement)
        .setQuery(query)
        .setVisitorId(VISITOR_ID)
        .addFacetSpecs(faceSpec)
        .setPageSize(pageSize)
        .build();

    System.out.printf("Search request: %s%n", searchRequest);

    return searchRequest;
  }

  /**
   * Call the retail search.
   *
   * @return SearchResponse.
   * @throws IOException if endpoint is not provided in getSearchServiceClient().
   */
  public static SearchResponse search() throws IOException {
    // TRY DIFFERENT FACETS HERE:
    String facetKey = "colorFamilies";

    SearchRequest searchRequest = getSearchRequest("Tee", facetKey);

    SearchResponse searchResponse = getSearchServiceClient().search(
        searchRequest).getPage().getResponse();

    System.out.printf("Search response: %s%n", searchResponse);

    return searchResponse;
  }

  /**
   * Executable tutorial class.
   *
   * @throws IOException from the called method.
   */
  public static void main(final String[] args)
      throws IOException {
    search();
  }
}

// [END retail_search_product_with_facet_spec]
