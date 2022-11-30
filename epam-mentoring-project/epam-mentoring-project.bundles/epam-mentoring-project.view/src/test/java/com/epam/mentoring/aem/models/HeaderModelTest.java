package com.epam.mentoring.aem.models;

import io.wcm.testing.mock.aem.junit.AemContext;

import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * JUnit test for HeaderModel
 */
public class HeaderModelTest {

    public static final String PN_FILE_LOGO_REFERENCE = "fileLogoReference";
    
    public static final String PN_RESOURCE_REFERENCE = "resource";
    
    public static final String PN_PAGE_MANAGER_REFERENCE = "pageManager";

    private static final Map<String, Object> resourceProperties = new HashMap<>();
    
    private HeaderModel headerModel;

    @Rule
    public final AemContext context = new AemContext();

    @Before
    public void setUp() throws WCMException {
        context.addModelsForPackage("com.epam.mentoring.aem.models");
        
        context.pageManager().create("/", "content", "", "");
        context.pageManager().create("/content", "sample", "", "");
        context.pageManager().create("/content/sample", "en", "", "");
        context.pageManager().create("/content/sample/en/", "testpage", "", "");
        
        PageManager pageManager = context.pageManager();
    	
        Resource injectedResource = context.create().resource("/content/sample/en/testpage/header", resourceProperties);
        
        resourceProperties.put(PN_RESOURCE_REFERENCE, injectedResource);
        resourceProperties.put(PN_PAGE_MANAGER_REFERENCE, pageManager);
    	resourceProperties.put(PN_FILE_LOGO_REFERENCE, "/content/dam/projects/media/cover");  
    	
    	Resource resource = context.create().resource("test", resourceProperties);
        headerModel = resource.adaptTo(HeaderModel.class);
    }

    @Test
    public void shouldHaveDAMImage() {
        assertTrue("Header should have referenced DAM image", headerModel.hasDAMImage());
    }
    
    @Test
    public void shouldNotHaveUploadedImage() {
        assertFalse("Header shoud not have uploaded image",headerModel.hasUploadedImage());
    }
    
    @Test
    public void topMenuShouldHavePages() {
        assertTrue("Top menu contains no pages", headerModel.getTopMenu().hasNext());
    }
}