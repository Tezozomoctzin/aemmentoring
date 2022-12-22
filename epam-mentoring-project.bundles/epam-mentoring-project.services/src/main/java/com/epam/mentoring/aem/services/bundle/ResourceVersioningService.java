package com.epam.mentoring.aem.services.bundle;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageEvent;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

@Component(service = EventHandler.class,
        immediate = true,
        property = {
                EventConstants.EVENT_TOPIC+"="+ PageEvent.EVENT_TOPIC,
        })
public class ResourceVersioningService implements EventHandler {

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    public final String TARGET_PATH = "/content/mentoring";

    private Logger logger = LoggerFactory.getLogger(ResourceVersioningService.class);

    @Override
    public void handleEvent(Event event) {
        Object modifications = event.getProperty("modifications");
        //cast modifications to ArrayList
        HashMap<?,?> modificationsMap = (HashMap<?,?>) ((ArrayList<?>) modifications).get(0);
        //get path of the modified resource
        String modificationPath = (String) modificationsMap.get("path");

        if(modificationPath.equals(TARGET_PATH)){
            return;
        }
        try (ResourceResolver resolver = ResourceResolverUtility.getResourceResolver(resourceResolverFactory)) {
            PageManager pageManager = resolver.adaptTo(PageManager.class);
            if (pageManager != null) {
                Page page = pageManager.getContainingPage(modificationPath);
                if (page != null) {
                    ValueMap properties = page.getProperties();
                    String description = properties.get("jcr:description", String.class);
                    if (StringUtils.isNotBlank(description)) {
                        pageManager.createRevision(page);
                    }
                }
            }
        } catch (WCMException | LoginException e) {
            logger.error("Error while creating page revision", e);
        }
    }
}
