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

package events.setup;

import static events.setup.SetupCleanup.createBqDataset;
import static events.setup.SetupCleanup.createBqTable;
import static events.setup.SetupCleanup.uploadDataToBqTable;

import com.google.cloud.bigquery.Field;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.StandardSQLTypeName;

public class EventsCreateBigQueryTable {

  public static void main(String[] args) {
    String dataset = "user_events";
    String validEventsTable = "events";
    String invalidEventsTable = "events_some_invalid";
    String eventsSchemaFile = "resources/event_schema.json";
    String validEventsSourceFile = "resources/user_events.json";
    String invalidEventsSourceFile = "resources/user_events_some_invalid.json";

    Schema eventsSchema = Schema.of(
        Field.of(eventsSchemaFile, StandardSQLTypeName.STRING));

    createBqDataset(dataset);

    createBqTable(dataset, validEventsTable, eventsSchema);

    uploadDataToBqTable(dataset, validEventsTable, validEventsSourceFile,
        eventsSchema);

    createBqTable(dataset, invalidEventsTable, eventsSchema);

    uploadDataToBqTable(dataset, invalidEventsTable, invalidEventsSourceFile,
        eventsSchema);
  }
}
