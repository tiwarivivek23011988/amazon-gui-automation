package com.assignment.amazon.utilities;

import java.io.File;

public class FileSearchUtility {

	/**
     * Searches for a file in the given directory and its sub-directories.
     * 
     * @param directoryPath the path of the directory to search in
     * @param fileName the name of the file to search for
     * @return the path of the file if found, null otherwise
     */
    public static String searchFile(String directoryPath, String fileName) {
        File directory = new File(directoryPath);

        // Check if the provided path is a directory
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("The provided path is not a directory: " + directoryPath);
        }

        // Call the recursive search method
        return searchFileRecursive(directory, fileName);
    }

    /**
     * Recursive method to search for a file in the given directory and its sub-directories.
     * 
     * @param directory the directory to search in
     * @param fileName the name of the file to search for
     * @return the path of the file if found, null otherwise
     */
    private static String searchFileRecursive(File directory, String fileName) {
        // Get all files and sub-directories in the current directory
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                // If the current file matches the search file name, return the file path
                if (file.isFile() && file.getName().equals(fileName)) {
                    return file.getAbsolutePath();
                }
                // If the current file is a directory, recursively search within it
                if (file.isDirectory()) {
                    String result = searchFileRecursive(file, fileName);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }

        // If the file is not found in the current directory and its sub-directories, return null
        return null;
    }
}
