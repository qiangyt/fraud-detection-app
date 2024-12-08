/*
 * fraud-detection-app - fraud detection app
 * Copyright © 2024 Yiting Qiang (qiangyt@wxcount.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package qiangyt.fraud_detection;

import org.junit.jupiter.api.Test;

import qiangyt.fraud_detection.client.v1.client.rest.AppClient;
import qiangyt.fraud_detection.framework.json.JacksonHelper;
import qiangyt.fraud_detection.sdk.DetectionReq;

public class HelloIT {

    public static final String BASE_URL = "http://localhost:8080";

    private static final AppClient client;
    static {
        client = new AppClient(BASE_URL);
        client.init();
    }


    @Test
    public void test() {
        var req = DetectionReq.builder()
                    .accountId("integration-test-account-1")
                    .amount(99999999)
                    .memo("N/A")
                    .build();
        var entity = client.submit(req);
        dump("entity: ", entity);
    }

    static int i = 0;

    void dump(String hint, Object obj) {
        System.out.printf("\n%02d. %s:\n%s\n", ++i, hint, JacksonHelper.pretty(obj));
    }
}