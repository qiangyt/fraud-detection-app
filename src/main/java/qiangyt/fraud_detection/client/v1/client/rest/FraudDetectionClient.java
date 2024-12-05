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
import qiangyt.fraud_detection.framework.json.Jackson;
import qiangyt.fraud_detection.framework.rest.ApiClient;
import qiangyt.fraud_detection.framework.rest.ApiClientErrorHandler;
import qiangyt.fraud_detection.sdk.api.TenantAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@lombok.Getter
@lombok.Setter
@Component
public class FraudDetectionClient
        implements TenantAPI {

    @Autowired TenantClient tenantClient;

    @Autowired ApiClientErrorHandler clientErrorHandler;

    @Autowired Jackson jackson;

    final String baseUrl;

    @lombok.Setter(lombok.AccessLevel.PRIVATE)
    boolean inited;

    public FraudDetectionClient(
            @Value("${amplilot_control.base-url:http://localhost:8080}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    void prepareClient(ApiClient c) {
        c.setJackson(getJackson());
        c.setErrorHandler(getClientErrorHandler());
        c.setBaseUrl(getBaseUrl());
    }

    @PostConstruct
    public void init() {
        if (isInited()) {
            return;
        }

        if (getJackson() == null) {
            setJackson(Jackson.DEFAULT);
        }

        if (getClientErrorHandler() == null) {
            setClientErrorHandler(new ApiClientErrorHandler(getJackson()));
        }

        var tc = getTenantClient();
        if (tc == null) {
            tc = new TenantClient();
            prepareClient(tc);
            setTenantClient(tc);
        }
        tc.init();

        setInited(true);
    }
    
    @Override
    public void deleteTenant(Long tenantId) {
        getTenantClient().deleteTenant(tenantId);
    }
    


}
