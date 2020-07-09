package com.loong;


import org.junit.Test;

import java.util.Objects;

/**
 * @author yvanme
 * @version 1.0
 * @Description
 * @create 2020-07-08 16:32:11
 **/
public class ObjectsTest {

    @Test
    public void test01() {
        System.out.println(Objects.hashCode("111"));
        System.out.println(Objects.hash("111"));
    }
}
