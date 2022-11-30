package com.epam.mentoring.aem.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables =  Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SliderModel {

  @Inject
  private Resource resource;
  
  @Inject
  private String sliderHeaderText;
  
  private Resource sliderPar;
  
  private static final String SLIDER_PAR = "sliderpar";
  private static final String GREETING = "Slider Component, you can add slides here";
  
  @PostConstruct
  public void activate() {
	  sliderPar = resource.getChild(SLIDER_PAR);	  
  }
  
  public List<Resource> getSlides() {
    List<Resource> children = new ArrayList<Resource>();
    
    if (sliderPar!=null){
    	for (Resource child : sliderPar.getChildren()) {
    		children.add(child);
    	}
    }
    
    return children;
  }
  
  
  public String getSliderHeaderText(){
	  if (this.sliderHeaderText == null){
		  this.sliderHeaderText=GREETING;
	  }
	  
	  return this.sliderHeaderText;
  }

}