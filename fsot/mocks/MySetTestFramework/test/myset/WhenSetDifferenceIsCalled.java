package myset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import static myset.TestUtilities.initialiseSet;
import static myset.TestUtilities.verifySetContents;

@RunWith(Parameterized.class)
public class WhenSetDifferenceIsCalled extends TwoClassTestBase {

    public WhenSetDifferenceIsCalled(String firstClassName, String secondClassName) {
        super(firstClassName, secondClassName);
    }

    @Test
    public void differenceOfTwoEmptySetsShouldBeEmpty() throws MySetException {
        MySet difference = firstSet.difference(secondSet);
        assertThat(difference.size(), is(0));
    }

    // Add tests for other cases when difference is called.
    @Test
    public void differenceOfTwoEqualSetsShouldBeEmpty() throws MySetException {
        initialiseSet(firstSet, 1, 2, 3);
        initialiseSet(secondSet, 1, 2, 3);
        MySet difference = firstSet.difference(secondSet);
        assertThat(difference.size(), is(0));
    }

    @Test
    public void differenceOfNonEmptySetFromEmptySetShouldBeNonEmptySet() throws MySetException {
        initialiseSet(firstSet, 1, 2, 3);
        MySet difference = firstSet.difference(secondSet);
        assertThat(difference.size(), is(3));
        verifySetContents(difference, 1, 2, 3);
    }

    @Test
    public void differenceOfEmptySetFromNonEmptySetShouldBeEmptySet() throws MySetException {
        initialiseSet(secondSet, 1, 2, 3);
        MySet difference = firstSet.difference(secondSet);
        assertThat(difference.size(), is(0));
    }

    @Test
    public void differenceOfIntersectingSetsShouldBeCorrect() throws MySetException {
        initialiseSet(firstSet, 1, 2, 3, 4);
        initialiseSet(secondSet, 3, 4, 5, 6);
        MySet difference = firstSet.difference(secondSet);
        assertThat(difference.size(), is(2));
        verifySetContents(difference, 1, 2);
    }

    @Test
    public void differenceOfNonIntersectingSetsShouldBeCorrect() throws MySetException {
        initialiseSet(firstSet, 1, 2, 3, 4);
        initialiseSet(secondSet, 5, 6, 7, 8);
        MySet difference = firstSet.difference(secondSet);
        assertThat(difference.size(), is(4));
        verifySetContents(difference, 1, 2, 3, 4);
    }
}
