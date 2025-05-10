### Task #1 – Use Migrations

Take the project from the previous homework. This project currently loads and executes two SQL files: `sql/init_db.sql` and `sql/populate_db.sql`.

Integrate the Flyway migration framework and create two migration files:
- `V1__init_db.sql`
- `V2__populate_db.sql`

Remove the old code that was loading and executing the SQL files — this should now be handled by Flyway.

Run the project and make sure Flyway is properly configured and executes both migrations.  
You should see in the logs that both migrations have been successfully applied.

---

### Task #2 – Create a Service for CRUD Operations on the `client` entity

Create a class named `ClientService`.  
This class should provide CRUD operations for clients with the following methods:

- `long create(String name)` – adds a new client with the given name and returns the ID of the newly created client.
- `String getById(long id)` – returns the name of the client with the specified ID.
- `void setName(long id, String name)` – sets a new name for the client with the specified ID.
- `void deleteById(long id)` – deletes the client with the specified ID.
- `List<Client> listAll()` – returns all clients from the database as a collection of `Client` objects.

You should also create the `Client` class, which must have two fields: `id` and `name`.

Include input validation in the methods of `ClientService`. If any input is invalid (for example, trying to create a client with a name that is too short or too long), the corresponding method should throw an `Exception`.