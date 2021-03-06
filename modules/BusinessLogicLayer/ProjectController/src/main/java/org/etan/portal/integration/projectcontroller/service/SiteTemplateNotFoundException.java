package org.etan.portal.integration.projectcontroller.service;

/**
 * If site template with some name not found.
 *
 * @author Efimov Timur
 * @version 1.0.1
 */
public class SiteTemplateNotFoundException extends RuntimeException {

    public SiteTemplateNotFoundException(String projectSiteTemplateName) {
        super("This site template not found: " + projectSiteTemplateName);
    }
}
