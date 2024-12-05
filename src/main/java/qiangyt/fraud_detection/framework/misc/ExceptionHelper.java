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
package qiangyt.fraud_detection.framework.misc;

import java.lang.reflect.InvocationTargetException;

/** */
public class ExceptionHelper {

    /** */
    private ExceptionHelper() {
        // dummy
    }

    /**
     * @param error
     * @return
     */
    public static Throwable getRootCause(Throwable error) {
        Throwable ex = error;

        while (true) {
            Throwable cause;

            if (ex instanceof InvocationTargetException) {
                cause = ((InvocationTargetException) ex).getTargetException();
            } else {
                cause = ex.getCause();
            }

            if (cause == null) {
                return ex;
            }
            ex = cause;
        }
    }

    public static StackTraceElement topElement(Throwable error) {
        var elements = error.getStackTrace();

        if (elements.length > 0) {
            return elements[0];
        }

        return null; // or handle differently if no stack trace is available
    }
}
