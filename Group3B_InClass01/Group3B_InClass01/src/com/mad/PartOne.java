package com.mad;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PartOne{
	static User user;
	static HashSet<User> userRecords = new HashSet<User>();
	static Set<User> duplicateUsersSet = new HashSet<User>();
	static ArrayList<User> sortedList;
	public static void main(String[] args) {
		readFileAtPath("userList1.txt");
	}

	public static void readFileAtPath(String filename) {
		Boolean flag = false;
		// Lets make sure the file path is not empty or null
		if (filename == null || filename.isEmpty()) {
			System.out.println("Invalid File Path");
			return;
		}
		String filePath = System.getProperty("user.dir") + "/" + filename;
		BufferedReader inputStream = null;
		// We need a try catch block so we can handle any potential IO errors
		try {
			try {
				inputStream = new BufferedReader(new FileReader(filePath));
				String lineContent = null;
				// Loop will iterate over each line within the file.
				// It will stop when no new lines are found.
				while ((lineContent = inputStream.readLine()) != null) {
					user = new User(lineContent);
					flag = userRecords.add(user);
					if (flag == false) {
						duplicateUsersSet.add(user);
					}
				}
				sortedList= new ArrayList<User>(duplicateUsersSet);
				Collections.sort((java.util.List) sortedList);
				displayDuplicates();
			}
			// Make sure we close the buffered reader.
			finally {
				if (inputStream != null)
					inputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end of method

	private static void displayDuplicates() {
		for (Iterator<User> iterator = sortedList.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			System.out.println(user);
		}
	}
	
}
