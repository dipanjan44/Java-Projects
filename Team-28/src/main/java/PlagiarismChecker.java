import java.io.File;

/**
 * PlagiarismChecker analyses different Deliverable to provide IncidentReport.
 * Implementation of PlagiarismChecker will be language specific
 */

public interface PlagiarismChecker {

	/**
	 * creates and returns Instance of PlagiarismChecker
	 */

	public PlagiarismChecker createPlagiarismChecker();

	/**
	 * Analyzes and compares two Deliverable
	 * 
	 * @param fileOne
	 *            Object of File for student 1
	 * @param fileTwo
	 *            Object of File for student 2
	 */
	public IncidentReport analyze(File fileOne, File fileTwo);
}
