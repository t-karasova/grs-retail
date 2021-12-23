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

import static events.setup.SetupCleanup.createBucket;
import static events.setup.SetupCleanup.uploadObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class EventsCreateGcsBucket {

  public static void main(String[] args) throws IOException {

    String PROJECT_ID = System.getenv("PROJECT_ID");

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    Timestamp timestamp = Timestamp.from(Instant.now());

    String bucketName = String.format("%s_%s", PROJECT_ID,
        dateFormat.format(timestamp));

    createBucket(bucketName);

    uploadObject(bucketName, "user_events.json",
        "src/main/resources/user_events.json");

    uploadObject(bucketName, "user_events_some_invalid.json",
        "src/main/resources/user_events_some_invalid.json");

    System.out.printf("%nThe gcs bucket %s was created.%n", bucketName);
  }
}
