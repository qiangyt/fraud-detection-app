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
package qiangyt.fraud_detection.framework.errs;

/**
 * Enum representing various error codes used in the fraud detection application.
 */
public enum ErrorCode {
    /**
     * No error.
     */
    NONE,

    /**
     * The requested path was not found.
     */
    PATH_NOT_FOUND,

    // ALREADY_EXISTS,
    // ALREADY_VALIDATED,
    // OTHER,

    /**
     * The provided parameter is not valid.
     */
    PARAMETER_NOT_VALID,

    /**
     * A constraint violation occurred.
     */
    CONSTRAINT_VIOLATION,

    /**
     * The data format is incorrect.
     */
    WRONG_DATA_FORMAT,

    /**
     * The field cannot be updated.
     */
    FIELD_NOT_UPDATEABLE,

    /**
     * The field cannot be assigned.
     */
    FIELD_NOT_ASSIGNABLE,

    /**
     * The field does not exist.
     */
    FIELD_NOT_EXISTS,

    /**
     * The value is not a valid enum.
     */
    INVALID_ENUM,

    /**
     * The property is invalid.
     */
    INVALID_PROPERTY
}
