/** This class contains methods associated with a plagiarism incident report **/
public class IncidentReport {
	private Double plagiarizedScore;

	/**
	 * This is the constructor
	 * 
	 * @param plagiarizedScore
	 */
	public IncidentReport(Double plagiarizedScore) {
		this.plagiarizedScore = plagiarizedScore;
	}

	/**
	 * 
	 * @return the plagiarised score for the files compared
	 */
	public Double getPlagiarizedScore() {
		return plagiarizedScore;
	}
}
