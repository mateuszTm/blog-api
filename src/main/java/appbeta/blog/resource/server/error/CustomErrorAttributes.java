package appbeta.blog.resource.server.error;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

	public CustomErrorAttributes() {
		super(false);
	}

	public CustomErrorAttributes(boolean includeException) {
		super(includeException);
	}
	
	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
		Map<String, Object> attributes = super.getErrorAttributes(webRequest, includeStackTrace);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		attributes.put(
				"time", formatter.format(attributes.get("timestamp")));
		attributes.remove("timestamp");
		return attributes;
	}
}
