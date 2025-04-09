package coroutines

interface Continuation<T> {
    fun resumeWith(result: Result<T>)
}


abstract class BaseContinuation<T>(
    protected val completion: Continuation<T>
) : Continuation<Any?> {

    abstract fun invokeSuspend(result: Result<Any?>): Any?

    override fun resumeWith(result: Result<Any?>) {
        try {
            val outcome = invokeSuspend(result)
            if (outcome != COROUTINE_SUSPENDED) {
                @Suppress("UNCHECKED_CAST")
                completion.resumeWith(Result.success(outcome as T))
            }
        } catch (e: Throwable) {
            completion.resumeWith(Result.failure(e))
        }
    }

    companion object {
        // COROUTINE_SUSPENDED is Kotlin’s way of marking a suspension point. If this is returned, the coroutine is paused.
        val COROUTINE_SUSPENDED = Any()
    }
}


//
//abstract class BaseContinuation<T> : Continuation<T> {
//    abstract fun invokeSuspend(result: Result<Any?>): Any?
//
//    final override fun resumeWith(result: Result<T>) {
//        try {
//            val outcome = invokeSuspend(result as Result<Any?>)
//            if (outcome != COROUTINE_SUSPENDED) {
//                (this as Continuation<Any?>).resumeWith(Result.success(outcome))
//            }
//        } catch (e: Throwable) {
//            (this as Continuation<Any?>).resumeWith(Result.failure(e))
//        }
//    }
//
//    companion object {
//
//        val COROUTINE_SUSPENDED = Any()
//    }
//}