@echo off
REM Banking Agent Application - Database Setup Script for Windows
REM This script initializes the MySQL database and loads sample data

setlocal enabledelayedexpansion

REM Configuration
set DB_HOST=%1
if "!DB_HOST!"=="" set DB_HOST=localhost

set DB_PORT=%2
if "!DB_PORT!"=="" set DB_PORT=3306

set DB_USER=%3
if "!DB_USER!"=="" set DB_USER=root

set DB_NAME=banking_db

REM Colors
set GREEN=[92m
set YELLOW=[93m
set RED=[91m
set NC=[0m

echo.
echo ================================
echo Banking Agent - Database Setup
echo ================================
echo.

REM Check if MySQL is installed
where mysql >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo Error: MySQL client is not installed or not in PATH
    pause
    exit /b 1
)

echo Configuration:
echo   Host: !DB_HOST!
echo   Port: !DB_PORT!
echo   User: !DB_USER!
echo   Database: !DB_NAME!
echo.

REM Prompt for password
set /p DB_PASSWORD="Enter MySQL password for user '!DB_USER!': "
echo.

REM Test connection
echo Testing MySQL connection...
mysql -h !DB_HOST! -P !DB_PORT! -u !DB_USER! -p!DB_PASSWORD! -e "SELECT 1" >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo Error: Cannot connect to MySQL
    pause
    exit /b 1
)
echo Connection successful
echo.

REM Create database and tables
echo Creating database and tables...
mysql -h !DB_HOST! -P !DB_PORT! -u !DB_USER! -p!DB_PASSWORD! < "%~dp0init.sql"
if %ERRORLEVEL% NEQ 0 (
    echo Error: Failed to create database and tables
    pause
    exit /b 1
)
echo Database and tables created
echo.

REM Load sample data
echo Loading sample data...
mysql -h !DB_HOST! -P !DB_PORT! -u !DB_USER! -p!DB_PASSWORD! < "%~dp0sample_data.sql"
if %ERRORLEVEL% NEQ 0 (
    echo Error: Failed to load sample data
    pause
    exit /b 1
)
echo Sample data loaded
echo.

REM Verify setup
echo Verifying setup...
mysql -h !DB_HOST! -P !DB_PORT! -u !DB_USER! -p!DB_PASSWORD! -e "USE !DB_NAME!; SELECT 'Users' as Table_Name, COUNT(*) as Record_Count FROM users UNION ALL SELECT 'Subscribers', COUNT(*) FROM subscribers UNION ALL SELECT 'Merchants', COUNT(*) FROM merchants UNION ALL SELECT 'Transactions', COUNT(*) FROM transactions;"
echo.

echo ================================
echo Database setup completed!
echo ================================
echo.
echo Next steps:
echo 1. Update src\main\resources\application.properties with your database credentials
echo 2. Run: mvn spring-boot:run
echo 3. Access the application at: http://localhost:8080
echo 4. Login with: admin / admin123
echo.

pause
