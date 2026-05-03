# FashionStore — technical documentation

Jakarta Servlet 6 + JSP, Java 21, JDBC to MySQL (`fashion_store`). Docs are kept in sync with `src/main/java` and DAO SQL.

| Document | Contents |
|----------|----------|
| [architecture.md](./architecture.md) | MVC layers, source layout, class/component diagrams, sequence diagrams per servlet flow, auth (BCrypt) behavior |
| [database.md](./database.md) | ERD, tables/columns inferred from DAOs, model ↔ table mapping |
| [controllers-and-daos.md](./controllers-and-daos.md) | URL → servlet → DAO matrix and dependency graphs |

Diagrams use [Mermaid](https://mermaid.js.org/) (GitHub, many IDEs, and VS Code Markdown preview).
