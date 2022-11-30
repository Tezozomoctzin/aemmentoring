package com.epam.mentoring.aem.models;


import javax.inject.Inject;
import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

import com.day.cq.wcm.foundation.Image;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SlideModel {
	
	
	@Inject
	private Resource resource;
	
	@Inject
	private String fileSlideReference;
	
	@Inject
	private String slideText;
	
	private Image image;
	
	private static final String IMAGE_RESOURCE_NAME = "file";
	private static final String IMAGE_SELECTOR = ".img";
	private static final String NONEXISTING_RESOURCE_TYPE = "sling:nonexisting";
	private static final String SLIDER_PAR="sliderpar";
	private static final String FILE_SLIDE_REFERENCE="fileSlideReference";
	private static final String SLIDE_TEXT="slideText";
	private static final String GREETING="Slide text here";
	
	@PostConstruct
	public void activate() {
		try {			
			String resourceName = resource.getName();
			
			Resource parentResource = resource.getParent();
			
			if (!parentResource.getPath().endsWith(SLIDER_PAR)){
				this.resource = parentResource.getChild(SLIDER_PAR).getChild(resourceName);
				this.fileSlideReference = resource.getValueMap().get(FILE_SLIDE_REFERENCE, String.class);
				this.slideText = resource.getValueMap().get(SLIDE_TEXT, String.class);
			}

			if (this.slideText == null){
				this.slideText = GREETING;
			}
			
			this.image = new Image(resource, IMAGE_RESOURCE_NAME);
			this.image.setSelector(IMAGE_SELECTOR);

		} catch (Exception e){
			throw new IllegalArgumentException("Failed to initialize slide component", e);
		}
	}

	public Image getImage() {
		return this.image;
	}
	
	public boolean hasDAMImage() {
		return this.fileSlideReference != null;
	}
	
	public boolean hasUploadedImage() {
		return this.image != null && !this.image.getResourceType().equals(NONEXISTING_RESOURCE_TYPE);
	}
	
	public String getFileSlideReference(){
		return this.fileSlideReference;
	}
	
	public String getSlideText(){
		return this.slideText;
	}

}