package id.riotfallen.footballmatchschedule

import id.riotfallen.footballmatchschedule.utils.CoroutineContextProvider
import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

class TestContextProvider : CoroutineContextProvider(){
    override val main: CoroutineContext = Unconfined

}