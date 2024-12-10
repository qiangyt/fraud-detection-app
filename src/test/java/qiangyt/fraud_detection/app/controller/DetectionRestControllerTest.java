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
package qiangyt.fraud_detection.app.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import qiangyt.fraud_detection.app.service.DetectionService;
import qiangyt.fraud_detection.framework.json.Jackson;
import qiangyt.fraud_detection.framework.misc.UuidHelper;
import qiangyt.fraud_detection.framework.test.AbstractRestTest;
import qiangyt.fraud_detection.sdk.DetectionReq;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.DetectionResult;

/**
 * Test class for {@link DetectionRestController}. It contains test cases for the REST endpoints.
 */
@ContextConfiguration(classes = {DetectionRestController.class})
@WebMvcTest(DetectionRestController.class)
public class DetectionRestControllerTest extends AbstractRestTest {

    @MockitoBean DetectionService service;

    @MockitoBean Jackson jackson;

    /**
     * Test case for the submit endpoint. It verifies that a detection request can be submitted
     * successfully.
     */
    @Test
    void test_submit() {
        // Create a detection request
        var req = DetectionReq.builder().accountId("a").amount(3).build();
        var entity = req.toEntity();

        // Mock the service to return the entity when submit is called
        when(this.service.submit(any())).thenReturn(entity);

        // Perform a POST request and expect an OK response
        postThenExpectOk(req, entity, "/rest/detection");
    }

    /**
     * Test case for the detect endpoint. It verifies that a detection result can be retrieved
     * successfully.
     */
    @Test
    void test_detect() {
        // Generate a short UUID for the detection request entity
        String id = UuidHelper.shortUuid();
        var entity = DetectionReqEntity.builder().id(id).build();
        var result = DetectionResult.builder().entity(entity).build();

        // Mock the service to return the result when detect is called
        when(this.service.detect(any())).thenReturn(result);

        // Perform a GET request and expect an OK response
        getThenExpectOk(entity, result, "/rest/detection");
    }
}
