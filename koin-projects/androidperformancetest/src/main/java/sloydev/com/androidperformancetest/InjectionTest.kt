package sloydev.com.androidperformancetest

import android.os.Build
import android.util.Log
import org.koin.core.time.measureDuration
import org.koin.log.Logger
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.get

class InjectionTest : KoinComponent {

    private val rounds = 100

    private val testLogger = object : Logger {
        override fun debug(msg: String) {
            //Log.d("KOIN-PERFORMANCE", msg)
        }

        override fun info(msg: String) {
        }

        override fun err(msg: String) {
        }
    }

    fun runTests() {
        Log.d("KOIN-RESULT", " ")
        Log.d("KOIN-RESULT", "-> Running...")
        Log.d("KOIN-RESULT", "=========|=====================")
        Log.d("KOIN-RESULT", "Device:  | ${Build.BRAND} ${Build.DEVICE} v${Build.VERSION.RELEASE}")
        runKotlinInjection()
        runJavaInjection()
        Log.d("KOIN-RESULT", "=========|=====================")
        Log.d("KOIN-RESULT", "<- Finished")
        Log.d("KOIN-RESULT", " ")
    }

    private fun runKotlinInjection() {
        StandAloneContext.startKoin(listOf(fibonacciKotlinModule), logger = testLogger)

        val durations = (1..rounds).map {
            measureDuration {
                get<Fib8>()
            }
        }

        report(durations, "Koin + Kotlin")

        stopKoin()
    }

    private fun runJavaInjection() {
        StandAloneContext.startKoin(listOf(fibonacciJavaModule), logger = testLogger)

        val durations = (1..rounds).map {
            measureDuration {
                get<Fibonacci.Fib8>()
            }
        }

        report(durations, "Koin + Java")

        stopKoin()
    }

    private fun report(durations: List<Double>, testName: String) {
        Log.d("KOIN-RESULT", "---------|--------------------")
        Log.d("KOIN-RESULT", "Test:    | $testName")
        Log.d("KOIN-RESULT", "Max:     | " + durations.max())
        Log.d("KOIN-RESULT", "Min:     | " + durations.min())
        Log.d("KOIN-RESULT", "Average: | " + durations.average())
    }
}