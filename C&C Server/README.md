C&C Server: Dashboard and management of a Command & Control (C&C) server on the attackers' power.

This component shows all zombies recruited under the botnet that can be used as source points in executing cyber-attacks (e.g. an HTTP Flood attack). After C&C installation, the C&C dashboard will be accessible at http://localhost/botload

Installation of the C&C Server
------------------------------

Note that all software should be configured during their installation by using this account credential: root/test (name/pass)

0. A LAMP server is required
1. Copy botload folder in /var/www/html/
2. Open http://localhost/phpmyadmin (or execute the steps in the MySQL command prompt)
   2.1. Create a new database called <botload>, with Collation = utf8_general_ci
   2.2. Within the <botload> database, click on <Import> and browse in your computer the SQL file <database.sql>
   2.3. Click on Go (at the bottom of that web page) to create the required tables in the botload database
   2.4. In the db_bots table you can review the list of bots/zombies recruited by the botnet

Software requirements:
----------------------

- Apache 2.4
- MySQL 5.7
- PHP 7
- (Optional) phpMyAdmin 4.8
