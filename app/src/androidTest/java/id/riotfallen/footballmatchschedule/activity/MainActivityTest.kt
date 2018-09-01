package id.riotfallen.footballmatchschedule.activity

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import id.riotfallen.footballmatchschedule.MyMatcher
import id.riotfallen.footballmatchschedule.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAppBehaviour(){

        // check apakah spinner telah muncul
        onView(withId(spinnerMainActivity))
                .check(matches(isDisplayed()))
        // click spinnner
        onView(withId(spinnerMainActivity)).perform(click())

        // sleep fungsi 2detik : Menunggu view Muncul
        Thread.sleep(2000)

        // check apakah terdapat view dengan text "Spanish La Liga"
        onView(withText("Italian Serie A"))
                .check(matches(isDisplayed()))
        // click view dengan text "Spanish La Liga"
        onView(withText("Italian Serie A")).perform(click())

        // sleep fungsi 5detik : Menunggu view Muncul
        Thread.sleep(5000)

        // check apakah recyclerview-index 0 telah muncul
        onView(MyMatcher().withIndex(withId(recyclerViewEvent), 0))
                .check(matches(isDisplayed()))

        // swipe recyclerview-0 ke posisi index 5
        onView(MyMatcher().withIndex(withId(recyclerViewEvent), 0)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))

        // click item recyclerview-0 index 5
        onView(MyMatcher().withIndex(withId(recyclerViewEvent), 0)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click())
        )

        // sleep fungsi 2detik : Menunggu view Muncul
        Thread.sleep(2000)

        // check apakah terdapat view menu favorite telah muncul
        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))

        // click menu favorite
        onView(withId(add_to_favorite)).perform(click())

        // sleep fungsi menunggu 1 detik
        Thread.sleep(1000)

        // fungsi kembali
        pressBack()

        // sleep funsi menunggu 1 detik
        Thread.sleep(1000)

        onView(withText("FAVORITES EVENTS"))
                .check(matches(isDisplayed()))
        onView(withText("FAVORITES EVENTS")).perform(click())

        // sleep funsi menunggu 1 detik
        Thread.sleep(1000)

        // check apakah recyclerview-index 1 telah muncul
        onView(MyMatcher().withIndex(withId(recyclerViewEvent), 1))
                .check(matches(isDisplayed()))

        // click item recyclerview-0 index 0
        onView(MyMatcher().withIndex(withId(recyclerViewEvent), 1)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        // sleep fungsi 2detik : Menunggu view Muncul
        Thread.sleep(2000)

        // check apakah terdapat view menu favorite telah muncul
        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))

        // click menu favorite
        onView(withId(add_to_favorite)).perform(click())

        // sleep fungsi menunggu 1 detik
        Thread.sleep(1000)

        // fungsi kembali
        pressBack()


    }

}
