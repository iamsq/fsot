package myset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class WhenAConstructorThrowsAnException extends OneClassTestBase
{

  public WhenAConstructorThrowsAnException(String firstClassName)
  {
    super(firstClassName);
  }

  @Test(expected = MySetException.class)
  public void creatingASetThatIsTooBigShouldThrowAnException() throws MySetException
  {
    FixtureFactory.createSetWithSize(className,MySet.MAX_SIZE+1);
  }

  // Add tests for other cases where a constructor should throw an exception.

  @Test(expected = MySetException.class)
  public void creatingASetThatHasSizeZeroShouldThrowAnException() throws MySetException
  {
    FixtureFactory.createSetWithSize(className,0);
  }
  @Test(expected = MySetException.class)
  public void creatingASetThatHasNegativeSizeShouldThrowAnException() throws MySetException
  {
    FixtureFactory.createSetWithSize(className,-1);
  }
}
