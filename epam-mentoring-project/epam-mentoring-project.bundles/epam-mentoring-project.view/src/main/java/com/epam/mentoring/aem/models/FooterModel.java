package com.epam.mentoring.aem.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for page footer component.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FooterModel {

    private static final String PN_LINK_TEXT = "text";
    private static final String PN_LINK_TARGET_URL = "targetUrl";
    private static final String LINK_TARGET_URL_DEFAULT = "#";
    private static final String LINK_TEXT_DEFAULT = "";

    @Inject
    private String copyright;

    @Inject
    @Default(values = {})
    @Named("links")
    private String[] linksJson;

    private List<FooterLink> links;

    @PostConstruct
    public void activate() {
        try {
            this.links = parseLinksFromJson();
        } catch (JSONException e) {
            throw new IllegalStateException("Failed to extract links definition from JSON", e);
        }
    }

    public String getCopyright() {
        return copyright;
    }

    public List<FooterLink> getLinks() {
        return links;
    }

    private List<FooterLink> parseLinksFromJson() throws JSONException {
        List<FooterLink> result = new ArrayList<>();
        for (String linkJson : linksJson) {
            JSONObject jsonObject = new JSONObject(linkJson);
            String linkText = jsonObject.optString(PN_LINK_TEXT, LINK_TEXT_DEFAULT);
            String targetUrl = jsonObject.optString(PN_LINK_TARGET_URL, LINK_TARGET_URL_DEFAULT);
            result.add(new FooterLink(linkText, targetUrl));
        }
        return result;
    }

    public final static class FooterLink {
        private final String text;
        private final String targetUrl;

        public FooterLink(final String text, final String targetUrl) {
            this.text = text;
            this.targetUrl = targetUrl;
        }

        public String getText() {
            return text;
        }

        public String getTargetUrl() {
            return targetUrl;
        }
    }
}


