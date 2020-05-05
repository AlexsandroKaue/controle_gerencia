package br.com.cogerh.template.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="percentCvt")
public class PercentConverter implements Converter
{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
			
		if(value == null || value.isEmpty())
			return null;
		
		value = value.split(" ")[0];
		value = value.replace(',', '.');
		
		return value;
		
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		String formattedValue;
		
		if(value == null)
			return null;
		
		formattedValue = value.toString();
		formattedValue = formattedValue.replace('.', ',');
		formattedValue = formattedValue.concat(" %");
		return formattedValue;
	}

}
