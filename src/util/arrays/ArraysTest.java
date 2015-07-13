package util.arrays;

import java.util.Arrays;

public class ArraysTest {
	public static void main(String[] args) {
		int[] arr = new int[5];
		arr[0] = 0;
		arr[1] = 1;

		printArr(arr);
		
		System.out.println("");
		//
		arr = Arrays.copyOf(arr, arr.length *2);
		printArr(arr);
	}

	private static void printArr(int[] arr) {
		for (int i = 0, len = arr.length; i < len; i++) {
			System.out.print(arr[i] + " ");
		}
	}
}
