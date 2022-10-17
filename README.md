Create a library system that is capable of adding new books, removing books and allowing members to loan/return a book. Each book should have an author, category and title.

For the sake of expediency it is not a requirement to have data persisted
when the application is no longer running.



Business rules

- no need to authenticate the user (we can assume they are members and are already authenticated)
- maximum number of books loaned at any time is 3 per user
- if a member has any outstanding loaned books, they cannot loan any more until all books returned.
- there is only 1 copy of each book
- each book can have more than one category 



OpenAPI Docs:
http://localhost:8080/v3/api-docs/

Postman Collection: Attached in Root

Unit Tests: Written for Services


