package br.com.cogerh.template.controller.listener;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.cogerh.template.controller.UsuarioWeb;

public class AutorizacaoListener implements PhaseListener
{
	private static final long serialVersionUID = 3943818313153567310L;

	@Override
	public void afterPhase(PhaseEvent event) 
	{
		FacesContext ctx = event.getFacesContext();

		String pagina = ctx.getViewRoot().getViewId();

		if ("/login.xhtml".equals(pagina))
			return;

		UsuarioWeb usuarioWeb = ctx.getApplication().evaluateExpressionGet(ctx, "#{usuarioWeb}", UsuarioWeb.class);

		if (!usuarioWeb.isLogado())
		{
			NavigationHandler hand = ctx.getApplication().getNavigationHandler();
			hand.handleNavigation(ctx, null, "/login?faces-redirect=true");

			ctx.renderResponse();
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {}

	@Override
	public PhaseId getPhaseId() 
	{
		return PhaseId.RESTORE_VIEW;
		
	}
}
