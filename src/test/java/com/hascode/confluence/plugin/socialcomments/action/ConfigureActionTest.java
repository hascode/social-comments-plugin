package com.hascode.confluence.plugin.socialcomments.action;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.atlassian.user.User;
import com.hascode.confluence.plugin.socialcomments.persistence.SocialCommentsConfigurationService;
import com.hascode.confluence.plugin.socialcomments.util.Util;

/**
 * test for ConfigureAction
 */
@RunWith(MockitoJUnitRunner.class)
public class ConfigureActionTest {
	@Mock
	private Util								util;

	@Mock
	private User								user;

	@Mock
	private SocialCommentsConfigurationService	configService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testExecute() {
		// stub
		when(util.getCurrentUser()).thenReturn(user);
		when(util.userIsAdmin()).thenReturn(false);
		when(user.getName()).thenReturn("test-user");

		// init
		final ConfigureAction action = new ConfigureAction(configService);
		action.setUtil(util);

		// test
		Assert.assertEquals(ActionOutcomeAware.NO_PERMISSION, action.execute());
		when(util.userIsAdmin()).thenReturn(true);
		Assert.assertEquals(ActionOutcomeAware.OK, action.execute());

		// spy
		verify(util, times(1)).getCurrentUser();
		verify(util, times(2)).userIsAdmin();
	}
}
