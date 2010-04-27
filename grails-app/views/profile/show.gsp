<meta name="layout" content="main" />

<h3 class="pagetitle">${user.name}'s profile</h3>
<div class="contactdetails">
  <div class="contactpicture">
    <img src="${user.profileImageUrl}"/>
  </div>
</div>

<div class="content">
  <g:form name="profile" method="post" action="update">
    <table cellpadding="0" cellspacing="0">
      <tr>
        <td>
          <img src="${resource(dir: 'img', file: 'twitterbutton.png')}"></img>
          <span class="grey bold">
            <a href="${twitter.linkToProfile(user: user)}">
              ${user.username}
            </a>
          </span>
        </td>
      </tr>
      <g:if test="${user.facebookProfile}">
	      <tr>
	        <td>
	            <img src="${resource(dir: 'img', file: 'fbbutton.png')}"></img>
	            <a href="${user.facebookProfile}">
	              ${user.facebookProfile}
	            </a>
	        </td>
	      </tr>
      </g:if>
      <g:if test="${user.linkedInProfile}">
	      <tr>
	        <td>
	          <img src="${resource(dir: 'img', file: 'linkedinbutton.png')}"></img>
	          <a href="${user.linkedInProfile}">
	            ${user.linkedInProfile}
	          </a>
	        </td>
	      </tr>
	    </g:if>
    </table>
  </g:form>
  
  <h2>${user.name}'s upcoming lunches</h2>
  <g:render template="/templates/upcomingLunches" model="[lunches: upcomingLunches]" />
</div>

<g:javascript src="inputTitle.js"/>
<script type="text/javascript">
  $(document).ready(function() {
     prepareFieldsWithTitle();
  });
</script>