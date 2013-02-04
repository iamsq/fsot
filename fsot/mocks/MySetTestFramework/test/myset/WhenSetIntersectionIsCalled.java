package myset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import static myset.TestUtilities.initialiseSet;
import static myset.TestUtilities.verifySetContents;

@RunWith(Parameterized.class)
public class WhenSetIntersectionIsCalled extends TwoClassTestBase {

    public WhenSetIntersectionIsCalled(String firstClassName, String secondClassName) {
        super(firstClassName, secondClassName);
    }

    @Test
    public void intersectionOfTwoEmptySetsShouldBeEmpty() throws MySetException {
        MySet intersection = firstSet.intersection(secondSet);
        assertThat(intersection.size(), is(0));
    }

    // Add tests for other cases when intersection is called.
    @Test
    public void intersectionOfTwoEqualSetsShouldBeCorrect() throws MySetException {
        initialiseSet(firstSet, 1, 2, 3);
        initialiseSet(secondSet, 1, 2, 3);
        MySet intersection = firstSet.intersection(secondSet);
        assertThat(intersection.size(), is(3));
        verifySetContents(intersection, 1, 2, 3);
    }

    @Test
    public void intersectionOfNonEmptySetWithEmptySetShouldBeCorrect() throws MySetException {
        initialiseSet(firstSet, 1, 2, 3);
        MySet intersection = firstSet.intersection(secondSet);
        assertThat(intersection.size(), is(0));
    }

   
    @Test
    public void intersectionOfIntersectingSetsShouldBeCorrect() throws MySetException {
        initialiseSet(firstSet, 1, 2, 3, 4);
        initialiseSet(secondSet, 3, 4, 5, 6);
        MySet intersection = firstSet.intersection(secondSet);
        assertThat(intersection.size(), is(2));
        verifySetContents(intersection, 3, 4);
    }

    @Test
    public void intersectionOfNonIntersectingSetsShouldBeCorrect() throws MySetException {
        initialiseSet(firstSet, 1, 2, 3, 4);
        initialiseSet(secondSet, 5, 6, 7, 8);
        MySet intersection = firstSet.intersection(secondSet);
        assertThat(intersection.size(), is(0));
        
    }
}
