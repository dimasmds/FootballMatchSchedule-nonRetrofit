package id.riotfallen.footballmatchschedule.utils

import android.widget.ImageView
import com.google.gson.Gson
import id.riotfallen.footballmatchschedule.presenter.BadgesPresenter
import id.riotfallen.footballmatchschedule.api.ApiRepository

class BadgeFetcher {

    private lateinit var presenter: BadgesPresenter
    private lateinit var image: ImageView


    fun loadBadges(id: String, img: ImageView) {
        img.setImageDrawable(null)
        val request = ApiRepository()
        val gson = Gson()

        presenter = BadgesPresenter(img, request, gson)
        presenter.getBadge(id)
    }
}
