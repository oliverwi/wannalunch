<div class="upcominglunch">
  <img class="creatorImage" src="${lunch.creator.profileImageUrl}" />
  <img class="locationImage" src="${p.logo(lunch: lunch)}" />
  <span class="grey lunchTopic">
    <a href="${createLink(controller: "lunch", action: "show", id: lunch.id)}" class="grey">${lunch.topic}</a>
  </span>
  <span class="bold lunchCreator">
    <a href="${createLink(controller: "profile", action: "show", id: lunch.creator.username)}">${lunch.creator.name}</a>
  </span>
  <p class="bold lunchDetails">
    <span class="orange">
      <f:date value="${lunch.date}" /> - <g:fieldValue bean="${lunch}" field="time" />
    </span>
    @ <g:fieldValue bean="${lunch}" field="location" />
  </p>
</div>
<hr/>