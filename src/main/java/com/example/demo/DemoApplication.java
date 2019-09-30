package com.example.demo;

import brave.Tracer;
import brave.Tracing;
import brave.sampler.Sampler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.okhttp3.OkHttpSender;

@SpringBootApplication
public class DemoApplication {
    @Bean
    Tracing tracing() {
        return Tracing
                .newBuilder()
                .localServiceName("app2")
                .spanReporter(AsyncReporter.builder(OkHttpSender.create("http://localhost:9411/api/v2/spans")).build())
                .sampler(Sampler.ALWAYS_SAMPLE) /* or any other Sampler */
                .supportsJoin(true)
                .build();
    }

    @Bean
    Tracer tracer() {
        return tracing().tracer();
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
