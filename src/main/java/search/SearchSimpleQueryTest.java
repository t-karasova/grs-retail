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
 * [START retail_search_for_products_with_query_parameter]
 * Call Retail API to search for a products in a catalog using only search query.
 */

package search;

import static search.SearchSimpleQuery.search;

import com.google.cloud.retail.v2.SearchResponse;
import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;

public class SearchSimpleQueryTest {

  @Test
  public void testSearchSimpleQuery() throws IOException, InterruptedException {

    SearchResponse response = search();

    Assert.assertEquals(10, response.getResultsCount());

    String productTitle = response.getResults(0).getProduct().getTitle();

    Assert.assertTrue(productTitle.contains("Hoodie"));

    Assert.assertEquals(51, response.getTotalSize()); // todo : fix fail test
  }

}

// [END retail_search_for_products_with_query_parameter]