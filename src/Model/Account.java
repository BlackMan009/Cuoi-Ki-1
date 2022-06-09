package Model;

import java.util.Date;

public class Account {

	private String Username, Password, Type, Link;
	Date DateAdd;

	public Account() {
	}

	public Account(String username, String password, String type, Date dateAdd, String link) {
		super();
		this.Username = username;
		this.Password = password;
		this.Type = type;
		this.DateAdd = dateAdd;
		this.Link = link;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public Date getDateAdd() {
		return DateAdd;
	}

	public void setDateAdd(Date date) {
		DateAdd = date;
	}

	public String getLink() {
		return Link;
	}

	public void setLink(String link) {
		Link = link;
	}

	@Override
	public String toString() {
		return "Account [Username=" + Username + ", Password=" + Password + ", Type=" + Type + ", DateAdd=" + DateAdd
				+ ", Link=" + Link + ", getUsername()=" + getUsername() + ", getPassword()=" + getPassword()
				+ ", getType()=" + getType() + ", getDateAdd()=" + getDateAdd() + ", getLink()=" + getLink()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}