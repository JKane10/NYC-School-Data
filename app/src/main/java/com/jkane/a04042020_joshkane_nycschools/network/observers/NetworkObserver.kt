package com.jkane.a04042020_joshkane_nycschools.network.observers

import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class NetworkObserver : Observer<Any> {
    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: Any) {

    }

    override fun onError(e: Throwable) {
        Log.d("NetworkObserver", e.toString())
    }
}