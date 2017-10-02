package com.example.pancho.umbrellaweatherproject;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.example.pancho.umbrellaweatherproject.utils.RecyclerViewMatcher;
import com.example.pancho.umbrellaweatherproject.view.mainactivity.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by FRANCISCO on 29/08/2017.
 */

public class MainActivityTest {

    String zip_code;

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void setup(){
        zip_code = "30009";
    }

    @Test
    public void change_zip_code(){

        //Click settings
        onView(withId(R.id.action_settings))
                .perform(click());

        //Click zip code
        onView(withId(R.id.recycler_settings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Enter zip code
        onView(withId(R.id.etValue))
                .perform(typeText(String.valueOf(zip_code)), ViewActions.closeSoftKeyboard());

        //Click save
        onView(withId(R.id.btnSave))
                .perform(click());

        // Check item at position 0 has "zip_code"
        onView(withRecyclerView(R.id.recycler_settings)
                .atPositionOnView(0, R.id.tvValueSettings))
                .check(matches(withText(zip_code)));

        //Go back
        Espresso.pressBack();

    }

    @Test
    public void change_fahrenheit(){

        //Click settings
        onView(withId(R.id.action_settings))
                .perform(click());

        //Click degrees code
        onView(withId(R.id.recycler_settings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        //Clicking Fahrenheit
        onView(withId(R.id.rFahrenheit))
                .perform(click());

        //Click save
        onView(withId(R.id.btnSave))
                .perform(click());

        // Check item at position 0 has "Fahrenheit"
        onView(withRecyclerView(R.id.recycler_settings)
                .atPositionOnView(1, R.id.tvValueSettings))
                .check(matches(withText("Fahrenheit")));

        //Go back
        Espresso.pressBack();
    }

    @Test
    public void change_celsius(){

        //Click settings
        onView(withId(R.id.action_settings))
                .perform(click());

        //Click degrees code
        onView(withId(R.id.recycler_settings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        //Clicking Celsius
        onView(withId(R.id.rCelsius))
                .perform(click());

        //Click save
        onView(withId(R.id.btnSave))
                .perform(click());

        // Check item at position 0 has "Celsius"
        onView(withRecyclerView(R.id.recycler_settings)
                .atPositionOnView(1, R.id.tvValueSettings))
                .check(matches(withText("Celsius")));

        //Go back
        Espresso.pressBack();
    }

    // Convenience helper
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

}
