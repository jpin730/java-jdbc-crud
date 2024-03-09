# Java JDBC CRUD

This is a simple Java JDBC (Java Database Connection) CRUD application. It uses a MySQL database and Swing for the GUI. It implements the ACID principles for database transactions and connection pooling for performance.

## Prepare database

Run docker-compose to start a MySQL database:

```bash
docker-compose up -d
```

If data does not exist, run the `seed.sql` script to create the database and table.

Database credentials are in the `docker-compose.yml` file.

To stop the database:

```bash
docker-compose down
```
