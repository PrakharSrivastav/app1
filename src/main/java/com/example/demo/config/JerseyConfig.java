package com.example.demo.config;

import brave.Tracing;
import brave.sampler.Sampler;
import com.example.demo.service.ReverseService;
import org.apache.cxf.tracing.brave.BraveFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        final Sender sender = OkHttpSender.create("http://localhost:9411/api/v2/spans");
        final Tracing brave = Tracing
                .newBuilder()
                .localServiceName("app2")
                .spanReporter(AsyncReporter.builder(sender).build())
                .sampler(Sampler.ALWAYS_SAMPLE) /* or any other Sampler */
                .supportsJoin(true)
                .build();
        register(ReverseService.class);
        register(new BraveFeature(brave));
    }
}