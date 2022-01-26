package com.example.dw.provider;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.example.dw.datafetchers.GraphQLDataFetcher;
import graphql.ExecutionInput;
import graphql.GraphQL;
import graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentation;
import graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentationOptions;
import graphql.schema.*;
import graphql.schema.idl.*;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;


@Slf4j
public class GraphQLProvider {

    @Inject
    private GraphQLDataFetcher graphQLDataFetchers;
    private GraphQL graphQL;

    public GraphQLProvider() {
    }

    @Inject
    public void init() throws IOException {
        URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        DataLoaderDispatcherInstrumentationOptions options = DataLoaderDispatcherInstrumentationOptions
                .newOptions().includeStatistics(true);
        DataLoaderDispatcherInstrumentation dispatcherInstrumentation
                = new DataLoaderDispatcherInstrumentation(options);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema)
                .instrumentation(dispatcherInstrumentation)
                .build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring(typeRegistry);
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring(TypeDefinitionRegistry registry) {
        GraphQLCodeRegistry.Builder codeRegistryBuilder = GraphQLCodeRegistry.newCodeRegistry();
        codeRegistryBuilder = graphQLDataFetchers.generateQueryFetchers(codeRegistryBuilder);
        codeRegistryBuilder = graphQLDataFetchers.generateMutationFetchers(codeRegistryBuilder);
        return RuntimeWiring.newRuntimeWiring().codeRegistry(codeRegistryBuilder).build();
    }

    public GraphQL graphQL() {
        return graphQL;
    }

    public Object invokePerRequest(String query) {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .build();
        return graphQL().execute(executionInput);
    }
}