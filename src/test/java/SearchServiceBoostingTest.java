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
    private static final long YOUR_PROJECT_NUMBER = 00000L;

    private static final String ENDPOINT = "retail.googleapis.com:443";
    private static final String DEFAULT_CATALOG_NAME =
            String.format("projects/%d/locations/global/catalogs/default_catalog", YOUR_PROJECT_NUMBER);
    private static final String DEFAULT_SEARCH_PLACEMENT_NAME =
            DEFAULT_CATALOG_NAME + "/placements/default_search";

    private static final String VISITOR_ID = UUID.randomUUID().toString();

    // [START search_client]
    private static SearchServiceClient getSearchServiceClient() throws IOException {
        SearchServiceSettings settings = SearchServiceSettings.newBuilder()
                .setEndpoint(ENDPOINT)
                .build();
        return SearchServiceClient.create(settings);
    }
    // [END search_client]

    private static final String DEFAULT_BRANCH_NAME =
            DEFAULT_CATALOG_NAME + "/branches/default_branch";

    // [START search_product_boost]
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
                .setPlacement(DEFAULT_SEARCH_PLACEMENT_NAME)
                .setBranch(DEFAULT_BRANCH_NAME)
                .setVisitorId(VISITOR_ID)
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
    // [END search_product_boost]

    @Test
    public void search() throws IOException, InterruptedException {

        searchProductsWithBoostSpec("Nest", 10, "(colorFamily: ANY(\"grey\"))", 0.5f);

        List<String> variantRollupKeys = Lists
                .newArrayList("colorFamily", "pickupInStore.store123");
    }
}