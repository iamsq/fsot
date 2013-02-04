package myset;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class TestUtilities
{
  public static void initialiseSet(MySet set, Object... items) throws MySetException
  {
    for (Object item : items)
    {
      set.add(item);
    }
  }

  public static void verifySetContents(MySet set, Object... items) throws MySetException
  {
    for (Object item : items)
    {
      assertThat(set.contains(item), is(true));
      set.remove(item);
    }
    assertThat(set.size(), is(0));
  }
}