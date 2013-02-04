package myset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class WhenSetUnionThrowsAnException extends OneClassTestBase
{

  public WhenSetUnionThrowsAnException(String className)
  {
    super(className);
  }

  private static class MockFactory implements MySetFactory
  {
    private DefaultMySetFactory factory = new DefaultMySetFactory();

    public MySet getInstance(Class setClass) throws MySetException
    {
      return factory.getInstance(setClass,2);
    }

    public MySet getInstance(Class setClass, int size) throws MySetException
    {
      return factory.getInstance(setClass,size);
    }
  }

  @Test(expected = MySetException.class)
  public void aUnionThatIsTooLargeShouldThrowAnException() throws MySetException
  {
    MySet one = FixtureFactory.createSet(className);
    MySet two = FixtureFactory.createSet(className);

    one.setFactory(new MockFactory());
    one.add(1);
    two.add(2);
    two.add(3);
    one.union(two);
  }
}