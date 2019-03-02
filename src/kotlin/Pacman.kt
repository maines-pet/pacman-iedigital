class Pacman() {
    lateinit var game: Game
    var x: Int = 0
        private set
    var y: Int = 0
        private set
    var face: Direction = Direction.EAST
        private set

    fun place(x: Int, y: Int, f: String) {
        if (Direction.values().any { it.name == f }) {
            if (game.isWithinBounds(x, y)){
                this.x = x
                this.y = y
                this.face = Direction.valueOf(f)
            }
        }
    }

    fun move() {
        var newX = this.x
        var newY = this.y
        when (this.face) {
            Direction.NORTH -> newY++
            Direction.EAST -> newX++
            Direction.SOUTH -> newY--
            Direction.WEST -> newX--
        }
        if (game.isWithinBounds(newX, newY)) {
            this.x = newX
            this.y = newY
        }
    }

    fun left() {
        val mapping = Direction.values().associateBy { it.ordinal }
        val index = if (this.face.ordinal - 1 < 0) 3 else this.face.ordinal - 1
        this.face = mapping.getValue(index)
    }

    fun right() {
        val mapping = Direction.values().associateBy { it.ordinal }
        val index = (this.face.ordinal + 1) % 4
        this.face = mapping.getValue(index)
    }

    fun report(): String {
        return """Pacman @ ${this.x},${this.y} facing ${this.face}."""
    }

}






