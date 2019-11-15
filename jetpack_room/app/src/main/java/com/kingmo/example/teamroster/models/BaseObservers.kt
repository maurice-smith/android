package com.kingmo.example.teamroster.models

import io.reactivex.FlowableSubscriber
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscription

abstract class BaseObserver<T>: Observer<T> {
    override fun onComplete() {
        //no-op
    }

    override fun onSubscribe(d: Disposable) {
        //no-op
    }

    override fun onNext(result: T) {
        //no-op
    }

    override fun onError(error: Throwable) {
        //no-op
    }
}

abstract class BaseFlowableSubscriber<T>: FlowableSubscriber<T> {
    override fun onComplete() {
        //no-op
    }

    override fun onSubscribe(s: Subscription) {
        //no-op
    }

    override fun onNext(result: T) {
        //no-op
    }

    override fun onError(error: Throwable?) {
        //no-op
    }
}