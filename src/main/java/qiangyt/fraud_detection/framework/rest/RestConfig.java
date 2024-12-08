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

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import qiangyt.fraud_detection.framework.errs.ErrorAdvice;
import qiangyt.fraud_detection.framework.json.Jackson;

@lombok.Getter
@lombok.Setter
@EnableWebMvc
@Import({ErrorAdvice.class})
public class RestConfig implements WebMvcConfigurer {

    @Autowired Jackson jackson;

    @Bean
    public ApiClientErrorHandler clientErrorHandler() {
        return new ApiClientErrorHandler(getJackson());
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        getJackson().replaceConverter(converters);
    }

    @Bean
    public CorsFilter corsFilter() {
        var s = new UrlBasedCorsConfigurationSource();
        var c = new CorsConfiguration();
        c.setAllowCredentials(true);
        c.setAllowedOriginPatterns(Arrays.asList("*"));
        // config.setAllowedOrigins(Arrays.asList("http://192.168.6.93:7070"));
        c.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        c.setAllowedHeaders(Arrays.asList("*"));
        s.registerCorsConfiguration("/**", c);
        return new CorsFilter(s);
    }
}
