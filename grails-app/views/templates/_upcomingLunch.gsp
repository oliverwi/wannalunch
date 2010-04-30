<div class="upcominglunch">
  <img class="creatorImage" src="${lunch.creator.profileImageUrl}" />
  <img class="locationImage" src="${p.logo(lunch: lunch)}" />
  <span class="bold lunchCreator">
    <a href="${createLink(controller: "lunch", action: "show", id: lunch.id)}">${lunch.creator.name}'s lunch</a>
  </span>
  <span class="grey lunchTopic">
    ${lunch.topic}
  </span>
  <p class="bold lunchDetails">
    <span class="orange">
      <f:date value="${lunch.date}" /> - <g:fieldValue bean="${lunch}" field="time" />
    </span>
    @ <g:fieldValue bean="${lunch}" field="location" />
  </p>
</div>
<hr/>