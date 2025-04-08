package homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SectionA {

	
	//חלק 1
	public static void writeToFile(String line, int part) throws IOException {
		try {
			FileWriter writer = new FileWriter(part + "filePartion.txt", true);// writing to a new file named by part
			writer.write(line + "\n");
			writer.close();

		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	//חלק 1
	// dividing the txt file after I converted it form xlsx to txt on the Internet
	public static int devideFile(String txtFile) throws Exception {
		try {
			File f = new File(txtFile);
			Scanner reader = new Scanner(f);
			int part = 0;
			while (reader.hasNextLine()) {// run till the end of the file
				part++;
				for (int i = 0; i < 50000 && reader.hasNextLine(); i++) {// every 50000 in a new file
					writeToFile(reader.nextLine(), part);
				}

			}
			reader.close();
			return part;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return 0;

	}

	//חלק 2 
	public static HashMap<String, Integer> frequencyHashmap(String filepath) throws FileNotFoundException {
		HashMap<String, Integer> errorVSfrequency = new HashMap<>();

		try {
			File f = new File(filepath);
			Scanner myReader = new Scanner(f);
			while (myReader.hasNextLine()) {
				{
					String data = myReader.nextLine();
					errorVSfrequency.put(data, errorVSfrequency.containsKey(data) ? errorVSfrequency.get(data) + 1 : 1);//מאתחל ל1 אם קיים ערך מפתח ואם לא, מוסיף
				}
			}
//			      for (String i : errorVSfrequency.keySet()) {// הדפסה מטעמי בדיקה
//					  System.out.println("key: " + i + " value: " + errorVSfrequency.get(i));}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return errorVSfrequency;
	}
//חלק 3
	public static HashMap<String, Integer> AllfrequenciesHashmap(int numberOfFile) throws FileNotFoundException {
		HashMap<String, Integer> errorVSfrequency = new HashMap<>();

		try {
			while (numberOfFile > 0) {
				HashMap<String, Integer> map = frequencyHashmap(
						"C:\\Users\\Adis2\\Documents\\army\\homework\\" + numberOfFile + "filePartion.txt");

				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					String key = entry.getKey();
					Integer value = entry.getValue();

					errorVSfrequency.put(key,
							errorVSfrequency.containsKey(key) ? errorVSfrequency.get(key) + value : value);
				}
				numberOfFile--;
			}

//			    for (String i : errorVSfrequency.keySet()) {// הדפסה מטעמי בדיקה
//					  System.out.println("key: " + i + " value: " + errorVSfrequency.get(i));}

		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return errorVSfrequency;
	}

	//חלק 4
	// o(nlogn)
	public static void topNvalue(int n, Map<String, Integer> map) {

		Map<String, Integer> SortedHashMap = map.entrySet().stream().sorted(Map.Entry.comparingByValue())//מיון על ידי ערך
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new))// לאסוף את המיון ולשמור אותו במפה מקושרת אם יש ערך כפול הראשון bcjr
				.reversed();//הפיכה לסדר יורד

		// המרה להדפסה
		Object[] keys = SortedHashMap.keySet().toArray();// מערך מפתחות
		int length=keys.length;
		for (int i = 0; i < n && i <length; i++) {//הדפסת n גבוהים 
			System.out.println("Key: " + keys[i] + ", Value: " + map.get(keys[i]));// הדפסת מפתח וערך
		}
		if (n > length)
			System.out.println("there are only " + length + " values");

	}
	
	
	//חלק 5
	
	//סיבוכיות מקום- O(n)\\\\\ O(nlogn) -סיבוכיות זמן

	public static void main(String[] args) throws Exception {
		
		//הקובץ התקבל אומנם בתור קובץ אקסל המרתי אותו באתר המרות כיוון שלא הצלחתי להתקין את הספרייה המתאימה
		int filesNumber = devideFile("src\\logs.txt");
  
		topNvalue(8, AllfrequenciesHashmap(filesNumber));

	}
}
