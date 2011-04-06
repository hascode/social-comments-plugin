package com.hascode.confluence.plugin.socialcomments.service;

import java.util.Set;

import com.atlassian.confluence.pages.Comment;
import com.atlassian.user.User;

public interface RecipientExtractorService {
	/**
	 * extracts possible recipients from a given comment
	 * 
	 * @param comment
	 *            the comment
	 * @return the set of recipients
	 */
	public abstract Set<User> extractUserFromComment(final Comment comment);
}
