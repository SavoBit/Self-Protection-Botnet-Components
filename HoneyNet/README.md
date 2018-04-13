ZombieBot App: Execution of a real or fake/cloned zombie that will be recruited in a Command & Control (C&C) server.

This component will request time to time actions to the C&C Server, based on a frequency number passed by parameter (in seconds). Actions demanded by the C&C will be executed (e.g. an HTTP Flood attack) in case the component is executed as a real zombie; otherwise, actions (cyber attacks) are ignored.

Type the following command to execute:

- Real Zombie: $ java -jar ZombieBot.jar -type zeus -freq 10 -v true <C&C IP address>
- Cloned Zombie: $ java -jar ZombieBot.jar -type zeus -freq 10 -uid <Real Zombie UID> -bot fake <C&C IP address>

Additional parameters can be set, which can be seen by running the application directly without parameters:

$ java -jar ZombieBot.jar

Software requirements:
----------------------

- Java JRE 8
