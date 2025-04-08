package homework;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class sectionB {

	


	public static boolean isValidIntCast(String str) {
		return str.matches("-?\\d+");
	}
	public static boolean isValidDoubleCast(String str) {
		return str != null && str.matches("-?\\d+(\\.\\d+)?");
	}
	public static double stringToDouble(String s) {
		double num = -1;
		try {
			if (isValidIntCast(s) || isValidDoubleCast(s))
				num = Double.parseDouble(s);
			return num;
		} catch (Exception e) {
			return num;
		}
	}
	public static int stringToInt(String s) {
		int num = -1;
		try {
			if (isValidIntCast(s))
				num = Integer.parseInt(s);
			return num;
		} catch (Exception e) {
			return num;
		}
	}
	public static boolean dateValidation(String p1, String p2, String p3) {
		int p1int = stringToInt(p1);
		int p2int = stringToInt(p2);
		int p3int = stringToInt(p3);
		if (p3int < 1)//חלק השנה ביציאה מנקודת הנחה שיכולים לכתוב גם תאריכים הסטוריים ועתידיים
			return false;

		if (p2int < 1 || p2int > 12)//חודשים
			return false;

		if (p1int > 31 || p1int < 1)//יום בחודש
			return false;

		return true;
	}

	public static boolean splitTimeValidation(String time) {
		String tarr[];
		if (time.contains(":")) {
			tarr = time.split(":");
			if (tarr.length == 2)
				return timeValidation(tarr[0], tarr[1]);
		}
		return false;
	}

	public static boolean splitDateValidation(String date) {
		String darr[];
		if (date.contains("/")) {
			darr = date.split("/");
			if (darr.length == 3)
				return dateValidation(darr[0], darr[1], darr[2]);
		}
		return false;
	}

	public static boolean timeValidation(String p1, String p2) {

		int p1int = stringToInt(p1);
		int p2int = stringToInt(p2);
		if (p1int < 0 || p1int > 24 || p1.length() != 2)
			return false;
		if (p2int < 0 || p2int > 59 || p2.length() != 2)
			return false;
		return true;
	}

	public static boolean valueValdation(String value) {
		return (stringToDouble(value) > 0 || value.equalsIgnoreCase("NaN") || value.equalsIgnoreCase("not_a_number"));
	}

	public static boolean allValidation(String s) {
		String varr[] = null;
		String tarr[] = null;

		if (s.contains(","))//בדיקה שאכן יש שני ערכים
		{
			varr = s.split(",");
			if (varr.length == 2 && varr[1].contains(" ")) {
				tarr = s.split(" ");
				if (tarr.length == 2)
					return valueValdation(tarr[1]) && splitDateValidation(tarr[0]) && splitTimeValidation(varr[0]);
			}
		}

		return false;
	}

	public static boolean validionForRow(String line) {
		line = line.trim().replaceAll("\\s{2,}", " "); // הפחתת רווחים מרובים לאחד
		String[] row = line.split(",");
		if (row.length != 2) {
			return false;
		}
		String timestamp = row[0].trim(), value = row[1].trim(); //צמצום רווחים שלא באמצע משפט 

		String[] timestampDivde = timestamp.split(" ");//חלוקת חלק הזמן לשעה ותאריך לפי רווח בהתאם למבנה הקובץ
		if (timestampDivde.length != 2) {
			return false;
		}
		String date = timestampDivde[0], time = timestampDivde[1];

		return splitDateValidation(date) && splitTimeValidation(time); //&& valueValdation(value);
	}
	
	
	public static String[] valueForHour(String line) {
		line = line.trim().replaceAll("\\s{2,}", " "); // הפחתת רווחים מרובים לאחד
		String[] row = line.split(",");
		if (row.length != 2) {
			return null;
		}
		String timestamp = row[0].trim(), value = row[1].trim(); //צמצום רווחים שלא באמצע משפט 

		String[] timestampDivde = timestamp.split(" ");//חלוקת חלק הזמן לשעה ותאריך לפי רווח בהתאם למבנה הקובץ
		if (timestampDivde.length != 2) {
			return null;
		}
		String date = timestampDivde[0], time = timestampDivde[1];
	
		String tarr[];
		if (validionForRow(line)) 
			{tarr = time.split(":");
             return new String[] {tarr[0]+" "+date,value};}
             
             return null;
					}
	
	public static void averageByHourToDate(String filePath) throws IOException {
	    HashMap<String, double[]> valueByHour = new HashMap<>();
	    BufferedReader reader = null;

	    try {
	        reader = new BufferedReader(new FileReader(filePath));
	        String line;
	        reader.readLine(); // קריאת שורת הכותרת
	        String[] lineAnalyze;

	        while ((line = reader.readLine()) != null) {
	            lineAnalyze = valueForHour(line); // [0] = שעה, [1] = ערך + מונה
	            if (lineAnalyze != null) {
	                double val = stringToDouble(lineAnalyze[1]);
	                String hourKey = lineAnalyze[0];

	                valueByHour.put(hourKey,valueByHour.containsKey(hourKey)? new double[] {
	                            valueByHour.get(hourKey)[0] + val,valueByHour.get(hourKey)[1] + 1}:
	                            	new double[] { val,1 });}
	        }

	        // הדפסת תוצאות לבדיקה
	        for (String hour : valueByHour.keySet()) {
	            double[] arr = valueByHour.get(hour);
	            double value = arr[0] / arr[1];
	            System.out.printf("time: %s, Average: %.2f\n", hour.replaceFirst(" ", ":00 "), value);
	        }

	    } catch (Exception e) {
	      //  e.printStackTrace();
	    	System.out.println("חזרי");
	    } finally {
	        if (reader != null) {
	            reader.close();
	        }
	    }
	}
	
	
//חלק א בדיקת ולידציה בחרתי לעשות הדפס למרות שלא נדרש במפורש
	public static void readAndValidateCSV(String filePath) throws Exception {
		BufferedReader reader = null;
		int validCount = 0;
		int invalidCount = 0;

		try {
			reader = new BufferedReader(new FileReader(filePath));
			String line;
			reader.readLine();        // לקריאת הכותרת

			while ((line = reader.readLine()) != null) {
				if (validionForRow(line)) {
					validCount++;
				} else {
					invalidCount++;
				}
			}
			System.out.println("שורות תקינות: " + validCount);
			System.out.println("שורות לא תקינות: " + invalidCount);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();//צריך לסגור קורא בסוף הפונקציה
			}
		}
	}
	
	
	@SuppressWarnings("finally")
	public static HashMap<String,double[]> averageByHourToDateMethod(String filePath) throws IOException {
	    HashMap<String, double[]> valueByHour = new HashMap<>();
	    BufferedReader reader = null;

	    try {
	        reader = new BufferedReader(new FileReader(filePath));
	        String line;
	        reader.readLine(); // קריאת שורת הכותרת
	        String[] lineAnalyze;

	        while ((line = reader.readLine()) != null) {
	            lineAnalyze = valueForHour(line); // [0] = שעה, [1] = ערך + מונה
	            if (lineAnalyze != null) {
	                double val = stringToDouble(lineAnalyze[1]);
	                String hourKey = lineAnalyze[0];

	                valueByHour.put(hourKey,valueByHour.containsKey(hourKey)? new double[] {
	                            valueByHour.get(hourKey)[0] + val,valueByHour.get(hourKey)[1] + 1}:
	                            	new double[] { val,1 });}
	        }
	        // הדפסת תוצאות לבדיקה
//	        for (String hour : valueByHour.keySet()) {
//	            double[] arr = valueByHour.get(hour);
//	            double value = arr[0] / arr[1];
//	            System.out.printf("time: %s, Average: %.2f\n", hour.replaceFirst(" ", ":00 "), value);
//	        }

	    } catch (Exception e) {
	      //  e.printStackTrace();
	    	System.out.println("חזרי");
	    } finally {
	        if (reader != null) {
	            reader.close();	            
	        }
            return valueByHour;
	    }
	}


	public static void csvToNewAvgCsv(String inputFilePath, int partSize) throws IOException {//חלק 2 א
	    BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
	    String header = reader.readLine(); //  כותרת
	    String line;
	    int fileIndex = 0,lineCounter = 0;

	    List<String> lines = new ArrayList<>();
	    List<File> partFiles = new ArrayList<>();

	    while ((line = reader.readLine()) != null) {
	        lines.add(line);
	        lineCounter++;

	        if (lineCounter == partSize) {
	            File part = writingToFile(lines, header, fileIndex++);
	            partFiles.add(part);
	            lines.clear();
	            lineCounter = 0;
	        }
	    }

	    if (!lines.isEmpty()) {//כותב את מה שנשאר בסוף
	        File part = writingToFile(lines, header, fileIndex);
	        partFiles.add(part);
	    }

	    reader.close();

	    HashMap<String, double[]> finalMap = new HashMap<>();

	    for (File partFile : partFiles) {
	        HashMap<String, double[]> partialMap = averageByHourToDateMethod(partFile.getAbsolutePath());
	        mergeHashMaps(finalMap, partialMap);
	    }

//	    //הדפסת תוצאה מאוחדת
//	    for (String hour : finalMap.keySet()) {    //תשובה מהחלק הקודם של התרגיל
//	        double[] arr = finalMap.get(hour);
//	        double value = arr[0] / arr[1];
//	        System.out.printf("time: %s, Average: %.2f\n", hour.replaceFirst(" ", ":00 "), value);
//	    }
	    
	    writingAveragesToCSV(finalMap, "output_averages.csv");

	}

	private static File writingToFile(List<String> lines, String header, int index) throws IOException {
	    File partFile = new File("part_" + index + ".csv");
	    BufferedWriter writer = new BufferedWriter(new FileWriter(partFile));
	    writer.write(header);
	    writer.newLine();
	    for (String l : lines) {
	        writer.write(l);
	        writer.newLine();
	    }
	    writer.close();
	    return partFile;
	}

	private static void mergeHashMaps(HashMap<String, double[]> baseMap, HashMap<String, double[]> newMap) {
	    for (String key : newMap.keySet()) {
	        double[] newValues = newMap.get(key); 
	        if (baseMap.containsKey(key)) { //חיבור שתי מפות בדיקה האם הערך מפתח קיים במפה אם כן חיבור אם לא הוספה
	            double[] existing = baseMap.get(key);
	            baseMap.put(key, new double[] {existing[0] + newValues[0], existing[1] + newValues[1]	            });
	        } else {baseMap.put(key, new double[] { newValues[0], newValues[1] });
	        }
	    }
	}

	private static void writingAveragesToCSV(HashMap<String, double[]> finalMap, String outputFilePath) throws IOException {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
	        writer.write("time,average\n"); // כותרת

	        for (String hour : finalMap.keySet()) {
	            double[] arr = finalMap.get(hour);
	            double value = arr[0] / arr[1];
	            String formattedTime = hour.replaceFirst(" ", ":00 ");
	            writer.write(String.format("%s,%.2f\n", formattedTime, value));
	            System.out.println("קובץ עם ממוצעים נוצר בהצלחה: output_averages.csv");

	        }
	    }
	}

	
	
	public static void main(String[] args) throws Exception {
		String file = "src\\time_series.csv";

		//valueValdation("9not_a_number"); רעיון לולידציה נוספת על הערך ביציאה מנקודת הנחה שהוא אמור להיות חיובי או לפי האותיות שנכתבו בלבד
		readAndValidateCSV(file); //1א
		averageByHourToDate(file); //1ב
		csvToNewAvgCsv("src\\time_series.csv", 50000); //2
		//3 אתכנן את זה מבוסס על מילון-האשמאפ לנתח לערך המפתח ולפי זה לעשות את הממוצע נח מבחינת זמן ריצה ונוחות גישה
		//4 פוקציה להמרת Parquet To Csv ולקרוא לפונקציות בצורה רגילה לאחר המרת הקובץ אפילו במיין
		
	}
}
