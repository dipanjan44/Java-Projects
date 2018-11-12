package tests;


// this class has been created to test the user-defined type<T>. Here <T> is the Student
public class Student implements Comparable<Student> {

	private Integer studentId;
	private String studentName;

	public Student(int studentId, String studentName) {

		this.studentId = studentId;
		this.studentName = studentName;
	}

	@Override
	public int compareTo(Student s) {

		return this.studentId.compareTo(s.studentId);
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	@Override
	public String toString() {
		return "StudentID[" + studentId + "]";

	}
	  
	  @Override
	  public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((studentId == null) ? 0 : studentId.hashCode());
	    return result;
	  }

	  @Override
	  public boolean equals(Object obj) {
	    if (this == obj)
	      return true;
	    if (obj == null)
	      return false;
	    if (getClass() != obj.getClass())
	      return false;
	    Student other = (Student) obj;
	    if (studentId == null) {
	      if (other.studentId != null)
	        return false;
	    } else if (!studentId.equals(other.studentId))
	      return false;
	    return true;
	}
}