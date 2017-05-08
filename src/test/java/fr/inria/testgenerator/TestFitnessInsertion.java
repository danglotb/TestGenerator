package fr.inria.testgenerator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 08/05/17
 */
public class TestFitnessInsertion {

    @Test
    public void testOnBinarySearch() throws Exception {
        assertEquals(expectedClass, FitnessInsertion.run("eu.fbk.se.tcgen2.BinarySearch",
                "src/main/java/eu/fbk/se/tcgen2/BinarySearch.java").toString());
    }

    private static final String expectedClass = "public class BinarySearch {\n" +
            "    public static void main(String[] args) {\n" +
            "        int c;\n" +
            "        int search;\n" +
            "        int[] array = new int[5];\n" +
            "        Scanner in = new Scanner(in);\n" +
            "        out.print(\"Enter 5 increasing integers: \");\n" +
            "        for (c = 0; c < 5; c++)\n" +
            "            array[c] = in.nextInt();\n" +
            "        \n" +
            "        out.print(\"Enter value to find: \");\n" +
            "        search = in.nextInt();\n" +
            "        int middle = BinarySearch.search(array, search);\n" +
            "        if (middle >= 0) {\n" +
            "            out.println((((search + \" found at location \") + (middle + 1)) + \".\"));\n" +
            "        }else\n" +
            "            if (middle == (-1)) {\n" +
            "                out.println((search + \" is not present in the list.\\n\"));\n" +
            "            }else {\n" +
            "                out.println(\"Integers not sorted.\\n\");\n" +
            "            }\n" +
            "        \n" +
            "        in.close();\n" +
            "    }\n" +
            "\n" +
            "    public static int search(int[] array, int search) {\n" +
            "        int first = 0;\n" +
            "        int last = (array.length) - 1;\n" +
            "        int middle = (first + last) / 2;\n" +
            "        boolean sorted = false;\n" +
            "        if ((array[0]) <= (array[1])) {\n" +
            "            if ((array[1]) <= (array[2])) {\n" +
            "                if ((array[2]) <= (array[3])) {\n" +
            "                    if ((array[3]) <= (array[4])) {\n" +
            "                        sorted = true;\n" +
            "                        while (first <= last) {\n" +
            "                            // element found at index middle\n" +
            "                            if ((array[middle]) < search)\n" +
            "                                first = middle + 1;\n" +
            "                            // element found at index middle\n" +
            "                            else\n" +
            "                                if ((array[middle]) == search)\n" +
            "                                    return middle;\n" +
            "                                else\n" +
            "                                    last = middle - 1;\n" +
            "                                \n" +
            "                            \n" +
            "                            middle = (first + last) / 2;\n" +
            "                        } \n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "        if (!sorted)\n" +
            "            return -2;\n" +
            "        // array not sorted\n" +
            "        \n" +
            "        return -1;// element not found\n" +
            "        \n" +
            "    }\n" +
            "\n" +
            "    public static int fitness;\n" +
            "}";
}
