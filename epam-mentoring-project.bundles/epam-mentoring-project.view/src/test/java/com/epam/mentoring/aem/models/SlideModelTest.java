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
 * JUnit test for SlideModel
 */
public class SlideModelTest {

    public static final String PN_FILE_SLIDE_REFERENCE = "fileSlideReference";
    
    public static final String PN_RESOURCE_REFERENCE = "resource";

    public static final String PN_SLIDE_TEXT = "slideText";
    
    public static final String PV_SLIDE_TEXT = "This is slide";

    private static final Map<String, Object> resourceProperties = new HashMap<>();
    
    private SlideModel slideModel;

    @Rule
    public final AemContext context = new AemContext();

    @Before
    public void setUp() throws WCMException {
        context.addModelsForPackage("com.epam.mentoring.aem.models");
        
    	Resource sliderpar = context.create().resource("/sliderpar");
    	Resource injectedResource = context.create().resource("/sliderpar/slide");

        resourceProperties.put(PN_RESOURCE_REFERENCE, injectedResource);
    	resourceProperties.put(PN_FILE_SLIDE_REFERENCE, "/content/dam/projects/media/cover");
    	resourceProperties.put(PN_SLIDE_TEXT, PV_SLIDE_TEXT);
    	
    	Resource resource = context.create().resource("test", resourceProperties);
        slideModel = resource.adaptTo(SlideModel.class);
    }

    @Test
    public void shouldHaveDAMImage() {
        assertTrue("Slide should have referenced DAM image", slideModel.hasDAMImage());
    }
    
    @Test
    public void shouldNotHaveUploadedImage() {
        assertFalse("Slider shoud not have uploaded image", slideModel.hasUploadedImage());
    }
    
    @Test
    public void shouldHaveText() {
        assertEquals("Slide text is not as expected", PV_SLIDE_TEXT, slideModel.getSlideText());
    }
}