package id.riotfallen.footballmatchschedule.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.gson.Gson
import id.riotfallen.footballmatchschedule.R
import id.riotfallen.footballmatchschedule.adapter.viewpager.EventPagerAdapter
import id.riotfallen.footballmatchschedule.api.ApiRepository
import id.riotfallen.footballmatchschedule.db.database
import id.riotfallen.footballmatchschedule.model.league.League
import id.riotfallen.footballmatchschedule.presenter.LeaguesPresenter
import id.riotfallen.footballmatchschedule.view.LeaguesView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.*

class MainActivity : AppCompatActivity(), LeaguesView {


    private lateinit var presenter: LeaguesPresenter
    private lateinit var eventPagerAdapter: EventPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbarMainActivity)
        supportActionBar?.title = "Football Match Schedule"

        tabLayoutMainActivity.addTab(tabLayoutMainActivity.newTab().setText("Prev Events"))
        tabLayoutMainActivity.addTab(tabLayoutMainActivity.newTab().setText("Next Events"))
        tabLayoutMainActivity.addTab(tabLayoutMainActivity.newTab().setText("Favorites Events"))

        database.use {
            createTable("TABLE_FAVORITE", true,
                    "ID_" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                    "TEAM_ID" to TEXT + UNIQUE,
                    "TEAM_NAME" to TEXT,
                    "TEAM_BADGE" to TEXT)
        }

        val request = ApiRepository()
        val gson = Gson()
        presenter = LeaguesPresenter(this, request, gson)
        presenter.getLeagues()

    }

    override fun showLeagues(data: List<League>) {
        val arraySpinner: MutableList<String> = mutableListOf()
        for (index in data.indices) {
            if(index == 22){
                break
            }
            data[index].strLeague?.let { arraySpinner.add(index, it) }
        }

        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(this,
                R.layout.spinner_item, arraySpinner)

        spinnerMainActivity.adapter = arrayAdapter

        tabLayoutMainActivity.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPagerMainActivity.currentItem = tab.position
            }

        })

        spinnerMainActivity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val idLeague = data[spinnerMainActivity.selectedItemPosition].idLeague
                eventPagerAdapter = EventPagerAdapter(supportFragmentManager, tabLayoutMainActivity.tabCount, idLeague!!)
                viewPagerMainActivity.adapter = eventPagerAdapter
                viewPagerMainActivity.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayoutMainActivity))
            }

        }
    }
}
