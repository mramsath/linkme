package com.arz.linkme.analytics.config;

import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class DroolConfig {

    private KieServices kieService = KieServices.Factory.get();


    private KieFileSystem getKieFileSystem() throws IOException{
        KieFileSystem kieFileSystem = kieService.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/offer.drl"));
        return kieFileSystem;
    }

    @Bean
    public KieContainer getKieContainer() throws IOException{
        System.out.println("Container created");
        getKieRepository();
        KieBuilder kb = kieService.newKieBuilder(getKieFileSystem());
        kb.buildAll();

        KieModule kieModule = kb.getKieModule();
        KieContainer kieContainer = kieService.newKieContainer(kieModule.getReleaseId());
        return kieContainer;
    }

    private void getKieRepository(){
        final KieRepository kieRepository = kieService.getRepository();
        kieRepository.addKieModule(new KieModule(){
            @Override
            public ReleaseId getReleaseId() {
                return kieRepository.getDefaultReleaseId();
            }
        });
    }

    @Bean
    public KieSession getKieSession() throws IOException{
        System.out.println("Session Created");
        return getKieContainer().newKieSession();
    }
}
