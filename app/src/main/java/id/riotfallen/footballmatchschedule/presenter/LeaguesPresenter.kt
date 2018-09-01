package id.riotfallen.footballmatchschedule.presenter

import com.google.gson.Gson
import id.riotfallen.footballmatchschedule.api.ApiRepository
import id.riotfallen.footballmatchschedule.api.TheSportDBApi
import id.riotfallen.footballmatchschedule.model.league.LeagueResponse
import id.riotfallen.footballmatchschedule.utils.CoroutineContextProvider
import id.riotfallen.footballmatchschedule.view.LeaguesView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class LeaguesPresenter(private val view: LeaguesView,
                       private val apiRepository: ApiRepository,
                       private val gson: Gson,
                       private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getLeagues(){
        async(contextPool.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getLeagues()),
                        LeagueResponse::class.java
                )
            }

            view.showLeagues(data.await().leagues)
        }
    }
}
