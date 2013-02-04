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
public class WhenASetIsEmpty
{
  private String setClassName;
  private MySet set;

  @Parameters
  public static List data() throws MySetException
  {
    return FixtureFactory.classesToTest();
  }

  public WhenASetIsEmpty(String setClassName)
  {
    this.setClassName = setClassName;
  }

  @Before
  public void setUp() throws MySetException
  {
    set = FixtureFactory.createSet(setClassName);
  }

  @Test
  public void anEmptySetShouldHaveSizeZero()
  {
    assertThat(set.size(), is(0));
  }

  // Add tests for an empty set.
  @Test
  public void isEmptyShouldReturnTrue()
  {
    assertThat(set.isEmpty(), is(true));
  }
}
