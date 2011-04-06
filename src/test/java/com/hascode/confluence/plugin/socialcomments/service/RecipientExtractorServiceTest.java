package com.hascode.confluence.plugin.socialcomments.service;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.atlassian.confluence.pages.Comment;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.user.User;
@RunWith(MockitoJUnitRunner.class)
public class RecipientExtractorServiceTest {
	private static final String	COMMENT_TEXT	= "This is a really important information!\n@admin: you should notice this! @tester: - hey check out this comment .. @tim you won't receive this @nonexisting: you do not exist - sorry ...";

	@Mock
	private UserAccessor		userAccessor;

	@Mock
	private Comment				comment;

	@Mock
	private User				admin;

	@Mock
	private User				user;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testExtractUserFromComment() {
		// stub
		when(comment.getContent()).thenReturn(COMMENT_TEXT);
		when(userAccessor.getUser("admin")).thenReturn(admin);
		when(userAccessor.getUser("tester")).thenReturn(user);

		// init
		final RecipientExtractorService service = new RecipientExtractorServiceImpl(userAccessor);

		// test
		Set<User> recipients = service.extractUserFromComment(comment);
		Assert.assertNotNull(recipients);
		Assert.assertEquals(2, recipients.size());

		// spy
		verify(userAccessor, times(3)).getUser(anyString());
	}
}
