package appbeta.blog.resource.server.config;

public enum Role {
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	
	public final String authority;
	
	private Role(String authority) {
		this.authority = authority;
	}
	
	public String authority() {
		return authority;
	}
}
