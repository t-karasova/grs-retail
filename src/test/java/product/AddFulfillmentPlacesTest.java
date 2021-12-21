/*
 * Copyright 2021 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * [START add_remove_fulfillment_places]
 */

package product;

import static product.AddFulfillmentPlaces.DEFAULT_CATALOG;
import static product.AddFulfillmentPlaces.getAddFulfillmentRequest;

import com.google.cloud.retail.v2.AddFulfillmentPlacesRequest;

import org.junit.Assert;
import org.junit.Test;

public class AddFulfillmentPlacesTest {

  private static final String productName = "GGOEAAEC172013";

  @Test
  public void getAddFulfillmentRequestTest() {
    AddFulfillmentPlacesRequest fulfillmentRequest = getAddFulfillmentRequest(
        DEFAULT_CATALOG + productName);

    Assert.assertEquals(1, fulfillmentRequest.getPlaceIdsCount());

    Assert.assertEquals("pickup-in-store", fulfillmentRequest.getType());

    Assert.assertTrue(fulfillmentRequest.getAllowMissing());
  }

}
