import com.google.cloud.retail.v2.SearchRequest;
import com.google.cloud.retail.v2.SearchResponse;
import com.google.cloud.retail.v2.SearchServiceClient;
import com.google.cloud.retail.v2.SearchServiceSettings;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SearchServicePaginationNextPageTest {
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
        System.out.println("Search with pagination using page token, request: " + secondRequest);
        SearchResponse response = searchClient.search(secondRequest).getPage().getResponse();

        searchClient.shutdownNow();
        searchClient.awaitTermination(2, TimeUnit.SECONDS);
        System.out.println("Search with pagination using page token, response: " + response);
        return response;
    }
    // [END search_product_page_token]

    @Test
    public void search() throws IOException, InterruptedException {

        searchProducts_withNextPageToken("Nest_Maxi", 10);

    }
}