package com.hascode.confluence.plugin.socialcomments.service;

import org.apache.log4j.Logger;

import com.atlassian.confluence.pages.Comment;
import com.atlassian.confluence.setup.settings.SettingsManager;
import com.atlassian.user.User;
import com.hascode.confluence.plugin.socialcomments.persistence.SocialCommentsConfigurationService;

public class SocialNotificationTemplateCreatorImpl implements SocialNotificationTemplateCreator {
	/**
	 * the settings manager
	 */
	private final SettingsManager						settingsManager;

	/**
	 * the logger instance
	 */
	private final Logger								logger	= Logger.getLogger(this.getClass());

	/**
	 * the config dao
	 */
	private final SocialCommentsConfigurationService	configService;

	public SocialNotificationTemplateCreatorImpl(final SocialCommentsConfigurationService configService, final SettingsManager settingsManager) {
		this.settingsManager = settingsManager;
		this.configService = configService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hascode.confluence.plugin.socialcomments.service.
	 * SocialNotificationTemplateCreator#generateBody(com.atlassian.user.User,
	 * com.atlassian.confluence.pages.Comment)
	 */
	@Override
	public String generateBody(final User user, final Comment comment) {
		final String url = settingsManager.getGlobalSettings().getBaseUrl() + comment.getUrlPath();
		final String userName = user.getFullName();
		final String content = comment.getContent();
		final String notification = configService.load().getNotificationText();
		if (notification == null) {
			logger.warn("notification is not set in the configuration, skipping further processing..");
			return null;
		}

		String body = notification;
		body = body.replaceAll("#name#", userName);
		body = body.replaceAll("#url#", url);
		body = body.replaceAll("#content#", content);
		body = body.replaceAll("#creator#", comment.getCreatorName());

		logger.debug("generated notification template is: " + body);
		return body;
	}
}
