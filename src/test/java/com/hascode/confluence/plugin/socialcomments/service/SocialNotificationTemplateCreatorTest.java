package com.hascode.confluence.plugin.socialcomments.service;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.atlassian.confluence.pages.Comment;
import com.atlassian.confluence.setup.settings.Settings;
import com.atlassian.confluence.setup.settings.SettingsManager;
import com.atlassian.user.User;
import com.hascode.confluence.plugin.socialcomments.config.SocialCommentsConfiguration;
import com.hascode.confluence.plugin.socialcomments.persistence.SocialCommentsConfigurationService;

@RunWith(MockitoJUnitRunner.class)
public class SocialNotificationTemplateCreatorTest {
	private static final String					BASE_URL			= "http://localhost:8080/";
	private static final String					COMMENT_PATH		= "somecomment123#2133412";
	private static final String					TEMPLATE_CONTENT	= "Dear #name# #url# #content# #creator#";
	private static final String					USER_NAME			= "tester";
	private static final String					COMMENT_CONTENT		= "Hey @admin: i wanted to nofify you ..";
	private static final String					CREATOR_NAME		= "John the Admin";
	private static final String					OUTPUT_EXPECTED		= "Dear " + USER_NAME + " " + BASE_URL + COMMENT_PATH + " " + COMMENT_CONTENT + " " + CREATOR_NAME;

	@Mock
	private SocialCommentsConfigurationService	configService;

	@Mock
	private SettingsManager						settingsManager;

	@Mock
	private User								user;

	@Mock
	private Comment								comment;

	@Mock
	private Settings							settings;

	@Mock
	private SocialCommentsConfiguration			config;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testInvalidState() {
		// init
		final SocialNotificationTemplateCreator creator = new SocialNotificationTemplateCreatorImpl(configService, settingsManager);

		// test
		try {
			creator.generateBody(user, comment);
			Assert.fail("npe should be thrown..");
		} catch (NullPointerException e) {
		}

		when(settingsManager.getGlobalSettings()).thenReturn(settings);
		when(settings.getBaseUrl()).thenReturn(BASE_URL);
		when(comment.getUrlPath()).thenReturn(COMMENT_PATH);
		try {
			creator.generateBody(user, comment);
			Assert.fail("npe should be thrown..");
		} catch (NullPointerException e) {
		}
	}

	@Test
	public void testSuccess() {
		// init
		final SocialNotificationTemplateCreator creator = new SocialNotificationTemplateCreatorImpl(configService, settingsManager);

		// test
		when(settingsManager.getGlobalSettings()).thenReturn(settings);
		when(settings.getBaseUrl()).thenReturn(BASE_URL);
		when(comment.getUrlPath()).thenReturn(COMMENT_PATH);
		when(comment.getContent()).thenReturn(COMMENT_CONTENT);
		when(comment.getCreatorName()).thenReturn(CREATOR_NAME);
		when(configService.load()).thenReturn(config);
		when(config.getNotificationText()).thenReturn(TEMPLATE_CONTENT);
		when(user.getFullName()).thenReturn(USER_NAME);

		String result = creator.generateBody(user, comment);
		Assert.assertNotNull(result);
		Assert.assertEquals(OUTPUT_EXPECTED, result);
	}
}
