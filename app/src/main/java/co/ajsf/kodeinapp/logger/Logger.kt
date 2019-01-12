package co.ajsf.kodeinapp.logger

interface Logger {
    fun log(tag: String, level: Level = Level.Verbose, message: String)

    sealed class Level {
        object Info : Level()
        object Debug : Level()
        object Warning : Level()
        object Error : Level()
        object Verbose : Level()
    }
}