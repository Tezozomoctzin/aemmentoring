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
 * JUnit test for SliderModel
 */
public class SliderModelTest {

    public static final String PN_SLIDER_HEADER_TEXT = "sliderHeaderText";
    
    public static final String PN_RESOURCE_REFERENCE = "resource";

    public static final String PV_SLIDER_HEADER_TEXT = "This is slider component";

    private static final Map<String, Object> resourceProperties = new HashMap<>();
    
    private SliderModel sliderModel;

    @Rule
    public final AemContext context = new AemContext();

    @Before
    public void setUp() {
        context.addModelsForPackage("com.epam.mentoring.aem.models");
        
    	Resource injectedResource = context.create().resource("/slider", resourceProperties);
    	
        Resource sliderPar = context.create().resource("/slider/sliderpar");
        Resource slide = context.create().resource("/slider/sliderpar/slide");
        
        resourceProperties.put(PN_RESOURCE_REFERENCE, injectedResource);
    	resourceProperties.put(PN_SLIDER_HEADER_TEXT, PV_SLIDER_HEADER_TEXT);  
    	
    	Resource resource = context.create().resource("test", resourceProperties);
        sliderModel = resource.adaptTo(SliderModel.class);
    }

    @Test
    public void shouldHaveSlides() {
        assertFalse("Slider should have slides inside built-in paragraph system", sliderModel.getSlides().isEmpty());
    }
    
    @Test
    public void shouldHaveHeader() {
        assertEquals("Header text is not as expected", PV_SLIDER_HEADER_TEXT, sliderModel.getSliderHeaderText());
    }
}