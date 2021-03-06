<meta name="layout" content="main" />

<meta property="og:title" content="wannalunch with ${fieldValue(bean: lunch, field: 'creator.name')}? ${fieldValue(bean: lunch, field: 'topic')}"/>
<meta property="og:site_name" content="wannalunch.com"/>

<g:javascript>
  $(document).ready(function() {
    $('#delete').click(function() {
      return confirm('Do you really want to delete this lunch?');
    });
  });
</g:javascript>

<h4>${lunch.creator.name}'s lunch</h4>
<div class="contactdetails">
  <div class="contactpicture">
    <img src="${lunch.creator.profileImageUrl}"/>
  </div>
  <div class="contactbuttons">
    <g:if test="${lunch.creator.twitterAccount}">
      <a href="${twitter.linkToProfile(user: lunch.creator)}" target="_blank" class="clearLink">
        <img src="${resource(dir: 'img', file: 'twitterbutton.png')}" class="contactbutton"/>
      </a>
    </g:if>

    <g:if test="${lunch.creator.facebookAccount}">
      <a href="${facebook.linkToProfile(user: lunch.creator)}" target="_blank" class="clearLink">
        <img src="${resource(dir: 'img', file: 'fbbutton.png')}" class="contactbutton"/>
      </a>
    </g:if>

    <g:if test="${lunch.creator.linkedInProfile}">
      <a href="${lunch.creator.linkedInProfile}" target="_blank" class="clearLink">
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

  <p class="grey bold author">
    <a href="${createLink(controller: "profile", action: "show", id: lunch.creator.username)}">
      ${lunch.creator.name}
    </a>
  </p>

  <fb:like layout="standard" show_faces="false" action="recommend" width="400"/>

  <div class="eventdetails bold">
    <p>On       <f:date value="${lunch.date}" /> at ${fieldValue(bean: lunch, field: 'time')}</p>
    <p>
      @ ${lunch.location}
      <a class="contentlink" href="${createLink(controller: "about", action: "partners")}">See special offers</a>
    </p>
    <p class="payment">$ ${lunch.paymentOption.text}</p>
  </div>

  <g:if test="${showDeleteButton}">
    <a href="${createLink(action: 'delete', params: [id: lunch.id])}" id="delete" class="bigbluebutton mainbutton">Delete lunch</a>
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
		   <div class="commentBody">
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
		   <div class="clearfix"></div>
    </div>
  </g:each>
</div>

<g:if test="${lunch.participants.size() > 0}">
	<div class="participants grey">
	  <h3>Accepted!</h3>
	  <g:each var="participant" in="${lunch.participants}">
	    <div>
	      <a href="${createLink(controller: "profile", action: "show", id: participant.username)}">
	        <div class="participantImageWrapper">
	          <img src="${participant.profileImageUrl}"/>
	          <img class="participantImageTick" src="${resource(dir: 'img', file: 'tick.png')}" />
	        </div>
	        <span class="small">
	          ${participant.name}
	        </span>
	      </a>
	    </div>
	  </g:each>
	</div>
</g:if>

<g:if test="${lunch.applicants.size() > 0}">
	<div class="applicants grey">
	  <h3>Wanna!</h3>
	  <g:each var="applicant" in="${lunch.applicants}">
	    <div>
        <a href="${createLink(controller: "profile", action: "show", id: applicant.username)}">
          <img src="${applicant.profileImageUrl}"/><br/><span class="small">${applicant.name}</span>
        </a>
        <g:if test="${canAcceptApplicants}">
          <a href="${createLink(controller: "lunch", action: "accept", id: lunch.id, params: [username: applicant.username])}">
            Confirm!
          </a>
        </g:if>
	    </div>
	  </g:each>
	</div>
</g:if>