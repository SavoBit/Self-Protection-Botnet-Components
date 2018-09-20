# Self-Protection Botnet Components

This folder contains the code artifacts associated to the SELFNET Self-Protection Use Case, including the implementation of the main parts for starting-up a Zeus-based botnet as well as an actuator capable of mitigating its effects to supply the SELFNET framework with self-protection capabilities against distributed potential cyber-attacks.

This folder provides the installation of a Command & Control (C&C) Server making up a given Zeus-based botnet and a Java application that can be executed to recruit real or fake/cloned zombies in the botnet. This Java application can be used to launch real zombies, installed in users' devices (UEs), or as a mitigation strategy to emulate real zombies' behaviour and thus prevent the execution of crippling cyber-attacks.

## Installation of the C&C Server

Objective: C&C Server Dashboard and management of a C&C Server on the attackers' power.

This component shows all zombies recruited under the botnet that can be used as source points in executing cyber-attacks (e.g. an HTTP Flood attack). After C&C installation, the C&C dashboard will be accessible at http://localhost/botload

In order to install the C&C Server, check the following requirement before starting the installation process.

### Requirements

In order to successfully execute SELFNET-GUI the following requirement is needed:

* A [LAMP](https://howtoubuntu.org/how-to-install-lamp-on-ubuntu) (Linux/Ubuntu 14.04 or above, Apache 2.4, MySQL 5.7 and PHP 7), installed by following a traditional installation by default.

After LAMP installation, follow the next steps configuring all the software with this account credential: root/test (name/pass).

* Copy [botload](https://github.com/Selfnet-5G/Self-Protection-Botnet-Components/tree/master/C%26C%20Server/botload/) folder in /var/www/html/
* Open http://localhost/phpmyadmin (or execute the steps in the MySQL command prompt)

Within the phpMyAdmin tool, follow the next list of ations:

* Create a new database called <botload>, with Collation = utf8_general_ci
* Within the <botload> database, click on <Import> and browse in your computer the SQL file <database.sql>
* Click on Go (at the bottom of that web page) to create the required tables in the botload database
* In the db_bots table you can review the list of bots/zombies recruited by the botnet

## Installation of the HoneyNet

Objective: Execution of a real or fake/cloned zombie that will be recruited in a C&C Server.

This component will request time to time actions to the C&C Server, based on a frequency number passed by parameter (in seconds). Actions demanded by the C&C will be executed (e.g. an HTTP Flood attack) in case the component is executed as a real zombie; otherwise, actions (cyber-attacks) are ignored.

### Requirements

In order to install the HoneyNet, check the following requirement before starting the installation process.

* Java JRE 8

Copy the [ZombieBot.jar](https://github.com/Selfnet-5G/Self-Protection-Botnet-Components/blob/master/HoneyNet/ZombieBot.jar) application into the home folder.

### Usage

Type one of the following commands to execute the real or fake/cloned zombie:

```Real Zombie: $ java -jar ZombieBot.jar -type zeus -freq 10 -v true <C&C IP address>```

```Cloned Zombie: $ java -jar ZombieBot.jar -type zeus -freq 10 -uid -bot fake <C&C IP address>```

Additional parameters can be set, which can be seen by running the application directly without parameters:

```$ java -jar ZombieBot.jar```

## License

The Self-Protection Botnet Components are published under GNU General Public License v3.0. Please see the LICENSE file for more details.
