# Word List
Quarkus test project.  
This training project is created to improve knowledge of Quarkus and Kubernates. This Project contains imaginary scenario to perform various tasks for imaginary “World List” product.
## Quarkus

Our recently launched product "Word List" has had some success with consumers. The product is a word database that can be used to see the best words that exist. Our consumers praise its simplicity. The product has been launched last year and while our user base was continually rising throughout the year, we have seen that it is currently stagnating.
In order to attract new users, we would like to on board a big company in the word business called "Big Words". Our first meetings went really well but in order to seal the deal, they have requested a couple of features for our REST API. If we manage to fulfill these, we have calculated that our customer base might grow threefold.

### Requested features

1. The Word List GET API should be able to sort the words in ascending or descending alphabetical order if a "sortOrder" query parameter is set.
2. Currently, the word list needs to be maintained manually. Big Words wants us to provide them with a POST method that can be used to insert new words.
3. Big Words is not satisfied with our security model. They requested that our APIs should be secured with Role-based access control. The login will be handled by Big Words, our API should just allow the Roles "endUser" and "bigWords".
4. Big Words would like to provide a premium word list for their customers, that can only be accessed and updated for users with the "bigWords" role. Initially the premium word list should consist of the words "this", "is" and "premium".
5. The developers of Big Words mentioned that our API is currently not well documented, Big Words suggested that we should describe our API with OpenAPI Annotations.
6. We estimate that due to the larger pool of words, an in-memory store for the Word List might not be enough. Please come up with a solution that loads the words from a PostgreSQL database. Provide us with the necessary Entities. We would prefer that the database is initialized with the Quarkus flyway module.
7. Please make sure your code is working correctly by providing tests for the new code.

## Kubernetes

In order to satisfy the increased demand for our service, we need to scale the service up. Currently, it is running in a single pod. Your task is to adapt the Kubernetes files so that instead of using a single pod, we can scale the service to 3 pods using a singular deployment configuration file.

