import com.google.cloud.retail.v2.Interval;
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

public class SearchServiceFacetsRestrictedValuesTest {
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
    System.out.println("Search and return textual facet with restricted values, request: " + searchRequest);
    SearchResponse response = searchClient.search(searchRequest).getPage().getResponse();

    searchClient.shutdownNow();
    searchClient.awaitTermination(2, TimeUnit.SECONDS);
    System.out.println("Search and return textual facet with restricted values, response: " + response);
    return response;
  }
  // [END search_product_textualFacet_withRestrictedValues]

  @Test
  public void search() throws IOException, InterruptedException {

    List<String> restrictedValues = Lists.newArrayList("store123", "store456");

    searchProductsWithTextualFacet_restrictedValues("Nest_Maxi", "pickupInStore",
        restrictedValues
    );

  }
}