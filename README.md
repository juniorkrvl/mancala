# Mancala

This project is an implementation of the Mancala game.

## Live Solution

Check the deployed solution through the following link:

https://mancala.jvnior.com/

## Technology Stack

The technology behind the solution involves a client-server architecture. The client interface is made using Vue.js framework while the server is an API written in Java using the Spring Boot framework

# API Code Architecture

The design inspiration comes from the Hexagonal Architecture.

The project is divided in three main parts

- Domain
- Application
- Web

The fundamental principle behind these layers is to regulate dependency directions.

As demonstrated in the hexagonal architecture model, the **Domain** don't have any external dependencies. It does not know about any other part of the system and it exclusively focuses on modelling business problems.

The **Application** knows about the domain and orchestrates the domain objects with infrastructure to store them in the persistence layer.

Finally, the **Web** layer serves as the system user interface where we have the http controllers and where we map requests and responses to the client.

## Domain Layer

The Domain package encapsulates the game's core mechanics. The main components include:

- **Board**: Represents the mancala board and handles everything that has to do with moving stones from one place to another, checking players scores and playable range

- **Game**: Represents a Mancala match, setting up the board and players. It also monitors player turns. 

- **Pit**: A pit is the representation of the playable part of the board, the Pit class encapsulates all the actions that can be done in a pit.

- **PitRange**: The pit range represents a playable range where players can grab stones from.

- **Player**: The Player class represents a player in a mancala match and its main responsibility is to keep track of players scores and identification as well as the pit range that the player owns

## Application

The application layer bridges the gap between the domain layer and the infrastructure coordinating application activities without meddling with business logic.

The main components of the application layer are:

- **GameService**: The game service is the bridge between the domain and persistence layer, it keeps track of the created games, it fetches games from the repository and trigger action in the Game domain object such as `makeMove` method.

- **GameRepository**: An interface that inverts dependency on concrete repository implementations. Its direct usage by GameService allows for easy swapping of implementations.

- **InMemoryGameRepository**: A concrete implementation of the GameRepository interface, storing game instances in memory. While not suitable for production environments, it simplifies this implementation.

  The `InMemoryGameRepository` could in fact be in its own **Infrastructure** layer, but for this project size, I opted to keep it in the **Application** layer. The important thing to note is that the dependency should never be directly to the `InMemoryRepository` but instead should always be throught its interface `GameRepository`

## Web

The web layer is where I kept everything related to how the application is consumed. It is basically the UI of the application. It contains the controllers and some DTO's that I use to transfer data from and to the **Application** layer.

The main components from this layer are:

- **GameController**: Serves as the application's primary entry point, receiving requests to manage a game. The controller interacts directly with the `GameService` to provide end-users with essential game operations.  
My approach to configure the endpoint was basically use the `/game` as a resource and then use the HTTP methods as actions to act upon a game.
  - `POST` `/api/game` to create a new game
  - `PUT` `/api/game` to perform a move
  - `GET` `/api/game/<game_id>` to get the state of the game
  - `DELETE` `/api/game/<game_id>` to delete a game  

- **ApplicationExceptionHandler**: This class is responsible to map the business exceptions into HTTP exceptions. I created a new type to represent all the errors of the API called `ErrorResponse`. The `ErrorResponse` is used to keep all the error messages from the API consistent.

- **Data Transfer Objects**: I introduced some DTOs to facilitate the transit of data from the controllers to the application layer.  
  -  `MoveRequest` represents a move that the current player wants to make.  
  -  `GameStateResponse` represents the state of the game. Whenever a new game is created or modified the state of the game will be returned.  
  - `ErrorResponse` represents all API errors and is used to keep the format of the error to the consumer of the API consistent.

- **CustomErrorController**: The custom error controller serves to deal with errors that don't happen in the application layer, for instance someone tried to call the endpoint `/api/scores` which in this case does not exists.