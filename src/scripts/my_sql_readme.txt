1. $ mysql -u root -p
2. mysql> create database if not exists wine;
3. mysql> quit;

4. Create user
mysql> GRANT ALL PRIVILEGES ON *.* TO 'wine'@'%' IDENTIFIED BY 'wine' WITH GRANT OPTION;

If you cannot figure out why you get Access denied, remove from the user table all rows that have Host values containing wildcards (rows that contain '%' or '_' characters). A very common error is to insert a new row with Host='%' and User='some_user', thinking that this enables you to specify localhost to connect from the same machine. The reason that this does not work is that the default privileges include a row with Host='localhost' and User=''. Because that row has a Host value 'localhost' that is more specific than '%', it is used in preference to the new row when connecting from localhost! The correct procedure is to insert a second row with Host='localhost' and User='some_user', or to delete the row with Host='localhost' and User=''. After deleting the row, remember to issue a FLUSH PRIVILEGES statement to reload the grant tables.

mysql> GRANT ALL PRIVILEGES ON *.* TO 'wine'@'%' IDENTIFIED BY 'wine' WITH GRANT OPTION;

4. $ mysql -u wine -p wine
5. source mysql_create_tables.sql

122097935