package com.hascode.confluence.plugin.socialcomments.service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.atlassian.confluence.pages.Comment;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.user.User;

public class RecipientExtractorServiceImpl implements RecipientExtractorService {
	/**
	 * the pattern todo: make configurable
	 */
	private static final String	PATTERN	= "@(\\w+):";

	/**
	 * the logger
	 */
	private final Logger		logger	= Logger.getLogger(RecipientExtractorService.class);

	/**
	 * the user dao
	 */
	private final UserAccessor	userAccessor;

	public RecipientExtractorServiceImpl(final UserAccessor userAccessor) {
		this.userAccessor = userAccessor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hascode.confluence.plugin.socialcomments.service.
	 * RecipientExtractorService
	 * #extractUserFromComment(com.atlassian.confluence.pages.Comment)
	 */
	@Override
	public Set<User> extractUserFromComment(final Comment comment) {
		final Set<String> userNames = new HashSet<String>();

		final String content = comment.getContent();
		if (StringUtils.isEmpty(content)) {
			logger.warn("comment content is empty .. we're done here. comment id: " + comment.getId());
			return new HashSet<User>();
		}

		final Pattern pattern = Pattern.compile(PATTERN);
		final Matcher m = pattern.matcher(content);

		logger.debug("trying to extract recipients. regex used: " + PATTERN);
		while (m.find()) {
			if (m.groupCount() > 0) {
				for (int i = 1; i <= m.groupCount(); i++) {
					userNames.add(m.group(i));
				}
			}
		}
		logger.debug("usernames resolved: " + userNames.toString());

		return getUserByNames(userNames);
	}

	/**
	 * resolve user objects by a given string
	 * 
	 * @param userNames
	 *            the set of names
	 * @return a set of users
	 */
	private Set<User> getUserByNames(final Set<String> userNames) {
		final Set<User> users = new HashSet<User>();
		for (final String userName : userNames) {
			final User user = userAccessor.getUser(userName);
			if (user == null) {
				logger.warn("unable to resolve user with given name: " + userName);
				continue;
			}

			users.add(user);
		}
		logger.debug("users resolved: " + users.toString());
		return users;
	}
}
