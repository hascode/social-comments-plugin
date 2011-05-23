package com.hascode.confluence.plugin.socialcomments.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.atlassian.confluence.mail.template.ConfluenceMailQueueItem;
import com.atlassian.confluence.pages.Comment;
import com.atlassian.confluence.setup.settings.Settings;
import com.atlassian.confluence.setup.settings.SettingsManager;
import com.atlassian.user.User;
import com.hascode.confluence.plugin.socialcomments.persistence.SocialCommentsConfigurationService;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConfluenceMailQueueItem.class)
public class SocialNotificationServiceTest {
	@Mock
	private SettingsManager						settingsManager;

	@Mock
	private User								user;

	@Mock
	private Comment								comment;

	@Mock
	private Settings							settings;

	@Mock
	private SocialCommentsConfigurationService	configService;

	@Mock
	private SocialNotificationTemplateCreator	templateCreator;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Ignore("tbd")
	@Test
	public void testNotifyUser() {
		// stub
		when(comment.getUrlPath()).thenReturn("/somecommentulrstring");
		when(comment.getContent()).thenReturn("This is a test comment");
		when(user.getFullName()).thenReturn("I R admin");
		when(user.getEmail()).thenReturn("tester@hascode.com");
		when(settingsManager.getGlobalSettings()).thenReturn(settings);
		when(settings.getBaseUrl()).thenReturn("http://localhost:8080/");
		PowerMockito.mock(ConfluenceMailQueueItem.class);

		// init
		final SocialNotificationService service = new SocialNotificationServiceImpl(settingsManager, configService, templateCreator);

		// test
		service.notifyUser(user, comment);

		// spy
		verify(templateCreator.generateBody(any(User.class), any(Comment.class)), times(1));
	}
}
