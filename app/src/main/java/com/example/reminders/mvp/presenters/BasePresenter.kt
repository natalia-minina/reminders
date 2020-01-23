package com.example.reminders.mvp.presenters

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView


abstract class BasePresenter<View : MvpView> : MvpPresenter<View>() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    protected open fun compositeRemove(disposableVariable: Disposable?) {
        if (disposableVariable != null) {
            compositeDisposable.remove(disposableVariable)
        }
    }

    protected open fun compositeAdd(disposable: Disposable?) {
        if (disposable != null) {
            compositeDisposable.add(disposable)
        }
    }
}