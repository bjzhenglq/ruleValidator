package com.sunshine.test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.web.rule.page.paravo.StaticQryPara;

public class GetBean {

	public static void main(String[] args) {
		ClassPathResource resource = new ClassPathResource(
				"WebContent/WEB-INF/config/spring.xml");
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(resource);

		WebApplicationContextUtils.getRequiredWebApplicationContext(null);
	}

	public @ResponseBody
	void queryResultRecord(@ModelAttribute StaticQryPara qryPara,
			HttpServletRequest request) {
		ServletContext context = request.getServletContext();
		WebApplicationContext appcontext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);
		appcontext.getBean("sdf");
	}
}
