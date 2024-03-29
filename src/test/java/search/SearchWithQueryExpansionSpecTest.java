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

package search;

import com.google.cloud.retail.v2.SearchResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.StreamGobbler;

public class SearchWithQueryExpansionSpecTest {

  private String output;

  @Before
  public void setUp() throws IOException, InterruptedException, ExecutionException {

    Process exec =
        Runtime.getRuntime()
            .exec("mvn compile exec:java -Dexec.mainClass=search.SearchWithQueryExpansionSpec");

    StreamGobbler streamGobbler = new StreamGobbler(exec.getInputStream());

    Future<String> stringFuture = Executors.newSingleThreadExecutor().submit(streamGobbler);

    output = stringFuture.get();
  }

  @Test
  public void testOutput() {

    Assert.assertTrue(output.matches("(?s)^(.*Search request.*)$"));

    Assert.assertTrue(output.matches("(?s)^(.*Search response.*)$"));

    Assert.assertTrue(output.matches("(?s)^(.*results.*id.*)$"));
  }

  @Test
  public void testSearchWithQueryExpansionSpec() throws IOException {

    SearchResponse response = SearchWithQueryExpansionSpec.search();

    Assert.assertEquals(10, response.getResultsCount());

    Assert.assertTrue(
        response.getResults(0).getProduct().getTitle().contains("Google Youth Hero Tee Grey"));

    Assert.assertFalse(
        response.getResults(2).getProduct().getTitle().contains("Google Youth Hero Tee Grey"));

    Assert.assertTrue(response.getQueryExpansionInfo().getExpandedQuery());
  }
}
