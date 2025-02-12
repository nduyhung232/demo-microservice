# Authentication Service - Database Schema

This document describes the database schema for the **Authentication Service**, supporting **JWT, OAuth2, Refresh Token**, and **Role-Based Access Control (RBAC)**.

## **📌 Database Tables**
| Table | Description |
|------|-------------|
| `users` | Stores user account information (username, password hash, status) |
| `roles` | Stores roles (e.g., ADMIN, USER, etc.) |
| `user_roles` | Many-to-Many relationship between users and roles |
| `permissions` | Stores permissions (READ, WRITE, DELETE, etc.) |
| `role_permissions` | Many-to-Many relationship between roles and permissions |
| `refresh_tokens` | Stores refresh tokens for issuing new access tokens |

---

## **🔹 1. `users` Table (Stores User Accounts)**
Each user has a unique username, password hash, status, email, and timestamps.
```sql
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(255) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);
```
- **`password_hash`**: Stores the hashed password (e.g., using BCrypt, Argon2).
- **`is_active`**: If `FALSE`, the user account is deactivated.

---

## **🔹 2. `roles` Table (Stores User Roles - ADMIN, USER, etc.)**
```sql
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);
```

---

## **🔹 3. `user_roles` Table (Many-to-Many Relationship Between Users and Roles)**
A user can have multiple roles.
```sql
CREATE TABLE user_roles (
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    role_id INT REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);
```

---

## **🔹 4. `permissions` Table (Stores Access Permissions - READ, WRITE, DELETE, etc.)**
```sql
CREATE TABLE permissions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);
```

---

## **🔹 5. `role_permissions` Table (Assigns Permissions to Roles)**
Each role can have multiple permissions.
```sql
CREATE TABLE role_permissions (
    role_id INT REFERENCES roles(id) ON DELETE CASCADE,
    permission_id INT REFERENCES permissions(id) ON DELETE CASCADE,
    PRIMARY KEY (role_id, permission_id)
);
```

---

## **🔹 6. `refresh_tokens` Table (Stores Refresh Tokens for Access Token Renewal)**
Stores refresh tokens linked to a user account for reissuing access tokens.
```sql
CREATE TABLE refresh_tokens (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    token TEXT NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT now()
);
```

---

## **📌 Schema Summary**
- **`users`** can have multiple **`roles`** (via `user_roles`).
- **`roles`** can have multiple **`permissions`** (via `role_permissions`).
- **`users`** can have multiple **refresh tokens** for session management.

### **Advantages of this Design:**
✅ Supports **Role-Based Access Control (RBAC)**.
✅ Uses JWT for **stateless authentication**.
✅ Supports **OAuth2, MFA, Social Login**.
✅ Ensures authentication is independent, making the system more scalable.

---

### **💡 Future Enhancements**
- Implement **OAuth2 authorization codes** for third-party authentication.
- Store failed login attempts for security monitoring.
- Add **audit logs** for tracking authentication events.

---

🚀 **This schema is designed for high scalability, security, and flexibility.**

