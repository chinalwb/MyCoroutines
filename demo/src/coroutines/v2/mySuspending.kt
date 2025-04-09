package coroutines.v2

import coroutines.BaseContinuation
import coroutines.Continuation

fun mySuspending(completion: Continuation<Int>): Any {
    val continuation = MySuspendingContinuation(completion)
    continuation.resumeWith(Result.success(Unit))
    return BaseContinuation.COROUTINE_SUSPENDED
}


class MySuspendingContinuation(
    completion: Continuation<Int>
) : BaseContinuation<Int>(completion) {

    private var label = 0
    private var intermediateResult = 0

    override fun invokeSuspend(result: Result<Any?>): Any? {
        when (label) {
            0 -> {
                println("Step 1: Start")
                label = 1
                // Call the nested suspend function
                heavyComputation(object : Continuation<Int> {
                    override fun resumeWith(innerResult: Result<Int>) {
                        intermediateResult = innerResult.getOrThrow()
                        // Resume outer continuation after inner finishes
                        this@MySuspendingContinuation.resumeWith(Result.success(Unit))
                    }
                })
                return COROUTINE_SUSPENDED
            }

            1 -> {
                println("Step 2: Got result from heavyComputation: $intermediateResult")
                label = 2
                delay(this)
                return COROUTINE_SUSPENDED
            }

            2 -> {
                println("Step 3: Final step after delay")
                return intermediateResult + 10
            }

            else -> throw IllegalStateException("Invalid state")
        }
    }
}


fun delay(continuation: Continuation<Any?>) {
    println("Simulated delay...")
    Thread {
        Thread.sleep(1000)
        continuation.resumeWith(Result.success(Unit))
    }.start()
}


fun main() {
    mySuspending(object : Continuation<Int> {
        override fun resumeWith(result: Result<Int>) {
            println("Coroutine done: ${result.getOrNull()}")
        }
    })
}

