package test;

import org.junit.Test;
import semisplay.SemiSplayTree;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PerformanceTest {

    @Test
    public void mainPerformanceTest() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("../resources/testVariables.properties");
        properties.load(inputStream);
        List<Integer> randomNumbers = new ArrayList<>();
        String[] randomNumbersString = properties.getProperty("randomNumbers").split(",");
        for (String s : randomNumbersString) randomNumbers.add(Integer.parseInt(s));
        String[] testNumbersString = properties.getProperty("testNumbers").split(",");
        int[] testNumbers = new int[4];
        for (int i = 0; i < 4; i++) testNumbers[i] = Integer.parseInt(testNumbersString[i]);

        Timer timer = new Timer();
        int amountOfTests = 30;
        // splaySize=3
        // print out the average time
        double totalTime = 0;
        for (int i = 0; i < amountOfTests; i++) {
            timer.start();
            performanceTest(randomNumbers, testNumbers, 3);
            timer.end();
            totalTime += timer.delta();
        }
        System.out.println("SplaySize = 3, ideal scenario, average time: "+totalTime/amountOfTests);

        // splaySize=7
        totalTime = 0;
        for (int i = 0; i < amountOfTests; i++) {
            timer.start();
            performanceTest(randomNumbers, testNumbers, 7);
            timer.end();
            totalTime += timer.delta();
        }
        System.out.println("SplaySize = 7, ideal scenario, average time: "+totalTime/amountOfTests);

        // splaySize=15
        totalTime = 0;
        for (int i = 0; i < amountOfTests; i++) {
            timer.start();
            performanceTest(randomNumbers, testNumbers, 15);
            timer.end();
            totalTime += timer.delta();
        }
        System.out.println("SplaySize = 15, ideal scenario, average time: "+totalTime/amountOfTests);

        // splaySize=3, non optimal scenario
        totalTime = 0;
        for (int i = 0; i < amountOfTests; i++) {
            timer.start();
            performanceTestBad(randomNumbers, 3);
            timer.end();
            totalTime += timer.delta();
        }
        System.out.println("SplaySize = 3, non optimal scenario, average time: "+totalTime/amountOfTests);
    }

    private void performanceTest(List<Integer> randomNumbers, int[] testNumbers, int splaySize) {
        SemiSplayTree<Integer> performanceTree = new SemiSplayTree<>(splaySize);
        for (int number : randomNumbers) performanceTree.add(number);
        for (int i = 0; i < 10000000; i++) {
            int index = (int) (Math.random() * ((3) + 1));
            performanceTree.contains(testNumbers[index]);
        }
    }

    private void performanceTestBad(List<Integer> randomNumbers, int splaySize) {
        SemiSplayTree<Integer> performanceTree = new SemiSplayTree<>(splaySize);
        for (int number : randomNumbers) performanceTree.add(number);
        for (int i = 0; i < 10000000; i++) {
            int index = (int) (Math.random() * ((randomNumbers.size()-1) + 1));
            performanceTree.contains((randomNumbers.get(index)));
        }
    }
}
