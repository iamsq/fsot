package myset;

/**
 * Example code illustrating the use of MySet objects.
 */
public class Main
{
  private MySetFactory factory = new DefaultMySetFactory();

  public void print(MySet set)
  {
    boolean first = true;
    System.out.print("{");
    for (Object obj : set)
    {
      if (!first) { System.out.print(" , "); }
      first = false;
      System.out.print(obj);
    }
    System.out.println("}");
  }

  public void go()
  {
    MySet set1;
    MySet set2;
    try
    {
      set1 = factory.getInstance(ArrayMySet.class);
      set1.add(1);
      set1.add(2);
      set1.add(3);
      System.out.print("Set 1: ");
      print(set1);
      set2 = factory.getInstance(LinkedListMySet.class);
      set2.add(1);
      set2.add(3);
      set2.add(4);
      System.out.print("Set 2: ");
      print(set2);
      System.out.print("Union: ");
      print(set1.union(set2));
      System.out.print("Intersection: ");
      print(set1.intersection(set2));
      System.out.print("Difference (Set 1 - Set 2): ");
      print(set1.difference(set2));
      System.out.print("Difference (Set 2 - Set 1): ");
      print(set2.difference(set1));
      System.out.print("Power set: ");
      print(set1.powerSet());
    }
    catch (MySetException e)
    {
      System.out.println("====> MySet Exception thrown...");
    }
  }

  public static void main(String[] args)
  {
    new Main().go();
  }
}
