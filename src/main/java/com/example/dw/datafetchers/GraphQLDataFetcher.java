package com.example.dw.datafetchers;

import com.example.dw.model.Book;
import com.example.dw.model.Books;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.*;
import javax.inject.Inject;
import java.util.*;

public class GraphQLDataFetcher {

    private final static Books list = new Books();

    static
    {
        list.getBookList().add(new Book(1, "USA", "Hachette", "howtodoinJava@gmail.com", 100));
        list.getBookList().add(new Book(2, "India", "HarperCollins", "titanic@gmail.com", 250));
        list.getBookList().add(new Book(3, "Canada", "Macmillan", "abc@gmail.com", 300));
    }

    @Inject
    public GraphQLDataFetcher() {
    }

    /* Utilities */
    private void printDataFetchingEnv(DataFetchingEnvironment dataFetchingEnvironment) {
        Map<String, Object> args = dataFetchingEnvironment.getArguments();
        System.out.println(args);
        List<SelectedField> fields = dataFetchingEnvironment.getSelectionSet().getFields();

        for (String key: args.keySet()) {
            System.out.println(key);
            System.out.println(args.get(key).toString());
        }
    }

    public DataFetcher createBook() {
        return dataFetchingEnvironment -> {
            ObjectMapper objectMapper = new ObjectMapper();
            if (dataFetchingEnvironment.getArgument("input") != null) {
                Object payload = dataFetchingEnvironment.getArgument("input");
                System.out.println(payload);
                Book book = objectMapper.convertValue(payload, Book.class);
                list.getBookList().add(book);
                return list.getBook(book.getId()-1);
            }
            return null;
        };
    }

    public DataFetcher updateBook() {
        return dataFetchingEnvironment -> {
            ObjectMapper objectMapper = new ObjectMapper();
            if (dataFetchingEnvironment.getArgument("input") != null) {
                Object payload = dataFetchingEnvironment.getArgument("input");
                Book book = objectMapper.convertValue(payload, Book.class);
                System.out.println(payload);
                list.getBookList().set(book.getId()-1, book);
                return list.getBook(book.getId()-1);
            }
            return null;
        };
    }

    public DataFetcher deleteBook() {
        return dataFetchingEnvironment -> {
            ObjectMapper objectMapper = new ObjectMapper();
            if (dataFetchingEnvironment.getArgument("input") != null) {
                Object payload = dataFetchingEnvironment.getArgument("input");
                Book book = objectMapper.convertValue(payload, Book.class);
                System.out.println(payload);
                list.getBookList().remove(book.getId()-1);
                return book.getId();
            }
            return null;
        };
    }

    public DataFetcher getBookByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            printDataFetchingEnv(dataFetchingEnvironment);
            int bookId = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return list.getBookList().get(bookId-1);
        };
    }

    public DataFetcher getAllBooksDataFetcher() {
        return dataFetchingEnvironment -> {
            printDataFetchingEnv(dataFetchingEnvironment);
            return list.getBookList();
        };
    }

    public GraphQLCodeRegistry.Builder generateBookFetchers(GraphQLCodeRegistry.Builder codeRegistryBuilder) {
        codeRegistryBuilder = codeRegistryBuilder.dataFetcher(FieldCoordinates.coordinates("Query", "books"), this.getAllBooksDataFetcher());
        codeRegistryBuilder = codeRegistryBuilder.dataFetcher(FieldCoordinates.coordinates("Query", "book"), this.getBookByIdDataFetcher());
        return codeRegistryBuilder;
    }

    public GraphQLCodeRegistry.Builder generateMutationFetchers(GraphQLCodeRegistry.Builder codeRegistryBuilder) {
        codeRegistryBuilder = codeRegistryBuilder.dataFetcher(FieldCoordinates.coordinates("Mutation", "createBook"), this.createBook());
        codeRegistryBuilder = codeRegistryBuilder.dataFetcher(FieldCoordinates.coordinates("Mutation", "updateBook"), this.updateBook());
        codeRegistryBuilder = codeRegistryBuilder.dataFetcher(FieldCoordinates.coordinates("Mutation", "deleteBook"), this.deleteBook());
        return codeRegistryBuilder;
    }

    public GraphQLCodeRegistry.Builder generateQueryFetchers(GraphQLCodeRegistry.Builder codeRegistryBuilder) {
        codeRegistryBuilder = generateBookFetchers(codeRegistryBuilder);
        codeRegistryBuilder = generateMutationFetchers(codeRegistryBuilder);
        return codeRegistryBuilder;
    }
}
