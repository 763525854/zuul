package zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class AccessFilter extends ZuulFilter {
	private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();
		log.info("sned {} request to {}", request.getMethod(), request.getRequestURL().toString());
		Object accessToekn=request.getParameter("accessToken");
		if (accessToekn==null) {
			log.warn("access token is empty");
			context.setSendZuulResponse(false);
			context.setResponseStatusCode(401);
			return null;
		}
		log.info("access token ok");
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
