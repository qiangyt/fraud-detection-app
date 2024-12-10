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
package qiangyt.fraud_detection.client.v1.client.rest;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import qiangyt.fraud_detection.framework.json.Jackson;
import qiangyt.fraud_detection.framework.rest.ApiClient;
import qiangyt.fraud_detection.framework.rest.ApiClientErrorHandler;
import qiangyt.fraud_detection.sdk.DetectionApi;
import qiangyt.fraud_detection.sdk.DetectionReq;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.DetectionResult;

@lombok.Getter
@lombok.Setter
@Component
public class AppClient implements DetectionApi {

    @Autowired DetectionApiClient detectionApiClient;

    @Autowired ApiClientErrorHandler errorHandler;

    @Autowired Jackson jackson;

    final String baseUrl;

    @lombok.Setter(lombok.AccessLevel.PRIVATE)
    boolean inited;

    public AppClient(@Value("${app.base-url:http://localhost:8080}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    void prepareClient(ApiClient c) {
        c.setJackson(getJackson());
        c.setErrorHandler(getErrorHandler());
        c.setBaseUrl(getBaseUrl());
    }

    @PostConstruct
    public void init() {
        if (getJackson() == null) {
            setJackson(Jackson.DEFAULT);
        }

        if (getErrorHandler() == null) {
            setErrorHandler(new ApiClientErrorHandler(getJackson()));
        }

        var tc = getDetectionApiClient();
        if (tc == null) {
            tc = new DetectionApiClient();
            prepareClient(tc);
            setDetectionApiClient(tc);
        }
        tc.init();

        setInited(true);
    }

    @Override
    public DetectionReqEntity submit(DetectionReq req) {
        return getDetectionApiClient().submit(req);
    }

    @Override
    public DetectionResult detect(DetectionReqEntity entity) {
        return getDetectionApiClient().detect(entity);
    }
}
