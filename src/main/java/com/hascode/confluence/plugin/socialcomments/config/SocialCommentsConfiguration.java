package com.hascode.confluence.plugin.socialcomments.config;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SocialCommentsConfiguration implements Serializable {
	private static final long	serialVersionUID	= 1L;

	@NotNull(message = "{com.hascode.socialcomments.validation.from.invalid}")
	@Pattern(regexp = "^.+@.+\\..+ $", message = "{com.hascode.socialcomments.validation.from.invalid}")
	private String				fromEmail;

	@NotNull(message = "{com.hascode.socialcomments.validation.notification.invalid}")
	@Size(min = 1, message = "{com.hascode.socialcomments.validation.notification.invalid}")
	private String				notificationText;

	@NotNull(message = "{com.hascode.socialcomments.validation.subject.invalid}")
	@Size(min = 1, message = "{com.hascode.socialcomments.validation.subject.invalid}")
	private String				emailSubject;

	/**
	 * @return the fromEmail
	 */
	public String getFromEmail() {
		return fromEmail;
	}

	/**
	 * @param fromEmail
	 *            the fromEmail to set
	 */
	public void setFromEmail(final String fromEmail) {
		this.fromEmail = fromEmail;
	}

	/**
	 * @return the notificationText
	 */
	public String getNotificationText() {
		return notificationText;
	}

	/**
	 * @param notificationText
	 *            the notificationText to set
	 */
	public void setNotificationText(final String notificationText) {
		this.notificationText = notificationText;
	}

	/**
	 * @return the emailSubject
	 */
	public String getEmailSubject() {
		return emailSubject;
	}

	/**
	 * @param emailSubject
	 *            the emailSubject to set
	 */
	public void setEmailSubject(final String emailSubject) {
		this.emailSubject = emailSubject;
	}
}
