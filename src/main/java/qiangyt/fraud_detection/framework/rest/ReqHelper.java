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

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class ReqHelper {

    public static String ip(HttpServletRequest req) {
        String xForwardedFor = req.getHeader("x-forwarded-for");
        if (xForwardedFor != null) {
            return xForwardedFor.split(",")[0].trim();
        }

        String realIp = req.getHeader("x-real-ip");
        if (realIp != null) {
            return realIp;
        }

        return req.getRemoteHost();
    }

    public static int port(HttpServletRequest req) {
        String xForwardedPort = req.getHeader("f-forwarded-port");
        if (xForwardedPort != null) {
            return Integer.parseInt(xForwardedPort);
        }

        return req.getRemotePort();
    }

    public static String proto(HttpServletRequest req) {
        String xForwardedProto = req.getHeader("x-forwarded-proto");
        if (xForwardedProto != null) {
            return xForwardedProto;
        }

        return req.getScheme();
    }

    public static List<String> proxies(HttpServletRequest req) {
        String xForwardedFor = req.getHeader("x-forwarded-for");
        if (xForwardedFor != null) {
            return Arrays.asList(xForwardedFor.split(",")).stream().map(String::trim).toList();
        }
        return List.of();
    }
}
