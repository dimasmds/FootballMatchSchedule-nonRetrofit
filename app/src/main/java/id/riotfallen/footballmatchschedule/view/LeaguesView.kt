package id.riotfallen.footballmatchschedule.view

import id.riotfallen.footballmatchschedule.model.league.League

interface LeaguesView {
    fun showLeagues(data: List<League>)
}