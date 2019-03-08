package main.java.entities;

import java.sql.SQLException;
import java.util.Map;

import main.java.backend.Utils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PartDatabase implements Entity {

	String partID;
	String distID;
	String name;
	String unitPrice;
	String distDelay;
	
	public PartDatabase(String partID) {
		super();
		this.partID = partID;
	}

	public PartDatabase(String partID, String distID, String name, String unitPrice, String distDelay) {
		super();
		this.partID = partID;
		this.distID = distID;
		this.name = name;
		this.unitPrice = unitPrice;
		this.distDelay = distDelay;
	}

	public String getPartID() {
		return partID;
	}

	public void setPartID(String partID) {
		this.partID = partID;
	}

	public String getDistID() {
		return distID;
	}

	public void setDistID(String distID) {
		this.distID = distID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getDistDelay() {
		return distDelay;
	}

	public void setDistDelay(String distDelay) {
		this.distDelay = distDelay;
	}

	@Override
	public void load() throws SQLException {
		Map<String,String> partDatabase = Utils.select("SELECT * FROM PartDatabase WHERE partID = ?;", 
				new String[] {this.partID}).get(0);
		this.distID = partDatabase.get("distID");
		this.name = partDatabase.get("name");
		this.unitPrice = partDatabase.get("unitPrice");
		this.distDelay = partDatabase.get("distDelay");
	}

	@Override
	public void save() throws SQLException {
        throw new NotImplementedException();
	}

	@Override
	public void insert() throws SQLException {
        throw new NotImplementedException();
	}

}
