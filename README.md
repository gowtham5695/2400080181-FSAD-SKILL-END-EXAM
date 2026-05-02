# Hibernate HQL Project – Service Entity
**Package:** `com.klef.fsad.exam`  
**Database:** `fsadendexam`

---

## Project Structure

```
hibernate-hql-project/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/klef/fsad/exam/
        │       ├── Service.java       ← Entity Class
        │       └── ClientDemo.java    ← HQL Operations Demo
        └── resources/
            ├── hibernate.cfg.xml      ← Hibernate Configuration
            └── log4j.properties       ← Logging Configuration
```

---

## Entity: `Service.java`

| Field         | Column Name          | Type        | Notes              |
|---------------|----------------------|-------------|--------------------|
| `id`          | `service_id`         | int (PK)    | Auto-generated     |
| `name`        | `service_name`       | VARCHAR(100)| Not null           |
| `date`        | `service_date`       | DATE        | Not null           |
| `status`      | `service_status`     | VARCHAR(50) | Not null           |
| `description` | `service_description`| VARCHAR(255)|                    |
| `category`    | `service_category`   | VARCHAR(80) |                    |
| `price`       | `service_price`      | DOUBLE      |                    |
| `provider`    | `service_provider`   | VARCHAR(100)|                    |
| `location`    | `service_location`   | VARCHAR(120)|                    |

---

## HQL Operations in `ClientDemo.java`

### I. Insert Records (Persistent Object)
```java
Session session = sessionFactory.openSession();
Transaction tx = session.beginTransaction();
session.save(service);   // Transient → Persistent
tx.commit();
session.close();
```

### II. Update Name & Status by ID (HQL Named Parameters)
```java
String hql = "UPDATE Service s "
           + "SET s.name = :newName, s.status = :newStatus "
           + "WHERE s.id = :serviceId";

Query<?> query = session.createQuery(hql);
query.setParameter("newName",   newName);
query.setParameter("newStatus", newStatus);
query.setParameter("serviceId", id);
query.executeUpdate();
```

---

## Prerequisites

| Tool      | Version      |
|-----------|-------------|
| JDK       | 11 or above  |
| Maven     | 3.6+         |
| MySQL     | 8.0+         |

---

## Setup Instructions

### Step 1 – Create MySQL Database
```sql
CREATE DATABASE IF NOT EXISTS fsadendexam;
```
> **Note:** The `hibernate.cfg.xml` already includes `createDatabaseIfNotExist=true`  
> so the database is created automatically on first run.

### Step 2 – Configure DB Credentials
Edit `src/main/resources/hibernate.cfg.xml`:
```xml
<property name="hibernate.connection.username">root</property>
<property name="hibernate.connection.password">root</property>
```
Replace with your MySQL username and password.

### Step 3 – Build the Project
```bash
mvn clean compile
```

### Step 4 – Run the Demo
```bash
mvn exec:java -Dexec.mainClass="com.klef.fsad.exam.ClientDemo"
```

### Step 5 – Verify in MySQL
```sql
USE fsadendexam;
SELECT * FROM services;
```

---

## Expected Output

```
=================================================
  SessionFactory created successfully.
  Database : fsadendexam
=================================================

───────────────────────────────────────────────────────
  OPERATION I : Inserting Service Records (Persistent Objects)
───────────────────────────────────────────────────────
  [INSERT] Service saved with ID = 1
  [INSERT] Service saved with ID = 2
  [INSERT] Service saved with ID = 3
  [INSERT] Service saved with ID = 4
  [INSERT] Service saved with ID = 5

───────────────────────────────────────────────────────
  OPERATION II : Update Name & Status via HQL Named Parameters
───────────────────────────────────────────────────────
  [UPDATE] Rows affected = 1  (ID=1 → Name='Advanced Web Development', Status='Completed')
  [UPDATE] Rows affected = 1  (ID=3 → Name='AWS Cloud Migration Pro', Status='Active')
  [UPDATE] Rows affected = 1  (ID=4 → Name='Oracle DB Administration', Status='Active')
```

---

## Dependencies (from `pom.xml`)

| Dependency              | Version       |
|-------------------------|--------------|
| hibernate-core          | 5.6.15.Final |
| mysql-connector-java    | 8.0.33       |
| hibernate-validator     | 6.2.5.Final  |
| slf4j-log4j12           | 1.7.36       |

---

*KLEF FSAD End Exam – Hibernate HQL Assignment*
