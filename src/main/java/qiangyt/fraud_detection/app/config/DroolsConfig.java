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

import java.io.IOException;
import org.kie.api.KieBase;
import org.kie.internal.io.ResourceFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import qiangyt.fraud_detection.framework.errs.Internal;


import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/** Drools rule engine auto configuration */
@Configuration
public class DroolsConfig {

    static final String RULES_PATH = "rules/";

    KieServices kieServices() {
        return KieServices.Factory.get();
    }

    @Bean
    @ConditionalOnMissingBean(KieFileSystem.class)
    public KieFileSystem kieFileSystem() {
        var ks = kieServices();
        var kfs = ks.newKieFileSystem();
        for (var f : getRuleFiles()) {
            kfs.write(ks.getResources().newClassPathResource(RULES_PATH + f.getFilename(), "UTF-8"));
        }
        return kfs;
    }

    Resource[] getRuleFiles() {
        var resolver = new PathMatchingResourcePatternResolver();
        try {
            return resolver.getResources("classpath*:" + RULES_PATH + "**/*.*");
        } catch (IOException e) {
            throw new Internal(e);
        }
    }

    @Bean
    @ConditionalOnMissingBean(KieContainer.class)
    public KieContainer kieContainer() {
        var ks = kieServices();
        
        // Load KieBase configurations or rules
        var builder = ks.newKieBuilder(kieFileSystem());
        builder.buildAll();

        var repo = ks.getRepository();
        var releaseId = ks.newReleaseId("qiangyt", "fraud-detection-rules", "1.0.0");
        //var mod = repo.addKieModule(new KieModule(releaseId));
        repo.addKieModule(new KieModule() {
            @Override
            public ReleaseId getReleaseId() {
                //return kieServices().newReleaseId("qiangyt", "fraud-detection-rules", "1.0.0");
                return repo.getDefaultReleaseId();
            }
        });

        return ks.newKieContainer(repo.getDefaultReleaseId());
    }

    /*@Bean
    public KieContainer kieContainer2() {
        KieServices kieServices = KieServices.Factory.get();

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        KieRepository kieRepository = kieServices.getRepository();

        // Example: Load KieBase configurations or rules
        kieFileSystem.write(kieServices.getResources().newClassPathResource("rules.drl"));
        kieServices.newKieBuilder(kieFileSystem).buildAll();

        KieModule kieModule = kieRepository.getDefaultKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }*/

    @Bean
    @ConditionalOnMissingBean(KieBase.class)
    public KieBase kieBase() {
        return kieContainer().getKieBase();
    }
}
