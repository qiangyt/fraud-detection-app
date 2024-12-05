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
package qiangyt.fraud_detection.framework.rest;

import jakarta.annotation.PostConstruct;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestBodySpec;
import qiangyt.fraud_detection.framework.json.Jackson;

@lombok.Getter
@lombok.Setter
@lombok.experimental.SuperBuilder
public class ApiClient {

    RestClient restClient;

    @Value("${fraud_detection.base-url:http://localhost:8080}")
    String baseUrl;

    @Autowired Jackson jackson;

    @Autowired ApiClientErrorHandler errorHandler;

    final String path;

    @lombok.Setter(lombok.AccessLevel.PRIVATE)
    boolean inited;

    public ApiClient(String path) {
        this.path = path;
    }

    @PostConstruct
    public void init() {
        if (isInited()) {
            return;
        }

        this.restClient =
                RestClient.builder()
                        .baseUrl(getBaseUrl() + "/" + getPath())
                        .defaultStatusHandler(getErrorHandler())
                        .messageConverters(converters -> getJackson().replaceConverter(converters))
                        .build();

        setInited(true);
    }

    public Consumer<HttpHeaders> buildHeaders() {
        return headers -> {};
    }

    protected RequestBodySpec get(String uri, Object... vars) {
        return (RequestBodySpec) getRestClient().get().uri(uri, vars).headers(buildHeaders());
    }

    protected RequestBodySpec head(String uri, Object... vars) {
        return (RequestBodySpec) getRestClient().head().uri(uri, vars).headers(buildHeaders());
    }

    protected RequestBodySpec post(String uri, Object... vars) {
        return (RequestBodySpec) getRestClient().post().uri(uri, vars).headers(buildHeaders());
    }

    protected RequestBodySpec put(String uri, Object... vars) {
        return (RequestBodySpec) getRestClient().put().uri(uri, vars).headers(buildHeaders());
    }

    protected RequestBodySpec patch(String uri, Object... vars) {
        return (RequestBodySpec) getRestClient().patch().uri(uri, vars).headers(buildHeaders());
    }

    protected RequestBodySpec delete(String uri, Object... vars) {
        return (RequestBodySpec) getRestClient().delete().uri(uri, vars).headers(buildHeaders());
    }

    protected RequestBodySpec options(String uri, Object... vars) {
        return (RequestBodySpec) getRestClient().options().uri(uri, vars).headers(buildHeaders());
    }
}
