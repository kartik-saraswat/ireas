<#-- @ftlvariable name="" type="projectireas.views.PersonView" -->

        <!-- calls getPerson().getFullName() and sanitizes it -->
        <h1>Hello, ${person.id?html}!</h1>
        <h1>Hello, ${person.fullName?html}!</h1>
         <h1>Hello, ${person.jobTitle?html}!</h1>
