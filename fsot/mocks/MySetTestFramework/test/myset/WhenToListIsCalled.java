package myset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static myset.TestUtilities.initialiseSet;
import static myset.TestUtilities.verifySetContents;

@RunWith(Parameterized.class)
public class WhenToListIsCalled extends OneClassTestBase
{
  public WhenToListIsCalled(String className)
  {
    super(className);
  }

  @Test
  public void emptySetShouldGiveEmptyList() throws MySetException
  {
    MySet set = FixtureFactory.createSet(className);
    List list = set.toList();
    assertThat(list.size(), is(0));
  }

  // Add tests for other cases when toList is called.

  @Test
  public void nonEmptySetShouldBeCorrect() throws MySetException
  {
    MySet set = FixtureFactory.createSet(className);
    set.add(1);
    List list = set.toList();
    assertThat(list.size(), is(1));
  }

  @Test
  public void powerSetShouldGiveNestedList() throws MySetException
  {
    MySet set = FixtureFactory.createSet(className);
    set.add(1);
    MySet powerSet = set.powerSet();
    List list = powerSet.toList();
    
    assertThat(list.size(), is(2));
    assertThat(list.get(0), is(List.class));
    assertThat(list.get(1), is(List.class));
    
  }
}

