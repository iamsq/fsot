package myset;

import java.util.Arrays;
import java.util.List;
/*
This class creates the set fixture objects needed to run tests on. It is designed to work
with the JUnit @Parameterized annotation.
Note that classes have to be named using strings for @Parameters to be used correctly.
*/
public class FixtureFactory
{
   public static List classesToTest()
   {
     return Arrays.asList(
       new String[][]{
         {"ArrayMySet"},
         {"MapMySet"},
         {"LinkedListMySet"}
       }
     );
   }

   public static List classesToTestWithOperators()
   {
     return Arrays.asList(
       new String[][]{
         {"ArrayMySet","ArrayMySet"},
         {"MapMySet","MapMySet"},
         {"LinkedListMySet","LinkedListMySet"},
         {"ArrayMySet","MapMySet"},
         {"ArrayMySet","LinkedListMySet"},
         {"MapMySet","ArrayMySet"},
         {"MapMySet","LinkedListMySet"},
         {"LinkedListMySet","ArrayMySet"},
         {"LinkedListMySet","MapMySet"}
       }
     );
   }

  public static MySet createSet(String setClassName) throws MySetException
  {
    if (setClassName.equals("ArrayMySet"))
      return new ArrayMySet();
    if (setClassName.equals("MapMySet"))
      return new MapMySet();
    if (setClassName.equals("LinkedListMySet"))
      return new LinkedListMySet();
    return null;
  }

  public static MySet createSetWithSize(String setClassName, int size) throws MySetException
  {
    if (setClassName.equals("ArrayMySet"))
      return new ArrayMySet(size);
    if (setClassName.equals("MapMySet"))
      return new MapMySet(size);
    if (setClassName.equals("LinkedListMySet"))
      return new LinkedListMySet(size);
    return null;
  }

  public static MySet createSetWithContent(String setClassName, Object... items)
    throws MySetException
  {
    MySet set = createSet(setClassName);
    if (set == null) return null;
    for (Object item : items)
    {
      set.add(item);
    }
    return set;
  }
}
