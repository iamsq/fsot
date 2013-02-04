package myset;

import org.junit.Before;
import org.junit.runners.Parameterized;

import java.util.List;

public class TwoClassTestBase
{
  protected String firstClassName;
  protected String secondClassName;
  protected MySet firstSet;
  protected MySet secondSet;

  @Parameterized.Parameters
  public static List data() throws MySetException
  {
    return FixtureFactory.classesToTestWithOperators();
  }

  @Before
  public void setUp() throws MySetException
  {
    firstSet = FixtureFactory.createSet(firstClassName);
    secondSet = FixtureFactory.createSet(secondClassName);
  }

  public TwoClassTestBase(String firstClassName, String secondClassName)
  {
    this.firstClassName = firstClassName;
    this.secondClassName = secondClassName;
  }
}