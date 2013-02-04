package myset;

import java.util.List;

/**
 *  This interface defines the public methods that all set classes have.
 *  The interface extends iterable to allow sets to be iterated using the
 *  for each loop.
 */
public interface MySet extends Iterable
{
  /**
   * The fixed maximum size of a set.
   */
  static final int MAX_SIZE = 1000;

  /**
   * Add a value to a set.
   * @param obj The value to add.
   * @throws MySetException If the set is full.
   */
  void add(Object obj) throws MySetException;

  /**
   * Check if the set contains a value.
   * @param obj The value to look for.
   * @return True if the set contains the value, false otherwise.
   */
  boolean contains(Object obj);

  /**
   * Check if the set is empty.
   * @return True if the set is empty, false otherwise.
   */
  boolean isEmpty();

  /**
   * Remove a value from the set. Do nothing if the value is not in the set.
   * @param obj The value to remove.
   */
  void remove(Object obj);

  /**
   * Determine the size of the set (the numbers of values stored).
   * @return The size of the set.
   */
  int size();

  /**
   * Create a List containing the set contents, with nested sets stored as nested lists.
   * @return A List
   */
  List toList();

  /**
   * Generate the union of the set with another set. The union is a new set, leaving the
   * source sets unchanged.
   * @param mySet The set to form a union with.
   * @return A new set containing the union.
   * @throws MySetException If the resulting set is too big or cannot be created.
   */
  MySet union(MySet mySet) throws MySetException;

  
  void setFactory(MySetFactory aFactory);


  /**
   * Generate the intersection of the set with another set. The intersection is
   * a new set, leaving the source sets unchanged.
   * @param mySet The set to form an intersection with.
   * @return A new set containing the intersection.
   * @throws MySetException If the resulting set is too big.
   */
  MySet intersection(MySet mySet) throws MySetException;

  /**
   * Generate the complementation the set with another set. The complementation is
   * a new set, leaving the source sets unchanged.
   * @param mySet The set to form a complementation with.
   * @return A new set containing the complementation.
   * @throws MySetException If the resulting set is too big.
   */
  MySet difference(MySet mySet) throws MySetException;

  MySet powerSet() throws MySetException;
}
