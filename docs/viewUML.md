# Battleship - Class Diagram (view)

Rendered automatically by GitHub. To export an image for the PDF, use `viewUML.png` or paste the code block into https://mermaid.live.

```mermaid
classDiagram
    direction TB

    class App {
        +start(stage: Stage) void
        +main(args: String[])$ void
    }

    class GameWindow {
        -stage: Stage
        -controller: GameController
        -currentScreen: Screen
        +GameWindow(stage: Stage, controller: GameController)
        +show() void
        +update() void
    }

    class Screen {
        <<abstract>>
        #controller: GameController
        #window: GameWindow
        +Screen(controller: GameController, window: GameWindow)
        +getRoot()* Parent
    }

    class MenuScreen {
        -playButton: Button
        +MenuScreen(controller: GameController, window: GameWindow)
        +getRoot() Parent
    }

    class PreparationScreen {
        -boardView: BoardView
        -fleetBox: VBox
        -randomButton: Button
        -rotateButton: Button
        -startButton: Button
        +PreparationScreen(controller: GameController, window: GameWindow)
        +getRoot() Parent
        -refreshFleetList() void
    }

    class BattleScreen {
        -enemyView: BoardView
        -playerView: BoardView
        -statusLabel: Label
        +BattleScreen(controller: GameController, window: GameWindow)
        +getRoot() Parent
        -handlePlayerShot(row: int, col: int) void
        -playEnemyTurn() void
    }

    class EndScreen {
        -resultLabel: Label
        -playAgainButton: Button
        +EndScreen(controller: GameController, window: GameWindow)
        +getRoot() Parent
    }

    class BoardView {
        -board: Board
        -hideShips: boolean
        -clickable: boolean
        -buttons: Button[][]
        -grid: GridPane
        -listener: CellClickListener
        +BoardView(board: Board, hideShips: boolean, clickable: boolean)
        +getGrid() GridPane
        +setListener(listener: CellClickListener) void
        +refresh() void
        -paintCell(button: Button, state: CellState) void
    }

    class CellClickListener {
        <<interface>>
        +onCellClick(row: int, col: int) void
    }

    class GameController
    class Board
    class CellState {
        <<enumeration>>
    }

    App --> GameWindow : creates
    GameWindow "1" o-- "1" Screen : currentScreen
    GameWindow --> GameController : reads state
    Screen --> GameController : calls
    Screen --> GameWindow : asks to update

    Screen <|-- MenuScreen
    Screen <|-- PreparationScreen
    Screen <|-- BattleScreen
    Screen <|-- EndScreen

    PreparationScreen "1" *-- "1" BoardView
    BattleScreen "1" *-- "2" BoardView
    BoardView --> CellClickListener : notifies
    BoardView ..> Board : reads
    BoardView ..> CellState : paints by
```

## How to read it

- `<<abstract>>` = abstract class. `getRoot()` is abstract (marked with `*`): each screen builds its own layout.
- `<<interface>>` = contract. `BoardView` tells whoever listens that a cell was clicked, without knowing who listens.
- Hollow triangle = inheritance. Filled diamond = composition. Hollow diamond = aggregation. Dashed arrow = uses.

## Screen for each state

| `GameState` | Screen |
|---|---|
| `MENU` | `MenuScreen` |
| `PREPARATION` | `PreparationScreen` |
| `PLAYER_ATTACK`, `ENEMY_ATTACK` | `BattleScreen` |
| `END_GAME` | `EndScreen` |

`GameWindow.update()` reads `controller.getState()` and shows the matching screen. After any action, a screen calls `window.update()`.

## Notes

- The view never decides rules. It calls one controller method per click and redraws from the model.
- The enemy board is created with `hideShips = true`: cells with `SHIP` are drawn like water.
- The enemy shots (`enemyAttack`) are called by `BattleScreen`, one per call, with a short pause between them.