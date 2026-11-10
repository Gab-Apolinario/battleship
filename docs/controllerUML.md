# Battleship - Class Diagram (controller)

Rendered automatically by GitHub. To export an image for the PDF, use `controllerUML.png` or paste the code block into https://mermaid.live.

```mermaid
classDiagram
    direction LR

    %% ===== Controller =====
    class GameState {
        <<enumeration>>
        MENU
        PREPARATION
        PLAYER_ATTACK
        ENEMY_ATTACK
        END_GAME
    }

    class GameController {
        -state: GameState
        -human: HumanPlayer
        -ai: AIPlayer
        -playerFleet: List~Ship~
        -selectedShip: Ship
        -winner: Player
        +GameController()
        +startGame() void
        +selectShip(ship: Ship) void
        +placeSelectedShip(row: int, col: int) boolean
        +rotateSelectedShip() boolean
        +randomizePlayerFleet() void
        +finishPreparation() boolean
        +playerAttack(row: int, col: int) ShotResult
        +enemyAttack() ShotResult
        -isGameOver(hit: Board) boolean
        +getState() GameState
        +getHuman() HumanPlayer
        +getAi() AIPlayer
        +getPlayerFleet() List~Ship~
        +getSelectedShip() Ship
        +getWinner() Player
    }

    %% ===== Model classes used by the controller (members omitted, see model diagram) =====
    class Player {
        <<abstract>>
    }
    class HumanPlayer
    class AIPlayer
    class Board
    class Ship {
        <<abstract>>
    }
    class ShotResult {
        <<enumeration>>
    }

    %% ===== Relationships =====
    Player <|-- HumanPlayer
    Player <|-- AIPlayer

    GameController --> GameState : current state
    GameController "1" *-- "1" HumanPlayer : human
    GameController "1" *-- "1" AIPlayer : ai
    GameController "1" *-- "5" Ship : playerFleet
    GameController --> Player : winner
    GameController ..> Board : fires at
    GameController ..> ShotResult : returns
    Player "1" *-- "1" Board : owns
```

## How to read it

- Attributes: `visibility name: Type`. Methods: `visibility name(param: Type) ReturnType`.
- Visibility: `-` private, `+` public. `isGameOver` is private: only `playerAttack` and `enemyAttack` call it.
- Filled diamond = composition (the controller creates and owns the part). The numbers are how many parts: one `HumanPlayer`, one `AIPlayer`, five `Ship` in `playerFleet`.
- Dashed arrow = uses (`fires at` a `Board`, `returns` a `ShotResult`).
- `winner` is a `Player`, so it can hold either a `HumanPlayer` or an `AIPlayer` (inheritance).
- Model classes appear without members to keep the diagram readable; their details are in `modelUML.md`.

## State flow

| From | Event | To |
|---|---|---|
| `MENU` | start game | `PREPARATION` |
| `PREPARATION` | all ships placed, battle starts | `PLAYER_ATTACK` |
| `PLAYER_ATTACK` | `MISS` | `ENEMY_ATTACK` |
| `PLAYER_ATTACK` | `HIT` or `SUNK` (ships left) | `PLAYER_ATTACK` |
| `PLAYER_ATTACK` | `SUNK` on the last ship | `END_GAME` |
| `ENEMY_ATTACK` | `MISS` | `PLAYER_ATTACK` |
| `ENEMY_ATTACK` | `HIT` or `SUNK` (ships left) | `ENEMY_ATTACK` |
| `ENEMY_ATTACK` | `SUNK` on the last ship | `END_GAME` |
| `END_GAME` | play again or back to menu | `PREPARATION` / `MENU` |

An `INVALID` shot changes nothing.

## Notes

- The controller imports nothing from `javafx`. The view calls one method per click and reads the result and the getters to redraw.
- Every action method starts with a guard: if the state is not the required one, it does nothing.