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
package qiangyt.fraud_detection.framework.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import qiangyt.fraud_detection.framework.json.Jackson;
import qiangyt.fraud_detection.framework.rest.RestConfig;

/**
 * Base class for Controller unit tests, encapsulating some basic usage methods for MockMVC.
 *
 * <p>Purposes of Controller unit tests: 1）Validate the correctness of URLs (including URL
 * parameters). 2）Validate JSON serialization and deserialization. 3）Validate the correctness of
 * HTTP status codes. 4）Validate the passing of parameters/results between controller and service.
 *
 * <p>Note: Although parameter validation is currently implemented at the controller level, it will
 * be moved to the service layer in the future, so it is not covered in these controller-level
 * tests.
 */
@Disabled
@Execution(ExecutionMode.SAME_THREAD) // Controller tests do not support parallel execution
@Import({RestConfig.class})
public class AbstractRestTest {

    @Autowired protected MockMvc mockMvc;

    public Jackson getJackson() {
        return Jackson.DEFAULT;
    }

    /**
     * Verify that the API did not encounter an error and that the returned response body (JSON
     * only) matches the expected value.
     *
     * @param expectedResponseContent - The expected response content, can be null
     * @return
     */
    public ResultActions expectOk(ResultActions actions, Object expectedResponseContent) {
        try {
            var ra = actions.andExpect(status().isOk());
            if (expectedResponseContent == null) {
                return ra; // TBD: should we turn to return HTTP 204 (No Content)?
            }
            return ra.andExpect(content().string(getJackson().str(expectedResponseContent)));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Create a GET request.
     *
     * @param requestContent - POST body (will be serialized into JSON)
     * @param urlTemplate - URL template (can use "{parameter}" as a placeholder for parameters)
     * @param uriVars - Parameter values corresponding to placeholders in the URL template
     */
    public MockHttpServletRequestBuilder GET(
            Object requestContent, String urlTemplate, Object... uriVars) {
        if (!urlTemplate.startsWith("/")) {
            urlTemplate = "/" + urlTemplate;
        }

        return MockMvcRequestBuilders.get(urlTemplate, uriVars)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getJackson().str(requestContent));
    }

    /**
     * Create a POST request.
     *
     * @param requestContent - POST body (will be serialized into JSON)
     * @param urlTemplate - URL template (can use "{parameter}" as a placeholder for parameters)
     * @param uriVars - Parameter values corresponding to placeholders in the URL template
     */
    public MockHttpServletRequestBuilder POST(
            Object requestContent, String urlTemplate, Object... uriVars) {
        if (!urlTemplate.startsWith("/")) {
            urlTemplate = "/" + urlTemplate;
        }

        return MockMvcRequestBuilders.post(urlTemplate, uriVars)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getJackson().str(requestContent));
    }

    /**
     * Send a GET request, then verify that the API did not encounter an error and that the returned
     * response body (JSON only) matches the expected value.
     *
     * @param expectedResponseContent - The expected response content, can be null
     * @param urlTemplate - URL template (can use "{parameter}" as a placeholder for parameters)
     * @param uriVars - Parameter values corresponding to placeholders in the URL template
     */
    public ResultActions getThenExpectOk(
            Object requestContent,
            Object expectedResponseContent,
            String urlTemplate,
            Object... uriVars) {
        try {
            var req = GET(requestContent, urlTemplate, uriVars);
            var actions = this.mockMvc.perform(req);
            return expectOk(actions, expectedResponseContent);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Send a POST request, then verify that the API did not encounter an error and that the
     * returned response body (JSON only) matches the expected value.
     *
     * @param requestContent - POST body (will be serialized into JSON)
     * @param expectedResponseContent - The expected response content, can be null
     * @param urlTemplate - URL template (can use "{parameter}" as a placeholder for parameters)
     * @param uriVars - Parameter values corresponding to placeholders in the URL template
     */
    public ResultActions postThenExpectOk(
            Object requestContent,
            Object expectedResponseContent,
            String urlTemplate,
            Object... uriVars) {
        try {
            var req = POST(requestContent, urlTemplate, uriVars);
            var actions = this.mockMvc.perform(req);
            return expectOk(actions, expectedResponseContent);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
