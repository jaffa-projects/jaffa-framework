<head>
    <title>Log On</title>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action='j_security_check' method="POST">
    <H1>Authenticate User</H1>
    <CENTER>
    <table>
        <tr><td>User ID:</td><td><input type='text' name='j_username' size='20'/></td></tr>
        <tr><td>Password:</td><td><input type='password' name='j_password' size='20'/></td></tr>
    </table>
    <BR/>
    <BR/>
    <table>
    <tr><td><input type='submit' value='Log In'></td><td><input type='button' value='Cancel' onClick='javascript:open("/index.html")'></td></tr>
    </table>
    </CENTER>
</form>
</body>

