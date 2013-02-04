package myset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.List;

@RunWith(Parameterized.class)
public class WhenASetHoldsOneItem {

    private String setClassName;
    private MySet set;

    @Parameters
    public static List data() throws MySetException {
        return FixtureFactory.classesToTest();
    }

    public WhenASetHoldsOneItem(String setClassName) {
        this.setClassName = setClassName;
    }

    @Before
    public void setUp() throws MySetException {
        set = FixtureFactory.createSet(setClassName);
        set.add(1);
    }

    @Test
    public void theSizeShouldBeOne() {
        assertThat(set.size(), is(1));
    }

    // Add other tests for a set containing one item.
    // What happens if the item is removed or another added?
    // Or a duplicate added?

    @Test
    public void AfterAddingDuplicatetheSizeShouldBeOne() throws MySetException {
        set.add(1);
        assertThat(set.size(), is(1));
    }

    @Test
    public void AfterAddingNonDuplicatetheSizeShouldBeTwo() throws MySetException {
        set.add(2);
        assertThat(set.size(), is(2));
    }

    @Test
    public void AfterRemovingtheSizeShouldBeZero() throws MySetException {
        set.remove(1);
        assertThat(set.size(), is(0));
    }

    @Test
    public void AfterRemovingNonExistingItemtheSizeShouldBeOne() throws MySetException {
        set.remove(2);
        assertThat(set.size(), is(1));
    }
}
