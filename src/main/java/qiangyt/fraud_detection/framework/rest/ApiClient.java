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

/**
 * ApiClient is a base class for making REST API calls. It initializes a RestClient with a base URL
 * and error handler.
 */
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

    /**
     * Constructs an ApiClient with the specified path.
     *
     * @param path the path for the API endpoint
     */
    public ApiClient(String path) {
        this.path = path;
    }

    /** Initializes the RestClient if it has not been initialized yet. */
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

    /**
     * Builds the HTTP headers for the request.
     *
     * @return a Consumer to set the HTTP headers
     */
    public Consumer<HttpHeaders> buildHeaders() {
        return headers -> {};
    }

    /**
     * Sends a GET request to the specified URI.
     *
     * @param uri the URI of the request
     * @param vars the variables for the URI template
     * @return a RequestBodySpec to further customize the request
     */
    protected RequestBodySpec get(String uri, Object... vars) {
        return (RequestBodySpec) getRestClient().get().uri(uri, vars).headers(buildHeaders());
    }

    /**
     * Sends a HEAD request to the specified URI.
     *
     * @param uri the URI of the request
     * @param vars the variables for the URI template
     * @return a RequestBodySpec to further customize the request
     */
    protected RequestBodySpec head(String uri, Object... vars) {
        return (RequestBodySpec) getRestClient().head().uri(uri, vars).headers(buildHeaders());
    }

    /**
     * Sends a POST request to the specified URI.
     *
     * @param uri the URI of the request
     * @param vars the variables for the URI template
     * @return a RequestBodySpec to further customize the request
     */
    protected RequestBodySpec post(String uri, Object... vars) {
        return (RequestBodySpec) getRestClient().post().uri(uri, vars).headers(buildHeaders());
    }

    /**
     * Sends a PUT request to the specified URI.
     *
     * @param uri the URI of the request
     * @param vars the variables for the URI template
     * @return a RequestBodySpec to further customize the request
     */
    protected RequestBodySpec put(String uri, Object... vars) {
        return (RequestBodySpec) getRestClient().put().uri(uri, vars).headers(buildHeaders());
    }

    /**
     * Sends a PATCH request to the specified URI.
     *
     * @param uri the URI of the request
     * @param vars the variables for the URI template
     * @return a RequestBodySpec to further customize the request
     */
    protected RequestBodySpec patch(String uri, Object... vars) {
        return (RequestBodySpec) getRestClient().patch().uri(uri, vars).headers(buildHeaders());
    }

    /**
     * Sends a DELETE request to the specified URI.
     *
     * @param uri the URI of the request
     * @param vars the variables for the URI template
     * @return a RequestBodySpec to further customize the request
     */
    protected RequestBodySpec delete(String uri, Object... vars) {
        return (RequestBodySpec) getRestClient().delete().uri(uri, vars).headers(buildHeaders());
    }

    /**
     * Sends an OPTIONS request to the specified URI.
     *
     * @param uri the URI of the request
     * @param vars the variables for the URI template
     * @return a RequestBodySpec to further customize the request
     */
    protected RequestBodySpec options(String uri, Object... vars) {
        return (RequestBodySpec) getRestClient().options().uri(uri, vars).headers(buildHeaders());
    }
}
