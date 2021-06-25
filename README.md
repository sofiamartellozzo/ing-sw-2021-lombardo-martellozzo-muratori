# Software Engineering Project 2021

## Group PSP23 
This game is developed for the final examination of Software engineering course at Politecnico di Milano (year 2020/2021)
#### Group members
* Gianluca Lombardo (https://github.com/GianlucaLombardo13)
* Sofia Martellozzo (https://github.com/sofiamartellozzo)
* Ilaria Muratori (https://github.com/ilariamuratori0)

## Index
* [Master of Reinessance](#game)
* [Tool used](#toolused)
* [How to start](#usage)
* [Requirements](#requirements)
* [Libraries](#libraries)

<a name= "game"></a>
## Masters of Renaissance
> In Masters of Renaissance, you are an important citizen of Florence and your goal is to increase your fame and prestige. Take resources from the market and use them to buy new cards. Expand your power both in the city and in the surrounding territories! Every card gives you a production power that transforms the resources so you can store them in your strongbox. Try to use the leaders’ abilities to your advantage and don’t forget to show your devotion to the Pope!

You can play in Solo Mode, and try to fight against Lorenzo Il Magnifico, or in Multiplayers Mode (2 to 4 players) 

### Intro
![Intro](deliverables/images/IntroImage.png?raw=true)

### Login
![Login](deliverables/images/LoginImage.png?raw=true)

### Match creation
![Match creation](deliverables/images/RoomSizeImage.png?raw=true)

### Card selection
![Card selection](deliverables/images/ChooseCardsImage.png?raw=true)

### PersonalBoard
![PersonalBoard](deliverables/images/PersonalBoardImage.png?raw=true)

### End Game, Win!
![End Game](deliverables/images/EndGameImage.png?raw=true)


<a name="toolused"></a>
## Tool used
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)
* Java 8
* [JavaFx](https://openjfx.io)
* [Maven](https://maven.apache.org)
* [JUnit](https://junit.org/junit5/)

<a name="usage"></a>
## How to start
To launch the program go to `target\*`

## Client.jar
in the directory save the `target\client.jar` and from the terminal 

```bash
java -jar client.jar
```

You will be able to choose in which mode play: *CLI* or *GUI* 

## Server.jar
in the directory save the `target\server.jar` and from the terminal

```bash
java -jar server.jar
```

<a name="requirements"></a>
## Requirements
| Requirements | State |
| ------------ | ----- |
| Basic Rules  |   ✅ |
| Complete Rules |  ✅ |
| Socket       |  ✅ |
| CLI          |  ✅ |
| GUI          |  ✅ | 
| Multiple Games |  ✅ |
| Persistence   |  ❌ |
| Parameters Editor |  ❌ |
| Local Game   |  ✅ |
| Resilience   |  ✅ |

<a name="libraries"></a>
## External libraries used
The external libraries we used to implement some game's features are linked below.

| Library | Link | Use |
| ------ | ------ | ------ |
| GSON | https://github.com/google/gson | We use this library to create all the game cards, and the different boxes in the Faith Track. |

