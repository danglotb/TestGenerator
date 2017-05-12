

package eu.fbk.se.tcgen2;


public class TestBinarySearch {
    @org.junit.Test
    public void testBinarySearchOnMiddle() {
        org.junit.Assert.assertEquals(1 ,BinarySearch.search(new int[]{3,10,23,42,45}, 10));
    }
}

