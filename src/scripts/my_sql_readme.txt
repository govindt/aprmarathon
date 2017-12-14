1. $ mysql -u root -p
2. mysql> create database if not exists wine;
3. mysql> quit;

4. Create user
mysql> GRANT ALL PRIVILEGES ON *.* TO 'wine'@'%' IDENTIFIED BY 'wine' WITH GRANT OPTION;

4. $ mysql -u wine -p wine
5. source mysql_create_tables.sql

122097935