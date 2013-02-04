package myset;

import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static myset.TestUtilities.initialiseSet;
import static myset.TestUtilities.verifySetContents;

@RunWith(Parameterized.class)
public class WhenPowerSetIsCalled extends OneClassTestBase {

    public WhenPowerSetIsCalled(String className) {
        super(className);
    }

    @Test
    public void powerSetOfEmptySetShouldContainEmptySet() throws MySetException {
        MySet powerSet = FixtureFactory.createSet(className).powerSet();
        assertThat(powerSet.size(), is(1));
        verifySetContents(powerSet, FixtureFactory.createSet(className));
    }

    // Add tests for non empty sets.
    @Test
    public void powerSetOfOneElementShouldContainTwoSets() throws MySetException {
        MySet set = FixtureFactory.createSet(className);
        set.add(1);
        MySet powerSet = set.powerSet();

        assertThat(powerSet.size(), is(2));
        
        ArrayList elements = new ArrayList<MySet>();
        elements.add(FixtureFactory.createSet(className));
        elements.add(FixtureFactory.createSetWithContent(className, 1));

        verifySetContents(powerSet, elements.toArray());
    }

    @Test
    public void powerSetOfTwoElementShouldContainFourSets() throws MySetException {
        MySet set = FixtureFactory.createSet(className);
        set.add(1);
        set.add(2);
        MySet powerSet = set.powerSet();

        assertThat(powerSet.size(), is(4));

        ArrayList elements = new ArrayList<MySet>();
        elements.add(FixtureFactory.createSet(className));
        elements.add(FixtureFactory.createSetWithContent(className, 1));
        elements.add(FixtureFactory.createSetWithContent(className, 2));
        elements.add(FixtureFactory.createSetWithContent(className, 1, 2));

        verifySetContents(powerSet, elements.toArray());
    }
}
