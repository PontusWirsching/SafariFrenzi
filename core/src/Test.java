import java.util.ArrayList;
import java.util.Arrays;

public class Test {

	static class Obj {

		public int y;

		public Obj(int y) {
			this.y = y;
		}

	}

	public static void displayY() {
		System.out.print("Y values: ");
		for (Obj o : objects) {
			System.out.print(o.y + " ");
		}
		System.out.println();
	}

	public static ArrayList<Obj> objects = new ArrayList<Obj>();

	public static void swap(int indexOne, int indexTwo, int[] array) {
		int temp = array[indexOne];
		array[indexOne] = array[indexTwo];
		array[indexTwo] = temp;
	}

	public static int[] bubbleSort() {
		int[] yValues = new int[objects.size()];
		for (Obj o : objects) {
			yValues[objects.indexOf(o)] = o.y;
		}

		for (int i = yValues.length - 1; i > 1; i--) {
			for (int j = 0; j < i; j++) {
				if (yValues[j] > yValues[j + 1]) {
					swap(j, j + 1, yValues);
				}
			}
		}
		return yValues;
	}
	
	public static int[] regularSort() {
		int[] yValues = new int[objects.size()];
		for (Obj o : objects) {
			yValues[objects.indexOf(o)] = o.y;
		}
		Arrays.sort(yValues);
		return yValues;
	}

	public static void main(String args[]) {

		long totalTime = 0;
		
		int sort = 1000;
		int amount = 100;
		

		// Sort different arrays 100 times and add each time to total time.
		for (int i = 0; i < sort; i++) {
			objects.clear();
			for (int j = 0; j < amount; j++) {
				objects.add(new Obj((int) (Math.random() * 1000000)));
			}
			long start = System.nanoTime();
			
			
			
//			int[] a = regularSort();
			
			
			long stop = System.nanoTime();
			totalTime += stop - start;
		}
		
		long average = totalTime / sort;
		

		System.out.println("Sorted " + amount + " items "+ sort +" times in a total of " + (totalTime / 1000000.0) + " millisecond(s)!");
		System.out.println("That is an average of " + (average / 1000000.0) + " millisecond(s) per sorting.");


		// int[] array = new int[10];
		// Random rand = new Random();
		//
		// for (int i = 0; i < array.length; i++)
		// array[i] = rand.nextInt(100) + 1;
		//
		// Arrays.sort(array);
		// System.out.println(Arrays.toString(array));
		//
		// // in reverse order
		// for (int i = array.length - 1; i >= 0; i--)
		// System.out.print(array[i] + " ");
		// System.out.println();

	}

}
