package cmov.pa.utils;

public class HouseInfo {

	int id;
	String address;
	String bedrooms;
	String kind;
	String city;
	boolean for_sale;
	String photo;
	String wc;
	String price;
	String extras;
	
	
	public HouseInfo(int id, String kind, String address, String city, String bedrooms, 
					String wcs, String extras , String photo ,boolean state){
		this.id = id;
		this.address = address;
		this.bedrooms = bedrooms;
		this.kind = kind;
		this.city = city;
		this.for_sale = state;
		this.wc = wcs;
		this.photo = photo;
		this.extras = extras;
	}

	
	public int getId() {
		return id;
	}


	public String getAddress() {
		return address;
	}


	public String getBedrooms() {
		return bedrooms;
	}


	public String getKind() {
		return kind;
	}
	
	
	public String getCity() {
		return city;
	}


	public String getPhoto() {
		return photo;
	}
	
	
	public boolean isFor_sale() {
		return for_sale;
	}


	public String getWc() {
		return wc;
	}


	public String getPrice() {
		return price;
	}


	public String getExtras() {
		return extras;
	}
}
