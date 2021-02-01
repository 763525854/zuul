package zuul.filter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

//增加一个Filter,通过@Component注入
@Component
public class ThrowExceptionFilter extends ZuulFilter {
	private static Logger log = LoggerFactory.getLogger(ThrowExceptionFilter.class);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		log.info("This is a pre filter,it will throw a RuntimeException");
		RequestContext context = RequestContext.getCurrentContext();
		try {
			doSomething();
		} catch (Exception e) {
			// 要想在日志里能打印到该异常，需要将参数信息传回context中否则，不会被打印
			context.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			context.set("error.exception", e);
			context.set("error.message", e.getMessage());
		}
		return null;
	}

	private void doSomething() {
		// 抛出一个异常，测试异常日志打印处理功能
		// throw new RuntimeException("Exist some erros.....");
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
