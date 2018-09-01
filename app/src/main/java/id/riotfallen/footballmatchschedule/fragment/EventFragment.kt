package id.riotfallen.footballmatchschedule.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.google.gson.Gson

import id.riotfallen.footballmatchschedule.R
import id.riotfallen.footballmatchschedule.adapter.recyclerview.EventListAdapter
import id.riotfallen.footballmatchschedule.adapter.recyclerview.FavoriteListAdapter
import id.riotfallen.footballmatchschedule.model.event.Event
import id.riotfallen.footballmatchschedule.utils.invisible
import id.riotfallen.footballmatchschedule.utils.visible
import id.riotfallen.footballmatchschedule.view.EventView
import id.riotfallen.footballmatchschedule.api.ApiRepository
import id.riotfallen.footballmatchschedule.db.database
import id.riotfallen.footballmatchschedule.model.favorite.Favorite
import id.riotfallen.footballmatchschedule.presenter.EventsPresenter
import kotlinx.android.synthetic.main.fragment_event.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh

/**
 * A simple [Fragment] subclass.
 *
 */
class EventFragment : Fragment(), EventView {


    private var position: Int = 0
    private lateinit var leagueId: String

    private var favorites: MutableList<Favorite> = mutableListOf()

    private lateinit var presenter: EventsPresenter

    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var favoriteListAdapter: FavoriteListAdapter

    private lateinit var progressBarEvent: ProgressBar
    private lateinit var swipeRefreshLayoutEvent: SwipeRefreshLayout
    private lateinit var recyclerViewEvent: RecyclerView


    companion object {
        fun newInstance() : EventFragment{
            return EventFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_event, container, false)

        position = arguments?.getInt("position") ?: 0
        leagueId = arguments?.getString("leagueId") ?: "0"

        progressBarEvent = rootView.findViewById(R.id.progressBarEvent)
        swipeRefreshLayoutEvent = rootView.findViewById(R.id.swipeRefreshLayoutEvent)
        recyclerViewEvent = rootView.findViewById(R.id.recyclerViewEvent)

        val request = ApiRepository()
        val gson = Gson()

        loadData(request, gson)

        swipeRefreshLayoutEvent.onRefresh {
            loadData(request, gson)
        }

        return rootView
    }

    private fun loadData(request: ApiRepository, gson: Gson) {
        when(position){
            0 -> {
                presenter = EventsPresenter(this, request, gson)
                presenter.getPrevEventList(leagueId)
            }

            1 -> {
                presenter = EventsPresenter(this, request, gson)
                presenter.getNextEventList(leagueId)
            }

            2 -> {
                loadFavorite()
            }
        }
    }


    override fun showLoading() {
        progressBarEvent.visible()
    }

    override fun hideLoading() {
        progressBarEvent.invisible()
    }

    override fun showEvent(data: List<Event>) {
        swipeRefreshLayoutEvent.isRefreshing = false
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, 1)
        recyclerViewEvent.layoutManager = layoutManager
        recyclerViewEvent.itemAnimator = DefaultItemAnimator()
        eventListAdapter = EventListAdapter(this.context, data as MutableList<Event>)
        recyclerViewEvent.adapter = eventListAdapter
    }


    private fun loadFavorite() {
        swipeRefreshLayoutEvent.isRefreshing = true
        favorites.clear()
        context?.database?.use {
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            showFavorite()
        }
    }

    private fun showFavorite() {
        swipeRefreshLayoutEvent.isRefreshing = false
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, 1)
        recyclerViewEvent.layoutManager = layoutManager
        recyclerViewEvent.itemAnimator = DefaultItemAnimator()
        favoriteListAdapter = FavoriteListAdapter(this.context, favorites as ArrayList<Favorite>)
        recyclerViewEvent.adapter = favoriteListAdapter
    }

}
