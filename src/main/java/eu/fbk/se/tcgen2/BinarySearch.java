package eu.fbk.se.tcgen2;

import java.util.Scanner;

public class BinarySearch {
	public static void main(String args[]) {
		int c, search, array[] = new int[5];

		Scanner in = new Scanner(System.in);
		System.out.print("Enter 5 increasing integers: ");
		for (c = 0; c < 5; c++)
			array[c] = in.nextInt();
		
		System.out.print("Enter value to find: ");
		search = in.nextInt();

		int middle = search(array, search);
		if (middle >= 0) {
			System.out.println(search + " found at location " + (middle + 1) + ".");
		} else if (middle == -1) {
			System.out.println(search + " is not present in the list.\n");
		} else {
			System.out.println("Integers not sorted.\n");
		}
		in.close();
	}

	public static int search(int array[], int search) {
		int first = 0;
		int last = array.length - 1;
		int middle = (first + last) / 2;
		boolean sorted = false;
		
		if (array[0] <= array[1]) {
			if (array[1] <= array[2]) {
				if (array[2] <= array[3]) {
					if (array[3] <= array[4]) {
						sorted = true;
						while (first <= last) {
							if (array[middle] < search) 
								first = middle + 1;
							else if (array[middle] == search) 
								return middle; // element found at index middle
							else
								last = middle - 1;
							
							middle = (first + last) / 2;
						}
					}
				}
			}
		}
		if (!sorted)
			return -2; // array not sorted
		return -1; // element not found
	}
}
