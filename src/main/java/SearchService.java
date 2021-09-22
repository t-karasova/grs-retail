import com.google.cloud.retail.v2.Interval;
import com.google.cloud.retail.v2.SearchRequest;
import com.google.cloud.retail.v2.SearchRequest.BoostSpec;
import com.google.cloud.retail.v2.SearchRequest.BoostSpec.ConditionBoostSpec;
import com.google.cloud.retail.v2.SearchRequest.FacetSpec;
import com.google.cloud.retail.v2.SearchRequest.FacetSpec.FacetKey;
import com.google.cloud.retail.v2.SearchRequest.QueryExpansionSpec;
import com.google.cloud.retail.v2.SearchRequest.QueryExpansionSpec.Condition;
import com.google.cloud.retail.v2.SearchResponse;
import com.google.cloud.retail.v2.SearchServiceClient;
import com.google.cloud.retail.v2.SearchServiceSettings;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class SearchService {
    private static final long YOUR_PROJECT_NUMBER = 1038874412926L;
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

    // [START search_product]
    public static SearchResponse searchProducts(String query) throws IOException, InterruptedException {
        SearchServiceClient searchClient = getSearchServiceClient();

        SearchRequest searchRequest = SearchRequest.newBuilder()
                .setPlacement(DEFAULT_SEARCH_PLACEMENT_NAME)
                .setBranch(DEFAULT_BRANCH_NAME)
                .setVisitorId(VISITOR_ID)
                .setQuery(query)
                .build();

        SearchResponse response = searchClient.search(searchRequest).getPage().getResponse();

        searchClient.shutdownNow();
        searchClient.awaitTermination(2, TimeUnit.SECONDS);

        System.out.println(response);
        return response;
    }
    // [END search_product]

    // [START search_product_filter]
    public static SearchResponse searchFilteredProducts(String query, int pageSize,
                                                        String filter) throws IOException, InterruptedException {
        SearchServiceClient searchClient = getSearchServiceClient();

        SearchRequest searchRequest = SearchRequest.newBuilder()
                .setPlacement(DEFAULT_SEARCH_PLACEMENT_NAME)
                .setBranch(DEFAULT_BRANCH_NAME)
                .setVisitorId(VISITOR_ID)
                .setQuery(query)
                .setPageSize(pageSize)
                .setFilter(filter)
                .build();

        SearchResponse response = searchClient.search(searchRequest).getPage().getResponse();

        searchClient.shutdownNow();
        searchClient.awaitTermination(2, TimeUnit.SECONDS);

        return response;
    }
    // [END search_product_filter]

    // [START search_product_order]
    public static SearchResponse searchOrderedProducts(String query, int pageSize,
                                                       String orderBy) throws IOException, InterruptedException {
        SearchServiceClient searchClient = getSearchServiceClient();

        SearchRequest searchRequest = SearchRequest.newBuilder()
                .setPlacement(DEFAULT_SEARCH_PLACEMENT_NAME)
                .setBranch(DEFAULT_BRANCH_NAME)
                .setVisitorId(VISITOR_ID)
                .setQuery(query)
                .setPageSize(pageSize)
                .setOrderBy(orderBy)
                .build();

        SearchResponse response = searchClient.search(searchRequest).getPage().getResponse();

        searchClient.shutdownNow();
        searchClient.awaitTermination(2, TimeUnit.SECONDS);

        return response;
    }
    // [END search_product_order]

    // [START search_product_page_token]
    public static SearchResponse searchProducts_withNextPageToken(String query, int pageSize)
            throws IOException, InterruptedException {
        SearchServiceClient searchClient = getSearchServiceClient();

        SearchRequest firstRequest = SearchRequest.newBuilder()
                .setPlacement(DEFAULT_SEARCH_PLACEMENT_NAME)
                .setBranch(DEFAULT_BRANCH_NAME)
                .setVisitorId(VISITOR_ID)
                .setQuery(query)
                .setPageSize(pageSize)
                .build();
        SearchResponse firstResponse = searchClient.search(firstRequest).getPage()
                .getResponse();

        SearchRequest secondRequest = SearchRequest.newBuilder()
                .setPlacement(DEFAULT_SEARCH_PLACEMENT_NAME)
                .setBranch(DEFAULT_BRANCH_NAME)
                .setVisitorId(VISITOR_ID)
                .setQuery(query)
                .setPageSize(pageSize)
                .setPageToken(firstResponse.getNextPageToken())
                .build();

        SearchResponse response = searchClient.search(secondRequest).getPage().getResponse();

        searchClient.shutdownNow();
        searchClient.awaitTermination(2, TimeUnit.SECONDS);

        return response;
    }
    // [END search_product_page_token]

    // [START search_product_page_offset]
    public static SearchResponse searchProducts_withOffset(String query, int pageSize,
                                                           int offset) throws IOException, InterruptedException {
        SearchServiceClient searchClient = getSearchServiceClient();

        SearchRequest searchRequest = SearchRequest.newBuilder()
                .setPlacement(DEFAULT_SEARCH_PLACEMENT_NAME)
                .setBranch(DEFAULT_BRANCH_NAME)
                .setVisitorId(VISITOR_ID)
                .setQuery(query)
                .setPageSize(pageSize)
                .setOffset(offset)
                .build();

        SearchResponse response = searchClient.search(searchRequest).getPage().getResponse();

        searchClient.shutdownNow();
        searchClient.awaitTermination(2, TimeUnit.SECONDS);

        return response;
    }
    // [END search_product_page_offset]

    // [START search_product_textualFacet]
    public static SearchResponse searchProductsWithTextualFacet(String query, String key,
                                                                String orderBy) throws IOException, InterruptedException {
        SearchServiceClient searchClient = getSearchServiceClient();

        FacetKey facetKey = FacetKey.newBuilder()
                .setKey(key)
                .setOrderBy(orderBy)
                .build();
        FacetSpec facetSpec = FacetSpec.newBuilder()
                .setFacetKey(facetKey)
                .build();
        SearchRequest searchRequest = SearchRequest.newBuilder()
                .setPlacement(DEFAULT_SEARCH_PLACEMENT_NAME)
                .setBranch(DEFAULT_BRANCH_NAME)
                .setVisitorId(VISITOR_ID)
                .setQuery(query)
                .addFacetSpecs(facetSpec)
                .build();

        SearchResponse response = searchClient.search(searchRequest).getPage().getResponse();

        searchClient.shutdownNow();
        searchClient.awaitTermination(2, TimeUnit.SECONDS);

        return response;
    }
    // [END search_product_textualFacet]

    // [START search_product_numericalFacet]
    public static SearchResponse searchProductsWithNumericalFacet(String query, String key,
                                                                  List<Interval> intervals, String orderBy) throws IOException, InterruptedException {
        SearchServiceClient searchClient = getSearchServiceClient();

        FacetKey facetKey = FacetKey.newBuilder()
                .setKey(key)
                .addAllIntervals(intervals)
                .setOrderBy(orderBy)
                .build();
        FacetSpec facetSpec = FacetSpec.newBuilder()
                .setFacetKey(facetKey)
                .build();
        SearchRequest searchRequest = SearchRequest.newBuilder()
                .setPlacement(DEFAULT_SEARCH_PLACEMENT_NAME)
                .setBranch(DEFAULT_BRANCH_NAME)
                .setVisitorId(VISITOR_ID)
                .setQuery(query)
                .addFacetSpecs(facetSpec)
                .build();

        SearchResponse response = searchClient.search(searchRequest).getPage().getResponse();

        searchClient.shutdownNow();
        searchClient.awaitTermination(2, TimeUnit.SECONDS);

        return response;
    }
    // [END search_product_numericalFacet]

    // [START search_product_textualFacet_withRestrictedValues]
    public static SearchResponse searchProductsWithTextualFacet_restrictedValues(String query,
                                                                                 String key, List<String> restrictedValues) throws IOException, InterruptedException {
        SearchServiceClient searchClient = getSearchServiceClient();

        FacetKey facetKey = FacetKey.newBuilder()
                .setKey(key)
                .addAllRestrictedValues(restrictedValues)
                .build();
        FacetSpec facetSpec = FacetSpec.newBuilder()
                .setFacetKey(facetKey)
                .build();
        SearchRequest searchRequest = SearchRequest.newBuilder()
                .setPlacement(DEFAULT_SEARCH_PLACEMENT_NAME)
                .setBranch(DEFAULT_BRANCH_NAME)
                .setVisitorId(VISITOR_ID)
                .setQuery(query)
                .addFacetSpecs(facetSpec)
                .build();

        SearchResponse response = searchClient.search(searchRequest).getPage().getResponse();

        searchClient.shutdownNow();
        searchClient.awaitTermination(2, TimeUnit.SECONDS);

        return response;
    }
    // [END search_product_textualFacet_withRestrictedValues]

    // [START search_product_textualFacet_withExcludedFilterKeys]
    public static SearchResponse searchProductsWithTextualFacet_excludedFilterKeys(String query,
                                                                                   String key, String filter, List<String> excludedFilterKeys)
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
                .setPlacement(DEFAULT_SEARCH_PLACEMENT_NAME)
                .setBranch(DEFAULT_BRANCH_NAME)
                .setVisitorId(VISITOR_ID)
                .setQuery(query)
                .setFilter(filter)
                .addFacetSpecs(facetSpec)
                .build();

        SearchResponse response = searchClient.search(searchRequest).getPage().getResponse();

        searchClient.shutdownNow();
        searchClient.awaitTermination(2, TimeUnit.SECONDS);

        return response;
    }
    // [END search_product_textualFacet_withExcludedFilterKeys]

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

        SearchResponse response = searchClient.search(searchRequest).getPage().getResponse();

        searchClient.shutdownNow();
        searchClient.awaitTermination(2, TimeUnit.SECONDS);
        return response;

    }
    // [END search_product_boost]

    // [START search_product_variantRollupKeys]
    public static SearchResponse searchProductsWithVariantRollupKeys(String query, int pageSize,
                                                                     List<String> rollupKeys) throws IOException, InterruptedException {
        SearchServiceClient searchClient = getSearchServiceClient();

        SearchRequest searchRequest = SearchRequest.newBuilder()
                .setPlacement(DEFAULT_SEARCH_PLACEMENT_NAME)
                .setBranch(DEFAULT_BRANCH_NAME)
                .setVisitorId(VISITOR_ID)
                .setQuery(query)
                .setPageSize(pageSize)
                .addAllVariantRollupKeys(rollupKeys)
                .build();

        SearchResponse response = searchClient.search(searchRequest).getPage().getResponse();

        searchClient.shutdownNow();
        searchClient.awaitTermination(2, TimeUnit.SECONDS);

        return response;
    }
    // [END search_product_variantRollupKeys]

    // [START search_product_queryExpansion]
    public static SearchResponse searchProductsWithQueryExpansion(String query, int pageSize,
                                                                  Condition condition) throws IOException, InterruptedException {
        SearchServiceClient searchClient = getSearchServiceClient();

        QueryExpansionSpec queryExpansionSpec = QueryExpansionSpec.newBuilder()
                .setCondition(condition)
                .build();

        SearchRequest searchRequest = SearchRequest.newBuilder()
                .setPlacement(DEFAULT_SEARCH_PLACEMENT_NAME)
                .setBranch(DEFAULT_BRANCH_NAME)
                .setVisitorId(VISITOR_ID)
                .setQuery(query)
                .setPageSize(pageSize)
                .setQueryExpansionSpec(queryExpansionSpec)
                .build();

        SearchResponse response = searchClient.search(searchRequest).getPage().getResponse();

        searchClient.shutdownNow();
        searchClient.awaitTermination(2, TimeUnit.SECONDS);

        return response;
    }
    // [END search_product_queryExpansion]

    @Test
    public void search() throws IOException, InterruptedException {

        SearchResponse searchResponse;

        searchResponse = searchProducts("Nest");

        searchResponse = searchOrderedProducts("Nest", 10, "price desc");

        searchResponse = searchFilteredProducts("Nest", 10,
                                                "(colorFamily: ANY(\"grey\")) AND (price>=100)");

        searchResponse = searchProducts_withNextPageToken("Nest", 10);

        searchResponse = searchProducts_withOffset("Nest", 10, 5);

        List<Interval> intervals = Lists.newArrayList(Interval.newBuilder()
                                                              .setMinimum(10.0)
                                                              .setExclusiveMaximum(20.0)
                                                              .build());

        searchResponse = searchProductsWithNumericalFacet("Nest", "price", intervals, "count desc");

        searchResponse = searchProductsWithTextualFacet("Nest", "colorFamily", "value desc");

        List<String> restrictedValues = Lists.newArrayList("store123", "store456");

        searchResponse = searchProductsWithTextualFacet_restrictedValues("Nest", "pickupInStore",
                                                                         restrictedValues);

        List<String> excludedFilterKeys = Lists.newArrayList("colorFamily");

        searchResponse = searchProductsWithTextualFacet_excludedFilterKeys("Nest", "colorFamily",
                                                                           "(colorFamily: ANY(\"grey\"))", excludedFilterKeys);

        searchResponse = searchProductsWithBoostSpec("Nest", 10, "(colorFamily: ANY(\"grey\"))", 0.5f);

        List<String> variantRollupKeys = Lists
                .newArrayList("colorFamily", "pickupInStore.store123");

        searchResponse = searchProductsWithVariantRollupKeys("Nest", 10, variantRollupKeys);

        searchResponse = searchProductsWithQueryExpansion("Nest", 10, Condition.AUTO);
    }
}