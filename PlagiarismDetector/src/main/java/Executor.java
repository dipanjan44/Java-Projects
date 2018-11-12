import java.io.File;

/**
 * This is the driver of the application
 **/
public class Executor {
	public static void main(String[] args) {
		Executor executor = new Executor();
		String response;
		if (args.length == 2) {
			response = executor.execute(args[0], args[1]);
		} else {
			response = executor.createErrorResponse("Invalid Number of Arguments: Expected 2, Received " + args.length);
		}
		// Needed to send the response
		executor.sendResponse(response);
	}

	/**
	 * This is method validates the 2 input files and triggers the plagiarism
	 * detection if validation is passed
	 * 
	 * @param fileOnePath
	 * @param fileTwoPath
	 * @return
	 */
	public String execute(String fileOnePath, String fileTwoPath) {
		if (fileOnePath == null || fileTwoPath == null) {
			return createErrorResponse("Invalid Files Uploaded!");
		} else {
			File fileOne = new File(fileOnePath);
			File fileTwo = new File(fileTwoPath);
			if (!FileUtils.isValidFile(fileOne) || !FileUtils.isValidJavaFile(fileOne)) {
				return createErrorResponse("Invalid JavaFile Provided in Argument 1");
			} else if (!FileUtils.isValidFile(fileTwo) || !FileUtils.isValidJavaFile(fileTwo)) {
				return createErrorResponse("Invalid JavaFile Provided in Argument 2");
			} else {
				PlagiarismChecker plagiarismChecker = new PlagiarismCheckerImpl();
				IncidentReport incidentReport = plagiarismChecker.analyze(fileOne, fileTwo);
				// Form the response you want to send angular:
				return setSuccessResponse(incidentReport.getPlagiarizedScore(), fileOne.getName(), fileTwo.getName());
			}

		}
	}

	/*
	 * Returns the plagiarised score for 2 valid input files
	 */
	public String setSuccessResponse(Double score, String fileOne, String fileTwo) {
		return "{response: { plagiarizedScore: " + score + ", files: [" + fileOne + ", " + fileTwo + "] } }";
	}

	/**
	 * returns an error message is input validation of the files failed
	 * 
	 * @param errorMessage
	 * @return
	 */
	public String createErrorResponse(String errorMessage) {
		return "{response: { errorMessage: " + errorMessage + " } }";
	}

	/**
	 * Send the response obtained from the execute() to the frontend
	 * 
	 * @param response
	 */
	public void sendResponse(String response) {
		System.out.print(response);
	}
}
