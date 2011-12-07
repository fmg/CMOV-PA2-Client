package cmov.pa.utils;

public class HouseInfo {

	int id;
	String address;
	int bedrooms;
	String type;
	String city;
	String state;
	
	
	public HouseInfo(int id, String type, String address, String city, int bedrooms, String state){
		this.id = id;
		this.address = address;
		this.bedrooms = bedrooms;
		this.type = type;
		this.city = city;
		this.state = state;
	}
	
	public int getId() {
		return id;
	}


	public String getAddress() {
		return address;
	}


	public int getBedrooms() {
		return bedrooms;
	}


	public String getType() {
		return type;
	}
	
	
	public String getCity() {
		return city;
	}
	
	

	public String getState() {
		return state;
	}



}
