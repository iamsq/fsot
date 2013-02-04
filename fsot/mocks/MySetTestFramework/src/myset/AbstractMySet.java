package myset;
/**
 * This class implements methods common to all concrete set implementations but does not
 * represent a complete set implementation.<br />
 *
 * New set objects are created using a Factory. By default a DefaultMySetFactory
 * instance is used but this can be replaced with any factory implementing the
 * MySetFactory interface.
 */

import java.util.List;
import java.util.ArrayList;

public abstract class AbstractMySet implements MySet
{
  private MySetFactory factory = new DefaultMySetFactory();

  public void setFactory(MySetFactory aFactory)
  {
    factory = aFactory;
  }

  @Override
  public String toString()
  {
    List elements = toList();
    boolean first = true;
    StringBuilder result = new StringBuilder();
    result.append('{');
    for (Object element : elements)
    {
      if (!first) { result.append(','); }
      result.append(element.toString());
      first = false;
    }
    result.append('}');
    return result.toString();
  }
  
  @SuppressWarnings(value="unchecked")
  public List toList()
  {
    ArrayList result = new ArrayList();
    for (Object obj : this)
    {
      if (obj instanceof MySet)
      {
        result.add(((MySet)obj).toList());
      }
      else {
        result.add(obj);
      }
    }
    return result;
  }
   @Override
  public boolean equals(Object obj)
  {
    if (obj == this) { return true; }
    if (!(obj instanceof MySet)) { return false; }
    MySet other = (MySet)obj;
    if (this.size() != other.size()) {return false;}
    for (Object value : other)
    {
      if (!this.contains(value)) { return false; }
    }
    return true;
  }

  @Override
  public int hashCode()
  {
    return toList().hashCode();
  }
  
  public MySet union(MySet mySet) throws MySetException
  {
    MySet result = getNewSetInstance(this);
    addAll(this,result);
    addAll(mySet,result);
    return result;
  }

  public MySet intersection(MySet mySet) throws MySetException{
    MySet result = getNewSetInstance(this);
    for (Object value : this){
        if (mySet.contains(value))
            result.add(value);
    }
    return result;
  }

  public MySet difference(MySet mySet) throws MySetException{
    MySet result = getNewSetInstance(this);
    addAll(this,result);
    for (Object value : this){
        if (mySet.contains(value))
            result.remove(value);
    }
    return result;
  }
   public MySet powerSet() throws MySetException
  {
    MySet powerSet = getNewSetInstance(this);
    powerSet.add(getNewSetInstance(this));
    for (Object element : this)
    {
      addElementToEachSet(powerSet, element);
    }
    return powerSet;
  }


  protected void checkSize(int maximumSize)
    throws MySetException
  {
    if ((maximumSize < 1) || (maximumSize > MAX_SIZE))
    {
      throw new
        MySetException("Attempting to create a MySet object with invalid size");
    }
  }

  private MySet getNewSetInstance(MySet kind) throws MySetException
  {
    return factory.getInstance(kind.getClass());
  }

  private void addAll(MySet source, MySet destination)
    throws MySetException
  {
    for (Object obj : source)
    {
      destination.add(obj);
    }
  }

 
  private void addElementToEachSet(MySet setOfSets, Object obj)
    throws MySetException
  {
    MySet temp = getNewSetInstance(this);
    for (Object set : setOfSets)
    {
      MySet copyOfSet = ((AbstractMySet)set).copy();
      copyOfSet.add(obj);
      temp.add(copyOfSet);
    }
    addAll(temp,setOfSets);
  }
  
  private MySet copy() throws MySetException
  {
    MySet result = getNewSetInstance(this);
    addAll(this,result);
    return result;
  }
}
