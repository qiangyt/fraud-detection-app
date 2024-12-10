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
package qiangyt.fraud_detection.app.config;

import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;

@lombok.Getter
@lombok.Setter

/**
 * Configuration properties for fraud detection rules.
 *
 * <p>Hard-coded suspicious accounts here, just to simplifes the demo
 */
@ConfigurationProperties(prefix = "app.rules")
public class RuleProps {

    /** Maximum transaction amount allowed before flagging as suspicious. */
    int maxTransactionAmount = 100000;

    /** Set of suspicious account usernames. */
    Set<String> suspicousAccounts = Set.of("cgrant", "fbiden");
}
