package com.hascode.confluence.plugin.socialcomments.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationProviderResolver;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.spi.ValidationProvider;

import org.hibernate.validator.HibernateValidator;

import com.hascode.confluence.plugin.socialcomments.config.SocialCommentsConfiguration;

public class SocialCommentsConfigurationServiceImpl implements SocialCommentsConfigurationService {
	/**
	 * the validator instance
	 */
	private static Validator	validator;
	static {
		final Configuration<?> config = Validation.byDefaultProvider().providerResolver(new ValidationProviderResolver() {
			@Override
			public List<ValidationProvider<?>> getValidationProviders() {
				List<ValidationProvider<?>> providers = new ArrayList<ValidationProvider<?>>();
				providers.add(new HibernateValidator());
				return providers;
			}
		}).configure();
		final ValidatorFactory factory = config.buildValidatorFactory();
		validator = factory.getValidator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hascode.confluence.plugin.socialcomments.persistence.
	 * SocialCommentsConfigurationService
	 * #validate(com.hascode.confluence.plugin.
	 * socialcomments.config.SocialCommentsConfiguration)
	 */
	@Override
	public Set<ConstraintViolation<SocialCommentsConfiguration>> validate(final SocialCommentsConfiguration config) {
		return validator.validate(config);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hascode.confluence.plugin.socialcomments.persistence.
	 * SocialCommentsConfigurationService
	 * #saveOrUpdate(com.hascode.confluence.plugin
	 * .socialcomments.config.SocialCommentsConfiguration)
	 */
	@Override
	public void saveOrUpdate(final SocialCommentsConfiguration config) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hascode.confluence.plugin.socialcomments.persistence.
	 * SocialCommentsConfigurationService#load()
	 */
	@Override
	public SocialCommentsConfiguration load() {
		return new SocialCommentsConfiguration();
	}

}
