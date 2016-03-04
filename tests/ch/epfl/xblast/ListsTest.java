package ch.epfl.xblast;

import java.util.ArrayList;
import java.util.List;

public class ListsTest {

    public static void main(String[] args) {
        List<String> test = new ArrayList<String>();
        test.add("k");
        test.add("a");
        test.add("y");
        test.add("3");
        test.add("saucisson");
        test = Lists.mirrored(test);
        System.out.println(test);
    }

}
