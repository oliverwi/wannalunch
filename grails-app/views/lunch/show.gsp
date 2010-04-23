<meta name="layout" content="main" />

<h4>${lunch.creator.name}'s lunch</h4>
<div class="contactdetails">
  <div class="contactpicture">
    <img src=""/>
  </div>
  <div class="contactbuttons">
    <img src="${resource(dir: 'img', file: 'twitterbutton.png')}" class="contactbutton"/>
    <img src="${resource(dir: 'img', file: 'fbbutton.png')}" class="contactbutton"/>
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
  </div>

  <a href="${createLink(action: 'join', params: [id: lunch.id])}" class="bigbluebutton">Lunch!</a>

  <a href="${createLink(action: 'show', params: [id: nextId])}" class="biggreybutton">Next</a>

  <div class="postcommentbox">
    <img src="img/comments1.png"/>
    <g:form action="comment" method="post">
	    <div class="textareawrapper">
	      <textarea name="text"></textarea>
	    </div>
	    <input type="hidden" name="lunch" value="${lunch.id}" />
	    <p><input class="greybutton" type="submit" value="Comment" /></p>
    </g:form>
  </div>

  <g:each var="comment" in="${lunch.comments}">
    <div class="comment">
		   <img src="img/comments2.png"/>
		   <p><span class="bold">${comment.author.name}</span> <span class="small">${fieldValue(bean: comment, field: 'date')} ${fieldValue(bean: comment, field: 'time')}</span></p>
		   ${comment.text}
    </div>
  </g:each>
</div>

<div class="participants grey">
  <h3>Wanna!</h3>
  <g:each var="participant" in="${lunch.participants}">
    <div>
      <img src="img/wanna.png"/><br/>${participant.name}
    </div>
  </g:each>
</div>