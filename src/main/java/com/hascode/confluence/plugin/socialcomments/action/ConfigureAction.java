package com.hascode.confluence.plugin.socialcomments.action;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.log4j.Logger;

import com.atlassian.confluence.spaces.actions.AbstractSpaceAction;
import com.hascode.confluence.plugin.socialcomments.config.SocialCommentsConfiguration;
import com.hascode.confluence.plugin.socialcomments.persistence.SocialCommentsConfigurationService;
import com.hascode.confluence.plugin.socialcomments.util.Util;

public class ConfigureAction extends AbstractSpaceAction implements ActionOutcomeAware {
	/**
	 * the serial UID
	 */
	private static final long							serialVersionUID	= 1L;

	/**
	 * the logger
	 */
	private final Logger								logger				= Logger.getLogger(ConfigureAction.class);

	/**
	 * utility helper
	 */
	private Util										util;

	/**
	 * the config
	 */
	private SocialCommentsConfiguration					config;

	/**
	 * the configuration service
	 */
	private final SocialCommentsConfigurationService	configurationService;

	public ConfigureAction(final SocialCommentsConfigurationService configurationService) {
		this.configurationService = configurationService;
		this.util = new Util();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork.ActionSupport#execute()
	 */
	@Override
	public String execute() {
		// permission check
		if (!util.userIsAdmin()) {
			logger.warn("user has not sufficient permission to invoke this action. user: " + util.getCurrentUser().getName());
			return NO_PERMISSION;
		}

		config = configurationService.load();

		if (util.getRequestParameter("action") != null) {
			config.setFromEmail(util.getRequestParameter("from"));
			config.setNotificationText(util.getRequestParameter("notification"));
			config.setEmailSubject(util.getRequestParameter("subject"));
			final Set<ConstraintViolation<SocialCommentsConfiguration>> errors = configurationService.validate(config);
			if (errors.isEmpty()) {
				configurationService.saveOrUpdate(config);
			} else {
				for (final ConstraintViolation<SocialCommentsConfiguration> error : errors) {
					addActionError(error.getMessage());
				}
			}
		}

		return OK;
	}
	/**
	 * @param util
	 *            the util to set
	 */
	public void setUtil(final Util util) {
		this.util = util;
	}

	/**
	 * @return the config
	 */
	public SocialCommentsConfiguration getSocialCommentConfig() {
		return config;
	}

}
