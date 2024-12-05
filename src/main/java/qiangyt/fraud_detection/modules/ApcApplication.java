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
package qiangyt.fraud_detection.modules;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import qiangyt.fraud_detection.framework.misc.ManifestFile;
import qiangyt.fraud_detection.modules.shared.ApcRestConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "qiangyt.fraud_detection")
@Import({ApcRestConfig.class})
public class ApcApplication {

    public static void main(String[] args) throws Exception {
        for (var dep : ManifestFile.parseClassPath().entrySet()) {
            System.out.printf("%s: %s\n", dep.getKey(), dep.getValue());
        }
        SpringApplication.run(ApcApplication.class, args);
    }

    @Bean
    public OpenAPI openApiInfo() {
        return new OpenAPI().info(new Info().title("Fraud Detection API").version("1.0"));
    }
}
