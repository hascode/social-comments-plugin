package com.hascode.confluence.plugin.socialcomments.event;

import java.util.Set;

import org.apache.log4j.Logger;

import com.atlassian.confluence.event.events.content.comment.CommentCreateEvent;
import com.atlassian.confluence.event.events.content.comment.CommentUpdateEvent;
import com.atlassian.confluence.pages.Comment;
import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.user.User;
import com.hascode.confluence.plugin.socialcomments.service.RecipientExtractorService;
import com.hascode.confluence.plugin.socialcomments.service.SocialNotificationService;
public class CommentEventListenerImpl implements CommentEventListener {
	/**
	 * the logger
	 */
	private final Logger					logger	= Logger.getLogger(CommentEventListener.class);

	/**
	 * the recipient extractor
	 */
	private final RecipientExtractorService	recipientExtractorService;

	/**
	 * the social notification service
	 */
	private final SocialNotificationService	socialNotificationService;

	public CommentEventListenerImpl(final EventPublisher eventPublisher, final RecipientExtractorService recipientExtractorService, final SocialNotificationService socialNotificationService) {
		this.recipientExtractorService = recipientExtractorService;
		this.socialNotificationService = socialNotificationService;
		eventPublisher.register(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hascode.confluence.plugin.socialcomments.event.CommentEventListener
	 * #handleCommentCreateEvent
	 * (com.atlassian.confluence.event.events.content.comment
	 * .CommentCreateEvent)
	 */
	@Override
	@EventListener
	public void handleCommentCreateEvent(final CommentCreateEvent commentCreateEvent) {
		logger.debug("comment create event handler called");
		extractAndNotify(commentCreateEvent.getComment());
	}

	/**
	 * extracts recipients and notifies them
	 * 
	 * @param comment
	 *            the comment
	 */
	private void extractAndNotify(final Comment comment) {
		try {
			final Set<User> recipients = recipientExtractorService.extractUserFromComment(comment);
			for (final User user : recipients) {
				socialNotificationService.notifyUser(user, comment);
			}
		} catch (final Exception e) {
			logger.warn("handling social comment failed!", e);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hascode.confluence.plugin.socialcomments.event.CommentEventListener
	 * #handleCommentUpdateEvent
	 * (com.atlassian.confluence.event.events.content.comment
	 * .CommentUpdateEvent)
	 */
	@Override
	@EventListener
	public void handleCommentUpdateEvent(final CommentUpdateEvent commentUpdateEvent) {
		logger.debug("comment update event handler called");
		extractAndNotify(commentUpdateEvent.getComment());
	}

}
