package com.hascode.confluence.plugin.socialcomments.persistence;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.hascode.confluence.plugin.socialcomments.config.SocialCommentsConfiguration;

public interface SocialCommentsConfigurationService {
	/**
	 * validates a given configuration
	 * 
	 * @param config
	 *            the configuration to validate
	 * @return a set of generic constraint violations
	 */
	public abstract Set<ConstraintViolation<SocialCommentsConfiguration>> validate(final SocialCommentsConfiguration config);

	/**
	 * saves or updates the configuration
	 * 
	 * @param config
	 *            the configuration
	 */
	public abstract void saveOrUpdate(final SocialCommentsConfiguration config);

	/**
	 * returns the configuration from the persistence api
	 * 
	 * @return the configuration
	 */
	public abstract SocialCommentsConfiguration load();
}
