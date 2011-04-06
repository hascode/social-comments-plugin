package com.hascode.confluence.plugin.socialcomments.service;

import org.apache.log4j.Logger;

import com.atlassian.confluence.mail.template.ConfluenceMailQueueItem;
import com.atlassian.confluence.pages.Comment;
import com.atlassian.confluence.setup.settings.SettingsManager;
import com.atlassian.mail.MailException;
import com.atlassian.user.User;

public class SocialNotificationServiceImpl implements SocialNotificationService {
	/**
	 * the logger instance
	 */
	private final Logger			logger	= Logger.getLogger(SocialNotificationService.class);

	/**
	 * the settings manager
	 */
	private final SettingsManager	settingsManager;

	public SocialNotificationServiceImpl(final SettingsManager settingsManager) {
		this.settingsManager = settingsManager;
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
		final String subject = "Social Comment Notification";
		final String body = generateMailBody(user, comment);
		final String mimeType = "text/plain";
		final String fromName = "test@test.com";

		final ConfluenceMailQueueItem mailItem = new ConfluenceMailQueueItem(user.getEmail(), subject, body, mimeType);
		mailItem.setFromName(fromName);
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
		final String url = settingsManager.getGlobalSettings().getBaseUrl() + comment.getUrlPath();
		return String.format("Dear %s,\nsomeone wanted to notify you about the following wiki comment: \n\n%s", user.getFullName(), url);
	}

}
