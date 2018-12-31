package com.sunkara.inv.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class FileList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		FileList fl = new FileList();
		String folderBasePath = "/Users/Srini/Documents/Munny/Cloud_Storage/Private Company Inv/EquityZen/Company Profiles/";
		boolean includeSubFolders = true;
		List<File> outFilesList = fl.listFiles(folderBasePath, includeSubFolders);
		
		System.out.println(outFilesList.size());
		outFilesList.forEach(file -> System.out.println(file.getAbsolutePath()));

	}
	
	public List<File> listFiles(String folderBasePath, boolean includeSubFolders) {
		
		List<File> outFilesList = new ArrayList<>();
		
		return listFiles(folderBasePath, includeSubFolders, outFilesList);
		
	}

	private List<File> listFiles(String folderBasePath, boolean includeSubFolders, List<File> outFilesList) {
		File dir = new File(folderBasePath);

		File[] files = dir.listFiles();

//		files = dir.listFiles((dir1, name) -> name.startsWith("temp") && name.endsWith(".txt"));

		for (int i = 0; (files != null && i < files.length); i++) {
			if (files[i].isDirectory() && includeSubFolders) {
				listFiles(files[i].getAbsolutePath(), includeSubFolders, outFilesList);
			} else {
				if (files[i].getName().toUpperCase().endsWith(".PDF")) {
					outFilesList.add(files[i]);
				}
			}
		}
		
		return outFilesList;
	}

}
