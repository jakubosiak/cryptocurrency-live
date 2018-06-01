package josiak.android.example.cryptocurrency.charts

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // ContextForResources of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("josiak.android.example.android.cryptocurrency.charts", appContext.packageName)
    }
}
