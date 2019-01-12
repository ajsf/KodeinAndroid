package co.ajsf.kodeinapp.common.domain.repository

sealed class CachePolicy {
    object NetworkFirst : CachePolicy()
    object LocalFirst : CachePolicy()
    object LocalOnly : CachePolicy()
    object NetworkOnly : CachePolicy()
}