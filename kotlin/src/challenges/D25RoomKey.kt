package challenges

class D25RoomKey : Puzzle(25) {
    override fun part1() = getEncryptionKey().toString()
    override fun part2() = "N/A"

    private fun getEncryptionKey(): Long {
        val pkCard = rawInput.split("\n")[0].toLong()
        val pkDoor = rawInput.split("\n")[1].toLong()

        var pkMatch = 1L
        var encryptionKey = 1L

        while (pkMatch != pkCard) {
            pkMatch = pkMatch * 7 % 20201227
            encryptionKey = encryptionKey * pkDoor % 20201227
        }

        return encryptionKey
    }
}