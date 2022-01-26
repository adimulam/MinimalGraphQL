# Minimal GraphQL

Java - JDK 8+\
Framework - Dropwizard\
DI - Guice\
GraphQL - graphql-java\
DataSet - Uses in memory map.

To Build:
 mvn clean install

To Run:
 java -jar target/MinimalGraphQL-1.0-SNAPSHOT.jar server conf.yml

Sample Queries:

1. Get all books
query {
    books {
        id
        pages
        publisher
        contact
        countryOfOrigin
    }
}

2. Get book by ID:
query {
    book(id:3) {
        id
        pages
        publisher
        contact
        countryOfOrigin
    }
}

3. Create a book:
mutation {
	createBook(
    	input: {
    		id:4,
      		pages:400,
      		publisher:"ABC",
    		contact:"abc@xyz.com",
      		countryOfOrigin:"India"
    }) {
    	id
    	pages
    	publisher
    	contact
    	countryOfOrigin
    }
}


4. Update Book
mutation {
    updateBook(
    input: {
    	id:4,
      	pages:450,
      	publisher:"ABC",
    	contact:"abc@xyz.com",
      	countryOfOrigin:"AUS"
    }) {
    id
    pages
    publisher
    contact
    countryOfOrigin
    }
}

5. Delete a book:
mutation {
    deleteBook(
    input: {
    	id:3
    })
}
