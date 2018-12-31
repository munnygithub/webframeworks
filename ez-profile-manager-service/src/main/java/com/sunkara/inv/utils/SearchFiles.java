package com.sunkara.inv.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

public class SearchFiles {
	// It's used in dfs
	private Map<String, Boolean> map = new HashMap<>();

	private File root;

	public SearchFiles(File root) {
		this.root = root;
	}

	public static void main(String args[]) {
		SearchFiles searcher = new SearchFiles(
				new File("///Users/Srini/Documents/Munny/Cloud_Storage/Private Company Inv/EquityZen/"));
		searcher.recursive(searcher.root);
	}

	/**
	 * How many files in the parent directory and its subdirectory <br>
	 * depends on how many files in each subdirectory and their subdirectory
	 */
	private void recursive(File path) {

		printFiles(path, getTargetFiles(path));
		File[] f = path.listFiles();

		for (File file : path.listFiles()) {
			if (file.isDirectory()) {
				recursive(file);
			}
		}
//		if (path.isDirectory()) {
//			printFiles(getTargetFiles(path));
//		}

	}

	/**
	 * List eligible files on current path
	 * 
	 * @param directory
	 *            The directory to be searched
	 * @return Eligible files
	 */
	private String[] getTargetFiles(File directory) {
		String[] files = directory.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
//				System.out.println(name);
//				return true;
				 return name.endsWith(".pdf");
				// return name.startsWith("Temp") && name.endsWith(".txt");
			}

		});

		return files;
	}

	/**
	 * Print all eligible files
	 */
	private void printFiles(File path, String[] targets) {
		if (targets == null) {
			return;
		}
		for (String target : targets) {
			System.out.println(path + " - " + target);
		}
	}
}
