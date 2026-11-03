# Battleship - Class Diagram (model)

```mermaid
classDiagram
    direction TB

    %% ===== Enums =====
    class CellState {
        <<enumeration>>
        WATER
        SHIP
        WATER_HIT
        SHIP_HIT
    }

    class ShotResult {
        <<enumeration>>
        MISS
        HIT
        SUNK
        INVALID
    }

    class Orientation {
        <<enumeration>>
        HORIZONTAL
        VERTICAL
    }

    %% ===== Ships =====
    class Ship {
        <<abstract>>
        -id: int
        -name: String
        -symbol: char
        -size: int
        -orientation: Orientation
        -startRow: int
        -startCol: int
        -hitSegments: boolean[]
        -placed: boolean
        -sunk: boolean
        +Ship(id: int, name: String, symbol: char, size: int, orientation: Orientation)
        +registerHit(segmentIndex: int) void
        +occupies(row: int, col: int) boolean
        +segmentIndexAt(row: int, col: int) int
        +isSegmentHit(segmentIndex: int) boolean
        +setPosition(row: int, col: int, orientation: Orientation) void
        +setOrientation(orientation: Orientation) void
        +getCoordinates() List~String~
        +getHits() int
        +isSunk() boolean
        +getSize() int
        +getDescription()* String
    }

    class Carrier {
        +Carrier(id: int, orientation: Orientation)
        +getDescription() String
    }
    class Battleship {
        +Battleship(id: int, orientation: Orientation)
        +getDescription() String
    }
    class Cruiser {
        +Cruiser(id: int, orientation: Orientation)
        +getDescription() String
    }
    class Submarine {
        +Submarine(id: int, orientation: Orientation)
        +getDescription() String
    }
    class Destroyer {
        +Destroyer(id: int, orientation: Orientation)
        +getDescription() String
    }

    %% ===== Board =====
    class Board {
        -size: int
        -grid: CellState[][]
        -ships: List~Ship~
        -shipByCoordinate: Map~String, Ship~
        -shotsReceived: int
        -hitsReceived: int
        -missesReceived: int
        -sunkShips: int
        -locked: boolean
        -lastShot: String
        +Board(size: int)
        +placeShip(ship: Ship, row: int, col: int, orientation: Orientation) boolean
        +rotateShip(ship: Ship) boolean
        +removeShip(ship: Ship) void
        +canPlace(ship: Ship, row: int, col: int, orientation: Orientation) boolean
        +receiveShot(row: int, col: int) ShotResult
        +isInside(row: int, col: int) boolean
        +getCellState(row: int, col: int) CellState
        +getShipAt(row: int, col: int) Ship
        +placeFleetRandomly(fleet: List~Ship~, random: Random) void
        +lock() void
        +allShipsSunk() boolean
        +getRemainingShips() int
        +coordinateKey(row: int, col: int)$ String
    }

    %% ===== Players =====
    class Player {
        <<abstract>>
        -name: String
        #board: Board
        -shotsFired: int
        -hits: int
        -misses: int
        -sunkEnemyShips: int
        -turnsPlayed: int
        -score: int
        -ready: boolean
        -lastShot: String
        #shotHistory: Set~String~
        +Player(name: String, board: Board)
        +fireAt(opponent: Board, row: int, col: int) ShotResult
        +registerResult(row: int, col: int, result: ShotResult) void
        +alreadyShot(row: int, col: int) boolean
        +addScore(points: int) void
        +setBoard(board: Board) void
        +getAccuracy() double
        +isAutomatic()* boolean
    }

    class HumanPlayer {
        +HumanPlayer(name: String, board: Board)
        +isAutomatic() boolean
    }

    class AIPlayer {
        -random: Random
        -targetQueue: Deque~String~
        -lastHitRow: int
        -lastHitCol: int
        -huntMode: boolean
        +AIPlayer(name: String, board: Board)
        +chooseTarget(opponent: Board) String
        +enqueueNeighbors(row: int, col: int, boardSize: int) void
        +registerResult(row: int, col: int, result: ShotResult) void
        +resetTargetMode() void
        +isAutomatic() boolean
    }

    %% ===== Relationships =====
    Ship <|-- Carrier
    Ship <|-- Battleship
    Ship <|-- Cruiser
    Ship <|-- Submarine
    Ship <|-- Destroyer

    Player <|-- HumanPlayer
    Player <|-- AIPlayer

    Player "1" *-- "1" Board : owns
    Board "1" *-- "5" Ship : fleet
    Board ..> CellState : grid cells
    Board ..> ShotResult : returns
    Ship --> Orientation : has
    Player ..> ShotResult : uses
```

## How to read it

- Attributes: `visibility name: Type`. Methods: `visibility name(param: Type) ReturnType` (the renderer shows `: ReturnType`).
- Visibility: `-` private, `+` public, `#` protected.
- `<<abstract>>` above the name = abstract class. A method in *italic* is abstract (`getDescription`, `isAutomatic`). A method underlined is static (`coordinateKey`).
- Hollow triangle arrow = inheritance (`extends`). Filled diamond = composition (the whole owns the part).
- Ship sizes: Carrier 5, Battleship 4, Cruiser 3, Submarine 3, Destroyer 2.
- `shipByCoordinate` (Map) links keys like `"B3"` to the `Ship` occupying that cell.
- Getters/setters that only return or assign a field are omitted for readability.
- This diagram covers only the `model` package.