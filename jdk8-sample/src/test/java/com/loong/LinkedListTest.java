package com.loong;



import org.junit.Test;

import java.util.LinkedList;

/**
 * @author yvanme
 * @version 1.0
 * @Description
 * @create 2020-04-15 18:04:30
 **/

/**
 * 有序,可重复
 * 优点: 底层数据结构是链表，查询慢，增删快。
 * 缺点: 线程不安全，效率高
 */
public class LinkedListTest {

    @Test
    public void test01() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(null);
        linkedList.add(1);
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        System.out.println(linkedList);
    }
}
