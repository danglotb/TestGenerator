package fr.inria.testgenerator;

import org.junit.Test;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

import static org.junit.Assert.assertEquals;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 08/05/17
 */
public class TestFitnessAssignmentInjector {

    @Test
    public void testOnBinarySearch() throws Exception {
        final String path = "src/main/java/eu/fbk/se/tcgen2/BinarySearch.java";
        final String fullQualifiedName = "eu.fbk.se.tcgen2.BinarySearch";

        Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setCommentEnabled(true);

        launcher.addInputResource(path);
        launcher.buildModel();

        final CtClass<Object> clazz = launcher.getFactory().Class().get(fullQualifiedName);

        FitnessInsertion.insertFitnessField(clazz);
        final CtMethod<?> method = clazz.getMethodsByName("search").get(0);
        FitnessAssignmentInjector.insertFitnessAssignement(43, method);

        assertEquals(expectedBody, method.getBody().toString());
    }

    private static String expectedBody = "{\n" +
            "    fitness = Math.min(fitness,8);\n" +
            "    int first = 0;\n" +
            "    int last = (array.length) - 1;\n" +
            "    int middle = (first + last) / 2;\n" +
            "    boolean sorted = false;\n" +
            "    if ((array[0]) <= (array[1])) {\n" +
            "        fitness = Math.min(fitness,7);\n" +
            "        if ((array[1]) <= (array[2])) {\n" +
            "            fitness = Math.min(fitness,6);\n" +
            "            if ((array[2]) <= (array[3])) {\n" +
            "                fitness = Math.min(fitness,5);\n" +
            "                if ((array[3]) <= (array[4])) {\n" +
            "                    fitness = Math.min(fitness,4);\n" +
            "                    sorted = true;\n" +
            "                    while (first <= last) {\n" +
            "                        fitness = Math.min(fitness,3);\n" +
            "                        // element found at index middle\n" +
            "                        if ((array[middle]) < search) {\n" +
            "                            fitness = Math.min(fitness,2);\n" +
            "                            first = middle + 1;\n" +
            "                        }// element found at index middle\n" +
            "                        else {\n" +
            "                            fitness = Math.min(fitness,1);\n" +
            "                            if ((array[middle]) == search) {\n" +
            "                                fitness = Math.min(fitness,0);\n" +
            "                                return middle;\n" +
            "                            }else {\n" +
            "                                fitness = Math.min(fitness,1);\n" +
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
            "        fitness = Math.min(fitness,2);\n" +
            "        return -2;\n" +
            "    }// array not sorted\n" +
            "    \n" +
            "    return -1;// element not found\n" +
            "    \n" +
            "}";
}
