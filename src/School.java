@SuppressWarnings("serial")
public class School implements java.io.Serializable {

	private long id;
	private String name;
	private String address;

	public School() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String toString() {
		return "School: " + getName() + " (" + getAddress() + ")";
}

}
