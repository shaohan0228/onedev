package io.onedev.server.web.page.project.setting.build;

import java.io.Serializable;

import org.apache.wicket.feedback.FencedFeedbackPanel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import io.onedev.server.OneDev;
import io.onedev.server.entitymanager.ProjectManager;
import io.onedev.server.web.editable.PropertyContext;

@SuppressWarnings("serial")
public class ProjectBuildPreserveRulesPage extends ProjectBuildSettingPage {

	public ProjectBuildPreserveRulesPage(PageParameters params) {
		super(params);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(PropertyContext.view("inheritedBuildPreservations", getProject().getOwner().getBuildSetting(), "buildPreservations"));

		Form<?> form = new Form<Void>("form") {

			@Override
			protected void onSubmit() {
				super.onSubmit();
				
				OneDev.getInstance(ProjectManager.class).save(getProject());
				getSession().success("Build preserve rules have been saved");
				setResponsePage(ProjectBuildPreserveRulesPage.class, ProjectBuildPreserveRulesPage.paramsOf(getProject()));
			}
			
		};
		
		form.add(new FencedFeedbackPanel("feedback", form));
		
		form.add(PropertyContext.editModel("editor", new AbstractReadOnlyModel<Serializable>() {

			@Override
			public Serializable getObject() {
				return getProject().getBuildSetting();
			}
			
		}, "buildPreservations"));
		
		add(form);
	}

}
