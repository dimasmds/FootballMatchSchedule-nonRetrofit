package id.riotfallen.footballmatchschedule.adapter.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import id.riotfallen.footballmatchschedule.R
import id.riotfallen.footballmatchschedule.activity.EventDetailActivity
import id.riotfallen.footballmatchschedule.model.favorite.Favorite
import id.riotfallen.footballmatchschedule.utils.BadgeFetcher
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

class FavoriteListAdapter(private val context: Context?, private val events: MutableList<Favorite>) :
        RecyclerView.Adapter<FavoriteListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListViewHolder {
        return FavoriteListViewHolder(LayoutInflater.from(context).inflate(R.layout.match_list_view, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteListViewHolder, position: Int) {
        holder.bindItem(events[position])
        holder.itemView.setOnClickListener {
            context?.startActivity<EventDetailActivity>("idEvent" to events[position].eventId,
                    "idHome" to events[position].homeId, "idAway" to events[position].awayId)
        }
    }

    override fun getItemCount(): Int = events.size
}

class FavoriteListViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val textViewHomeName : TextView = view.findViewById(R.id.textViewHomeClub)
    private val textViewHomeScore : TextView = view.findViewById(R.id.textViewHomeScore)

    private val textViewAwayName : TextView = view.findViewById(R.id.textViewAwayClub)
    private val textViewAwayScore : TextView = view.findViewById(R.id.textViewAwayScore)


    private val imageViewAwayLogo : ImageView = view.findViewById(R.id.imageViewAwayLogo)
    private val imageViewHomeLogo : ImageView = view.findViewById(R.id.imageViewHomeLogo)

    private val textViewDate : TextView = view.findViewById(R.id.textViewDateMatch)

    fun bindItem(match : Favorite) {

        textViewHomeName.text = match.homeName
        if (match.homeScore != null){
            textViewHomeScore.text = match.homeScore.toString()
        } else {
            textViewHomeScore.text = "V"
        }
        textViewAwayName.text = match.awayName

        if(match.awayScore != null){
            textViewAwayScore.text = match.awayScore.toString()
        } else {
            textViewAwayScore.text = "S"
        }

        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = format.parse(match.eventDate)
        val dateText = SimpleDateFormat("EEEE, dd-MM-yyyy", Locale.getDefault())
                .format(date).toString()

        textViewDate.text = dateText

        match.awayId?.let { BadgeFetcher().loadBadges(it, imageViewAwayLogo) }
        match.homeId?.let { BadgeFetcher().loadBadges(it, imageViewHomeLogo) }
    }

}