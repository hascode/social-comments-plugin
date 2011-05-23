package com.hascode.confluence.plugin.socialcomments.service;

import com.atlassian.confluence.pages.Comment;
import com.atlassian.user.User;

public interface SocialNotificationTemplateCreator {
	/**
	 * creates the notification's body
	 * 
	 * @param user
	 *            the user notified
	 * @param comment
	 *            the original comment
	 * @return the created notification
	 */
	public abstract String generateBody(final User user, final Comment comment);
}
