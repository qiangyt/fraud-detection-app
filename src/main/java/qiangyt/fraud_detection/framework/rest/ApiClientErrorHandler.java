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

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import qiangyt.fraud_detection.framework.errs.ErrorResponse;
import qiangyt.fraud_detection.framework.errs.RemoteError;
import qiangyt.fraud_detection.framework.json.Jackson;

/**
 * Handles client-side HTTP errors by converting the response body to an {@link ErrorResponse} and
 * throwing a {@link RemoteError}.
 */
@lombok.Getter
public class ApiClientErrorHandler implements ResponseErrorHandler {

    Jackson jackson;

    /**
     * Constructs an {@code ApiClientErrorHandler} with the specified {@link Jackson} instance.
     *
     * @param jackson the {@link Jackson} instance used for JSON deserialization
     */
    public ApiClientErrorHandler(@Autowired Jackson jackson) {
        this.jackson = jackson;
    }

    /**
     * Checks if the given {@link ClientHttpResponse} has an error status code.
     *
     * @param resp the {@link ClientHttpResponse} to check
     * @return {@code true} if the response has an error status code, {@code false} otherwise
     * @throws IOException if an I/O error occurs
     */
    @Override
    public boolean hasError(ClientHttpResponse resp) throws IOException {
        return resp.getStatusCode().isError();
    }

    /**
     * Handles the error in the given {@link ClientHttpResponse} by converting the response body to
     * an {@link ErrorResponse} and throwing a {@link RemoteError}.
     *
     * @param resp the {@link ClientHttpResponse} containing the error
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void handleError(ClientHttpResponse resp) throws IOException {
        var err = getJackson().from(resp.getBody(), ErrorResponse.class);
        throw new RemoteError(err);
    }
}
