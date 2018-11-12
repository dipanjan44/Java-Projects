import java.io.File;

/**
 * Implementation of the PlagiarismChecker interface
 **/
public class PlagiarismCheckerImpl implements PlagiarismChecker {
	private static final Double MAX_SIMILAR_VALUE = 100.00; // Max Value of
															// Comparator
	private static final Double MULTIPLIER_FOR_METADATA = 0.3; // Specifies the
																// dominance of
																// Metadata
																// Comparator
	private static final Double MULTIPLIER_FOR_LCS_COMPARE = 0.7; // Specifies
																	// the
																	// dominance
																	// of LCS
																	// Comparator

	@Override
	public PlagiarismChecker createPlagiarismChecker() {
		return null;
	}

	/**
	 * This method analyzes a pair of files for plagiarism and returns a report
	 *
	 * @param fileOne
	 *            is the first file to be checked for plagiarism
	 * @param fileTwo
	 *            is the second file to be checked for plagiarism
	 * @return an object of IncidentReport
	 */
	@Override
	public IncidentReport analyze(File fileOne, File fileTwo) {
		if (CompareUtils.compareContent(fileOne, fileTwo)) {
			return new IncidentReport(MAX_SIMILAR_VALUE);
		} else {
			int metaDataValue = CompareUtils.compareMetaData(fileOne.getAbsolutePath(), fileTwo.getAbsolutePath());
			int astLCSValue = CompareUtils.compareLCS(fileOne, fileTwo);
			Double score = (metaDataValue * MULTIPLIER_FOR_METADATA) + (astLCSValue * MULTIPLIER_FOR_LCS_COMPARE);

			return new IncidentReport(score);
		}
	}
}
