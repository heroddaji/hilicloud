<#-- @ftlvariable name="" type="com.example.views.PersonView" -->
<html>
    <body>
        <!-- calls getPerson().getName() and sanitizes it -->
        <h1>Hello, ${user.username?html}!</h1>
        <h1>Hello, ${user.role?html}!</h1>
        <h1>Hello, ${user.password?html}!</h1>
    </body>
</html>