package myset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class WhenAddThrowsAnException extends OneClassTestBase
{

  public WhenAddThrowsAnException(String firstClassName)
  {
    super(firstClassName);
  }

  @Test(expected = MySetException.class)
  public void addingToAFullSetShouldThrowAnException() throws MySetException
  {
    MySet set = FixtureFactory.createSetWithSize(className,1);
    set.add(1);
    set.add(2);
  }
}
