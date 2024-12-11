package qiangyt.fraud_detection.app.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DetectionRestController.class)
public class DetectionRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DetectionService service;

    @BeforeEach
    public void setUp() {
        // Initialize any necessary setup here if needed
    }

    /**
     * Test case for submitting a detection request asynchronously.
     *
     * <p>Steps:
     * 1. Create a valid DetectionReq object.
     * 2. Mock the submit method of DetectionService to return a mock DetectionReqEntity.
     * 3. Perform a POST request with the DetectionReq object as the body.
     * 4. Verify that the submit method of DetectionService is called once with the correct argument.
     * 5. Verify that the response status is OK and the returned entity matches the expected result.
     */
    @Test
    public void testSubmitAsync() throws Exception {
        DetectionReq req = new DetectionReq();
        DetectionReqEntity mockEntity = new DetectionReqEntity();

        when(service.submit(req)).thenReturn(mockEntity);

        mockMvc.perform(post("/rest/detection")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"value\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockEntity.getId()))
                .andExpect(jsonPath("$.status").value(mockEntity.getStatus()));

        verify(service, times(1)).submit(req);
    }

    /**
     * Test case for submitting a detection request synchronously.
     *
     * <p>Steps:
     * 1. Create a valid DetectionReqEntity object.
     * 2. Mock the detect method of DetectionService to return a mock DetectionResult.
     * 3. Perform a GET request with the DetectionReqEntity object as the body.
     * 4. Verify that the detect method of DetectionService is called once with the correct argument.
     * 5. Verify that the response status is OK and the returned result matches the expected result.
     */
    @Test
    public void testDetectSync() throws Exception {
        DetectionReqEntity entity = new DetectionReqEntity();
        DetectionResult mockResult = new DetectionResult();

        when(service.detect(entity)).thenReturn(mockResult);

        mockMvc.perform(get("/rest/detection")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"value\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockResult.getId()))
                .andExpect(jsonPath("$.result").value(mockResult.getResult()));

        verify(service, times(1)).detect(entity);
    }

    /**
     * Test case for submitting an invalid detection request asynchronously.
     *
     * <p>Steps:
     * 1. Create an invalid DetectionReq object (e.g., missing required fields).
     * 2. Perform a POST request with the invalid DetectionReq object as the body.
     * 3. Verify that the response status is BAD_REQUEST and the returned error message matches the expected result.
     */
    @Test
    public void testSubmitAsyncInvalidRequest() throws Exception {
        DetectionReq req = new DetectionReq(); // Invalid request (e.g., missing required fields)

        mockMvc.perform(post("/rest/detection")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"value\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request"));
    }

    /**
     * Test case for submitting an invalid detection request synchronously.
     *
     * <p>Steps:
     * 1. Create an invalid DetectionReqEntity object (e.g., missing required fields).
     * 2. Perform a GET request with the invalid DetectionReqEntity object as the body.
     * 3. Verify that the response status is BAD_REQUEST and the returned error message matches the expected result.
     */
    @Test
    public void testDetectSyncInvalidRequest() throws Exception {
        DetectionReqEntity entity = new DetectionReqEntity(); // Invalid request (e.g., missing required fields)

        mockMvc.perform(get("/rest/detection")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"value\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request"));
    }
}
