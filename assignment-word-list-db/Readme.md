# Word List DB

---
**NOTE**

This project is created to control word_list database using Flyway migration tool.
---

## Flyway

Migration scripts location: src/main/resources/db/migration

Migration script format: V<version#>.<subversion#>.<subsubversion#>__<name describes migration subject>.sql (e.g V1.0.1__definePremiumLeadingWords.sql)

Run migration: ...\git\word-list\assignment-word-list-db>mvn quarkus:dev