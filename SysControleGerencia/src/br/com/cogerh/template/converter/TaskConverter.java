package br.com.cogerh.template.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.cogerh.template.model.Task;

@FacesConverter("taskCvt")
public class TaskConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		if (arg2 == null) {
			return null;
		}
		
		
		
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		
		if (arg2 == null) {
		   return null;
		}
		
		Task tarefa = (Task)arg2;
		return tarefa.getNome();
	}

}
