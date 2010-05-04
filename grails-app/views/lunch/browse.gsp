<meta name="layout" content="main" />

<div class="mainPageHeader">
	<h3 class="mainPageTitle">Wanna lunch with some cool people like you and<br/>discuss the most interesting topics?<br/>That's the place!</h3>
	
	<div class="helpBox">
		<div class="lightGrey">How to begin?</div>
	  <a href="" class="helpBoxLink">Check out some tips and tricks</a>
	</div>
  
  <div class="clearfix"></div>	
</div>

<a href="${createLink(controller: 'lunch', action: 'upcomingLunches')}" class="clearLink mainPageTab ${params.action == null || params.action == 'upcomingLunches' ? 'selectedTab' : ''}">
  Upcoming lunches
</a>
<a href="${createLink(controller: 'lunch', action: 'freshlyAddedLunches')}" class="clearLink mainPageTab ${params.action == 'freshlyAddedLunches' ? 'selectedTab' : ''}">
  Freshly added lunches
</a>

<div class="paginateButtons paginateButtonsTop">
  <g:paginate next="Next" prev="Prev" controller="lunch" action="index" total="${totalUpcomingLunches}" />
</div>

<div class="mainPageTabLine"></div>

<g:each var="lunch" in="${upcomingLunches}">
  <g:render template="/templates/upcomingLunch" model="[lunch: lunch]" />
</g:each>

<div class="paginateButtons">
  <g:paginate next="Next" prev="Prev" controller="lunch" action="index" total="${totalUpcomingLunches}" />
</div>