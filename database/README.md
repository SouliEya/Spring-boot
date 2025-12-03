# Database Setup - Banking Agent Application

## 📁 Fichiers de la Base de Données

Ce dossier contient tous les fichiers nécessaires pour configurer la base de données MySQL.

### Fichiers Inclus

- **init.sql** - Script d'initialisation de la base de données (création des tables et indexes)
- **sample_data.sql** - Données de test pour développement et démonstration
- **setup.sh** - Script d'installation automatique pour Linux/macOS
- **setup.bat** - Script d'installation automatique pour Windows
- **README.md** - Ce fichier

## 🚀 Installation Rapide

### Pour Windows

```bash
# Double-cliquer sur setup.bat
# Ou via PowerShell
.\database\setup.bat

# Ou via CMD
cd database
setup.bat
```

### Pour Linux/macOS

```bash
# Rendre le script exécutable
chmod +x database/setup.sh

# Exécuter le script
./database/setup.sh

# Ou avec des paramètres personnalisés
./database/setup.sh localhost 3306 root
```

## 📊 Structure de la Base de Données

### Tables Créées

1. **users** - Utilisateurs du système
   - 3 utilisateurs par défaut (admin, agent1, agent2)

2. **subscribers** - Abonnés/Clients
   - 8 abonnés de test avec soldes variés

3. **merchants** - Commerçants/Partenaires
   - 8 commerçants de test avec différents taux de commission

4. **transactions** - Transactions
   - 20 transactions de test de différents types

### Indexes Créés

- Index sur les colonnes fréquemment interrogées
- Index sur les clés étrangères
- Index sur les colonnes de recherche

## 🔐 Identifiants par Défaut

### Utilisateurs

| Rôle | Username | Password | Email |
|------|----------|----------|-------|
| Admin | admin | admin123 | admin@banking.com |
| Agent 1 | agent1 | agent123 | agent1@banking.com |
| Agent 2 | agent2 | agent123 | agent2@banking.com |

### Accès Base de Données

| Paramètre | Valeur |
|-----------|--------|
| Host | localhost |
| Port | 3306 |
| Username | root |
| Password | root |
| Database | banking_db |

## 🔧 Configuration Manuelle

Si vous préférez configurer manuellement:

### 1. Créer la Base de Données

```bash
mysql -u root -p
```

```sql
CREATE DATABASE banking_db;
USE banking_db;
```

### 2. Exécuter le Script d'Initialisation

```bash
mysql -u root -p banking_db < init.sql
```

### 3. Charger les Données de Test

```bash
mysql -u root -p banking_db < sample_data.sql
```

### 4. Vérifier l'Installation

```bash
mysql -u root -p banking_db
```

```sql
SHOW TABLES;
SELECT COUNT(*) FROM users;
SELECT COUNT(*) FROM subscribers;
SELECT COUNT(*) FROM merchants;
SELECT COUNT(*) FROM transactions;
```

## 📝 Données de Test

### Abonnés (8)

| Nom | Solde | Statut |
|-----|-------|--------|
| Michael Johnson | $5,000.00 | ACTIVE |
| Sarah Williams | $3,500.50 | ACTIVE |
| David Brown | $7,200.75 | ACTIVE |
| Emily Davis | $2,100.00 | ACTIVE |
| Robert Miller | $8,900.25 | ACTIVE |
| Jennifer Wilson | $1,500.00 | SUSPENDED |
| James Moore | $4,300.50 | ACTIVE |
| Patricia Taylor | $6,100.75 | ACTIVE |

### Commerçants (8)

| Nom | Type | Commission | Statut |
|-----|------|-----------|--------|
| ABC Electronics Store | Retail | 2.50% | ACTIVE |
| XYZ Restaurant Group | Restaurant | 3.00% | ACTIVE |
| Tech Solutions Inc | Technology | 2.00% | ACTIVE |
| Fashion Hub Boutique | Fashion | 3.50% | ACTIVE |
| Health & Wellness Center | Healthcare | 1.50% | ACTIVE |
| Auto Parts Warehouse | Automotive | 2.75% | SUSPENDED |
| Book Store Paradise | Retail | 2.25% | ACTIVE |
| Coffee House Deluxe | Food & Beverage | 3.25% | ACTIVE |

### Transactions (20)

- 15 transactions COMPLETED
- 1 transaction PENDING
- Types variés: PAYMENT, DEPOSIT, WITHDRAWAL, TRANSFER, REFUND

## 🔄 Synchronisation avec Hibernate

L'application utilise Hibernate pour gérer le schéma automatiquement.

### Configuration dans application.properties

```properties
spring.jpa.hibernate.ddl-auto=update
```

### Options disponibles

- **create** - Crée les tables (supprime les existantes)
- **create-drop** - Crée au démarrage, supprime à l'arrêt
- **update** - Met à jour le schéma (RECOMMANDÉ)
- **validate** - Valide sans modifications
- **none** - Pas de gestion

## 🧪 Tester la Connexion

### Via MySQL CLI

```bash
mysql -u root -p banking_db -e "SELECT * FROM users;"
```

### Via l'Application

```bash
curl -u admin:admin123 http://localhost:8080/api/users
```

### Via MySQL Workbench

1. Créer une nouvelle connexion
2. Host: localhost
3. Port: 3306
4. Username: root
5. Password: root
6. Tester la connexion

## 🛠️ Maintenance

### Sauvegarder la Base de Données

```bash
mysqldump -u root -p banking_db > backup.sql
```

### Restaurer la Base de Données

```bash
mysql -u root -p banking_db < backup.sql
```

### Nettoyer les Données

```sql
DELETE FROM transactions;
DELETE FROM subscribers;
DELETE FROM merchants;
ALTER TABLE transactions AUTO_INCREMENT = 1;
ALTER TABLE subscribers AUTO_INCREMENT = 1;
ALTER TABLE merchants AUTO_INCREMENT = 1;
```

### Réinitialiser Complètement

```sql
DROP DATABASE banking_db;
```

Puis réexécuter les scripts d'initialisation.

## 📋 Checklist de Configuration

- [ ] MySQL installé et en cours d'exécution
- [ ] Base de données créée
- [ ] Tables créées
- [ ] Données de test chargées
- [ ] Connexion testée
- [ ] application.properties configuré
- [ ] Application démarrée avec succès
- [ ] API accessible

## ⚠️ Dépannage

### Erreur: "Access denied"

```bash
# Réinitialiser le mot de passe
mysql -u root
ALTER USER 'root'@'localhost' IDENTIFIED BY 'new_password';
FLUSH PRIVILEGES;
```

### Erreur: "Can't connect to MySQL server"

```bash
# Vérifier que MySQL est en cours d'exécution
sudo systemctl status mysql

# Redémarrer MySQL
sudo systemctl restart mysql
```

### Erreur: "Database doesn't exist"

```bash
# Réexécuter le script d'initialisation
mysql -u root -p < init.sql
```

### Erreur: "Table doesn't exist"

```bash
# Vérifier les tables
SHOW TABLES;

# Réexécuter le script d'initialisation
mysql -u root -p banking_db < init.sql
```

## 📚 Ressources

- [MySQL Documentation](https://dev.mysql.com/doc/)
- [MySQL Workbench](https://www.mysql.com/products/workbench/)
- [Hibernate ORM](https://hibernate.org/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)

## 📞 Support

Pour des problèmes de configuration de base de données:

1. Vérifier que MySQL est installé et en cours d'exécution
2. Vérifier les identifiants de connexion
3. Vérifier les permissions utilisateur
4. Consulter les logs de l'application
5. Vérifier la documentation MySQL

---

**Configuration de Base de Données Complète!** ✅
