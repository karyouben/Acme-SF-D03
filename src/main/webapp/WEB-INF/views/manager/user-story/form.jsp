<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	

	<acme:input-textbox code="manager.userstory.form.label.title" path="title"/>
	<acme:input-textbox code="manager.userstory.form.label.description" path="description"/>
	<acme:input-integer code="manager.userstory.form.label.cost" path="cost"/>
	<acme:input-textbox code="manager.userstory.form.label.acceptanceCriteria" path="acceptanceCriteria"/>
	<acme:input-url code="manager.userstory.form.label.link" path="link"/>
	<acme:input-select code="manager.userstory.form.label.priority" path="priority" choices="${priorityOptions}"/>	
		
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="manager.userstory.form.button.update" action="/manager/user-story/update"/>
			<acme:submit code="manager.userstory.form.button.delete" action="/manager/user-story/delete"/>
			<acme:button code="manager.userstory.form.button.publish" action="/manager/user-story/publish?id=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.userstory.form.button.create" action="/manager/user-story/create"/>
		</jstl:when>		
	</jstl:choose>
			
</acme:form>
