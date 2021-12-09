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
 */

package search;

import com.google.cloud.retail.v2.SearchResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class SearchSimpleQueryTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  @Test
  public void outputTest() throws IOException, InterruptedException {

    System.out.print(SearchSimpleQuery.search().toString());

    Assert.assertTrue(outContent.toString().contains("Search request:"));

    Assert.assertTrue(outContent.toString().contains("Search response:"));

    Assert.assertTrue(outContent.toString().contains("results {\n" + "  id:"));
  }

  @Test
  public void testSearchSimpleQuery() throws IOException, InterruptedException {

    SearchResponse response = SearchSimpleQuery.search();

    Assert.assertEquals(10, response.getResultsCount());

    String productTitle = response.getResults(0).getProduct().getTitle();

    Assert.assertTrue(productTitle.contains("Hoodie"));
  }

}
