package com.hascode.confluence.plugin.socialcomments.service;

import org.apache.log4j.Logger;

import com.atlassian.confluence.mail.template.ConfluenceMailQueueItem;
import com.atlassian.confluence.pages.Comment;
import com.atlassian.confluence.setup.settings.SettingsManager;
import com.atlassian.mail.MailException;
import com.atlassian.user.User;
import com.hascode.confluence.plugin.socialcomments.config.SocialCommentsConfiguration;
import com.hascode.confluence.plugin.socialcomments.persistence.SocialCommentsConfigurationService;

public class SocialNotificationServiceImpl implements SocialNotificationService {
	/**
	 * the email's mime type
	 */
	private static final String							MIME_TYPE	= "text/plain";

	/**
	 * the logger instance
	 */
	private final Logger								logger		= Logger.getLogger(SocialNotificationService.class);

	/**
	 * the settings manager
	 */
	private final SettingsManager						settingsManager;

	/**
	 * the config dao
	 */
	private final SocialCommentsConfigurationService	configService;

	/**
	 * the template creator
	 */
	private final SocialNotificationTemplateCreator		templateCreator;

	public SocialNotificationServiceImpl(final SettingsManager settingsManager, final SocialCommentsConfigurationService configService, final SocialNotificationTemplateCreator templateCreator) {
		this.settingsManager = settingsManager;
		this.configService = configService;
		this.templateCreator = templateCreator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hascode.confluence.plugin.socialcomments.service.
	 * SocialNotificationService#notifyUser(com.atlassian.user.User,
	 * com.atlassian.confluence.pages.Comment)
	 */
	@Override
	public void notifyUser(final User user, final Comment comment) {
		final SocialCommentsConfiguration config = configService.load();
		if (!configService.validate(config).isEmpty()) {
			logger.warn("social comments configuration is not validated. please adjust your settings in the admin area!");
			return;
		}

		final String body = generateMailBody(user, comment);
		if (body == null) {
			logger.warn("e-mail body empty .. skipping here ..");
			return;
		}

		final ConfluenceMailQueueItem mailItem = new ConfluenceMailQueueItem(user.getEmail(), config.getEmailSubject(), body, MIME_TYPE);
		mailItem.setFromName(config.getFromEmail());
		logger.debug("trying to send mail: " + mailItem.toString());

		try {
			mailItem.send();
		} catch (MailException e) {
			logger.error("mail transfer failed!", e);
		}

	}
	/**
	 * generates the mail body
	 * 
	 * @param user
	 *            the user
	 * @param comment
	 *            the comment
	 * @return the generated mail body
	 */
	private String generateMailBody(final User user, final Comment comment) {
		return templateCreator.generateBody(user, comment);
	}
}
