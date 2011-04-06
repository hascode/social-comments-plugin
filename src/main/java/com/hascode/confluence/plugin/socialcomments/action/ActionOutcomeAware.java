package com.hascode.confluence.plugin.socialcomments.action;

public interface ActionOutcomeAware {
	/**
	 * default action outcome - no errors
	 */
	public static final String	OK				= "ok";

	/**
	 * outcome for an action with errors that should be displayed using a
	 * different template
	 */
	public static final String	ERROR			= "error";

	/**
	 * outcome if the current user does not have sufficient permission to invoke
	 * the action
	 */
	public static final String	NO_PERMISSION	= "no-permission";
}
