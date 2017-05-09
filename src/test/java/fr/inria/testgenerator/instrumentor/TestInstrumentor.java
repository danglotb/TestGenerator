package fr.inria.testgenerator.instrumentor;

import org.junit.Before;
import org.junit.Test;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

import static fr.inria.testgenerator.instrumentor.Instrumentor.insertFitnessField;
import static org.junit.Assert.assertEquals;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 08/05/17
 */
public class TestInstrumentor {

    private Launcher launcher;

    @Before
    public void setUp() throws Exception {
        launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setCommentEnabled(true);

        launcher.addInputResource("src/main/resources/BinarySearch.java");
        launcher.buildModel();
    }

    @Test
    public void testInsertFitnessField() throws Exception {
        final CtClass<Object> clazz = launcher.getFactory().Class().get("eu.fbk.se.tcgen2.BinarySearch");
        insertFitnessField(clazz);
        assertEquals(expectedClass, clazz.toString());
    }

    @Test
    public void testInsertFitnessAssignment() throws Exception {
        final String fullQualifiedName = "eu.fbk.se.tcgen2.BinarySearch";
        final CtClass<Object> clazz = launcher.getFactory().Class().get(fullQualifiedName);
        Instrumentor.insertFitnessField(clazz);
        final CtMethod<?> method = clazz.getMethodsByName("search").get(0);
        Instrumentor.insertFitnessAssignement(43, method);
        assertEquals(expectedBody, method.getBody().toString());
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

    private static String expectedBody = "{\n" +
            "    fitness = Math.min(fitness, 8);\n" +
            "    int first = 0;\n" +
            "    int last = (array.length) - 1;\n" +
            "    int middle = (first + last) / 2;\n" +
            "    boolean sorted = false;\n" +
            "    if ((array[0]) <= (array[1])) {\n" +
            "        fitness = Math.min(fitness, 7);\n" +
            "        if ((array[1]) <= (array[2])) {\n" +
            "            fitness = Math.min(fitness, 6);\n" +
            "            if ((array[2]) <= (array[3])) {\n" +
            "                fitness = Math.min(fitness, 5);\n" +
            "                if ((array[3]) <= (array[4])) {\n" +
            "                    fitness = Math.min(fitness, 4);\n" +
            "                    sorted = true;\n" +
            "                    while (first <= last) {\n" +
            "                        fitness = Math.min(fitness, 3);\n" +
            "                        // element found at index middle\n" +
            "                        if ((array[middle]) < search) {\n" +
            "                            fitness = Math.min(fitness, 2);\n" +
            "                            first = middle + 1;\n" +
            "                        }// element found at index middle\n" +
            "                        else {\n" +
            "                            fitness = Math.min(fitness, 1);\n" +
            "                            if ((array[middle]) == search) {\n" +
            "                                fitness = Math.min(fitness, 0);\n" +
            "                                return middle;\n" +
            "                            }else {\n" +
            "                                fitness = Math.min(fitness, 1);\n" +
            "                                last = middle - 1;\n" +
            "                            }\n" +
            "                        }\n" +
            "                        middle = (first + last) / 2;\n" +
            "                    } \n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "    if (!sorted) {\n" +
            "        fitness = Math.min(fitness, 2);\n" +
            "        return -2;\n" +
            "    }// array not sorted\n" +
            "    \n" +
            "    return -1;// element not found\n" +
            "    \n" +
            "}";
}