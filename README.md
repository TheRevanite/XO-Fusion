# XO Fusion

## Project Objective

The goal of this project is to create an enhanced graphical user interface (GUI)-based spinoff of the classic Tic Tac Toe game using Java Swing. The game incorporates a visually striking neon theme, offering an engaging and vibrant experience for players. It features two primary game modes—Multiplayer and Player vs. CPU—to cater to different user preferences. 

Additionally, a unique gameplay rule has been introduced, limiting each player to a maximum of three moves on the board at any given time. When a player exceeds this limit, their oldest move is automatically removed, encouraging strategic thinking and adding an element of dynamic gameplay.

The project focuses on crafting a highly interactive and visually appealing user interface, leveraging Java Swing components. It also emphasizes object-oriented programming (OOP) principles to efficiently manage the game's state, logic, and user interactions, ensuring the game remains modular, maintainable, and ready for future updates or modifications.

## Design and Functionality Overview

The game begins with an aesthetically designed home screen offering multiple game modes and user options such as:

- Multiplayer
- Player vs. CPU
- Instructions
- Exit

Once a player selects their preferred game mode, the core functionality of the game is activated. Players can interact with the board, make moves, and engage with the game’s rules.

## GUI Layout and Aesthetic Theme

The game employs a neon color scheme to create an eye-catching, futuristic feel. The background is set to a dark tone, while interactive elements like buttons and status labels are highlighted in baby blue and hot pink. These color choices not only enhance the visual experience but also ensure good contrast, making the interface easy to navigate.

### Key Design Elements:

- **Buttons**: The buttons for game options and the Tic Tac Toe grid are styled with rounded corners, bold fonts, and dynamic color changes on mouse hover. The color transitions between neon pink and blue create an energetic interaction with the user.
- **Text and Labels**: Game status, such as whose turn it is or if someone has won, is clearly displayed at the bottom of the screen using bold white text.

The interface is responsive and visually balanced, using `BorderLayout` for container arrangement and `GridLayout` for the Tic Tac Toe board itself (a 3x3 grid of buttons).

## Game Modes: Multiplayer and Player vs. CPU

The game supports two main modes:

1. **Multiplayer**: Two players can take turns placing their respective marks ('X' and 'O') on the board.
2. **Player vs. CPU**: One player (‘X’) competes against the computer (‘O’), which makes random moves on the board. The CPU’s moves are implemented through a simple random choice algorithm that checks for available spots, simulating a basic but functional opponent.

In both modes, the game alternates between players, updating the game status after each move and checking for a winner.

## Unique Rule: Limited to Three Moves

A key feature of this Tic Tac Toe implementation is the custom rule limiting each player to three active moves on the board at any given time. This introduces a layer of strategy, as players must carefully manage their moves.

- **Move Management**: The game uses two `LinkedList<Integer>` objects, `xMoves` and `oMoves`, to track the positions of the moves for players 'X' and 'O'. Once a player reaches three moves, the oldest move is removed before placing a new one. This ensures that players need to plan and think strategically.

- **Move Highlighting**: The removal of an old move is visually indicated by changing the border of the oldest move to a highlighted color (light pink) and disabling the button, signaling to the user that their move is being overwritten.

The board is updated asynchronously using a `Timer`, ensuring smooth transitions between moves.

## Game Logic

The game’s core logic is implemented through the `ButtonClickListener` class, which handles user interactions. Each button on the board corresponds to a cell in the 3x3 grid. When a user clicks a button, the following steps are executed:

1. **Move Placement**: The button clicked is marked with either 'X' or 'O' depending on whose turn it is.
2. **Move Validation**: If a player has already made three moves, the oldest move is removed before placing the new one.
3. **Winner Check**: After each move, the `checkWinner()` method is called to determine if a player has won or if the game is a draw. This method checks for winning combinations (three consecutive marks in a row, column, or diagonal) and disables all buttons if a winner is found.
4. **Turn Update**: The game alternates between players after each move, with the status label updating accordingly. In Player vs. CPU mode, the CPU automatically makes its move after the player’s turn.

## CPU AI

The CPU AI for this game is based on a random move selection approach. The `makeCpuMove()` method randomly chooses an available cell on the board, ensuring that it doesn't overwrite an already filled cell. While simple, this provides a functional opponent for the player, adding an element of unpredictability.

Future versions could include more advanced AI algorithms, such as the **Minimax algorithm**, to make the CPU more competitive.

## Conclusion

This project combines engaging gameplay with an aesthetically vibrant interface and introduces an innovative twist on the classic Tic Tac Toe game by limiting the number of active moves per player. The use of object-oriented programming principles ensures that the game’s logic is clean and modular, while the GUI provides an enjoyable and intuitive user experience.

Future enhancements could include:

- Refining the AI (e.g., using Minimax or other strategies)
- Adding multiplayer over a network
- Introducing additional gameplay features to expand the experience
