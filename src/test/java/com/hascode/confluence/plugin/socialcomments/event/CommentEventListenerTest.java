package com.hascode.confluence.plugin.socialcomments.event;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.atlassian.confluence.event.events.content.comment.CommentCreateEvent;
import com.atlassian.confluence.event.events.content.comment.CommentUpdateEvent;
import com.atlassian.confluence.pages.Comment;
import com.atlassian.event.api.EventPublisher;
import com.hascode.confluence.plugin.socialcomments.service.RecipientExtractorService;
import com.hascode.confluence.plugin.socialcomments.service.SocialNotificationService;
@RunWith(MockitoJUnitRunner.class)
public class CommentEventListenerTest {
	@Mock
	private EventPublisher				eventPublisher;

	@Mock
	private RecipientExtractorService	recipientExtractorService;

	@Mock
	private CommentCreateEvent			commentCreateEvent;

	@Mock
	private CommentUpdateEvent			commentUpdateEvent;

	@Mock
	private SocialNotificationService	socialNotificationService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testHandleCommentCreateEvent() {
		// stub

		// init
		final CommentEventListener listener = new CommentEventListenerImpl(eventPublisher, recipientExtractorService, socialNotificationService);

		// test
		listener.handleCommentCreateEvent(commentCreateEvent);

		// spy
		verify(eventPublisher, times(1)).register(any(CommentEventListener.class));
		verify(recipientExtractorService, times(1)).extractUserFromComment(any(Comment.class));
	}

	@Test
	public void testHandleCommentUpdateEvent() {
		// stub

		// init
		final CommentEventListener listener = new CommentEventListenerImpl(eventPublisher, recipientExtractorService, socialNotificationService);

		// test
		listener.handleCommentUpdateEvent(commentUpdateEvent);

		// spy
		verify(eventPublisher, times(1)).register(any(CommentEventListener.class));
		verify(recipientExtractorService, times(1)).extractUserFromComment(any(Comment.class));
	}
}
