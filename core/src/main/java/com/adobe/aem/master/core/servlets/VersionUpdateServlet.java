/**
 * 
 */
package com.adobe.aem.master.core.servlets;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * @author kartikkarnayil
 *
 */
@Component(service = Servlet.class, immediate = true)
@Designate(ocd = VersionUpdateServlet.Config.class)
public class VersionUpdateServlet extends SlingAllMethodsServlet {

	Pattern p = Pattern.compile("v[0-9]");

	@ObjectClassDefinition(name = "DynamicResourceTypeServlet", description = "Resource Types to Enable this Servlet")
	public static @interface Config {
		@AttributeDefinition(name = "Selectors", description = "Standard Sling servlet property")
		String[] sling_servlet_selectors() default { "update" };

		@AttributeDefinition(name = "Resource Types", description = "Standard Sling servlet property")
		String[] sling_servlet_resourceTypes() default { "" };

		@AttributeDefinition(name = "Methods", description = "Standard Sling servlet property")
		String[] sling_servlet_methods() default { "POST" };

		@AttributeDefinition(name = "Extensions", description = "Standard Sling servlet property")
		String[] sling_servlet_extensions() default { "html" };
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {

		final Resource resource = req.getResource();
		String version = resource.getValueMap().get("./version", String.class);
		final Matcher matcher = p.matcher(resource.getResourceType());
		final String newVersion = matcher.replaceFirst(version);

		if (!StringUtils.isEmpty(version) && !version.equalsIgnoreCase(newVersion)) {
			final ModifiableValueMap map = resource.adaptTo(ModifiableValueMap.class);
			map.put(ResourceResolver.PROPERTY_RESOURCE_TYPE, newVersion);
			resource.getResourceResolver().commit();
			resp.getWriter().println("Version updated to :" + newVersion);

		} else {
			resp.getWriter().println("Version not updated.");
		}

	}

}