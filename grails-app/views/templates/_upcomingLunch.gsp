<div class="upcominglunch">
  <img src="${lunch.creator.profileImageUrl}" />
  <p class="bold">
    <a href="${createLink(controller: "lunch", action: "show", id: lunch.id)}">${lunch.topic}</a>
  </p>
  <p class="bold small">
    <g:fieldValue bean="${lunch}" field="date" />
    <g:fieldValue bean="${lunch}" field="time" />
    @ <g:fieldValue bean="${lunch}" field="location" />
  </p>
  <p class="small">
    with
    <a class="blue" href="${createLink(controller: "profile", action: "show", id: lunch.creator.username)}">
      ${lunch.creator.name}
    </a>
  </p>
</div>