package com.asif.urbandictionary;

import junit.framework.TestSuite;

import org.junit.Test;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;

import androidx.test.filters.LargeTest;
import androidx.test.filters.SdkSuppress;
import androidx.test.runner.AndroidJUnitRunner;

import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.asif.urbandictionary.activities.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.click;

/**
 * JUnit3 Ui Tests for {@link CalculatorActivity} using the {@link AndroidJUnitRunner}. This class
 * uses the Junit3 syntax for tests.
 *
 * <p> With the new AndroidJUnit runner you can run both JUnit3 and JUnit4 tests in a single test
 * test suite. The {@link AndroidRunnerBuilder} which extends JUnit's {@link
 * AllDefaultPossibilitiesBuilder} will create a single {@link TestSuite} from all tests and run
 * them. </p>
 */
@LargeTest
public class ExampleInstrumentedTest
        extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mActivity;

    public ExampleInstrumentedTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Espresso does not start the Activity for you we need to do this manually here.
        mActivity = getActivity();
    }

    public void testPreconditions() {
        assertThat(mActivity, notNullValue());
    }

    public void testEditText_OperandOneHint() {
        String operandOneHint = mActivity.getString(R.string.text_hint);
        onView(withId(R.id.editTextWord)).check(matches(withHint(operandOneHint)));
    }

    @Test
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void testMinBuild() {
        Log.d("Test Filters", "Checking for min build > Lollipop");
        assertNotNull("MainActivity is not available", mActivity);
    }

    @Test
    public void testHintVisibility(){
        // check hint visibility
        onView(withId(R.id.editTextWord)).check(matches(withHint(mActivity.getString(R.string.text_hint))));
        // enter name
        onView(withId(R.id.editTextWord)).perform(typeText("asif"),closeSoftKeyboard());
        onView(withId(R.id.editTextWord)).check(matches(withText("asif")));
    }

    @Test
    public void testButtonClick(){
        // enter name`
        onView(withId(R.id.editTextWord)).perform(typeText("asif"),closeSoftKeyboard());
        // clear text
        onView(withText("SEARCH")).perform(click());
        // check hint visibility after the text is cleared
        onView(withId(R.id.editTextWord)).check(matches(withHint(mActivity.getString(R.string.text_hint))));
    }

}
