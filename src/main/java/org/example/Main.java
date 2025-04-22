package org.example;

import java.io.*;
import java.util.Random;

public class Main {
    private static int totalIterations = 0;

    public static void main(String[] args) throws IOException {

        generateMultipleDataFiles("data", 100, 10000, 100);


        BufferedWriter resultsWriter = new BufferedWriter(new FileWriter("results.csv"));
        resultsWriter.write("Size,Time(ns),Iterations\n");

        for (int i = 1; i <= 100; i++) {
            String fileName = "data_" + i + ".txt";
            int[] arr = readDataFromFile(fileName);


            totalIterations = 0;


            long startTime = System.nanoTime();
            int[] result = HeapSort(arr);
            long endTime = System.nanoTime();
            long timeElapsed = endTime - startTime;


            resultsWriter.write(arr.length + "," + timeElapsed + "," + totalIterations + "\n");
        }

        resultsWriter.close();
        System.out.println("All measurements saved to results.csv");
    }


    private static void generateMultipleDataFiles(String prefix, int minSize, int maxSize, int numFiles) throws IOException {
        Random rand = new Random();
        for (int i = 1; i <= numFiles; i++) {
            int size = rand.nextInt(maxSize - minSize + 1) + minSize;
            int[] data = new int[size];
            for (int j = 0; j < size; j++) {
                data[j] = rand.nextInt(10000);
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(prefix + "_" + i + ".txt"))) {
                for (int num : data) {
                    writer.write(num + " ");
                }
            }
        }
    }

    private static int[] readDataFromFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        String[] values = line.split(" ");
        int[] data = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            data[i] = Integer.parseInt(values[i]);
        }
        reader.close();
        return data;
    }
    // ------------------------------------------------------------------------------------------//
    // АЛГОРИТМ //
    private static int[] HeapSort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, i, n);
        }
        for (int i = n - 1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, 0, i);
        }
        return arr;
    }

    private static void heapify(int[] arr, int i, int n) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        totalIterations++;

        if (l < n && arr[l] > arr[largest]) {
            largest = l;
        }
        if (r < n && arr[r] > arr[largest]) {
            largest = r;
        }
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, largest, n);
        }
    }
}