package id.riotfallen.footballmatchschedule.presenter

import android.widget.ImageView
import com.google.gson.Gson
import id.riotfallen.footballmatchschedule.TestContextProvider
import id.riotfallen.footballmatchschedule.api.ApiRepository
import id.riotfallen.footballmatchschedule.api.TheSportDBApi
import id.riotfallen.footballmatchschedule.model.team.Team
import id.riotfallen.footballmatchschedule.model.team.TeamResponse
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class BadgesPresenterTest {
    @Mock
    private
    lateinit var view: ImageView

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private
    lateinit var gson: Gson

    private lateinit var presenter: BadgesPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = BadgesPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetBadge() {
        val teamId = "133619"
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getDetailTeam(teamId)),
                TeamResponse::class.java
        )).thenReturn(response)

        presenter.getBadge(teamId)

        Mockito.verify(view)
    }
}