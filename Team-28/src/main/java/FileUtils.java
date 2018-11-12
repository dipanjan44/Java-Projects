import java.io.File;

/**
 * This class is implemented to check the validity of the input files before the
 * plagiarism check is kicked off
 * 
 * @author Dipanjan
 *
 */
public class FileUtils {
	private static final String JAVA_EXTENSION = "java";

	/**
	 * This function validates whether a given file passed for plagiarism
	 * detection exists
	 * 
	 * @param file
	 *            - a given input file
	 * @return - true if file exists else false
	 */
	public static Boolean isValidFile(File file) {
		return file.exists();
	}

	/**
	 * This function checks whether the given input file has a valid extension
	 * for plagiarism checking
	 * 
	 * @param file
	 *            - the input file
	 * @param extension
	 *            - the extension of the input file
	 * @return
	 */
	public static Boolean isExtensionValid(File file, String extension) {
		String fileName = file.getName();
		String[] strings = fileName.split("\\.");
		return strings[strings.length - 1].toLowerCase().equals(extension);
	}

	/**
	 * This function validates whether the given input file is a java file
	 * 
	 * @param file
	 *            - input file
	 * @return - true if the input file is a valid java file
	 */
	public static Boolean isValidJavaFile(File file) {
		return isExtensionValid(file, JAVA_EXTENSION);
	}

	/**
	 * 
	 * @return the resource path of the file
	 */
	public static String getResourcePath() {
		return "";
	}
}
