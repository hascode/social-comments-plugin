package com.hascode.confluence.plugin.socialcomments.service;

import com.atlassian.confluence.pages.Comment;
import com.atlassian.user.User;

public interface SocialNotificationService {
	/**
	 * notifies a given user that he is mentioned in a comment
	 * 
	 * @param user
	 *            the user
	 * @param comment
	 *            the comment
	 */
	public abstract void notifyUser(final User user, final Comment comment);
}
