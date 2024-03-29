/*
 * Copyright 2022 Google LLC
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
 */

/*
 * [START retail_search_product_with_boost_spec]
 * Call Retail API to search for a products in a catalog, rerank the
 * results boosting or burying the products that match defined condition.
 */

package search;

import com.google.cloud.retail.v2.SearchRequest;
import com.google.cloud.retail.v2.SearchRequest.BoostSpec;
import com.google.cloud.retail.v2.SearchRequest.BoostSpec.ConditionBoostSpec;
import com.google.cloud.retail.v2.SearchResponse;
import com.google.cloud.retail.v2.SearchServiceClient;
import java.io.IOException;
import java.util.UUID;

public final class SearchWithBoostSpec {

  /**
   * This variable describes project number getting from environment variable.
   */
  private static final String YOUR_PROJECT_NUMBER = System.getenv(
      "PROJECT_NUMBER");

  /**
   * This variable describes default catalog name.
   */
  private static final String DEFAULT_CATALOG_NAME =
      String.format("projects/%s/locations/global/catalogs/default_catalog",
          YOUR_PROJECT_NUMBER);

  /**
   * This variable describes default search placement name. Using for identify
   * the Serving Config name.
   */
  private static final String DEFAULT_SEARCH_PLACEMENT_NAME =
      DEFAULT_CATALOG_NAME + "/placements/default_search";

  /**
   * This variable describes a unique identifier to track visitors.
   */
  private static final String VISITOR_ID = UUID.randomUUID().toString();

  private SearchWithBoostSpec() {
  }

  /**
   * Get search service client.
   *
   * @return SearchServiceClient.
   * @throws IOException if endpoint is incorrect.
   */
  private static SearchServiceClient getSearchServiceClient()
      throws IOException {
    return SearchServiceClient.create();
  }

  /**
   * Get search service request.
   *
   * @param query         search keyword.
   * @param condition     provides search clarification.
   * @param boostStrength is a rate of boost strength.
   * @return SearchRequest.
   */
  public static SearchRequest getSearchRequest(
      final String query, final String condition, final float boostStrength) {

    final int pageSize = 10;

    BoostSpec boostSpec =
        BoostSpec.newBuilder()
            .addConditionBoostSpecs(
                ConditionBoostSpec.newBuilder()
                    .setCondition(condition)
                    .setBoost(boostStrength)
                    .build())
            .build();

    SearchRequest searchRequest =
        SearchRequest.newBuilder()
            .setPlacement(DEFAULT_SEARCH_PLACEMENT_NAME)
            .setQuery(query)
            .setVisitorId(VISITOR_ID)
            .setBoostSpec(boostSpec)
            .setPageSize(pageSize)
            .build();

    System.out.printf("Search request: %n%s", searchRequest);

    return searchRequest;
  }

  /**
   * Call the retail search.
   *
   * @return SearchResponse.
   * @throws IOException if endpoint is not provided.
   */
  public static SearchResponse search() throws IOException {
    // TRY DIFFERENT CONDITIONS HERE:
    String condition = "(colorFamilies: ANY(\"Blue\"))";
    float boost = 0.0f;

    SearchRequest searchRequest = getSearchRequest("Tee", condition, boost);

    SearchResponse searchResponse =
        getSearchServiceClient().search(searchRequest).getPage().getResponse();

    System.out.println("Search response: " + searchResponse);

    return searchResponse;
  }

  /**
   * Executable tutorial class.
   *
   * @param args command line arguments.
   * @throws IOException from the called method.
   */
  public static void main(final String[] args) throws IOException {
    search();
  }
}

// [END retail_search_product_with_boost_spec]
