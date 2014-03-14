package nc.web.pub.fw.handler;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.web.pub.exception.message.ExceptionMessage;
import com.web.pub.fw.controller.BaseController;

public class PubExceptionInterceptor extends HandlerInterceptorAdapter {

	/**
	 * controllerִ��֮ǰ����
	 * 
	 * @author:sunshine
	 * @date:2014-1-7 ����4:48:23
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		BaseController bc = (BaseController) handler;
		bc.setRequest(request);
		bc.setResponse(response);
		return super.preHandle(request, response, handler);
	}

	/**
	 * controllerִ��֮�� view render֮ǰ����
	 * 
	 * @author:sunshine
	 * @date:2014-1-7 ����4:48:31
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		// super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * view render֮��ִ�У���ȡ�쳣����ת������ҳ�棬������Ϣ��ʾ����
	 * 
	 * @author:sunshine
	 * @date:2014-1-7 ����4:48:40
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#afterCompletion(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// ��������쳣����ת������ҳ�棬�����쳣��Ϣ
		if (ex != null) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(out);
			ex.printStackTrace(ps);
			ps.close();
			ExceptionMessage message = new ExceptionMessage(ex.getCause()
					.toString(), new String(out.toByteArray()));
			request.setAttribute("exceptionMessage", message);
			request.getRequestDispatcher("esfw/pub/error.htm").forward(request,
					response);
		} else {
			super.afterCompletion(request, response, handler, ex);
		}
	}

}
