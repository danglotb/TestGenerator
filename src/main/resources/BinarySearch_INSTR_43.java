package eu.fbk.se.tcgen2;

public class BinarySearch_INSTR_43 {
    public static void main(java.lang.String[] args) {
        int c;
        int search;
        int[] array = new int[5];
        java.util.Scanner in = new java.util.Scanner(java.lang.System.in);
        java.lang.System.out.print("Enter 5 increasing integers: ");
        for (c = 0; c < 5; c++)
            array[c] = in.nextInt();
        
        java.lang.System.out.print("Enter value to find: ");
        search = in.nextInt();
        int middle = eu.fbk.se.tcgen2.BinarySearch.search(array, search);
        if (middle >= 0) {
            java.lang.System.out.println((((search + " found at location ") + (middle + 1)) + "."));
        }else
            if (middle == (-1)) {
                java.lang.System.out.println((search + " is not present in the list.\n"));
            }else {
                java.lang.System.out.println("Integers not sorted.\n");
            }
        
        in.close();
    }

    public static int search(int[] array, int search) {
        fitness = Math.min(fitness, 7);
        int first = 0;
        int last = (array.length) - 1;
        int middle = (first + last) / 2;
        boolean sorted = false;
        if ((array[0]) <= (array[1])) {
            fitness = Math.min(fitness, 6);
            if ((array[1]) <= (array[2])) {
                fitness = Math.min(fitness, 5);
                if ((array[2]) <= (array[3])) {
                    fitness = Math.min(fitness, 4);
                    if ((array[3]) <= (array[4])) {
                        fitness = Math.min(fitness, 3);
                        sorted = true;
                        while (first <= last) {
                            fitness = Math.min(fitness, 2);
                            if ((array[middle]) < search)
                                first = middle + 1;
                            else {
                                fitness = Math.min(fitness, 1);
                                if ((array[middle]) == search) {
                                    fitness = Math.min(fitness, 0);
                                    return middle;
                                }else
                                    last = middle - 1;
                                
                            }
                            middle = (first + last) / 2;
                        } 
                    }
                }
            }
        }
        if (!sorted)
            return -2;
        
        return -1;
    }

    public static int fitness;
}
