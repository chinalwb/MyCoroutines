package coroutines.v1

import coroutines.Continuation

class SimpleSuspendContinuation(
    private val completion: Continuation<Int>
) : Continuation<Unit> {

    private var label = 0
    private var result: Int = 0

    override fun resumeWith(result: Result<Unit>) {
        try {
            when (label) {
                0 -> {
                    println("Start")
                    label = 1
                    delaySimulation(this) // simulate suspending here
                    return
                }
                1 -> {
                    println("End")
                    completion.resumeWith(Result.success(42))
                }
            }
        } catch (e: Throwable) {
            completion.resumeWith(Result.failure(e))
        }
    }
}

fun delaySimulation(continuation: Continuation<Unit>) {
    println("Delaying...")
    // Simulate async delay
    Thread {
        Thread.sleep(1000)
        continuation.resumeWith(Result.success(Unit))
    }.start()
}

fun main() {
    val coroutine = SimpleSuspendContinuation(object : Continuation<Int> {
        override fun resumeWith(result: Result<Int>) {
            println("Coroutine completed with: ${result.getOrNull()}")
        }
    })

    coroutine.resumeWith(Result.success(Unit))
}


