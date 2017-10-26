
import static org.junit.Assert.*;
import org.junit.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.rules.ExpectedException;

/**
 * 
 * A unit-testing harness over the MyLinkedList class.
 * 
 */
public class MyLinkedListTest
{
   private MyLinkedList<String> _testLinkedList; // Collection to test
   private final int TO_STORE = 5; // The number of elements we'll store
   private final String[] _STRINGS = { "this", "that", "another", "and",
         "one more" };


   /**
    * Method called by JUnit before each test
    */
   @Before
   public void setUp()
   {
      // Initialize the object we're testing
      _testLinkedList = new MyLinkedList<String>();
   }

   @Rule
   public final ExpectedException exceptionGrabber = ExpectedException.none();


   /**
    * Fill the collection, testing the addElement, isFull, size, and isEmpty
    * methods.
    *
    * Postcondition: collection is filled with String objects 0-to-(TO_STORE-1)
    */
   @Test
   public void testFillCollection() throws Exception
   {
      // Collection should not be full, but should be empty
      testCardinality(0);

      // Add things to the collection. Make sure it shows the correct
      // number of elements and is not empty and is not full.
      for (int i = 0; i < TO_STORE; i++)
      {
         // Add a value to the collection
         _testLinkedList.addElement(_STRINGS[i]);
         // Make sure the empty, full, and size are correct
         testCardinality(i + 1);
      }

      // Make sure the empty, full, and size are correct
      testCardinality(TO_STORE);

   } // testFillCollection


   /**
    * Make sure null's can't be added
    */
   @Test
   public void testAddNulls() throws Exception
   {
      // If we try to add NULL, it should throw an
      // exception
      try
      {
         _testLinkedList.addElement(null);
         // Uh-oh, if we got to this point we have a problem, so fail
         // this test
         fail("Should throw exception");
      }
      catch (IllegalArgumentException e)
      {
      } // Success

      // Make sure it's still empty
      testCardinality(0);
   }


   /**
    * Ensures that findElement only finds elements that are in the false
    * 
    * @throws Exception
    */
   @Test
   public void testFindSomething() throws Exception
   {
      MyLinkedList<String> newLinkedList = new MyLinkedList<String>();
      fillList(_STRINGS, newLinkedList);

      // make sure that a random item is not found in the list
      assertTrue(newLinkedList.findElement("FooBar") == null);

      // make sure that every item that was added into the list is equal to it's
      // source element.
      for (int i = 0; i < _STRINGS.length; i++)
      {
         assertTrue(newLinkedList.findElement(_STRINGS[i]).equals(_STRINGS[i]));
      }

      // Remove all elements from the list
      for (int i = 0; i < _STRINGS.length; i++)
      {
         newLinkedList.removeElement(_STRINGS[i]);
      }

      // Make sure that all those items can no longer be found in the list.
      for (int i = 0; i < _STRINGS.length; i++)
      {
         assertTrue(newLinkedList.findElement(_STRINGS[i]) == null);
      }
   }


   /**
    * Make sure null's can't be found
    */
   @Test
   public void testContainsNulls() throws Exception
   {
      // If we search for a NULL we should get false
      assertFalse(_testLinkedList.containsElement(null));
   }


   /**
    * Tests that findElement works on equality not identity.
    */
   @Test
   public void testFindElementEquality()
   {
      // Trick the compiler with appending to the String. otherwise it'll
      // just make another reference to _STRINGS[4]
      String oneMore = "one ";
      oneMore += "more";

      // Make sure that they aren't identical
      assertFalse(oneMore == _testLinkedList.findElement(oneMore));

   }


   /**
    * Testing the makeEmpty, isFull, size, and isEmpty methods.
    *
    * Postcondition - collection is empty
    */
   @Test
   public void testMakeEmpty() throws Exception
   {
      // Make sure we can safely call it on an empty collection
      _testLinkedList.makeEmpty();
      testCardinality(0);

      // Fill the collection
      testFillCollection();

      // Empty the collection and verify that it's empty, not full, with
      // the right number of elements
      _testLinkedList.makeEmpty();
      testCardinality(0);

   } // testMakeEmpty


   /**
    * Test the containsElement method
    *
    * Postcondition - collection is empty
    */
   @Test
   public void testContainsElement() throws Exception
   {
      // Try to find some things in an empty collection
      assertFalse(_testLinkedList.containsElement("Not in the collection"));

      // Call testFillCollection since it fills the collection for us
      testFillCollection();

      // Try to find each of the things we added to this collection
      for (int i = 0; i < TO_STORE; i++)
      {
         assertTrue(_testLinkedList.containsElement(_STRINGS[i]));
      }

      // Test to make sure that we find based on equality, not identity
      StringBuffer s = new StringBuffer(_STRINGS[0]);
      assertTrue(_testLinkedList.containsElement(s.toString()));

      // Make sure missing things aren't there
      assertFalse(_testLinkedList.containsElement("Not in the collection"));

   } // testContainsElement


   /**
    * Test the findElement method
    *
    * Postcondition - collection is empty
    */
   @Test
   public void testFindElement() throws Exception
   {
      // Try to find some things in an empty collection
      assertNull(_testLinkedList.findElement(""));

      // Call testFillCollection since it fills the collection for us
      testFillCollection();

      // Try to find each of the things we added to this collection
      for (int i = 0; i < TO_STORE; i++)
      {
         assertEquals(_STRINGS[i],
            (String) _testLinkedList.findElement(_STRINGS[i]));
      }

      // Make sure missing things aren't there
      assertNull(_testLinkedList.findElement(""));

      // Test to make sure that we find based on equality, not identity
      StringBuffer s = new StringBuffer(_STRINGS[0]);
      assertSame(_STRINGS[0],
         (String) _testLinkedList.findElement(s.toString()));

   } // testContainsElement


   /**
    * Tests prepending elements to a new list, checking the cardinality and
    * order after the elements have been prepended.
    */
   @Test
   public void testPrependingToNewList()
   {
      MyLinkedList<String> prependList = new MyLinkedList<String>();

      String[] stringsInReverseOrder = new String[_STRINGS.length];
      // prepends all of the Strings
      for (int i = _STRINGS.length - 1; i >= 0; i--)
      {
         prependList.prependElement(_STRINGS[i]);
         stringsInReverseOrder[i] = _STRINGS[i];
      }
      // ensure they're the same length
      testCardinality(_STRINGS.length, prependList);

      // ensure they're in the same order as the array
      testOrder(stringsInReverseOrder, prependList);
   }


   /**
    * Tests appending elements to a new list, checking cardinality and order
    * after the elements have been appended.
    */
   @Test
   public void testAppendingToNewList() throws Exception
   {
      MyLinkedList<String> appendList = new MyLinkedList<String>();

      // for every string in _STRINGS, add it to the end of the list
      for (int i = 0; i < _STRINGS.length; i++)
      {
         appendList.addElement(_STRINGS[i]);
      }
      // Ensure that the length and order of the list is correct.
      testCardinality(_STRINGS.length, appendList);

      testOrder(_STRINGS, appendList);

   }


   /**
    * Tests the isEmpty method on a list, ensuring that it works correctly on a
    * new list, a populated list, and a list that had all elements removed.
    */
   @Test
   public void testIsEmpty() throws Exception
   {

      MyLinkedList<String> isEmptiedList = new MyLinkedList<String>();
      // ensure the brand-new list is empty
      assertTrue(isEmptiedList.isEmpty());

      // fill it
      fillList(_STRINGS, isEmptiedList);

      // remove each element in the list, ensuring that it isn't empty until
      // after the last element has been removed
      for (int i = 0; i < _STRINGS.length; i++)
      {
         assertFalse(isEmptiedList.isEmpty());
         isEmptiedList.removeElement(_STRINGS[i]);
      }

      // should be empty here
      assertTrue(isEmptiedList.isEmpty());
   }


   /**
    * Tests that the makeEmpty method doesn't malfunction after an element has
    * been removed.
    * 
    */
   @Test
   public void testMakeEmptyAfterRemoving() throws Exception
   {
      MyLinkedList<String> emptiedAfterRemoving = new MyLinkedList<String>();
      fillList(_STRINGS, emptiedAfterRemoving);

      // remove a few items
      emptiedAfterRemoving.removeElement(_STRINGS[0]);
      emptiedAfterRemoving.removeElement(_STRINGS[1]);
      emptiedAfterRemoving.removeElement(_STRINGS[2]);


      // empty it and then check cardinality
      emptiedAfterRemoving.makeEmpty();
      testCardinality(0, emptiedAfterRemoving);
   }


   /**
    * Tests that the containsElement method actually checks the list, instead of
    * just returning true/false for everything.
    */
   @Test
   public void testContains()
   {
      // Ensure that every element in _STRINGS that was placed in 
      // _testLinkedList is actually there by .containsElement
      for (int i = 0; i < _testLinkedList.size(); i++)
      {
         assertTrue(_testLinkedList.containsElement(_STRINGS[i]));
      }

      // ensure that a random element that was never added is not in the 
      // list.
      assertFalse(_testLinkedList.containsElement("FOOBAR"));
   }


   /**
    * Tests that the internal state of the list is still correct after (some)
    * number of elements have been removed, including the order by way of an
    * Iterator, as well as the containsElement method, even on elements that
    * have already been removed.
    */
   @Test
   public void testContainsAfterRemoval() throws Exception
   {
      MyLinkedList<String> containsAfterRemoval = new MyLinkedList<String>();
      fillList(_STRINGS, containsAfterRemoval);

      // remove all but one element, checking size every time.
      for (int i = 0; i < _STRINGS.length - 1; i++)
      {
         containsAfterRemoval.removeElement(_STRINGS[i]);
      }

      // Test iteration over (one) element
      Iterator<String> removedIterator = containsAfterRemoval.iterator();

      // make sure it has a next element
      assertTrue(removedIterator.hasNext());

      // make sure that the last element in the _STRINGS array is the next item
      // in the Iterator.
      assertTrue(removedIterator.next().equals(_STRINGS[_STRINGS.length - 1]));

      // make sure it doesn't have any more elements
      assertFalse(removedIterator.hasNext());

      // Test contents of all other items - it doesn't contain any of the
      // strings that were removed.
      for (int i = 0; i < _STRINGS.length - 1; i++)
      {
         assertFalse(containsAfterRemoval.containsElement(_STRINGS[i]));

      }

      // finally, remove the last element - should be empty.
      containsAfterRemoval.removeElement(_STRINGS[_STRINGS.length - 1]);
      // it doesn't contain any of the old strings
      for (int i = 0; i < _STRINGS.length; i++)
      {
         assertFalse(containsAfterRemoval.containsElement(_STRINGS[i]));
      }


      // ensure that the list is empty.
      testCardinality(0, containsAfterRemoval);
   }


   /**
    * Tests the boundary case of filling, then removing all elements, then
    * filling up a list again, checking order and cardinality at the end.
    */
   @Test
   public void testFillThenWipeThenFillAgainList() throws Exception
   {
      MyLinkedList<String> listUnderTest = new MyLinkedList<String>();
      // Fill the list, and ensure that all those elements are registered
      // in size()
      fillList(_STRINGS, listUnderTest);
      testCardinality(_STRINGS.length, listUnderTest);

      // for every string that was just placed in listUnderTest, remove that 
      // string
      for (int i = 0; i < _STRINGS.length; i++)
      {
         listUnderTest.removeElement(_STRINGS[i]);
      }

      // add those elements back
      for (int i = 0; i < _STRINGS.length; i++)
      {
         listUnderTest.addElement(_STRINGS[i]);
      }

      // test the order, should be identical to _STRINGS
      testOrder(_STRINGS, listUnderTest);

      // test the number of elements recorded by size()
      testCardinality(_STRINGS.length, listUnderTest);

   }


   /**
    * Tests inserting elements after a particular element works correctly,
    * checking cardinality and order of elements.
    * 
    */
   @Test
   public void testAddAfterEnd() throws Exception
   {

      MyLinkedList<String> addAfter = new MyLinkedList<String>();
      // fill the list
      fillList(_STRINGS, addAfter);

      // insert an element after the last element in the list
      addAfter.insertAfter(addAfter.findElement(_STRINGS[_STRINGS.length - 1]),
         _STRINGS[0]);

      // create an identical array to the expected state of the list
      String[] insertedAfterEnd = { _STRINGS[0], _STRINGS[1], _STRINGS[2],
            _STRINGS[3], _STRINGS[4], _STRINGS[0] };


      // test the order of the list against the companion array
      testOrder(insertedAfterEnd, addAfter);

      // test the length against the companion array's length
      testCardinality(insertedAfterEnd.length, addAfter);

   }


   /**
    * Ensure that insertAfter uses identity to find the particular element, not
    * equality. [identity = shallow equality, equality = deep equality]
    */
   @Test
   public void testInsertAfterIdentityNotEquality() throws Exception
   {
      // "trick" the compiler into thinking that strings_1 is not the same
      // as _STRINGS[1]
      String strings_1 = "";

      // create a new list, and fill it with _STRINGS
      MyLinkedList<String> insertAfterIdentity = new MyLinkedList<String>();
      fillList(_STRINGS, insertAfterIdentity);

      // add a couple of tricky assignments that make strings_1 not-identical
      strings_1 = "tha";
      strings_1 += "t";
      assertTrue(strings_1 != _STRINGS[1]);

      // Expecting an exception because strings_1 != _STRINGS[1]

      // Tell the exception grabber to expect a NoSuchElementException
      exceptionGrabber.expect(NoSuchElementException.class);
      insertAfterIdentity.insertAfter(strings_1, "FooBar");
   }
   
   
   /**
    * 
    * @throws Exception
    */
   @Test
   public void testInsertAfterRetainsOrder() throws Exception
   {
      MyLinkedList<String> insertAfterOrder = new MyLinkedList<String>();
      // fill the list
      fillList(_STRINGS, insertAfterOrder);

      // insert a new string after an element in the middle of the list
      insertAfterOrder.insertAfter(_STRINGS[2], "Foo");

      // create a companion array that represents what the list should contain
      String[] insertAfterCompanion = { _STRINGS[0], _STRINGS[1], _STRINGS[2],
            "Foo", _STRINGS[3], _STRINGS[4] };

      // test the size and order against the companion array
      testCardinality(insertAfterCompanion.length, insertAfterOrder);
      testOrder(insertAfterCompanion, insertAfterOrder);

   }


   /**
    * Ensures that a populated/unpopulated list is not 'full' - linkedLists
    * can't be filled*
    * 
    * *unless you do something crazy I guess, I'm sure there's a way to fill the
    * stack, in which case, it would be full. But it would also be a
    * surprisingly large misuse of resources, and probably a waste of time.
    */
   @Test
   public void testIsFull()
   {
      assertFalse(_testLinkedList.isFull());
      assertFalse(new MyLinkedList<String>().isFull());
   }


   /**
    * A real big 'salad' test - 
    * fills the list, removes an element, checks what it contains, 
    * checks the order of the list, then it removes all the remaining elements.
    * Finally it tries to iterate over the empty list, and catches
    * the NoSuchElementException that is expected.
    */
   @Test
   public void testRemovingElementsThenOperating() throws Exception
   {
      MyLinkedList<String> removeFirstElement = new MyLinkedList<String>();

      // Fill the list
      fillList(_STRINGS, removeFirstElement);

      // Remove the first item
      removeFirstElement.removeElement(_STRINGS[0]);

      // ensure that the first element does not exist
      assertFalse(removeFirstElement.containsElement(_STRINGS[0]));

      // Ensure that an element cannot be inserted after the removed first
      // element
      exceptionGrabber.expect(NoSuchElementException.class);
      removeFirstElement.insertAfter(_STRINGS[0], "FooBar");

      // Create and populate an array of _STRINGS[1] ->
      // _STRINGS[_STRINGS.length]
      String[] currentItemsInMyLinkedList = new String[_STRINGS.length - 1];
      for (int i = 0; i < currentItemsInMyLinkedList.length; i++)
      {
         currentItemsInMyLinkedList[i] = _STRINGS[i + 1];
      }

      // Test order of the linkedList against the created array.
      testOrder(currentItemsInMyLinkedList, removeFirstElement);


      // Remove all elements starting from the end.
      for (int i = currentItemsInMyLinkedList.length - 1; i >= 0; i--)
      {
         removeFirstElement.removeElement(currentItemsInMyLinkedList[i]);
      }

      // Create a new iterator and ensure that it has nothing to iterate over
      Iterator<String> emptyIterator = removeFirstElement.iterator();

      // Should not have anything to iterate over
      assertFalse(emptyIterator.hasNext());

      // Should throw a NoSuchElementException
      exceptionGrabber.expect(NoSuchElementException.class);
      emptyIterator.next();

   }
   

   /**
    * Adds and removes the same elements 100 times, is not a stress test, more
    * looking for weird logic errors that are prevalent when the problem grows.
    */
   @Test
   public void testBruteForceRemoveAdd() throws Exception
   {
      MyLinkedList<String> bruteForced = new MyLinkedList<String>();

      // repeat this operation 100 times
      for (int i = 0; i < 100; i++)
      {
         // fill the list, then for every element in the companion array,
         // remove that element. After removing the element, ensure that
         // the element is no longer in the list.
         fillList(_STRINGS, bruteForced);
         for (int j = 0; j < _STRINGS.length; j++)
         {
            bruteForced.removeElement(_STRINGS[j]);
            assertFalse(bruteForced.containsElement(_STRINGS[j]));

         }
      }

      // fill it one last time, then check the order.
      fillList(_STRINGS, bruteForced);
      testOrder(_STRINGS, bruteForced);
   }


   /**
    * Ensure that the removeElement method only removes one element at a time.
    */
   @Test
   public void testRemoveElementRemovesOnlyOneElement() throws Exception
   {
      MyLinkedList<String> removeOnlyOne = new MyLinkedList<String>();

      // fill the list twice with _STRINGS
      fillList(_STRINGS, removeOnlyOne);
      fillList(_STRINGS, removeOnlyOne);

      // make sure that the length is correct
      testCardinality(_STRINGS.length * 2, removeOnlyOne);

      // remove one set of _STRINGS
      for (int i = 0; i < _STRINGS.length; i++)
      {
         removeOnlyOne.removeElement(_STRINGS[i]);
      }

      // Make sure that the order/number of actual elements is correct
      testOrder(_STRINGS, removeOnlyOne);

      // Test the size of the list again
      testCardinality(_STRINGS.length, removeOnlyOne);

   }
   
   /**
    * Tests the complete functionality of the compareTo method, using
    * un-changed lists.
    */
   @Test
   public void testCompareToBasic() throws Exception
   {
      MyLinkedList<String> linkedListA = new MyLinkedList<String>();
      MyLinkedList<String> linkedListB = new MyLinkedList<String>();

      // ensure that empty lists are equal by comparison, not identity
      assertFalse(linkedListA == linkedListB);
      assertTrue(linkedListA.compareTo(linkedListB) == 0);

      // fill both lists
      fillList(_STRINGS, linkedListA);
      fillList(_STRINGS, linkedListB);

      // ensure that they're identical by comparison
      assertTrue(linkedListA.compareTo(linkedListB) == 0);

      // add an element to one list
      linkedListA.addElement("Foo");

      // Since linkedListA has one more element than B, the first statement
      // should be greater than zero, and the second should be less than zero.
      assertTrue(linkedListA.compareTo(linkedListB) > 0);
      assertTrue(linkedListB.compareTo(linkedListA) < 0);

      // remove two elements from linkedListA
      linkedListA.removeElement("Foo");
      linkedListA.removeElement(_STRINGS[0]);

      // Since linkedListA has one less element than B, the first statement
      // should be less than zero, and the second should be greater than zero.
      assertTrue(linkedListA.compareTo(linkedListB) < 0);
      assertTrue(linkedListB.compareTo(linkedListA) > 0);

      // create equal linkedLists, then mess with the order of one
      MyLinkedList<String> similarLinkedListA = new MyLinkedList<String>();
      MyLinkedList<String> similarLinkedListB = new MyLinkedList<String>();

      // fill them
      fillList(_STRINGS, similarLinkedListA);
      fillList(_STRINGS, similarLinkedListB);

      // prepend/append identical elements
      similarLinkedListA.prependElement(_STRINGS[0]);
      similarLinkedListB.addElement(_STRINGS[0]);

      // ensure that they aren't equal
      assertTrue(similarLinkedListA.compareTo(similarLinkedListB) != 0);
   }
   
   
   /**
    * Tests the compareTo method after removing a couple items
    * @throws Exception
    */
   @Test
   public void testCompareToAfterRemovingItems() throws Exception
   {
      MyLinkedList<String> firstList = new MyLinkedList<String>();
      MyLinkedList<String> secondList = new MyLinkedList<String>();
      
      // fill both lists
      fillList(_STRINGS, firstList);
      fillList(_STRINGS, secondList);
      
      // Remove an element from the first list
      firstList.removeElement(_STRINGS[0]);

      // ensure that when compared, the first list is less than the second list
      assertTrue(firstList.compareTo(secondList) < 0);

      // Remove another element from the first list
      firstList.removeElement(_STRINGS[1]);

      // ensure that when compared, the list is less than the second list
      assertTrue(firstList.compareTo(secondList) < 0);
   }

   
   @Test
   public void testCompareToLongItems() throws Exception
   {
      MyLinkedList<String> longItemList = new MyLinkedList<String>();
      MyLinkedList<String> smallItemList = new MyLinkedList<String>();
      
      longItemList.addElement("a really really really really long String, like "
         + "a crazy long String.");
      longItemList.addElement("I'm not kidding, I want big Strings in this one. "
         + "no really-do it. just make the Strings in this one long and short "
            + "in the other");
      
      smallItemList.addElement("1");
      smallItemList.addElement("2");
      smallItemList.addElement("3");
      
      assertTrue(smallItemList.compareTo(longItemList) < 0);
   }

   /**
    * Tests inserting a new element after a removed element, should throw
    * a NoSuchElement exception.
    */
   @Test
   public void testInsertAfterRemovedElements() throws Exception
   {
      MyLinkedList<String> insertAfterRemoved = new MyLinkedList<String>();
      // fill the list with _STRINGS
      fillList(_STRINGS, insertAfterRemoved);

      // remove an element
      insertAfterRemoved.removeElement(_STRINGS[2]);

      // try to insert an item after the removed element
      exceptionGrabber.expect(NoSuchElementException.class);
      insertAfterRemoved.insertAfter(_STRINGS[2], "raBooF");

   }

   /**
    * Tests inserting elements, then removing those elements that were inserted.
    */
   @Test
   public void testInsertingThenRemoving() throws Exception
   {
      MyLinkedList<String> insertEdgeCase = new MyLinkedList<String>();
      fillList(_STRINGS, insertEdgeCase);
      
      // Insert four identical elements after the first element in the list
      for (int i = 0; i < 4; i++)
      {
         insertEdgeCase.insertAfter(_STRINGS[0], "Foo");
      }
      // create a companion array that should be identical to the list's order
      String[] edgeCaseInArray = {_STRINGS[0], "Foo", "Foo", "Foo", "Foo", 
            _STRINGS[1], _STRINGS[2], _STRINGS[3], _STRINGS[4]};
      
      // test the order against the companion array
      testOrder(edgeCaseInArray, insertEdgeCase);
      
      // remove three identical elements, ensuring that more identical 
      // elements still exist
      for (int i = 0; i < 3; i++)
      {
         assertTrue(insertEdgeCase.containsElement("Foo"));
         insertEdgeCase.removeElement("Foo");
      }
      
      // ensure that one more of those new elements still exists in the list
      assertTrue(insertEdgeCase.containsElement("Foo"));

   }

   /**
    * Test finding a null element in an empty and populated list
    */
   @Test
   public void findNullElement() throws Exception
   {
      MyLinkedList<String> nullElement = new MyLinkedList<String>();
      exceptionGrabber.expect(NoSuchElementException.class);
      nullElement.removeElement("FooBar");
      nullElement.findElement("FOO");
      nullElement.findElement(null);
      
      fillList(_STRINGS, nullElement);
      nullElement.findElement(null);
   }
   
   
   public class widget
   {
      int a;
      public widget()
      {
         a = 100;
      }
      public int getWidget()
      {
         return a;
      }
   }
   
   
   @Test
   public void testIncomparableObjects() throws Exception
   {
      MyLinkedList<widget> widgetLista = new MyLinkedList<widget>();
      MyLinkedList<widget> widgetListb = new MyLinkedList<widget>();
      widgetLista.addElement(new widget());
      widgetLista.addElement(new widget());
      widgetLista.addElement(new widget());
      widgetLista.addElement(new widget());
      
      widgetListb.addElement(new widget());
      widgetListb.addElement(new widget());
      widgetListb.addElement(new widget());
      widgetListb.addElement(new widget());
      widgetListb.addElement(new widget());

      exceptionGrabber.expect(ClassCastException.class);
      widgetLista.compareTo(widgetListb);
   }
   
   
   @Test
   public void testCompareToReturnsSameComparison() throws IllegalArgumentException, CollectionFullException
   {
      MyLinkedList<String> listFoo = new MyLinkedList<String>();
      MyLinkedList<String> listBar = new MyLinkedList<String>();
      String foo = "Foo";
      String bar = "Bar";  
      listFoo.addElement(foo);
      listBar.addElement(bar);
    
      assertEquals(listFoo.compareTo(listBar), foo.compareTo(bar));
   }
   
   @Test
   public void testCompareToReturnsOneDifferentComparison() throws Exception
   {
      MyLinkedList<String> fullListFoo = new MyLinkedList<String>();
      MyLinkedList<String> fullListBar = new MyLinkedList<String>();
      
      fillList(_STRINGS, fullListFoo);
      fillList(_STRINGS, fullListBar);
      
      
      assertEquals(fullListFoo.compareTo(fullListBar), 0);

      
      String foo = "Foo";
      String bar = "Bar";
      fullListFoo.addElement(foo);
      fullListBar.addElement(bar);
      
      assertEquals(fullListFoo.compareTo(fullListBar), foo.compareTo(bar));
      assertEquals(fullListBar.compareTo(fullListFoo), bar.compareTo(foo));
      fullListFoo.addElement(bar);
      fullListBar.addElement(foo);
      assertFalse(fullListFoo.compareTo(fullListBar) == 0);
      assertFalse(fullListBar.compareTo(fullListFoo) == 0);
   }
   
   @Test
   public void prependNullElement() throws Exception
   {
      MyLinkedList<String> prependedNull = new MyLinkedList<String>();
      exceptionGrabber.expect(IllegalArgumentException.class);
      prependedNull.prependElement(null);
   }
   
   @Test
   public void removeNullElement() throws Exception
   {
      MyLinkedList<String> removedNull = new MyLinkedList<String>();
      fillList(_STRINGS, removedNull);
      exceptionGrabber.expect(NoSuchElementException.class);
      removedNull.removeElement(null);
   }
   
   @Test
   public void removeNonExistentElement() throws Exception
   {
      MyLinkedList<String> removeNoElement = new MyLinkedList<String>();
      fillList(_STRINGS, removeNoElement);
      exceptionGrabber.expect(NoSuchElementException.class);
      removeNoElement.removeElement("FooBar");
   }
   
   /**
    * Helper method to see if isEmpty, isFull, and size are correct
    *
    * @param num is number of elements that should be in the collection
    */
   public void testCardinality(int num)
   {
      assertEquals((num == 0), _testLinkedList.isEmpty());
      assertEquals(num, _testLinkedList.size());
   }


   /**
    * Helper method to see if isEmpty, isFull, and size are correct for a given
    * MyLinkedList
    *
    * @param toTest is the given MyLinkedList to test
    * @param num is number of elements that should be in the collection
    */
   public void testCardinality(int num, MyLinkedList<String> toTest)
   {
      assertEquals((num == 0), toTest.isEmpty());
      assertEquals(num, toTest.size());
   }
   


   /**
    * Helper method to check the order of a given LinkedList full of Strings is
    * correct.
    * 
    * @param ordered is the control array of strings
    * @param toTest is the MyLinkedList under testing
    */
   public void testOrder(String[] ordered, MyLinkedList<String> toTest)
   {
      Iterator<String> linkedListIterator = toTest.iterator();

      // for every element in the array
      for (int i = 0; i < ordered.length; i++)
      {
         // if the array has an element, and the linkedList does not, then
         // fail a test
         if (!linkedListIterator.hasNext())
         {
            fail("Linked List missing element #" + (i + 1));
         }
         
         // otherwise, check for equality between the element in the array,
         // and the item in the list.
         else
         {
            assertTrue(linkedListIterator.next().equals(ordered[i]));
         }
      }
      if (linkedListIterator.hasNext())
      {
         fail("Linked List has extra element #" + ordered.length);
      }

   }
   


   /**
    * Adds the specified items in the given String array to the given
    * MyLinkedList.
    * 
    * @param itemsToFillWith array of elements to fill the MyLinkedList with
    * @param toFill MyLinkedList to fill with the specified array
    * @throws IllegalArgumentException
    * @throws CollectionFullException
    */
   public void fillList(String[] itemsToFillWith, MyLinkedList<String> toFill)
      throws IllegalArgumentException, CollectionFullException
   {
      for (int i = 0; i < itemsToFillWith.length; i++)
      {
         toFill.addElement(itemsToFillWith[i]);
      }
   }

}
