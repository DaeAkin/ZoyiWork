package work.dto;

public class Translation {
	
	int id;
	int keyId;
	String locale;
	String value;
	
	
	public Translation() {
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getKeyId() {
		return keyId;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	@Override
	public String toString() {
		String toString= "[ id : " + getId() + "," 
				+ "keyId : " +getKeyId() + "," +
				  "locale : " + getLocale() + "," +
				   "value : " + getValue() +  "]";
		return toString;
	}
	

}
