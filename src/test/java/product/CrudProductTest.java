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

package product;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.StreamGobbler;

public class CrudProductTest {

  private String output;

  @Before
  public void setUp()
      throws IOException, InterruptedException, ExecutionException {

    Process exec = Runtime.getRuntime()
        .exec(
            "mvn compile exec:java -Dexec.mainClass=product.CrudProduct");

    StreamGobbler streamGobbler = new StreamGobbler(exec.getInputStream());

    Future<String> stringFuture = Executors.newSingleThreadExecutor()
        .submit(streamGobbler);

    output = stringFuture.get();
  }

  @Test
  public void testCrudProduct() {
    Assert.assertTrue(output.matches("(?s)^(.*Create product request.*)$"));

    Assert.assertTrue(output.matches(
        "(?s)^(.*Create product request.*?parent: \"projects/.*?/locations/global/catalogs/default_catalog/branches/default_branch\".*)$"));

    Assert.assertTrue(output.matches(
        "(?s)^(.*Create product request.*?title: \"Nest Mini\".*)$"));

    Assert.assertTrue(output.matches("(?s)^(.*Created product.*)$"));

    Assert.assertTrue(output.matches(
        "(?s)^(.*Created product.*?id:.*)$"));

    Assert.assertTrue(
        output.matches("(?s)^(.*Created product.*?title: \"Nest Mini\".*)$"));

    Assert.assertTrue(output.matches(
        "(?s)^(.*Get product request.*?name: \"projects/.*?/locations/global/catalogs/default_catalog/branches/default_branch/products/.*)$"));

    Assert.assertTrue(output.matches(
        "(?s)^(.*Get product response.*?name: \"projects/.*?/locations/global/catalogs/default_catalog/branches/default_branch/products/.*)$"));

    Assert.assertTrue(output.matches("(?s)^(.*Update product request.*)$"));

    Assert.assertTrue(output.matches(
        "(?s)^(.*Update product request.*?name: \"projects/.*?/locations/global/catalogs/default_catalog/branches/default_branch/products/.*)$"));

    Assert.assertTrue(output.matches(
        "(?s)^(.*Update product request.*?title: \"Updated Nest Mini\".*)$"));

    Assert.assertTrue(output.matches(
        "(?s)^(.*Updated product.*?title.*?Updated Nest Mini.*)$"));

    Assert.assertTrue(output.matches(
        "(?s)^(.*Updated product.*?brands.*?Updated Google.*)$"));

    Assert.assertTrue(
        output.matches("(?s)^(.*Updated product.*?price.*?20.*)$"));

    Assert.assertTrue(output.matches(
        "(?s)^(.*Delete product request.*?name: \"projects/.*?/locations/global/catalogs/default_catalog/branches/default_branch/products/.*)$"));

    Assert.assertTrue(output.matches("(?s)^(.*Product was deleted.*)$"));
  }
}
