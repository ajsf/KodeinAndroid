package co.ajsf.kodeinapp.common.presenter

open class BasePresenter<T : BasePresenter.View> {

    interface View {
        fun showLoading(): Unit
        fun hideLoading(): Unit
    }

    var view: T? = null

    open fun resume(view: T) {
        this.view = view
    }

    open fun pause() {
        this.view = null
    }
}