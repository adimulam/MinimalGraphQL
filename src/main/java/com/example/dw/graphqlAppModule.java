package com.example.dw;


import com.example.dw.datafetchers.GraphQLDataFetcher;
import com.example.dw.provider.GraphQLProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import io.dropwizard.setup.Environment;

public class graphqlAppModule extends AbstractModule {

    final Environment environment;

    public graphqlAppModule(final Environment environment) {
        this.environment = environment;
    }

    @Override
    protected void configure() {
        bind(Environment.class).toInstance(environment);
        bind(GraphQLDataFetcher.class).in(Singleton.class);
        bind(GraphQLProvider.class).in(Singleton.class);
    }
}