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
public class WhenASetHoldsSeveralItems {

    private String setClassName;
    private MySet set;

    @Parameters
    public static List data() throws MySetException {
        return FixtureFactory.classesToTest();
    }

    public WhenASetHoldsSeveralItems(String setClassName) {
        this.setClassName = setClassName;
    }

    @Before
    public void setUp() throws MySetException {
        set = FixtureFactory.createSet(setClassName);
        set.add(1);
        set.add(2);
    }

    @Test
    public void theSetShouldContainTheInitialItems() {
        assertThat(set.contains(1), is(true));
        assertThat(set.contains(2), is(true));
    }

    // Add tests to check that sets work when items are added and removed
    // using this fixture.
    @Test
    public void theSetShouldContainOneAfterRemoveOther() {
        set.remove(1);
        assertThat(set.contains(1), is(false));
        assertThat(set.contains(2), is(true));
    }
    @Test
    public void theSetShouldContainOneAfterRemoveSecond() {
        set.remove(2);
        assertThat(set.contains(1), is(true));
        assertThat(set.contains(2), is(false));
    }
    @Test
    public void theSetShouldContainAllThreeItemsAfterAddingThree() throws MySetException{
        set.add(3);
        assertThat(set.contains(1), is(true));
        assertThat(set.contains(2), is(true));
        assertThat(set.contains(3), is(true));
    }
}
