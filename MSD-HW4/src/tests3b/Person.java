package tests3b;

class Person implements Comparable<Person> {
	  public Person(String name){
	    this.name = name;
	  }
	  
	  @Override
	  public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
	    Person other = (Person) obj;
	    if (name == null) {
	      if (other.name != null)
	        return false;
	    } else if (!name.equals(other.name))
	      return false;
	    return true;
	  }

	  @Override
	  public int compareTo(Person o) {
	    return this.name.compareTo(o.name);
	  }
	  
	  @Override
	  public String toString(){
	    return "Person[" + name + "]";
	  }

	  private String name;    
	}