package id.riotfallen.footballmatchschedule.presenter

import com.google.gson.Gson
import id.riotfallen.footballmatchschedule.TestContextProvider
import id.riotfallen.footballmatchschedule.api.ApiRepository
import id.riotfallen.footballmatchschedule.model.event.Event
import id.riotfallen.footballmatchschedule.model.event.EventResponse
import id.riotfallen.footballmatchschedule.view.EventView
import id.riotfallen.footballmatchschedule.api.TheSportDBApi
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EventsPresenterTest {

    @Mock
    private
    lateinit var view: EventView

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private
    lateinit var gson: Gson

    private lateinit var presenter: EventsPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = EventsPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetNextEventList() {
        val events: MutableList<Event> =  mutableListOf()
        val response = EventResponse(events)
        val id = "4328"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextMatch(id)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getNextEventList(id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showEvent(events)
        Mockito.verify(view).hideLoading()
    }
}