package myset;

import org.junit.runners.Parameterized;

import java.util.List;

public class OneClassTestBase
{
  protected String className;

  @Parameterized.Parameters
  public static List data() throws MySetException
  {
    return FixtureFactory.classesToTest();
  }

  public OneClassTestBase(String className)
  {
    this.className = className;
  }
}