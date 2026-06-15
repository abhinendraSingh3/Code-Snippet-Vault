# CodeVault - Developer Snippet Manager

A backend application that allows developers to save, organize, search, share, and version their code snippets.

Instead of storing useful code in random notes, text files, or searching the internet repeatedly for the same solution, developers can maintain their own searchable repository of reusable code.

---

## Features

### Snippet Management
- Create, update, delete, and retrieve code snippets
- Store snippets with titles, programming languages, and tags
- Organize snippets for easy access

### Smart Search
- Search snippets using keywords
- Search across titles, tags, and code content
- Powered by PostgreSQL Full-Text Search
- Relevance-based search results

### Expiring Share Links
- Generate temporary shareable links
- Links automatically expire after 24 hours
- Implemented using Redis TTL (Time To Live)

### Version History
- Every snippet update creates a new version
- View previous versions
- Restore older versions if needed
- Git-like history tracking for snippets

---

## Why This Project?

Developers often write useful pieces of code that they later struggle to find again.

Examples:
- Email validation functions
- Database queries
- Utility methods
- Automation scripts
- Frequently used algorithms

CodeVault provides a centralized system to store and retrieve these snippets efficiently.

---

## Tech Stack

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Maven

### Database
- PostgreSQL

### Caching & Temporary Storage
- Redis

### Testing
- Postman

---

## Architecture

```text
Client
   |
   v
Spring Boot REST API
   |
   +------------------+
   |                  |
   v                  v
PostgreSQL         Redis
(Snippets)      (Share Links)
```

---

## Core Concepts Demonstrated

- REST API Development
- CRUD Operations
- PostgreSQL Full-Text Search
- Redis Integration
- Database Design
- JPA & Hibernate
- Version Control Concepts
- Layered Architecture
- Backend System Design

---

## Database Design

### Snippets

| Field | Type |
|---------|--------|
| id | Long |
| title | String |
| language | String |
| code | Text |
| created_at | Timestamp |
| updated_at | Timestamp |

### Tags

| Field | Type |
|---------|--------|
| id | Long |
| name | String |

### Snippet Versions

| Field | Type |
|---------|--------|
| id | Long |
| snippet_id | Long |
| code | Text |
| version_number | Integer |
| created_at | Timestamp |

---

## API Endpoints

### Create Snippet

```http
POST /api/snippets
```

### Get Snippet

```http
GET /api/snippets/{id}
```

### Update Snippet

```http
PUT /api/snippets/{id}
```

### Delete Snippet

```http
DELETE /api/snippets/{id}
```

### Search Snippets

```http
GET /api/snippets/search?q=email
```

### Generate Share Link

```http
POST /api/snippets/{id}/share
```

### Access Shared Snippet

```http
GET /share/{token}
```

### Get Version History

```http
GET /api/snippets/{id}/versions
```

---

## Example Workflow

### Save a Snippet

A developer stores:

```java
public boolean isValidEmail(String email) {
    return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
}
```

with:

- Title: Email Validator
- Language: Java
- Tags: java, validation

### Search

Later, the developer searches:

```text
email validation
```

The application finds the snippet using PostgreSQL Full-Text Search.

### Share

The developer generates a share link:

```text
https://app.com/share/abc123xyz
```

The token is stored in Redis with a 24-hour expiration.

### Versioning

When the snippet is updated:

- Previous version is stored
- New version becomes active
- History remains accessible

---

## Future Improvements

- JWT Authentication
- User Accounts
- Public and Private Snippets
- Favorites and Bookmarks
- Folder Organization
- Syntax Highlighting
- Docker Support
- CI/CD Pipeline
- AI-Based Snippet Recommendations
- Team Collaboration Features

---

## Getting Started

### Prerequisites

- Java 21+
- Maven
- PostgreSQL
- Redis

### Clone Repository

```bash
git clone https://github.com/your-username/codevault.git
cd codevault
```

### Build Project

```bash
mvn clean install
```

### Run Application

```bash
mvn spring-boot:run
```

Application starts at:

```text
http://localhost:8080
```

---

## Project Structure

```text
src
├── controller
├── service
├── repository
├── entity
├── dto
├── config
└── exception
```

---

## Learning Outcomes

This project helped in understanding:

- Spring Boot Development
- REST API Design
- PostgreSQL Search Features
- Redis Caching and TTL
- Database Relationships
- Clean Architecture
- Backend Performance Considerations
- Real-world System Design

---

## License

This project is licensed under the MIT License.
