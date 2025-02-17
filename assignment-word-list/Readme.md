# Word List

## Quarkus

Our recently launched product "Word List" has had some success with consumers. The product is a word database
that can be used to see the best words that exist. Our consumers praise its simplicity. The product has been launched 
last year and while our user base was continually rising throughout the year, we have seen that it is currently 
stagnating.

In order to attract new users, we would like to on board a big company in the word business called "Big Words".
Our first meetings went really well but in order to seal the deal, they have requested a couple of features for our 
REST api. If we manage to fulfill these, we have calculated that our customer base might grow threefold.

### Requested features

1. The GET words api should be able to sort the words in ascending or descending alphabetical order if a "sortOrder"
query parameter is set.
2. Currently, the word list needs to be maintained manually. Big Words wants us to provide them with a POST method that 
can be used to insert new words.
3. Big Words is not satisfied with our security model. They requested that our apis should be secured with Role based 
access control. The login will be handled by Big Words, our api should just allow the Roles "endUser" and "bigWords".
4. Big Words would like to provide a premium word list for their customers, that can only be accessed and updated for
users with the "bigWords" role. Initially the premium word list should consist of the words "this", "is" and "premium".
5. Big Words suggested that we should describe our api with OpenAPI Annotations.
6. We estimate that due to the larger pool of words, an in memory store for the wordlist might not be enough. Please 
come up with a solution that loads the words from a PostgreSQL database. Provide us with the necessary Entities. We 
would prefer that the database is initialized with the quarkus flyway module.
