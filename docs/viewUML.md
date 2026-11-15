# Battleship - Class Diagram (view)

```mermaid
classDiagram
    direction TB

    class App {
        +start(stage: Stage) void
        +main(args: String[])$ void
    }

    class GameWindow {
        -stage: Stage
        -scene: Scene
        -gameController: GameController
        -currentScreen: Screen
        +GameWindow(stage: Stage, gameController: GameController)
        +show() void
        +update() void
    }

    class Screen {
        <<abstract>>
        #gameController: GameController
        #window: GameWindow
        #TITLE_FONT: String$
        #BODY_FONT: String$
        +Screen(gameController: GameController, window: GameWindow)
        #battleStatusText(player: Player) String
        +getRoot()* Parent
    }

    class MenuScreen {
        -playButton: Button
        -layout: VBox
        -titleLabel: Label
        +MenuScreen(gameController: GameController, window: GameWindow)
        +getRoot() Parent
    }

    class PreparationScreen {
        -startButton: Button
        -randomFleetButton: Button
        -layout: VBox
        -titleLabel: Label
        -infoLabel: Label
        -boardView: BoardView
        -titleBox: VBox
        -fleetCard: VBox
        -helpCard: VBox
        -sidePanel: VBox
        -contentBox: HBox
        -buttonsBox: HBox
        +PreparationScreen(gameController: GameController, window: GameWindow)
        +getRoot() Parent
        -handleCellClick(row: int, col: int) void
        -createCard(title: String, lines: String[]) VBox
        -createFleetRow(name: String, size: int) HBox
    }

    class BattleScreen {
        -enemyView: BoardView
        -playerView: BoardView
        -playerName: Label
        -enemyName: Label
        -playerStatusLabel: Label
        -enemyStatusLabel: Label
        -turnStatusLabel: Label
        -infoLabel: Label
        -turnBox: VBox
        -layout: VBox
        -hBoxViews: HBox
        -vBoxPlayer: VBox
        -vBoxEnemy: VBox
        -viewsRegion: Region
        -hBoxSubtitles: HBox
        +BattleScreen(gameController: GameController, gameWindow: GameWindow)
        +getRoot() Parent
        -handlePlayerShot(row: int, col: int) void
        -playEnemyTurn() void
        -refreshStatus() void
        -createLegendItem(color: String, text: String) HBox
    }

    class EndScreen {
        -winnerName: Label
        -playAgainButton: Button
        -layout: VBox
        -winnerBox: VBox
        -columnsBox: HBox
        +EndScreen(gameController: GameController, gamesWindow: GameWindow)
        +getRoot() Parent
        -createColumn(player: Player) VBox
        -createStatRow(label: String, value: String) HBox
    }

    class PlaceholderScreen {
        -layout: VBox
        -titleLabel: Label
        +PlaceholderScreen(gameController: GameController, window: GameWindow, title: String)
        +getRoot() Parent
    }

    class BoardView {
        -board: Board
        -hideShips: boolean
        -clickable: boolean
        -buttons: Button[][]
        -cellSize: int
        -grid: GridPane
        -listener: CellClickListener
        -selectedShip: Ship
        +BoardView(board: Board, hideShips: boolean, clickable: boolean, cellSize: int)
        +setSelectedShip(selectedShip: Ship) void
        +getGrid() GridPane
        +setListener(listener: CellClickListener) void
        +refresh() void
        -paintCell(button: Button, state: CellState, row: int, col: int) void
        +showPreview(row: int, col: int) void
    }

    class CellClickListener {
        <<interface>>
        +onCellClick(row: int, col: int) void
    }

    class GameController
    class Board
    class Ship {
        <<abstract>>
    }
    class Player {
        <<abstract>>
    }
    class CellState {
        <<enumeration>>
    }

    App --> GameWindow : creates
    App --> GameController : creates
    GameWindow "1" o-- "1" Screen : currentScreen
    GameWindow --> GameController : reads state
    Screen --> GameController : calls
    Screen --> GameWindow : asks to update
    Screen ..> Player : battleStatusText

    Screen <|-- MenuScreen
    Screen <|-- PreparationScreen
    Screen <|-- BattleScreen
    Screen <|-- EndScreen
    Screen <|-- PlaceholderScreen

    PreparationScreen "1" *-- "1" BoardView
    BattleScreen "1" *-- "2" BoardView
    EndScreen ..> BoardView
    EndScreen ..> Player : shows stats of
    BoardView --> CellClickListener : notifies
    BoardView --> Ship : selectedShip
    BoardView ..> Board : reads
    BoardView ..> CellState : paints by
```

## How to read it

- `<<abstract>>` = abstract class. `getRoot()` is abstract (marked with `*`): each screen builds its own layout.
- `<<interface>>` = contract. `BoardView` tells whoever listens that a cell was clicked, without knowing who listens.
- Hollow triangle = inheritance. Filled diamond = composition. Hollow diamond = aggregation. Dashed arrow = uses.
- Methods marked with `$` are static, as are the font constants (`TITLE_FONT`, `BODY_FONT`).
- JavaFX types (`Stage`, `Scene`, `Button`, `Label`, `VBox`, `HBox`, `Region`, `GridPane`, `Parent`) appear only as attribute and parameter types.
- `PlaceholderScreen` is not used by the game anymore (it was the first test screen). It can be deleted.

## Screen for each state

| `GameState` | Screen |
|---|---|
| `MENU` | `MenuScreen` |
| `PREPARATION` | `PreparationScreen` |
| `PLAYER_ATTACK`, `ENEMY_ATTACK` | `BattleScreen` |
| `END_GAME` | `EndScreen` |

`GameWindow.update()` reads `gameController.getState()` and shows the matching screen, always on the same `Scene` (`scene.setRoot`). After an action that changes the state, a screen calls `window.update()`.

## What each screen shows

| Screen | Content |
|---|---|
| `MenuScreen` | Game title and the play button |
| `PreparationScreen` | Title banner, own board, fleet card (names and sizes), help card, buttons to shuffle and start |
| `BattleScreen` | Turn banner, both boards with a separator bar, status of each player, color legend |
| `EndScreen` | Winner banner, one column per player (stats and small final board), play again button |

## Notes

- The view never decides rules. It calls one controller method per click and redraws from the model.
- The enemy board is created with `hideShips = true`: cells with `SHIP` are drawn like water.
- `cellSize` sets the size of each cell, so the same `BoardView` is used in three sizes: 54 (preparation), 42 (battle) and 28 (end screen).
- `BoardView.refresh()` repaints every cell. `showPreview` adds a green or red border to the cells the selected ship would occupy.
- Enemy shots are called by `BattleScreen`, one per call, with a 0.8 s pause (`PauseTransition`) between them.
- Fonts: `TITLE_FONT` is used on titles and `BODY_FONT` on the rest. Each screen sets `BODY_FONT` on its root layout and the children inherit it.
- The helper methods `createCard`, `createFleetRow`, `createLegendItem`, `createColumn` and `createStatRow` only build pieces of layout; none of them touches the game rules.

## Changes since the previous version

- `BoardView`: new parameter and attribute `cellSize`; row (1-10) and column (A-J) labels around the grid; sunk ships get a distinct color.
- `Screen`: new constants `TITLE_FONT` and `BODY_FONT`; `statusText` was removed (the end screen now reads each stat directly); `battleStatusText` is the short version used in battle.
- `PreparationScreen`: new layout with title banner, fleet card, help card and bottom buttons (`createCard`, `createFleetRow`).
- `BattleScreen`: turn banner (`turnStatusLabel`, `infoLabel`, `turnBox`), separator bar (`viewsRegion`), color legend (`hBoxSubtitles`, `createLegendItem`).
- `MenuScreen`: game title and larger play button.
- `EndScreen`: winner banner and two stat columns with small boards (`createColumn`, `createStatRow`).
