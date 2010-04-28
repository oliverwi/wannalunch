<meta name="layout" content="main" />

<h4>${lunch.creator.name}'s lunch</h4>
<div class="contactdetails">
  <div class="contactpicture">
    <img src="${lunch.creator.profileImageUrl}"/>
  </div>
  <div class="contactbuttons">
    <a href="${twitter.linkToProfile(user: lunch.creator)}" class="clearLink">
      <img src="${resource(dir: 'img', file: 'twitterbutton.png')}" class="contactbutton"/>
    </a>

    <g:if test="${lunch.creator.facebookProfile}">
      <a href="${lunch.creator.facebookProfile}" class="clearLink">
        <img src="${resource(dir: 'img', file: 'fbbutton.png')}" class="contactbutton"/>
      </a>
    </g:if>

    <g:if test="${lunch.creator.linkedInProfile}">
      <a href="${lunch.creator.linkedInProfile}" class="clearLink">
        <img src="${resource(dir: 'img', file: 'linkedinbutton.png')}" class="contactbutton"/>
      </a>
    </g:if>
  </div>
</div>

<div class="content">
  <h2>${lunch.topic}</h2>
  <p class="grey">
    ${lunch.description}
  </p>

  <p class="grey bold author">${lunch.creator.name}</p>

  <div class="eventdetails bold">
    <p>On ${fieldValue(bean: lunch, field: 'date')} at ${fieldValue(bean: lunch, field: 'time')}</p>
    @ ${lunch.location}

    <a class="contentlink" href="">See special offers</a>
    <p class="payment">$ ${lunch.paymentOption.text}</p>
  </div>

  <g:if test="${showDeleteButton}">
    <a href="${createLink(action: 'delete', params: [id: lunch.id])}" class="bigbluebutton mainbutton">Delete lunch</a>
  </g:if>

  <g:if test="${showNotGoingButton}">
    <a href="${createLink(action: 'leave', params: [id: lunch.id])}" class="bigbluebutton mainbutton">I'm not going</a>
  </g:if>

  <g:if test="${showLunchButton}">
    <u:isLoggedIn>
      <a href="${createLink(action: 'apply', params: [id: lunch.id])}" class="bigbluebutton mainbutton">Lunch!</a>
    </u:isLoggedIn>
    <u:isNotLoggedIn>
      <a href="#info" rel="facebox" class="bigbluebutton mainbutton">Lunch!</a>
    </u:isNotLoggedIn>
  </g:if>

  <a href="${createLink(action: 'previous', params: [id: lunch.id])}" class="biggreybutton navbutton">Previous</a>
  <a href="${createLink(action: 'next', params: [id: lunch.id])}" class="biggreybutton navbutton">Next</a>

  <div class="postcommentbox">
    <u:isLoggedIn>
		   <img src="${u.userInfo(field: 'profileImageUrl')}"/>
		   <g:form action="comment" method="post">
		    <div class="textareawrapper">
		      <textarea name="text"></textarea>
		    </div>
		    <input type="hidden" name="lunch" value="${lunch.id}" />
		    <p><input class="greybutton" type="submit" value="Comment" /></p>
		   </g:form>
    </u:isLoggedIn>
    <u:isNotLoggedIn>
      You need to log in to add comments
    </u:isNotLoggedIn>
  </div>

  <g:each var="comment" in="${lunch.sortedComments}">
    <div class="comment">
		   <img src="${comment.author.profileImageUrl}"/>
		   <p>
		     <span class="bold">
		       <a href="${createLink(controller: "profile", action: "show", id: comment.author.username)}">
		         ${comment.author.name}
		       </a>
		     </span>
		     <span class="small">
		       ${fieldValue(bean: comment, field: 'date')} ${fieldValue(bean: comment, field: 'time')}
		     </span>
		   </p>
		   ${comment.text}
    </div>
  </g:each>
</div>

<div class="participants grey">
  <h3>Accepted!</h3>
  <g:each var="participant" in="${lunch.participants}">
    <div>
      <img src="${participant.profileImageUrl}"/><br/>${participant.name}
    </div>
  </g:each>
</div>

<div class="applicants grey">
  <h3>Wanna!</h3>
  <g:each var="applicant" in="${lunch.applicants}">
    <div>
      <g:if test="${canAcceptApplicants}">
        <a href="${createLink(controller: "lunch", action: "accept", id: lunch.id, params: [username: applicant.username])}">
          <img src="${applicant.profileImageUrl}"/><br/>${applicant.name}
        </a>
      </g:if>
      <g:else>
        <img src="${applicant.profileImageUrl}"/><br/>${applicant.name}
      </g:else>
    </div>
  </g:each>
</div>