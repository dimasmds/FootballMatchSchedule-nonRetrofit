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
import id.riotfallen.footballmatchschedule.model.event.Event
import id.riotfallen.footballmatchschedule.utils.BadgeFetcher
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

class EventListAdapter(private val context: Context?,
                       private val events: MutableList<Event>)
    : RecyclerView.Adapter<EventListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        return EventListViewHolder(LayoutInflater.from(context).inflate(R.layout.match_list_view, parent, false))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        holder.bindItem(events[position])
        holder.itemView.setOnClickListener {
            context?.startActivity<EventDetailActivity>("idEvent" to events[position].idEvent,
                    "idHome" to events[position].idHomeTeam, "idAway" to events[position].idAwayTeam)
        }
    }
}

class EventListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val textViewHomeName : TextView = view.findViewById(R.id.textViewHomeClub)
    private val textViewHomeScore : TextView = view.findViewById(R.id.textViewHomeScore)

    private val textViewAwayName : TextView = view.findViewById(R.id.textViewAwayClub)
    private val textViewAwayScore : TextView = view.findViewById(R.id.textViewAwayScore)

    private val imageViewAwayLogo : ImageView = view.findViewById(R.id.imageViewAwayLogo)
    private val imageViewHomeLogo : ImageView = view.findViewById(R.id.imageViewHomeLogo)

    private val textViewDate : TextView = view.findViewById(R.id.textViewDateMatch)

    fun bindItem(event: Event){
        textViewHomeName.text = event.strHomeTeam
        if (event.intHomeScore != null){
            textViewHomeScore.text = event.intHomeScore.toString()
        } else {
            textViewHomeScore.text = "V"
        }
        textViewAwayName.text = event.strAwayTeam

        if(event.intAwayScore != null){
            textViewAwayScore.text = event.intAwayScore.toString()
        } else {
            textViewAwayScore.text = "S"
        }

        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = format.parse(event.dateEvent)
        val dateText = SimpleDateFormat("EEEE, dd-MM-yyyy", Locale.getDefault())
                .format(date).toString()

        textViewDate.text = dateText

        event.idAwayTeam?.let { BadgeFetcher().loadBadges(it, imageViewAwayLogo) }
        event.idHomeTeam?.let { BadgeFetcher().loadBadges(it, imageViewHomeLogo) }
    }

}
