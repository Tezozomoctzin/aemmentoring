package com.epam.mentoring.aem.models;

import io.wcm.testing.mock.aem.junit.AemContext;
import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * JUnit test for HeaderModel
 */
public class FooterModelTest {

    public static final String PN_COPYRIGHT = "copyright";
    public static final String PN_LINKS = "links";

    public static final String EXPECTED_COPYRIGHT_TEXT = "Copyright text";
    public static final int EXPECTED_LINKS_COUNT = 2;

    private static final Map<String, Object> resourceProperties = new HashMap<>();

    @Rule
    public final AemContext context = new AemContext();

    static {
        resourceProperties.put(PN_COPYRIGHT, EXPECTED_COPYRIGHT_TEXT);
        resourceProperties.put(PN_LINKS,
                new String[]{"{\"text\":\"site1\",\"targetUrl\":\"http://site1.com\"}",
                        "{\"text\":\"site2\",\"targetUrl\":\"http://site2.com\"}"});
    }

    @Before
    public void setUp() {
        context.addModelsForPackage("com.epam.mentoring.aem.models");
    }

    @Test
    public void shouldBeAdaptedFromResource() {
        Resource resource = context.create().resource("test", resourceProperties);
        FooterModel footerModel = resource.adaptTo(FooterModel.class);

        assertEquals("Copyright text is not equal to the expected one", EXPECTED_COPYRIGHT_TEXT, footerModel.getCopyright());
        assertNotNull("List of Footer Links is null", footerModel.getCopyright());
        assertEquals("Links count is not equal to the expected one", EXPECTED_LINKS_COUNT, footerModel.getLinks().size());
    }
}
