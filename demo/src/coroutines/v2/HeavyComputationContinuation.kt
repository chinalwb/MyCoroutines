package coroutines.v2

import coroutines.BaseContinuation
import coroutines.Continuation

class HeavyComputationContinuation(
    completion: Continuation<Int>
) : BaseContinuation<Int>(completion) {

    private var label = 0
    private var resultValue: Int = 0


    override fun invokeSuspend(result: Result<Any?>): Any? {
        when (label) {
            0 -> {
                println("Start heavy computation...")
                label = 1
                simulateHeavyWork(this)
                return COROUTINE_SUSPENDED
            }
            1 -> {
                resultValue = result.getOrThrow() as Int // Get the value passed from simulateHeavyWork
                println("Heavy computation finished. Got value = $resultValue")
                return resultValue
            }
            else -> throw IllegalStateException("Invalid state")
        }
    }
}

fun heavyComputation(completion: Continuation<Int>) {
    val continuation = HeavyComputationContinuation(completion)
    continuation.resumeWith(Result.success(Unit))
}

fun simulateHeavyWork(continuation: Continuation<Any?>) {
    Thread {
        Thread.sleep(1500) // simulate computation
        continuation.resumeWith(Result.success(10))
    }.start()
}
