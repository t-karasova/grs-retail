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
 */

package product.setup;

import static product.setup.SetupCleanup.createBqDataset;
import static product.setup.SetupCleanup.createBqTable;
import static product.setup.SetupCleanup.uploadDataToBqTable;

import com.google.cloud.bigquery.Field;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.StandardSQLTypeName;

public class ProductsCreateBigqueryTable {

  public static void main(String[] args) {
    String dataset = "products";
    String validProductsTable = "products";
    String invalidProductsTable = "products_some_invalid";
    Schema productSchema = Schema.of(Field.of("resources/product_schema.json",
        StandardSQLTypeName.STRING));
    String validProductsSourceFile = "resources/products.json";
    String invalidProductsSourceFile = "resources/products_some_invalid.json";

    createBqDataset(dataset);

    createBqTable(dataset, validProductsTable, productSchema);

    uploadDataToBqTable(dataset, validProductsTable, validProductsSourceFile,
        productSchema);

    createBqTable(dataset, invalidProductsTable, productSchema);

    uploadDataToBqTable(dataset, invalidProductsTable,
        invalidProductsSourceFile, productSchema);
  }
}
