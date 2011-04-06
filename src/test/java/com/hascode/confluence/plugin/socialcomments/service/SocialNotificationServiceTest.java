package com.hascode.confluence.plugin.socialcomments.service;

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

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConfluenceMailQueueItem.class)
public class SocialNotificationServiceTest {
	@Mock
	private SettingsManager	settingsManager;

	@Mock
	private User			user;

	@Mock
	private Comment			comment;

	@Mock
	private Settings		settings;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Ignore("tbd")
	@Test
	public void testNotifyUser() {
		// stub
		when(comment.getUrlPath()).thenReturn("/somecommentulrstring");
		when(user.getFullName()).thenReturn("I R admin");
		when(user.getEmail()).thenReturn("tester@hascode.com");
		when(settingsManager.getGlobalSettings()).thenReturn(settings);
		when(settings.getBaseUrl()).thenReturn("http://localhost:8080/");
		PowerMockito.mock(ConfluenceMailQueueItem.class);

		// init
		final SocialNotificationService service = new SocialNotificationServiceImpl(settingsManager);

		// test
		service.notifyUser(user, comment);

		// spy
	}
}