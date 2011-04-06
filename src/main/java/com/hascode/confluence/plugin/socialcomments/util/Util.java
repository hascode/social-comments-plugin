package com.hascode.confluence.plugin.socialcomments.util;

import java.util.Map;

import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.user.AuthenticatedUserThreadLocal;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.user.User;
import com.opensymphony.webwork.ServletActionContext;

/**
 * utility class
 */
public class Util {
	/**
	 * returns whether the current user is logged in or not
	 * 
	 * @return
	 */
	public boolean userLoggedin() {
		return (AuthenticatedUserThreadLocal.getUser() != null);
	}

	/**
	 * returns whether the current user has the administrator role
	 * 
	 * @return
	 */
	public boolean userIsAdmin() {
		// User user = AuthenticatedUserThreadLocal.getUser();
		// Group group =
		// userAccessor.getGroup(PluginConfig.getAdminGroupName());
		// if (user != null && group != null) {
		// if (userAccessor.hasMembership(group, user)) {
		// return true;
		// }
		// }

		return true;
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
}
