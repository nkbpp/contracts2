package ru.pfr.contracts2.global;

import org.junit.Test;

import java.util.*;


public class MyTestt {

    public static class Trainee {

        private final String name;

        public Trainee(String name){
            this.name = name;
        }

        public boolean isWoman(){
            return this.name.contains("ова");
        }

    }

    @Test
    public void mytest() {

        Set<Trainee> collection = new HashSet<>();
        collection.add(new Trainee("name1"));
        collection.add(new Trainee("name2ова"));

        collection.stream()
                        .filter(Trainee::isWoman)
                                .forEach(System.out::println);
        System.out.println(collection);

/*     List<Integer> list = List.of(1,2,3,4,5);
       int b = 10;
       list.stream()
                .filter(o -> 3 <= o)
               //.peek(x -> System.out.println("peek= " + x))
               .map(integer -> List.of(integer, integer*2))
               .flatMap(List::stream)
               .forEach(System.out::println);

       Map<String,String> map = new HashMap<>();
       map.put("1","Один");
       map.put("1","Один");
       map.put("1","Один");
       map.put("1","Один2");
       map.put("2","Два");

        System.out.println(map.get("1"));
        for (Map.Entry<String,String> entry:
             map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
        }
*/
    }

}
