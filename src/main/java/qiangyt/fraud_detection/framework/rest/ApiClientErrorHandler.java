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

@lombok.Getter
public class ApiClientErrorHandler implements ResponseErrorHandler {

    Jackson jackson;

    public ApiClientErrorHandler(@Autowired Jackson jackson) {
        this.jackson = jackson;
    }

    @Override
    public boolean hasError(ClientHttpResponse resp) throws IOException {
        return resp.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse resp) throws IOException {
        var err = getJackson().from(resp.getBody(), ErrorResponse.class);
        throw new RemoteError(err);
    }
}
