# XBlast2016
XBlast2016 is a bomberman style video game developed by Adrien Vandenbroucque and Guillaume Michel for the course "Pratique de la programmation orientée objet" at EPFL during spring semester 2016. 
## Main project
We realised the original multiplayer XBlast game with all the functionalities of Bomberman. This game requires 4 players in order to start. This project is located in the master branch. There is a server and a client java executable that has to be launched with some arguments in the terminal in order to play. 
![Alt text](/screenshots/normal_game.png?raw=true "")
## Improvements 
We decided to improve the game with a few bonuses we imagined by ourselves. We got the maximal grade for the bonus part. The version of the project containing all the bonuses is located in the Bonus branch.
### GUI Menu
As it was not very intuitive and convenient to launch the game from a command line, we decided to add a Graphical User Interface Menu. From this menu, it is possible create a game, that will launch a server where it is possible to customise the number of players, the map and the duration of the game. It is also possible to join a game with simply entering the ip address of the host (on the same LAN). We added victory script at the end of the game, and then the players can start again a new game.
![Alt text](/screenshots/start_screen.png?raw=true "")
![Alt text](/screenshots/create_game.png?raw=true "")
![Alt text](/screenshots/join_screen.png?raw=true "")
### Map Editor
We decided to add a map editor that would allow the users to create and customise their own maps. It is possible to edit all the blocks of the map as well as the spawn point of the players. We added a feature that allows the user to save the maps for future usage. 
![Alt text](/screenshots/map_editor.png?raw=true "")
![Alt text](/screenshots/custom_game.png?raw=true "")
### Music
We simply added the possibility to play a music during the game, making it more fun. The default music is a 8-bit adaptation of the soundtrack of Game of Thrones. 
