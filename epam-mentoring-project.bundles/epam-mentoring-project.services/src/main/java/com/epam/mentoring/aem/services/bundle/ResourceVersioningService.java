package com.epam.mentoring.aem.services.bundle;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.resource.observation.ResourceChange;
import org.apache.sling.api.resource.observation.ResourceChangeListener;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component(service = ResourceChangeListener.class,
        immediate = true,
        property = {
                ResourceChangeListener.PATHS + "=/content/mentoring",
                ResourceChangeListener.CHANGES + "=ADDED",
                ResourceChangeListener.CHANGES + "=CHANGED",
                ResourceChangeListener.CHANGES + "=REMOVED"
        })
public class ResourceVersioningService implements ResourceChangeListener {

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void onChange(List<ResourceChange> list) {
        ResourceResolver resolver = null;
        try {
            resolver = ResourceResolverUtility.getResourceResolver(resourceResolverFactory);
            PageManager pageManager = resolver.adaptTo(PageManager.class);
            for (ResourceChange change : list) {
                if(change.getUserId().equals(resolver.getUserID())){
                    continue;
                }
                Resource resource = resolver.getResource(change.getPath());
                if (resource != null) {
                    ValueMap properties = resource.adaptTo(ValueMap.class);
                    if (properties != null) {
                        String description = properties.get("jcr:description", String.class);
                        if (StringUtils.isNotBlank(description)) {
                            resource = resource.getParent();
                            Page page = resource.adaptTo(Page.class);
                            if (page != null && pageManager != null) {
                                pageManager.createRevision(page);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resolver != null) {
                resolver.close();
            }
        }
    }
}
