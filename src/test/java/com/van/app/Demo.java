package com.van.app;

import com.van.app.util.LRUCache;
import com.van.app.util.LRUUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class Demo {
    public volatile int i = 0;

    @Test
    public void test(){
        int i = (int)Math.ceil(12.1);
        System.out.println(i);
     }

     @Test
     public void test2() {
         Set<String> set1 = new TreeSet<String>();
         set1.add("s1");
         set1.add("s3");
         set1.add("s4");
         set1.add("s5");
         set1.add("s2");
         // 输出结果：TreeSet=[s1, s2, s3, s4, s5]
         System.out.println("TreeSet=" + set1);

         Set<String> set2 = new HashSet<String>();
         set2.add("s1");
         set2.add("s3");
         set2.add("s4");
         set2.add("s5");
         set2.add("s2");
         // 输出结果：HashSet=[s3, s4, s5, s1, s2]
         System.out.println("HashSet=" + set2);

         Set<String> set3 = new LinkedHashSet<String>();
         set3.add("s1");
         set3.add("s3");
         set3.add("s4");
         set3.add("s5");
         set3.add("s2");
         // 输出结果：LinkedHashSet=[s1, s3, s4, s5, s2]
         System.out.println("LinkedHashSet=" + set3);
     }


     @Test
     public void test3() {
         LRUUtil<Integer, String> lru = new LRUUtil<>(3);
         lru.put(1, "a");    // 1:a
         System.out.println(lru.toString());
         lru.put(2, "b");    // 2:b 1:a
         System.out.println(lru.toString());
         lru.put(3, "c");    // 3:c 2:b 1:a
         System.out.println(lru.toString());
         lru.put(4, "d");    // 4:d 3:c 2:b
         System.out.println(lru.toString());
         lru.put(1, "aa");   // 1:aa 4:d 3:c
         System.out.println(lru.toString());

         lru.get("3");
         lru.get("4");


         lru.put(2, "bb");   // 2:bb 1:aa 4:d
         System.out.println(lru.toString());
         lru.put(5, "e");    // 5:e 2:bb 1:aa
         System.out.println(lru.toString());

     }

     @Test
     public void test4() {
         LRUCache<Integer, String> lru = new LRUCache<>(3);
         lru.put(1, "a");    // 1:a
         System.out.println(lru.toString());
         lru.put(2, "b");    // 2:b 1:a
         System.out.println(lru.toString());
         lru.put(3, "c");    // 3:c 2:b 1:a
         System.out.println(lru.toString());
         lru.put(4, "d");    // 4:d 3:c 2:b
         System.out.println(lru.toString());
         lru.put(1, "aa");   // 1:aa 4:d 3:c
         System.out.println(lru.toString());

//         lru.get(3);
         lru.put(2, "bb");   // 2:bb 1:aa 4:d
         System.out.println(lru.toString());

         lru.get(4);
         lru.put(5, "e");    // 5:e 2:bb 1:aa
         System.out.println(lru.toString());
     }

     @Test
     public void test5() {
         BigDecimal decimal1 = new BigDecimal("0.04");
         BigDecimal decimal2 = new BigDecimal("0.02");
         System.out.println(decimal1.add(decimal2));
         Assert.assertEquals(new BigDecimal("0.06"), decimal1.add(decimal2));
         System.out.println(decimal1.subtract(decimal2));
         System.out.println(decimal1.multiply(decimal2));
         System.out.println(decimal1.divide(decimal2));
     }


     @Test
     public void test6() throws InterruptedException {
        JoinMain joinMain = new JoinMain();
      Thread thread = new Thread(joinMain);
        thread.start();
//        thread.join();
         System.out.println(i);
     }


     class JoinMain implements Runnable {

         @Override
         public void run() {
             for (i=0; i< 1000000; i++) {

             }
         }
     }

     @Test
     public void test7() {
        for (int i=0;;) {
            System.out.println(++i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

     }


     @Test
     public void test8() {
        int[] array = {1,2,3,4,5};
         Arrays.stream(array).map(i -> (i%2==0? i : i+1)).forEach(System.out::println);
         try {
             SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(new FileInputStream(""));
             SqlSession sqlSession = factory.openSession();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }
     }


}
