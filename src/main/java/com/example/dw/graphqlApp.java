package com.example.dw;

import com.example.dw.config.Config;
import com.example.dw.controller.GraphQLController;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class graphqlApp extends Application<Config> {

    public static void main(String[] args) throws Exception {
        new graphqlApp().run(args);
    }

    @Override
    public void initialize(final Bootstrap<Config> bootstrap) {
    }

    @Override
    public void run(final Config conf, final Environment env) throws Exception {
        final Injector injector = Guice.createInjector(new graphqlAppModule(env));
        env.jersey().register(injector.getInstance(GraphQLController.class));
    }
}