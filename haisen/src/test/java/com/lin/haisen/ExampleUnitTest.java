package com.lin.haisen;

import android.util.StateSet;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        TestBean testBean = new TestBean("a");
        LinkedList<TestBean> stringStack = new LinkedList<>();
        stringStack.add(new TestBean("a"));
        stringStack.add(new TestBean("b"));
        stringStack.add(testBean);
        stringStack.add(new TestBean("d"));
        stringStack.add(new TestBean("e"));

        for(TestBean s: stringStack){
            System.out.print(s+" ");
        }
        System.out.println();
//        for(String s: linkedList){
            System.out.println(stringStack.getLast());
//        }

        for(TestBean s: stringStack){
            System.out.print(s+" ");
        }

    }

    private class TestBean{
        String name;

        public TestBean(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "TestBean{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

}