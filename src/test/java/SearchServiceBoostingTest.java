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
 * [START retail_search_product_with_boost_spec]
 * Call Retail API to search for a products in a catalog, rerank the
 * results boosting or burying the products that match defined condition.
 */

import com.google.cloud.retail.v2.SearchRequest;
import com.google.cloud.retail.v2.SearchRequest.BoostSpec;
import com.google.cloud.retail.v2.SearchRequest.BoostSpec.ConditionBoostSpec;
import com.google.cloud.retail.v2.SearchResponse;
import com.google.cloud.retail.v2.SearchServiceClient;
import com.google.cloud.retail.v2.SearchServiceSettings;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SearchServiceBoostingTest {
    //TODO(developers) change the values of following variables to use proper project
    private static final long YOUR_PROJECT_NUMBER = 1038874412926L;

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

    private static final String DEFAULT_BRANCH_NAME =
            DEFAULT_CATALOG_NAME + "/branches/default_branch";

    // get search service request
    public static SearchResponse searchProductsWithBoostSpec(String query, int pageSize,
                                                             String condition, float boostStrength) throws IOException, InterruptedException {
        SearchServiceClient searchClient = getSearchServiceClient();

        BoostSpec boostSpec = BoostSpec.newBuilder()
                .addConditionBoostSpecs(ConditionBoostSpec.newBuilder()
                                                .setCondition(condition)
                                                .setBoost(boostStrength)
                                                .build())
                .build();

        SearchRequest searchRequest = SearchRequest.newBuilder()
                .setPlacement(DEFAULT_SEARCH_PLACEMENT_NAME) // Placement is used to identify the Serving Config name.
                .setBranch(DEFAULT_BRANCH_NAME)
                .setVisitorId(VISITOR_ID) // A unique identifier to track visitors
                .setQuery(query)
                .setPageSize(pageSize)
                .setBoostSpec(boostSpec)
                .build();
        System.out.println("Search with boosting specification, request: " + searchRequest);
        SearchResponse response = searchClient.search(searchRequest).getPage().getResponse();

        searchClient.shutdownNow();
        searchClient.awaitTermination(2, TimeUnit.SECONDS);
        System.out.println("Search with boosting specification, response: " + response);
        return response;
    }

    // call the Retail Search:
    @Test
    public void search() throws IOException, InterruptedException {
        // TRY DIFFERENT CONDITIONS HERE:
        searchProductsWithBoostSpec("Nest", 10, "(colorFamily: ANY(\"grey\"))", 0.5f);

        List<String> variantRollupKeys = Lists
                .newArrayList("colorFamily", "pickupInStore.store123");
    }
}

// [END retail_search_product_with_boost_spec]
