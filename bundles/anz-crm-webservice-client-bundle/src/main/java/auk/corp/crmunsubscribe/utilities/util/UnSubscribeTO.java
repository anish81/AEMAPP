package auk.corp.crmunsubscribe.utilities.util;

public class UnSubscribeTO {
	
	private String pid;
	private String mid;
	private String flag;
	private String site;
	private String email;
	private String username;
	private String password;
	private String endPointURL;
	public String getEndPointURL() {
		return endPointURL;
	}
	public void setEndPointURL(String endPointURL) {
		this.endPointURL = endPointURL;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}

}
