import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;

class DirectoryTree {

	static PrintWriter out = null;

	public static void main(String[] args) {

		try {
			out = new PrintWriter(new FileWriter("C:\\Users\\Pleasure Cruise\\Desktop\\output.txt"), true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		File head = new File("C:\\Users\\Pleasure Cruise\\Desktop\\eclipse");

		File[] files = head.listFiles();

		ArrayDeque<Boolean> verticalLine = new ArrayDeque<>();

		printFiles(files, verticalLine);
	}

	static void printFiles(File[] files, ArrayDeque<Boolean> verticalLine) {

		Boolean[] verticalLineArray = new Boolean[verticalLine.size()];
		verticalLine.toArray(verticalLineArray);

		ArrayDeque<File> directories = new ArrayDeque<>();
		ArrayDeque<File> normalFiles = new ArrayDeque<>();

		separateFilesAndDirectories(files, directories, normalFiles);

		if (printNormalFiles(verticalLineArray, normalFiles, directories)) {

			if (!directories.isEmpty()) {

				addSpacing(verticalLineArray);
				
			}
		}

		printDirectories(verticalLine, verticalLineArray, directories);
	}

	static void separateFilesAndDirectories(File[] files, ArrayDeque<File> directories, ArrayDeque<File> normalFiles) {

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				directories.push(files[i]);
			} else {
				normalFiles.push(files[i]);
			}
		}
	}

	static boolean printNormalFiles(Boolean[] verticalLineArray, ArrayDeque<File> normalFiles,
			ArrayDeque<File> directories) {

		boolean normalFilesPrinted = false;

		while (!normalFiles.isEmpty()) {

			normalFilesPrinted = true;

			for (int i = 0; i < verticalLineArray.length; i++) {
				if (verticalLineArray[i]) {
					System.out.print("|   ");
					out.print("|   ");
				} else {
					System.out.print("    ");
					out.print("    ");
				}
			}

			if (!directories.isEmpty()) {

				String name = normalFiles.pop().getName();

				System.out.print("|   ");
				System.out.println(name);
				out.print("|   ");
				out.println(name);
			} else {

				String name = normalFiles.pop().getName();

				System.out.print("    ");
				System.out.println(name);
				out.print("    ");
				out.println(name);
			}
		}

		return normalFilesPrinted;
	}

	static void addSpacing(Boolean[] verticalLineArray) {
		for (int i = 0; i < verticalLineArray.length; i++) {
			if (verticalLineArray[i]) {
				System.out.print("|   ");
				out.print("|   ");
			} else {
				System.out.print("    ");
				out.print("    ");
			}
		}

		System.out.print("|   ");

		System.out.println();

		out.print("|   ");

		out.println();
	}
	
	static void printDirectories(ArrayDeque<Boolean> verticalLine, Boolean[] verticalLineArray, ArrayDeque<File> directories){
		
		while (!directories.isEmpty()) {

			for (int i = 0; i < verticalLineArray.length; i++) {
				if (verticalLineArray[i]) {
					System.out.print("|   ");
					out.print("|   ");
				} else {
					System.out.print("    ");
					out.print("    ");
				}
			}

			File directory = directories.pop();

			System.out.print("+---");
			System.out.println(directory.getName());

			out.print("+---");
			out.println(directory.getName());

			ArrayDeque<Boolean> verticalLineClone = verticalLine.clone();

			if (!directories.isEmpty()) {
				verticalLineClone.add(true);
			} else {
				verticalLineClone.add(false);
			}

			printFiles(directory.listFiles(), verticalLineClone);
		}
	}
}