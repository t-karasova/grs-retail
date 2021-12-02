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
 * [START retail_search_for_products_with_ordering]
 * Call Retail API to search for a products in a catalog, order the results by different product fields.
 */

package search;

import com.google.cloud.retail.v2.SearchResponse;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

public class SearchWithOrderingTest {

  @Test
  public void TestSearchWithOrdering() throws IOException, InterruptedException {

    SearchResponse response = SearchWithOrdering.search();

    Assert.assertEquals(10, response.getResultsCount());

    String productTitle = response.getResults(0).getProduct().getTitle();

    Assert.assertTrue(productTitle.contains("Hoodie")); // todo : fix fail test

    Assert.assertEquals(39, response.getResults(0).getProduct().getPriceInfo().getPrice(), 0);

    Assert.assertEquals(51, response.getTotalSize()); // todo : fix fail test
  }
}

// [END retail_search_for_products_with_ordering]