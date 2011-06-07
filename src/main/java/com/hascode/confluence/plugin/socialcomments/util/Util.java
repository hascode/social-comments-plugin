package com.hascode.confluence.plugin.socialcomments.util;

import java.util.Map;

import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.user.AuthenticatedUserThreadLocal;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.spring.container.ContainerManager;
import com.atlassian.user.User;
import com.opensymphony.webwork.ServletActionContext;

/**
 * utility class
 */
public class Util {
	private UserAccessor	userAccessor;

	/**
	 * returns whether the current user is logged in or not
	 * 
	 * @return login state
	 */
	public boolean userLoggedin() {
		return (AuthenticatedUserThreadLocal.getUser() != null);
	}

	/**
	 * returns whether the current user has the administrator role
	 * 
	 * @return role check result
	 */
	public boolean userIsAdmin() {
		final User user = AuthenticatedUserThreadLocal.getUser();
		return getUserAccessor().isSuperUser(user);
	}

	/**
	 * returns parameters from the current request
	 * 
	 * @param key
	 *            the param key
	 * @return the param value
	 */
	public String getRequestParameter(final String key) {
		return ServletActionContext.getRequest().getParameter(key);
	}

	/**
	 * returns the current user
	 * 
	 * @return the user
	 */
	public User getCurrentUser() {
		return AuthenticatedUserThreadLocal.getUser();
	}

	/**
	 * returns a hashmap to use with the velocity context
	 * 
	 * @return
	 */
	public Map<String, Object> getDefaultVelocityContext() {
		return MacroUtils.defaultVelocityContext();
	}

	/**
	 * returns a rendered template enriched with context parameters
	 * 
	 * @param macroTemplate
	 *            the template
	 * @param context
	 *            the context
	 * @return the rendered template
	 */
	public String getVelocityRenderedTemplate(final String macroTemplate, final Map<String, Object> context) {
		return VelocityUtils.getRenderedTemplate(macroTemplate, context);
	}

	private UserAccessor getUserAccessor() {
		if (userAccessor == null) {
			userAccessor = (UserAccessor) ContainerManager.getComponent("userAccessor");
		}
		return userAccessor;
	}
}
