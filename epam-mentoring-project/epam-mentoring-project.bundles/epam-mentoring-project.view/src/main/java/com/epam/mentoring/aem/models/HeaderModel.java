package com.epam.mentoring.aem.models;


import javax.inject.Inject;

import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.foundation.Image;
import com.day.cq.commons.Filter;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeaderModel {
	
	
	@Inject
	private Resource resource;
	
	@Inject
	private PageManager pageManager;

	@Inject
	private String fileLogoReference;
	
	private Image image;
	
	private Iterator<Page> topMenu;
	
	private static final String IMAGE_RESOURCE_NAME = "file";
	private static final String IMAGE_SELECTOR = ".img";
	private static final String NONEXISTING_RESOURCE_TYPE = "sling:nonexisting";
	
	@PostConstruct
	public void activate() {
		try {			
			this.image = new Image(resource, IMAGE_RESOURCE_NAME);
			image.setSelector(IMAGE_SELECTOR);
			
			Page currentPage = pageManager.getContainingPage(resource);
			
			Page rootPage = currentPage.getAbsoluteParent(2);
			this.topMenu = rootPage.listChildren(new Filter<Page>() {
				@Override
				public boolean includes(final Page page) {
					return !page.isHideInNav();
				}
			});
		} catch (Exception e){
			throw new IllegalArgumentException("Failed to initialize header component", e);
		}
	}
	
	public Iterator<Page> getTopMenu() {		
		return this.topMenu;
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public boolean hasDAMImage() {
		return this.fileLogoReference != null;
	}
	
	public boolean hasUploadedImage() {
		return this.image != null && !this.image.getResourceType().equals(NONEXISTING_RESOURCE_TYPE);
	}

}