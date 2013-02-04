package myset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import static myset.TestUtilities.initialiseSet;
import static myset.TestUtilities.verifySetContents;

@RunWith(Parameterized.class)
public class WhenSetUnionIsCalled extends TwoClassTestBase {

    public WhenSetUnionIsCalled(String firstClassName, String secondClassName) {
        super(firstClassName, secondClassName);
    }

    @Test
    public void unionOfTwoEmptySetsShouldBeEmpty() throws MySetException {
        MySet union = firstSet.union(secondSet);
        assertThat(union.size(), is(0));
    }

    // Add tests for other cases when union is called.
    @Test
    public void unionOfTwoEqualSetsShouldBeCorrect() throws MySetException {
        initialiseSet(firstSet, 1, 2, 3);
        initialiseSet(secondSet, 1, 2, 3);
        MySet union = firstSet.union(secondSet);
        assertThat(union.size(), is(3));
        verifySetContents(union, 1, 2, 3);
    }

    @Test
    public void unionOfNonEmptySetWithEmptySetShouldBeCorrect() throws MySetException {
        initialiseSet(firstSet, 1, 2, 3);
        MySet union = firstSet.union(secondSet);
        assertThat(union.size(), is(3));
        verifySetContents(union, 1, 2, 3);
    }

    @Test
    public void unionOfIntersectingSetsShouldBeCorrect() throws MySetException {
        initialiseSet(firstSet, 1, 2, 3, 4);
        initialiseSet(secondSet, 3, 4, 5, 6);
        MySet union = firstSet.union(secondSet);
        assertThat(union.size(), is(6));
        verifySetContents(union, 1, 2, 3, 4, 5, 6);
    }

    @Test
    public void unionOfNonIntersectingSetsShouldBeCorrect() throws MySetException {
        initialiseSet(firstSet, 1, 2, 3, 4);
        initialiseSet(secondSet, 5, 6, 7, 8);
        MySet union = firstSet.union(secondSet);
        assertThat(union.size(), is(8));
        verifySetContents(union, 1, 2, 3, 4, 5, 6, 7, 8);

    }
}
