package id.riotfallen.footballmatchschedule.view

import id.riotfallen.footballmatchschedule.model.event.Event

interface EventView {
    fun showLoading()
    fun hideLoading()
    fun showEvent(data: List<Event>)
}