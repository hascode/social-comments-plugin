package com.hascode.confluence.plugin.socialcomments.event;

import com.atlassian.confluence.event.events.content.comment.CommentCreateEvent;
import com.atlassian.confluence.event.events.content.comment.CommentUpdateEvent;

public interface CommentEventListener {
	/**
	 * handles comment creation
	 * 
	 * @param commentCreateEvent
	 *            the event
	 */
	public abstract void handleCommentCreateEvent(final CommentCreateEvent commentCreateEvent);

	/**
	 * handles comment updates
	 * 
	 * @param commentUpdateEvent
	 *            the event
	 */
	public abstract void handleCommentUpdateEvent(final CommentUpdateEvent commentUpdateEvent);

}
