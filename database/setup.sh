#!/bin/bash

# Banking Agent Application - Database Setup Script
# This script initializes the MySQL database and loads sample data

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Configuration
DB_HOST="${1:-localhost}"
DB_PORT="${2:-3306}"
DB_USER="${3:-root}"
DB_NAME="banking_db"

echo -e "${YELLOW}================================${NC}"
echo -e "${YELLOW}Banking Agent - Database Setup${NC}"
echo -e "${YELLOW}================================${NC}"
echo ""

# Check if MySQL is installed
if ! command -v mysql &> /dev/null; then
    echo -e "${RED}Error: MySQL client is not installed${NC}"
    exit 1
fi

echo -e "${YELLOW}Configuration:${NC}"
echo "  Host: $DB_HOST"
echo "  Port: $DB_PORT"
echo "  User: $DB_USER"
echo "  Database: $DB_NAME"
echo ""

# Prompt for password
echo -n "Enter MySQL password for user '$DB_USER': "
read -s DB_PASSWORD
echo ""
echo ""

# Test connection
echo -e "${YELLOW}Testing MySQL connection...${NC}"
if ! mysql -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" -p"$DB_PASSWORD" -e "SELECT 1" &> /dev/null; then
    echo -e "${RED}Error: Cannot connect to MySQL${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Connection successful${NC}"
echo ""

# Create database and tables
echo -e "${YELLOW}Creating database and tables...${NC}"
mysql -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" -p"$DB_PASSWORD" < "$(dirname "$0")/init.sql"
echo -e "${GREEN}✓ Database and tables created${NC}"
echo ""

# Load sample data
echo -e "${YELLOW}Loading sample data...${NC}"
mysql -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" -p"$DB_PASSWORD" < "$(dirname "$0")/sample_data.sql"
echo -e "${GREEN}✓ Sample data loaded${NC}"
echo ""

# Verify setup
echo -e "${YELLOW}Verifying setup...${NC}"
mysql -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" -p"$DB_PASSWORD" -e "
USE $DB_NAME;
SELECT 'Users' as Table_Name, COUNT(*) as Record_Count FROM users
UNION ALL
SELECT 'Subscribers', COUNT(*) FROM subscribers
UNION ALL
SELECT 'Merchants', COUNT(*) FROM merchants
UNION ALL
SELECT 'Transactions', COUNT(*) FROM transactions;
"
echo ""

echo -e "${GREEN}================================${NC}"
echo -e "${GREEN}Database setup completed!${NC}"
echo -e "${GREEN}================================${NC}"
echo ""
echo -e "${YELLOW}Next steps:${NC}"
echo "1. Update src/main/resources/application.properties with your database credentials"
echo "2. Run: mvn spring-boot:run"
echo "3. Access the application at: http://localhost:8080"
echo "4. Login with: admin / admin123"
echo ""
