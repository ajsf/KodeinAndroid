package co.ajsf.kodeinapp.common.domain.error

sealed class Error {
    class NetWorkConnection : Error()
    class ServerError : Error()

    abstract class FeatureError : Error()
}